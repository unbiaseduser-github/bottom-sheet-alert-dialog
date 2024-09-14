@file:Suppress("unused")
package com.sixtyninefourtwenty.bottomsheetalertdialog

import android.content.Context
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Builder to show a regular [BottomSheetDialog].
 *
 * Usage: Call [show].
 *
 * See [BaseDialogBuilder] for explanation of parameters.
 *
 * @param dialog Custom dialog to use as the "base". Note that the below listed modifications are controlled by
 * the library:
 * - [BottomSheetDialog.setContentView]
 */
class BottomSheetAlertDialogBuilder @JvmOverloads constructor(
    view: View,
    context: Context = view.context,
    override val dialog: BottomSheetDialog = BottomSheetDialog(context)
) : BaseDialogBuilder<BottomSheetAlertDialogBuilder>(view, context) {

    override fun self() = this

    fun show() = dialog.show()

    init {
        dialog.setContentView(ui.root)
        expandDialog()
    }

}

/**
 * Builder to create a view to be used in [BottomSheetDialogFragment]s.
 *
 * Usage: Get the view via [rootView] in [the fragment's onCreateView][BottomSheetDialogFragment.onCreateView].
 *
 * See [BaseDialogBuilder] for explanation of parameters.
 */
class BottomSheetAlertDialogFragmentViewBuilder @JvmOverloads constructor(
    view: View,
    fragment: BottomSheetDialogFragment,
    context: Context = view.context
) : BaseDialogBuilder<BottomSheetAlertDialogFragmentViewBuilder>(view, context) {

    override val dialog: BottomSheetDialog = fragment.requireDialog() as BottomSheetDialog

    override fun self() = this

    val rootView get() = ui.root

    init {
        expandDialog()
    }

}
