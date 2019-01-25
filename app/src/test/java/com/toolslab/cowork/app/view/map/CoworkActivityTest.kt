package com.toolslab.cowork.app.view.map

import android.view.View.VISIBLE
import android.widget.LinearLayout
import com.google.android.gms.maps.GoogleMap
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.toolslab.cowork.R
import com.toolslab.cowork.base_repository.model.Space
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class CoworkActivityTest {

    private val minLatitude = 0.0
    private val minLongitude = 1.13
    private val maxLatitude = 1.1
    private val maxLongitude = 2.34
    private val space1 = Space("space1", "snippet1", minLatitude, minLongitude)

    private val mockLinearLayout: LinearLayout = mock()
    private val googleMap: GoogleMap = mock()

    private val underTest = CoworkActivity()

    @Before
    fun setUp() {
        underTest.presenter = mock()
        underTest.mapOperations = mock()
        underTest.uiMessenger = mock()
    }

    @Test
    fun onMapReady() {
        underTest.onMapReady(googleMap)

        verify(underTest.mapOperations).setGoogleMap(googleMap)
        verify(underTest.presenter).onMapReady()
    }

    @Ignore
    @Test
    fun showSearch() {
        whenever(underTest.window).thenReturn(mock())
        whenever(underTest.findViewById<LinearLayout>(R.id.activity_cowork_layout)).thenReturn(mockLinearLayout)

        underTest.showSearch()

        verify(mockLinearLayout).visibility = VISIBLE
    }

    @Test
    fun addMapMarker() {
        underTest.addMapMarker(space1)

        verify(underTest.mapOperations).addMarker(space1.title, space1.snippet, space1.latitude, space1.longitude)
    }

    @Test
    fun moveCamera() {
        underTest.moveCamera(minLatitude, minLongitude, maxLatitude, maxLongitude)

        verify(underTest.mapOperations).moveCamera(minLatitude, minLongitude, maxLatitude, maxLongitude)
    }

    @Test
    fun showInputMissesCountryError() {
        underTest.showInputMissesCountryError()

        verify(underTest.uiMessenger).showMessage(underTest, R.string.error_input_misses_country)
    }

    @Test
    fun showNoPlacesFoundError() {
        val locationDescription = "location description"

        underTest.showNoPlacesFoundError(locationDescription)

        verify(underTest.uiMessenger).showMessage(underTest, R.string.error_no_places_found, locationDescription)
    }

}
