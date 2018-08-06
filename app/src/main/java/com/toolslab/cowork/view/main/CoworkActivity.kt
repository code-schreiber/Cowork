package com.toolslab.cowork.view.main

import android.os.Bundle
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.toolslab.cowork.R
import com.toolslab.cowork.base_repository.model.Space
import com.toolslab.cowork.maps.MapOperations
import com.toolslab.cowork.view.base.BaseActivity
import javax.inject.Inject

class CoworkActivity : BaseActivity(), CoworkContract.View, OnMapReadyCallback {

    @BindView(R.id.activity_cowork_country_edit_text)
    internal lateinit var countryEditText: EditText

    @BindView(R.id.activity_cowork_city_edit_text)
    internal lateinit var cityEditText: EditText

    @BindView(R.id.activity_cowork_space_edit_text)
    internal lateinit var spaceEditText: EditText

    @Inject
    internal lateinit var mapOperations: MapOperations

    @Inject
    internal lateinit var presenter: CoworkContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cowork)
        ButterKnife.bind(this)
        presenter.bind(this)
    }

    override fun onDestroy() {
        presenter.unbind(this)
        super.onDestroy()
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
        mapOperations.googleMap = googleMap
        presenter.onMapReady()
    }

    override fun addMapMarker(space: Space) {
        mapOperations.addMarker(space.title, space.snippet, space.lat, space.lng)
    }

    override fun moveCamera(space: Space) {
        mapOperations.moveCamera(space.lat, space.lng)
    }

    @OnClick(R.id.activity_cowork_search_button)
    internal fun onSearchClicked() {
        val country = countryEditText.text.toString()
        val city = cityEditText.text.toString()
        val space = spaceEditText.text.toString()
        presenter.listSpaces(country, city, space)
    }

}
