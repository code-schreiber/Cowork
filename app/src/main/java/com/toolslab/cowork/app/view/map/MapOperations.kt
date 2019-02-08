package com.toolslab.cowork.app.view.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.toolslab.cowork.R
import com.toolslab.cowork.app.util.VectorDrawableUtil
import timber.log.Timber
import java.util.*
import javax.inject.Inject

internal class MapOperations @Inject constructor() {

    @Inject
    internal lateinit var vectorDrawableUtil: VectorDrawableUtil

    @Inject
    internal lateinit var context: Context

    /**
     * See: https://developers.google.com/maps/documentation/android-sdk/views#zoom
     * 1: World
     * 5: Landmass/continent
     * 10: City
     * 15: Streets
     * 20: Buildings
     */
    private val enoughZoomToSeeCity = 10

    private lateinit var googleMap: GoogleMap

    private var markers = mutableListOf<Marker>()

    @SuppressLint("MissingPermission")
    internal fun enableMyLocation() {
        // This shows the "locate me" button on the map
        googleMap.isMyLocationEnabled = true
    }

    internal fun setGoogleMap(googleMap: GoogleMap) {
        this.googleMap = googleMap
    }

    internal fun addMarker(title: String, snippet: String, latitude: Double, longitude: Double) {
        markers.add(googleMap.addMarker(MarkerOptions()
                .title(title)
                .snippet(snippet)
                .position(LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(vectorDrawableUtil.resourceAsBitmap(R.drawable.ic_pin)))))
    }

    internal fun removeMarkers() {
        markers.forEach {
            it.remove()
        }
        markers.clear()
    }

    internal fun moveCamera(minLatitude: Double, minLongitude: Double, maxLatitude: Double, maxLongitude: Double) {
        val bounds = LatLngBounds(LatLng(minLatitude, minLongitude), LatLng(maxLatitude, maxLongitude))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0))
    }

    internal fun getCurrentLocation(): Pair<String, String> {
        val position = googleMap.cameraPosition
        val target = position.target
        val zoom = position.zoom
        return getLocation(target.latitude, target.longitude, zoom)
    }

    private fun getLocation(latitude: Double, longitude: Double, zoom: Float): Pair<String, String> {
        val addresses = getAddresses(latitude, longitude)
        return if (addresses.isEmpty()) {
            Pair("", "")
        } else {
            val country = addresses[0].countryName ?: ""
            val city = if (zoom >= enoughZoomToSeeCity) addresses[0].locality ?: "" else ""
            Pair(country, city)
        }
    }

    private fun getAddresses(latitude: Double, longitude: Double): List<Address> {
        return try {
            Geocoder(context, Locale.US).getFromLocation(latitude, longitude, 1)
        } catch (e: Exception) {
            Timber.e(e)
            emptyList()
        }
    }

}
