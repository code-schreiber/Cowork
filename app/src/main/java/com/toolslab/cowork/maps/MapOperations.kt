package com.toolslab.cowork.maps

import android.support.annotation.VisibleForTesting
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class MapOperations @Inject constructor() {

    @VisibleForTesting
    internal lateinit var googleMap: GoogleMap

    fun addMarker(title: String, snippet: String, lat: Double, lng: Double) {
        googleMap.addMarker(MarkerOptions()
                .title(title)
                .snippet(snippet)
                .position(LatLng(lat, lng)))
    }

    fun moveCamera(lat: Double, lng: Double) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat, lng)))
    }

}
