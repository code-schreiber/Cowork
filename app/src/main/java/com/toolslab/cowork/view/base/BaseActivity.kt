package com.toolslab.cowork.view.base

import android.annotation.SuppressLint
import android.widget.Toast
import com.toolslab.cowork.R
import com.toolslab.cowork.base_mvp.BaseView
import dagger.android.support.DaggerAppCompatActivity

@SuppressLint("Registered") // BaseActivity should not go in the manifest
open class BaseActivity : DaggerAppCompatActivity(), BaseView {

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showNoConnectionError() {
        showMessage(getString(R.string.error_no_connection))
    }

    override fun showDefaultError() {
        showMessage(getString(R.string.error_default))
    }

}
