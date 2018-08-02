package com.toolslab.base_network

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.toolslab.cowork.base_network.CoworkingMapApi
import com.toolslab.cowork.base_network.CoworkingMapService
import com.toolslab.cowork.base_network.model.*
import com.toolslab.cowork.base_network.model.Map
import io.reactivex.Single
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class CoworkingMapApiTest {

    private val token = "a token"
    private val country = "a country"
    private val city = "a city"
    private val space = "a space"
    private val user = "a user"
    private val password = "a password"
    private val space1 = Space(city, Map("address1", "lat1", "lng1"), "space1", "slug1")
    private val space2 = Space(city, Map("address2", "lat2", "lng2"), "space2", "slug2")
    private val spaces = listOf(space1, space2)

    private val mockCoworkingMapService: CoworkingMapService = mock()

    private val underTest = CoworkingMapApi()

    @Before
    fun setUp() {
        underTest.coworkingMapService = mockCoworkingMapService

        val validBearerToken = underTest.createTokenForRequest(token)
        whenever(mockCoworkingMapService.listSpaces(validBearerToken, country)).thenReturn(Single.just(spaces))
        whenever(mockCoworkingMapService.listSpaces(validBearerToken, country, city)).thenReturn(Single.just(spaces))
        whenever(mockCoworkingMapService.listSpaces(validBearerToken, country, city, space)).thenReturn(Single.just(space1))
    }

    @Test
    fun getJwt() {
        val expectedJwt = Jwt("", "", "", "", 12)
        whenever(mockCoworkingMapService.getJwt(user, password)).thenReturn(Single.just(expectedJwt))

        val jwt = underTest.getJwt(user, password).blockingGet()

        jwt shouldEqual expectedJwt
    }

    @Test
    fun listSpaces() {
        val spacesInCountry = underTest.listSpaces(token, country, "", "").blockingGet()
        val spacesInCity = underTest.listSpaces(token, country, city, "").blockingGet()
        val space = underTest.listSpaces(token, country, city, space).blockingGet()

        spacesInCountry shouldEqual spaces
        spacesInCity shouldEqual spaces
        space shouldEqual listOf(space1)
    }

    @Test
    fun validate() {
        val expectedValidation = Validation("", Data(12))
        whenever(mockCoworkingMapService.validate(token)).thenReturn(Single.just(expectedValidation))

        val validation = underTest.validate(token).blockingGet()

        validation shouldEqual expectedValidation
    }

    @Test
    fun createTokenForRequest() {
        val token = "a token"
        val expectedTokenForRequest = "Bearer a token"

        val tokenForRequest = underTest.createTokenForRequest(token)

        tokenForRequest shouldEqual expectedTokenForRequest
    }

}
