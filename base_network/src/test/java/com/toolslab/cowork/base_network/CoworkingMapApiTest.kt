package com.toolslab.cowork.base_network

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.toolslab.cowork.base_network.model.Map
import com.toolslab.cowork.base_network.model.Space
import io.reactivex.Single
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class CoworkingMapApiTest {

    private val country = "a country"
    private val city = "a city"
    private val space = "a space"
    private val space1 = Space(city, Map("address1", "lat1", "lng1"), "space1", "slug1")
    private val space2 = Space(city, Map("address2", "lat2", "lng2"), "space2", "slug2")
    private val spaces = listOf(space1, space2)

    private val underTest = CoworkingMapApi()

    @Before
    fun setUp() {
        underTest.coworkingMapService = mock()
    }

    @Test
    fun listSpaces() {
        whenever(underTest.coworkingMapService.listSpaces(country)).thenReturn(Single.just(spaces))
        whenever(underTest.coworkingMapService.listSpaces(country, city)).thenReturn(Single.just(spaces))
        whenever(underTest.coworkingMapService.listSpaces(country, city, space)).thenReturn(Single.just(space1))

        val spacesInCountry = underTest.listSpaces(country, "", "").blockingGet()
        val spacesInCity = underTest.listSpaces(country, city, "").blockingGet()
        val space = underTest.listSpaces(country, city, space).blockingGet()

        spacesInCountry shouldEqual spaces
        spacesInCity shouldEqual spaces
        space shouldEqual listOf(space1)
    }

}
