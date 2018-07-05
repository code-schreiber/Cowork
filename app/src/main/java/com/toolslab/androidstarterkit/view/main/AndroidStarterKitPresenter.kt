package com.toolslab.androidstarterkit.view.main

import com.toolslab.androidstarterkit.mvp.BasePresenter
import javax.inject.Inject

class AndroidStarterKitPresenter @Inject constructor() :
        BasePresenter<AndroidStarterKitContract.View>(), AndroidStarterKitContract.Presenter {

    override fun onBound(view: AndroidStarterKitContract.View) {

    }

    override fun onUnbound(view: AndroidStarterKitContract.View) {

    }

}
