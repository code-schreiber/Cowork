package com.toolslab.cowork.app.view.base

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import javax.inject.Inject

internal class UiMessenger @Inject constructor() {

    internal fun showMessage(context: Context, @StringRes id: Int) {
        Toast.makeText(context, id, Toast.LENGTH_SHORT).show()
    }

    internal fun showMessage(context: Context, @StringRes id: Int, formatArgs: String) {
        Toast.makeText(context, context.getString(id, formatArgs), Toast.LENGTH_SHORT).show()
    }

}
