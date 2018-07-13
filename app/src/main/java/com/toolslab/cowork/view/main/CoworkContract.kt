package com.toolslab.cowork.view.main

import com.toolslab.base_mvp.BaseView
import com.toolslab.base_mvp.MvpPresenter
import com.toolslab.cowork.network.model.Space

interface CoworkContract {

    interface Presenter : MvpPresenter<View> {
        fun listSpaces(country: String, city: String = "", space: String = "")
    }

    interface View : BaseView {
        fun showSpaces(spaces: List<Space>)
        fun showMessage(message: String?)
    }

}
