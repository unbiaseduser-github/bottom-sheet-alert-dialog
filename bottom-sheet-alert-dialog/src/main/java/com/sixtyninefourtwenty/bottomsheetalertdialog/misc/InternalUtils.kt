@file:JvmSynthetic

package com.sixtyninefourtwenty.bottomsheetalertdialog.misc

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager
import kotlin.math.ceil

@SuppressLint("DiscouragedApi", "InternalInsetResource")
@Suppress("DEPRECATION")
internal fun Context.getWindowHeight(): Int {
    val windowHeight: Int
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = wm.currentWindowMetrics
        val windowInsets = windowMetrics.windowInsets
        val insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowHeight = windowMetrics.bounds.height() - (insets.top + insets.bottom)
    } else {
        val statusBarHeight: Int
        val resources = resources
        val statusBarResId = resources.getIdentifier("status_bar_height", "dimen", "android")
        statusBarHeight = if (statusBarResId > 0) {
            resources.getDimensionPixelSize(statusBarResId)
        } else {
            ceil(
                (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) 24 else 25) * resources.displayMetrics.density
            ).toInt()
        }

        val point = Point()
        wm.defaultDisplay.getSize(point)
        windowHeight = point.y - statusBarHeight
    }
    return windowHeight
}
