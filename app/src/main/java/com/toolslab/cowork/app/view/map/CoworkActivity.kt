package com.toolslab.cowork.app.view.map

import android.os.Bundle
import android.view.View.VISIBLE
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.toolslab.cowork.R
import com.toolslab.cowork.app.view.PermissionHelper
import com.toolslab.cowork.app.view.base.BaseActivity
import com.toolslab.cowork.base_repository.model.Space
import kotlinx.android.synthetic.main.activity_cowork.*
import javax.inject.Inject

class CoworkActivity : BaseActivity(),
        CoworkContract.View,
        OnMapReadyCallback,
        GoogleMap.OnCameraIdleListener {

    @Inject
    internal lateinit var mapOperations: MapOperations

    @Inject
    internal lateinit var permissionHelper: PermissionHelper

    @Inject
    internal lateinit var presenter: CoworkContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cowork)
        presenter.bind(this)
    }

    override fun onDestroy() {
        presenter.unbind(this)
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (permissionHelper.isLocationPermissionEnabled(requestCode, permissions, grantResults)) {
            mapOperations.enableMyLocation()
            showMessage(R.string.message_how_to_zoom_in)
        }
    }

    override fun getMapAsync() {
        // Get notified when the map is ready to be used via onMapReady().
        (supportFragmentManager.findFragmentById(R.id.activity_cowork_map_fragment) as SupportMapFragment).getMapAsync(this)
    }

    /**
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setOnCameraIdleListener(this)
        mapOperations.setGoogleMap(googleMap)
        if (permissionHelper.isLocationPermissionEnabled(this)) {
            mapOperations.enableMyLocation()
        } else {
            permissionHelper.requestPermission(this)
        }
        presenter.onMapReady()
    }

    override fun onCameraIdle() {
        val (country, city) = mapOperations.getCurrentLocation()
        presenter.onUserMapGestureStopped(country, city)
    }

    override fun showSearch() {
        activity_cowork_layout.visibility = VISIBLE
    }

    override fun addMapMarker(space: Space) {
        mapOperations.addMarker(space.title, space.snippet, space.latitude, space.longitude)
    }

    override fun removeMarkers() {
        mapOperations.removeMarkers()
    }

    override fun moveCamera(minLatitude: Double, minLongitude: Double, maxLatitude: Double, maxLongitude: Double) {
        mapOperations.moveCamera(minLatitude, minLongitude, maxLatitude, maxLongitude)
    }

    override fun showInputMissesCountryError() {
        showMessage(R.string.error_input_misses_country)
    }

    override fun showNoPlacesFoundError(locationDescription: String) {
        showMessage(R.string.error_no_places_found, locationDescription)
    }

}
