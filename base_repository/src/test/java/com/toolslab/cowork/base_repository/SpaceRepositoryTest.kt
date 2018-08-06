package com.toolslab.cowork.base_repository

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.toolslab.cowork.base_network.CoworkingMapApi
import com.toolslab.cowork.base_network.model.Map
import com.toolslab.cowork.base_network.model.Space
import com.toolslab.cowork.base_network.storage.Credentials
import com.toolslab.cowork.base_network.storage.CredentialsStorage
import com.toolslab.cowork.base_repository.converter.SpaceModelConverter
import io.reactivex.Single
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class SpaceRepositoryTest {

    private val country = "a country"
    private val city = "a city"
    private val space = "a space"
    private val space1 = Space(city, Map("address1", "lat1", "lng1"), "space1", "slug1")
    private val space2 = Space(city, Map("address2", "lat2", "lng2"), "space2", "slug2")
    private val repositorySpace1 = com.toolslab.cowork.base_repository.model.Space(space1.name)
    private val repositorySpace2 = com.toolslab.cowork.base_repository.model.Space(space2.name)
    private val spaces = listOf(space1, space2)
    private val credentials = Credentials("user", "pw")

    private val mockCredentialsStorage: CredentialsStorage = mock()
    private val mockCoworkingMapApi: CoworkingMapApi = mock()
    private val mockErrorHandler: ErrorHandler = mock()

    private val underTest = SpaceRepository()

    @Before
    fun setUp() {
        underTest.credentialsStorage = mockCredentialsStorage
        underTest.coworkingMapApi = mockCoworkingMapApi
        underTest.errorHandler = mockErrorHandler
        underTest.spaceModelConverter = SpaceModelConverter()
    }

    @Test
    fun listSpaces() {
        whenever(mockCoworkingMapApi.listSpaces(country, city, space)).thenReturn(Single.just(spaces))

        val result = underTest.listSpaces(credentials, country, city, space).blockingGet()

        result[0] shouldEqual repositorySpace1
        result[1] shouldEqual repositorySpace2
        verify(mockCredentialsStorage).saveCredentials(credentials)
    }

    @Test
    fun listSpacesWithError() {
        val exception = Exception("an exception")
        val handledException = Exception("a handled exception")
        whenever(mockCoworkingMapApi.listSpaces(country, city, space)).thenReturn(Single.error(exception))
        whenever(mockErrorHandler.handle<Space>(exception)).thenReturn(Single.error(handledException))

        val testObserver = underTest.listSpaces(credentials, country, city, space).test()
        testObserver.awaitTerminalEvent()

        testObserver.apply {
            valueCount() shouldEqual 0
            errorCount() shouldEqual 1
            errors()[0] shouldEqual handledException
        }

        verify(mockCredentialsStorage).saveCredentials(credentials)
    }

}
