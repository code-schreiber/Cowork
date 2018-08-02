package com.toolslab.cowork.view.main

import android.text.Editable
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test

class CoworkActivityTest {

    private val country = "a country"
    private val city = "a city"
    private val space = "a space"

    private val mockCountryEditable: Editable = mock()
    private val mockCityEditable: Editable = mock()
    private val mockSpaceEditable: Editable = mock()

    private val underTest = CoworkActivity()

    @Before
    fun setUp() {
        underTest.countryEditText = mock()
        underTest.cityEditText = mock()
        underTest.spaceEditText = mock()
        underTest.textView = mock()
        underTest.presenter = mock()

        whenever(underTest.countryEditText.text).thenReturn(mockCountryEditable)
        whenever(underTest.cityEditText.text).thenReturn(mockCityEditable)
        whenever(underTest.spaceEditText.text).thenReturn(mockSpaceEditable)
    }

    @Test
    fun showMessage() {
        val message = "a message"

        underTest.showMessage(message)

        verify(underTest.textView).text = message
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
