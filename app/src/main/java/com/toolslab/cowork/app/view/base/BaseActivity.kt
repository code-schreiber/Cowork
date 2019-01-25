package com.toolslab.cowork.app.view.base

import android.annotation.SuppressLint
import android.support.annotation.StringRes
import com.toolslab.cowork.R
import com.toolslab.cowork.base_mvp.BaseView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

@SuppressLint("Registered") // BaseActivity should not go in the manifest
open class BaseActivity : DaggerAppCompatActivity(), BaseView {

    @Inject
    internal lateinit var uiMessenger: UiMessenger

    override fun showNoConnectionError() {
        showMessage(R.string.error_no_connection)
    }

    override fun showDefaultError() {
        showMessage(R.string.error_default)
    }

    internal fun showMessage(@StringRes id: Int) {
        uiMessenger.showMessage(this, id)
    }

    internal fun showMessage(@StringRes id: Int, formatArgs: String) {
        uiMessenger.showMessage(this, id, formatArgs)
    }

}
