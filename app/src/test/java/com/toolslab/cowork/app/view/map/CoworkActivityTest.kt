package com.toolslab.cowork.app.view.map

import android.view.View.VISIBLE
import android.widget.LinearLayout
import com.google.android.gms.maps.GoogleMap
import com.nhaarman.mockito_kotlin.*
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
    private val mockGoogleMap: GoogleMap = mock()

    private val underTest = CoworkActivity()

    @Before
    fun setUp() {
        underTest.presenter = mock()
        underTest.mapOperations = mock()
        underTest.permissionHelper = mock()
        underTest.uiMessenger = mock()
    }

    @Test
    fun onRequestPermissionsResultGranted() {
        val requestCode = 123
        val permissions = emptyArray<String>()
        val grantResults = intArrayOf()
        whenever(underTest.permissionHelper.isLocationPermissionEnabled(requestCode, permissions, grantResults)).thenReturn(true)

        underTest.onRequestPermissionsResult(requestCode, permissions, grantResults)

        verify(underTest.mapOperations).enableMyLocation()
        verify(underTest.uiMessenger).showMessage(underTest, R.string.message_how_to_zoom_in)
    }

    @Test
    fun onRequestPermissionsResult() {
        whenever(underTest.permissionHelper.isLocationPermissionEnabled(any(), any(), any())).thenReturn(false)

        underTest.onRequestPermissionsResult(-1, emptyArray(), intArrayOf())

        verifyZeroInteractions(underTest.mapOperations)
        verify(underTest.uiMessenger, never()).showMessage(underTest, R.string.message_how_to_zoom_in)
    }

    @Test
    fun onMapReady() {
        whenever(underTest.permissionHelper.isLocationPermissionEnabled(underTest)).thenReturn(false)

        underTest.onMapReady(mockGoogleMap)

        verify(mockGoogleMap).setOnCameraIdleListener(underTest)
        verify(underTest.permissionHelper).requestPermission(underTest)
        verify(underTest.mapOperations, never()).enableMyLocation()
        verify(underTest.mapOperations).setGoogleMap(mockGoogleMap)
        verify(underTest.presenter).onMapReady()
    }

    @Test
    fun onMapReadyWithLocationPermission() {
        whenever(underTest.permissionHelper.isLocationPermissionEnabled(underTest)).thenReturn(true)

        underTest.onMapReady(mockGoogleMap)

        verify(mockGoogleMap).setOnCameraIdleListener(underTest)
        verify(underTest.permissionHelper, never()).requestPermission(underTest)
        verify(underTest.mapOperations).enableMyLocation()
        verify(underTest.mapOperations).setGoogleMap(mockGoogleMap)
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
