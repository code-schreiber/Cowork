package com.toolslab.cowork.view.main

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import com.toolslab.cowork.R
import com.toolslab.cowork.network.model.Space
import com.toolslab.cowork.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_cowork.*
import javax.inject.Inject

class CoworkActivity : BaseActivity(), CoworkContract.View {

    @Inject
    internal lateinit var presenter: CoworkContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cowork)
        presenter.bind(this)
        activity_cowork_search_button.setOnClickListener { onSearchClicked() }
    }

    override fun onDestroy() {
        presenter.unbind(this)
        super.onDestroy()
    }

    override fun showSpaces(spaces: List<Space>) {
        activity_cowork_text_view.text = "${spaces.size} spaces found:\n $spaces"
    }

    override fun showMessage(message: String?) {
        activity_cowork_text_view.text = message
    }

    @VisibleForTesting
    fun onSearchClicked() {
        val country = activity_cowork_country_edit_text.text.toString()
        val city = activity_cowork_city_edit_text.text.toString()
        val space = activity_cowork_space_edit_text.text.toString()
        presenter.listSpaces(country, city, space)
    }

}
