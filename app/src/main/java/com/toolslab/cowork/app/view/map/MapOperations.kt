package com.toolslab.cowork.app.view.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.toolslab.cowork.R
import com.toolslab.cowork.app.util.VectorDrawableUtil
import javax.inject.Inject

internal class MapOperations @Inject constructor() {

    @Inject
    internal lateinit var vectorDrawableUtil: VectorDrawableUtil

    internal lateinit var googleMap: GoogleMap

    internal fun addMarker(title: String, snippet: String, latitude: Double, longitude: Double) {
        googleMap.addMarker(MarkerOptions()
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromBitmap(vectorDrawableUtil.resourceAsBitmap(R.drawable.ic_pin)))
                .position(LatLng(latitude, longitude)))
    }

    internal fun moveCamera(minLatitude: Double, minLongitude: Double, maxLatitude: Double, maxLongitude: Double) {
        val bounds = LatLngBounds(LatLng(minLatitude, minLongitude), LatLng(maxLatitude, maxLongitude))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0))
    }

}
