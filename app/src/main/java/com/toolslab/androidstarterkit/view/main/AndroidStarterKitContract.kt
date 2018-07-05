package com.toolslab.androidstarterkit.view.main

import com.toolslab.androidstarterkit.mvp.BaseView
import com.toolslab.androidstarterkit.mvp.MvpPresenter

interface AndroidStarterKitContract {

    interface Presenter : MvpPresenter<View>

    interface View : BaseView

}
