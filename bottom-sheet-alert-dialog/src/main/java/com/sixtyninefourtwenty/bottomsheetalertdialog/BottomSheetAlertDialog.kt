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
 * All constructors take an optional custom dialog to use as the "base". Note that the below listed
 * modifications are controlled by the library:
 * - [BottomSheetDialog.setContentView]
 */
class BottomSheetAlertDialogBuilder : BaseDialogBuilder<BottomSheetAlertDialogBuilder> {

    override val dialog: BottomSheetDialog

    @JvmOverloads
    constructor(
        context: Context,
        dialog: BottomSheetDialog = BottomSheetDialog(context)
    ) : super(context) {
        this.dialog = dialog
        initCommon()
    }

    @JvmOverloads
    constructor(
        view: View,
        context: Context = view.context,
        dialog: BottomSheetDialog = BottomSheetDialog(context)
    ) : super(view, context) {
        this.dialog = dialog
        initCommon()
    }

    override fun self() = this

    fun show() = dialog.show()

    private fun initCommon() {
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
class BottomSheetAlertDialogFragmentViewBuilder : BaseDialogBuilder<BottomSheetAlertDialogFragmentViewBuilder> {

    override val dialog: BottomSheetDialog

    constructor(fragment: BottomSheetDialogFragment, context: Context) : super(context) {
        dialog = fragment.requireDialog() as BottomSheetDialog
        expandDialog()
    }

    @JvmOverloads
    constructor(view: View, fragment: BottomSheetDialogFragment, context: Context = view.context) : super(view, context) {
        dialog = fragment.requireDialog() as BottomSheetDialog
        expandDialog()
    }

    override fun self() = this

    val rootView get() = ui.root

}
