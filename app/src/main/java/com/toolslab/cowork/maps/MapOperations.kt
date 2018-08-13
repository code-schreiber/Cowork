package com.toolslab.cowork.maps

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.toolslab.cowork.R
import javax.inject.Inject

class MapOperations @Inject constructor() {

    private val zoom = 11.5F

    @Inject
    internal lateinit var resourceOperations: ResourceOperations

    internal lateinit var googleMap: GoogleMap

    fun addMarker(title: String, snippet: String, latitude: Double, longitude: Double) {
        googleMap.addMarker(MarkerOptions()
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromBitmap(resourceOperations.resourceAsBitmap(R.drawable.ic_pin)))
                .position(LatLng(latitude, longitude)))
    }

    fun moveCamera(latitude: Double, longitude: Double) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), zoom))

    }

}
