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
 */
class BottomSheetAlertDialogBuilder @JvmOverloads constructor(
    view: View,
    isFullscreen: Boolean = false,
    context: Context = view.context,
    isContentViewHeightDynamic: Boolean = false
) : BaseDialogBuilder<BottomSheetAlertDialogBuilder>(view, context, isFullscreen, isContentViewHeightDynamic) {

    override val dialog: BottomSheetDialog = BottomSheetDialog(context).apply {
        setContentView(ui.root)
    }

    override fun self() = this

    fun show() = dialog.show()

    init {
        initDialogBehavior()
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
    isFullscreen: Boolean = false,
    context: Context = view.context,
    isContentViewHeightDynamic: Boolean = false
) : BaseDialogBuilder<BottomSheetAlertDialogFragmentViewBuilder>(view, context, isFullscreen, isContentViewHeightDynamic) {

    override val dialog: BottomSheetDialog = fragment.requireDialog() as BottomSheetDialog

    override fun self() = this

    val rootView get() = ui.root

    init {
        initDialogBehavior()
    }

}
