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
 */
fun BottomSheetDialogFragment.createBottomSheetAlertDialog(
    view: View? = null,
    context: Context = requireContext(),
    titleText: CharSequence? = null,
    positiveButtonProperties: DialogButtonProperties? = null,
    neutralButtonProperties: DialogButtonProperties? = null,
    negativeButtonProperties: DialogButtonProperties? = null,
    action: ((BottomSheetAlertDialogActions) -> Unit)? = null
): BottomSheetAlertDialogFragmentViewBuilder {
    val builder = if (view != null) {
        BottomSheetAlertDialogFragmentViewBuilder(view, this, context)
    } else {
        BottomSheetAlertDialogFragmentViewBuilder(this, context)
    }
    return builder
        .setTitle(titleText)
        .setPositiveButton(positiveButtonProperties)
        .setNeutralButton(neutralButtonProperties)
        .setNegativeButton(negativeButtonProperties)
        .doActions(action?.let { a -> { a(it) } })
}
