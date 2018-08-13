package com.toolslab.cowork.view.main

import android.text.Editable
import com.google.android.gms.maps.GoogleMap
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.toolslab.cowork.base_repository.model.Space
import org.junit.Before
import org.junit.Test

class CoworkActivityTest {

    private val country = "a country"
    private val city = "a city"
    private val space = "a space"
    private val latitude = 1.1
    private val longitude = 1.13
    private val space1 = Space("space1", "snippet1", latitude, longitude)

    private val mockCountryEditable: Editable = mock()
    private val mockCityEditable: Editable = mock()
    private val mockSpaceEditable: Editable = mock()
    private val googleMap: GoogleMap = mock()

    private val underTest = CoworkActivity()

    @Before
    fun setUp() {
        underTest.countryEditText = mock()
        underTest.cityEditText = mock()
        underTest.spaceEditText = mock()
        underTest.presenter = mock()
        underTest.mapOperations = mock()

        whenever(underTest.countryEditText.text).thenReturn(mockCountryEditable)
        whenever(underTest.cityEditText.text).thenReturn(mockCityEditable)
        whenever(underTest.spaceEditText.text).thenReturn(mockSpaceEditable)
    }

    @Test
    fun onMapReady() {
        underTest.onMapReady(googleMap)

        verify(underTest.mapOperations).googleMap = googleMap
        verify(underTest.presenter).onMapReady()
    }

    @Test
    fun addMapMarker() {
        underTest.addMapMarker(space1)

        verify(underTest.mapOperations).addMarker(space1.title, space1.snippet, space1.latitude, space1.longitude)
    }

    @Test
    fun moveCamera() {
        underTest.moveCamera(latitude, longitude)

        verify(underTest.mapOperations).moveCamera(latitude, longitude)
    }

    @Test
    fun onSearchClickedWithCountry() {
        whenever(mockCountryEditable.toString()).thenReturn(country)
        whenever(mockCityEditable.toString()).thenReturn("")
        whenever(mockSpaceEditable.toString()).thenReturn("")

        underTest.onSearchClicked()

        verify(underTest.presenter).listSpaces(country)
    }

    @Test
    fun onSearchClickedWithCountryAndCity() {
        whenever(mockCountryEditable.toString()).thenReturn(country)
        whenever(mockCityEditable.toString()).thenReturn(city)
        whenever(mockSpaceEditable.toString()).thenReturn("")

        underTest.onSearchClicked()

        verify(underTest.presenter).listSpaces(country, city)
    }

    @Test
    fun onSearchClickedWithCountryCityAndSpace() {
        whenever(mockCountryEditable.toString()).thenReturn(country)
        whenever(mockCityEditable.toString()).thenReturn(city)
        whenever(mockSpaceEditable.toString()).thenReturn(space)

        underTest.onSearchClicked()

        verify(underTest.presenter).listSpaces(country, city, space)
    }

}
