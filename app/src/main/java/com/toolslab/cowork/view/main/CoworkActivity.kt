package com.toolslab.cowork.view.main

import android.os.Bundle
import com.toolslab.cowork.R
import com.toolslab.cowork.view.base.BaseActivity
import javax.inject.Inject

class CoworkActivity : BaseActivity(), CoworkContract.View {

    @Inject
    internal lateinit var presenter: CoworkContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cowork)
        presenter.bind(this)
    }

    override fun onDestroy() {
        presenter.unbind(this)
        super.onDestroy()
    }

}
