package com.toolslab.cowork.view.main

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.toolslab.cowork.R
import com.toolslab.cowork.view.base.BaseActivity
import javax.inject.Inject

class CoworkActivity : BaseActivity(), CoworkContract.View {

    @BindView(R.id.activity_cowork_country_edit_text)
    internal lateinit var countryEditText: EditText

    @BindView(R.id.activity_cowork_city_edit_text)
    internal lateinit var cityEditText: EditText

    @BindView(R.id.activity_cowork_space_edit_text)
    internal lateinit var spaceEditText: EditText

    @BindView(R.id.activity_cowork_text_view)
    internal lateinit var textView: TextView

    @Inject
    internal lateinit var presenter: CoworkContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cowork)
        ButterKnife.bind(this)
        presenter.bind(this)
    }

    override fun onDestroy() {
        presenter.unbind(this)
        super.onDestroy()
    }

    override fun showMessage(message: String) {
        textView.text = message
    }

    @OnClick(R.id.activity_cowork_search_button)
    internal fun onSearchClicked() {
        val country = countryEditText.text.toString()
        val city = cityEditText.text.toString()
        val space = spaceEditText.text.toString()
        presenter.listSpaces(country, city, space)
    }

}
