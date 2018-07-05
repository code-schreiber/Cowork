package com.toolslab.androidstarterkit.view.main

import android.os.Bundle
import com.toolslab.androidstarterkit.R
import com.toolslab.androidstarterkit.view.base.BaseActivity
import javax.inject.Inject

class AndroidStarterKitActivity : BaseActivity(), AndroidStarterKitContract.View {

    @Inject
    internal lateinit var presenter: AndroidStarterKitContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_starter_kit)
        presenter.bind(this)
    }

    override fun onDestroy() {
        presenter.unbind(this)
        super.onDestroy()
    }

}
