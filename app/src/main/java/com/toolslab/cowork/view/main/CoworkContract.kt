package com.toolslab.cowork.view.main

import com.toolslab.cowork.base_mvp.BaseView
import com.toolslab.cowork.base_mvp.MvpPresenter
import com.toolslab.cowork.base_repository.model.Space

internal interface CoworkContract {

    interface Presenter : MvpPresenter<View> {
        fun searchSpaces(country: String, city: String = "", space: String = "")

        fun onMapReady()
    }

    interface View : BaseView {
        fun getMapAsync()

        fun showSearch()

        fun addMapMarker(space: Space)

        fun moveCamera(minLatitude: Double, minLongitude: Double, maxLatitude: Double, maxLongitude: Double)

        fun showInputMissesCountryError()

        fun showNoPlacesFoundError()
    }

}
