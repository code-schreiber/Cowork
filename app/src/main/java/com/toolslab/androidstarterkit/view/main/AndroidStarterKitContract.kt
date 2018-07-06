package com.toolslab.androidstarterkit.view.main

import com.toolslab.base_mvp.BaseView
import com.toolslab.base_mvp.MvpPresenter

interface AndroidStarterKitContract {

    interface Presenter : MvpPresenter<View>

    interface View : BaseView

}
