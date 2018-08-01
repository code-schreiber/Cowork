package com.toolslab.cowork.view.main

import com.toolslab.cowork.base_mvp.BaseView
import com.toolslab.cowork.base_mvp.MvpPresenter

interface CoworkContract {

    interface Presenter : MvpPresenter<View> {
        fun listSpaces(country: String, city: String = "", space: String = "")
    }

    interface View : BaseView {
        fun showMessage(message: String)
    }

}
