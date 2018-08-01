package com.toolslab.cowork.view.main

import android.text.Editable
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.android.synthetic.main.activity_cowork.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@Ignore("Ignore until butterknife is fixed")
class CoworkActivityTest {

    private val country = "a country"
    private val city = "a city"
    private val space = "a space"

    private val mockCountryEditable: Editable = mock()
    private val mockCityEditable: Editable = mock()

    private val mockSpaceEditable: Editable = mock()

    private val mockPresenter: CoworkContract.Presenter = mock()

    private val underTest = CoworkActivity()

    @Before
    fun setUp() {
//        whenever(underTest.activity_cowork_country_edit_text.text).thenReturn(mockCountryEditable)
//        whenever(underTest.activity_cowork_city_edit_text.text).thenReturn(mockCityEditable)
//        whenever(underTest.activity_cowork_space_edit_text.text).thenReturn(mockSpaceEditable)
//        whenever(mockCountryEditable.toString()).thenReturn(country)
//        whenever(mockCityEditable.toString()).thenReturn(city)
//        whenever(mockSpaceEditable.toString()).thenReturn(space)

        underTest.presenter = mockPresenter
    }

    @Test
    fun showMessage() {
        val message = "a message"

        underTest.showMessage(message)

        verify(underTest.activity_cowork_text_view).text = message
    }

    @Test
    fun onSearchClicked() {
        underTest.onSearchClicked()

        verify(mockPresenter).listSpaces(country, city, space)
    }

    @Test
    fun onSearchClickedOnlyCountryAndCity() {
        whenever(mockSpaceEditable.toString()).thenReturn("")

        underTest.onSearchClicked()

        verify(mockPresenter).listSpaces(country)
    }

    @Test
    fun onSearchClickedOnlyCountry() {
        whenever(mockCityEditable.toString()).thenReturn("")
        whenever(mockSpaceEditable.toString()).thenReturn("")

        underTest.onSearchClicked()

        verify(mockPresenter).listSpaces(country)
    }

    @Test
    fun onSearchClickedWithNothing() {
        whenever(mockCountryEditable.toString()).thenReturn("")
        whenever(mockCityEditable.toString()).thenReturn("")
        whenever(mockSpaceEditable.toString()).thenReturn("")

        underTest.onSearchClicked()

        verify(mockPresenter).listSpaces("aaa")
    }

}
