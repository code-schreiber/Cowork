package com.toolslab.cowork.view.main

import com.toolslab.cowork.base_mvp.BaseView
import com.toolslab.cowork.base_mvp.MvpPresenter
import com.toolslab.cowork.base_repository.model.Space

interface CoworkContract {

    interface Presenter : MvpPresenter<View> {
        fun listSpaces(country: String, city: String = "", space: String = "")

        fun onMapReady()
    }

    interface View : BaseView {
        fun getMapAsync()

        fun addMapMarker(space: Space)

        fun moveCamera(space: Space)
    }

}
