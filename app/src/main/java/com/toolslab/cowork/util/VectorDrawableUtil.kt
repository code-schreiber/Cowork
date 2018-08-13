package com.toolslab.cowork.util

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.support.annotation.DrawableRes
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.ContextCompat
import javax.inject.Inject

internal class VectorDrawableUtil @Inject constructor() {

    @Inject
    internal lateinit var context: Context

    internal fun resourceAsBitmap(@DrawableRes id: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(context, id)
        return when {
            drawable is BitmapDrawable -> drawable.bitmap
            drawable is VectorDrawableCompat -> getBitmap(drawable)
            SDK_INT >= LOLLIPOP && drawable is VectorDrawable -> getBitmap(drawable)
            else -> throw  IllegalArgumentException("Unsupported drawable type: $drawable")
        }
    }

    @TargetApi(LOLLIPOP)
    private fun getBitmap(vector: VectorDrawable): Bitmap? {
        val bitmap = createBitmap(vector.intrinsicWidth, vector.intrinsicHeight, ARGB_8888)
        val canvas = Canvas(bitmap)
        vector.setBounds(0, 0, canvas.width, canvas.height)
        vector.draw(canvas)
        return bitmap
    }

    private fun getBitmap(vector: VectorDrawableCompat): Bitmap? {
        val bitmap = createBitmap(vector.intrinsicWidth, vector.intrinsicHeight, ARGB_8888)
        val canvas = Canvas(bitmap)
        vector.setBounds(0, 0, canvas.width, canvas.height)
        vector.draw(canvas)
        return bitmap
    }

}
