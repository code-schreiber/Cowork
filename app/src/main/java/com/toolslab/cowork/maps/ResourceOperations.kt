package com.toolslab.cowork.maps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import javax.inject.Inject

// TODO rename class and find a better package
internal class ResourceOperations @Inject constructor() {

    @Inject
    internal lateinit var context: Context

    internal fun color(@ColorRes id: Int) = ContextCompat.getColor(context, id)

    internal fun resourceAsBitmap(@DrawableRes id: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(context, id)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && drawable is VectorDrawable) getBitmap(drawable)
        else null // TODO handle other versions
    }

    private fun getBitmap(vector: VectorDrawable): Bitmap {
        val bitmap = createBitmap(vector.intrinsicWidth, vector.intrinsicHeight, ARGB_8888)
        val canvas = Canvas(bitmap)
        vector.setBounds(0, 0, canvas.width, canvas.height)
        vector.draw(canvas)
        return bitmap
    }

}
