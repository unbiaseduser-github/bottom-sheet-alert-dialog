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
 * @param context Context that will be used to create the root view. Default is the supplied view's
 * context.
 * @param isFullscreen Currently ignored and will be removed in the next major release.
 * @param isContentViewHeightDynamic Whether the content view's height can be changed after being
 * displayed on screen. Defaults to `false`. When kept at `false`, the layout works much more
 * efficiently, but when the content view's height changes, the root view's height will not change
 * with it.
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
 * Usage: Get the view via [rootView] in [BottomSheetDialogFragment.onCreateView].
 *
 * @param context Context that will be used to create the root view. Default is the supplied view's
 * context.
 * @param isFullscreen Currently ignored and will be removed in the next major release.
 * @param isContentViewHeightDynamic Whether the content view's height can be changed after being
 * displayed on screen. Defaults to `false`. When kept at `false`, the layout works much more
 * efficiently, but when the content view's height changes, the root view's height will not change
 * with it.
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
