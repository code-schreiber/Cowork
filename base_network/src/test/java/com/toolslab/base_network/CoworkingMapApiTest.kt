package com.toolslab.base_network

import com.nhaarman.mockito_kotlin.*
import com.toolslab.cowork.base_network.CoworkingMapApi
import com.toolslab.cowork.base_network.CoworkingMapService
import com.toolslab.cowork.base_network.model.Jwt
import com.toolslab.cowork.base_network.model.Map
import com.toolslab.cowork.base_network.model.Space
import io.reactivex.Single
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class CoworkingMapApiTest {

    private val validToken = "a valid token"
    private val invalidToken = "an invalid token"
    private val country = "a country"
    private val city = "a city"
    private val space = "a space"
    private val user = "a user"
    private val password = "a password"
    private val space1 = Space(city, Map("address1", "lat1", "lng1"), "space1", "slug1")
    private val space2 = Space(city, Map("address2", "lat2", "lng2"), "space2", "slug2")
    private val spaces = listOf(space1, space2)
    private val exception = Exception("an exception")
    private val handledException = Exception("a handled exception")
    private val jwt = Jwt(validToken, "", "", "", 0)

    private val mockCoworkingMapService: CoworkingMapService = mock()
    private val mockHttpErrorHandler: HttpErrorHandler = mock()

    private val underTest = CoworkingMapApi()

    @Before
    fun setUp() {
        underTest.coworkingMapService = mockCoworkingMapService
        underTest.httpErrorHandler = mockHttpErrorHandler
//        underTest.credentials = Credentials(user, password, validToken)

        val validBearerToken = underTest.createTokenForRequest(validToken)
        val invalidBearerToken = underTest.createTokenForRequest(invalidToken)
        val spacesSingle = Single.just(spaces)
        val errorSingle: Single<List<Space>> = Single.error(exception)
        whenever(mockCoworkingMapService.listSpaces(validBearerToken, country)).thenReturn(spacesSingle)
        whenever(mockCoworkingMapService.listSpaces(validBearerToken, country, city)).thenReturn(spacesSingle)
        whenever(mockCoworkingMapService.listSpaces(validBearerToken, country, city, space)).thenReturn(Single.just(space1))
        whenever(mockCoworkingMapService.listSpaces(eq(invalidBearerToken), any())).thenReturn(errorSingle)
        whenever(mockCoworkingMapService.listSpaces(eq(invalidBearerToken), any(), any())).thenReturn(errorSingle)
        whenever(mockCoworkingMapService.listSpaces(eq(invalidBearerToken), any(), any(), any())).thenReturn(Single.just(space1))
    }

    @Test
    fun listSpaces() {
        val spacesInCountry = underTest.listSpaces("", "", country).blockingGet()
        val spacesInCity = underTest.listSpaces(city, "", country).blockingGet()
        val spaces = underTest.listSpaces(city, space, country).blockingGet()

        spacesInCountry shouldEqual spaces
        spacesInCity shouldEqual spaces
        spaces shouldEqual spaces
    }

    @Test
    fun listSpacesInCountryWithInvalidToken() {
        underTest.credentials.token = invalidToken
        whenever(mockHttpErrorHandler.handle<Space>(exception)).thenReturn(Single.error(handledException))
        whenever(mockHttpErrorHandler.isTokenExpired(exception)).thenReturn(false)

        val testObserverForCountry = underTest.listSpaces("", "", country).test()
        val testObserverForCity = underTest.listSpaces(city, "", country).test()
        val testObserverForSpace = underTest.listSpaces(city, space, country).test()
        testObserverForCountry.awaitTerminalEvent()
        testObserverForCity.awaitTerminalEvent()
        testObserverForSpace.awaitTerminalEvent()

        testObserverForCountry.apply {
            valueCount() shouldEqual 0
            errorCount() shouldEqual 1
            errors()[0] shouldEqual handledException
        }
        testObserverForCity.apply {
            valueCount() shouldEqual 0
            errorCount() shouldEqual 1
            errors()[0] shouldEqual handledException
        }
        testObserverForSpace.apply {
            valueCount() shouldEqual 0
            errorCount() shouldEqual 1
            errors()[0] shouldEqual handledException
        }
    }

    @Test
    fun listSpacesAuthenticatingFirst() {
        underTest.credentials.token = ""
        whenever(mockCoworkingMapService.getJwt(user, password)).thenReturn(Single.just(jwt))

        val result = underTest.listSpaces(city, space, country).blockingGet()

        result shouldEqual spaces
        underTest.credentials.token shouldEqual validToken
    }

    @Test
    fun listSpacesAuthenticatingFirstWithError() {
        underTest.credentials.token = ""
        whenever(mockCoworkingMapService.getJwt(user, password)).thenReturn(Single.error(exception))
        whenever(mockHttpErrorHandler.handle<Space>(exception)).thenReturn(Single.error(handledException))

        val testObserver = underTest.listSpaces(city, space, country).test()
        testObserver.awaitTerminalEvent()

        testObserver.apply {
            valueCount() shouldEqual 0
            errorCount() shouldEqual 1
            errors()[0] shouldEqual handledException
        }

        underTest.credentials.token shouldEqual ""
        verify(mockCoworkingMapService, never()).listSpaces(any(), any())
        verify(mockCoworkingMapService, never()).listSpaces(any(), any(), any())
        verify(mockCoworkingMapService, never()).listSpaces(any(), any(), any(), any())
    }

    @Test
    fun listSpacesWithExpiredToken() {
        underTest.credentials.token = invalidToken
        whenever(mockHttpErrorHandler.isTokenExpired(exception)).thenReturn(true)
        whenever(mockCoworkingMapService.getJwt(underTest.credentials.user, underTest.credentials.password)).thenReturn(Single.just(jwt))

        val result = underTest.listSpaces(city, space, country).blockingGet()

        result shouldEqual spaces
        underTest.credentials.token shouldEqual validToken
    }

    @Test
    fun createTokenForRequest() {
        val token = "a token"
        val expectedTokenForRequest = "Bearer a token"

        val tokenForRequest = underTest.createTokenForRequest(token)

        tokenForRequest shouldEqual expectedTokenForRequest
    }
}
