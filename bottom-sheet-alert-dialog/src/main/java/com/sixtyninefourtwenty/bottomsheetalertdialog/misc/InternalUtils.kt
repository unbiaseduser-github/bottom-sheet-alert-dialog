@file:JvmSynthetic

package com.sixtyninefourtwenty.bottomsheetalertdialog.misc

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager

internal fun Context.getWindowHeight(): Int {
    val windowHeight: Int
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = wm.currentWindowMetrics
        val windowInsets = windowMetrics.windowInsets
        val insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowHeight = windowMetrics.bounds.height() - (insets.top + insets.bottom)
    } else {
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        windowHeight = dm.heightPixels
    }
    return windowHeight
}
