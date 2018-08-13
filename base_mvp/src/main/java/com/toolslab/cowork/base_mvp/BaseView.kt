package com.toolslab.cowork.base_mvp

interface BaseView : MvpView {
    fun showMessage(message: String)

    fun showNoConnectionError()

    fun showDefaultError()
}
