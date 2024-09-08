@file:JvmSynthetic
@file:Suppress("unused")

package com.sixtyninefourtwenty.bottomsheetalertdialog.misc

import android.content.Context
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sixtyninefourtwenty.bottomsheetalertdialog.BottomSheetAlertDialogActions
import com.sixtyninefourtwenty.bottomsheetalertdialog.BottomSheetAlertDialogFragmentViewBuilder
import com.sixtyninefourtwenty.bottomsheetalertdialog.DialogButtonProperties

/**
 * Creates a [BottomSheetAlertDialogFragmentViewBuilder] using named/default parameters.
 *
 * @param isFullscreen Currently ignored and will be removed in the next major release.
 */
fun BottomSheetDialogFragment.createBottomSheetAlertDialog(
    view: View,
    isFullscreen: Boolean = false,
    context: Context = view.context,
    isContentViewHeightDynamic: Boolean = false,
    titleText: CharSequence? = null,
    positiveButtonProperties: DialogButtonProperties? = null,
    neutralButtonProperties: DialogButtonProperties? = null,
    negativeButtonProperties: DialogButtonProperties? = null,
    action: ((BottomSheetAlertDialogActions) -> Unit)? = null
) = BottomSheetAlertDialogFragmentViewBuilder(
    view,
    this,
    isFullscreen,
    context,
    isContentViewHeightDynamic
).apply {
    if (titleText != null) {
        setTitle(titleText)
    }
    if (positiveButtonProperties != null) {
        setPositiveButton(positiveButtonProperties)
    }
    if (neutralButtonProperties != null) {
        setNeutralButton(neutralButtonProperties)
    }
    if (negativeButtonProperties != null) {
        setNegativeButton(negativeButtonProperties)
    }
    if (action != null) {
        doActions(action::invoke)
    }
}
