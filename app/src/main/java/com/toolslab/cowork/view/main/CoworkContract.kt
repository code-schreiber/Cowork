package com.toolslab.cowork.view.main

import com.toolslab.base_mvp.BaseView
import com.toolslab.base_mvp.MvpPresenter

interface CoworkContract {

    interface Presenter : MvpPresenter<View>

    interface View : BaseView

}
