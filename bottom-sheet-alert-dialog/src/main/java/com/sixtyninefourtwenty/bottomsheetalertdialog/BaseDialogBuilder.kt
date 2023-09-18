package com.sixtyninefourtwenty.bottomsheetalertdialog

import android.content.Context
import android.content.res.Configuration
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sixtyninefourtwenty.bottomsheetalertdialog.internal.BottomSheetAlertDialogCommon
import java.util.function.Consumer

abstract class BaseDialogBuilder<T : BaseDialogBuilder<T>>(
    view: View,
    context: Context,
    isFullscreen: Boolean = false
) {

    private val shouldBeFullScreen: Boolean
    protected val ui: BottomSheetAlertDialogCommon
    protected val actions: BottomSheetAlertDialogActions
    protected abstract val dialog: BottomSheetDialog
    protected abstract fun self(): T

    fun setTitle(@StringRes titleRes: Int) = self().apply { ui.setTitle(titleRes) }
    fun setTitle(titleText: CharSequence) = self().apply { ui.setTitle(titleText) }

    private fun applyBtnProps(whichButton: DialogButton, props: DialogButtonProperties) {
        ui.setButtonAppearance(whichButton, props)
        ui.getButton(whichButton).setOnClickListener {
            props.listenerWithDialog?.accept(dialog) ?: props.listener?.run()
            if (props.dismissAfterClick) {
                dialog.dismiss()
            }
        }
    }

    fun setPositiveButton(properties: DialogButtonProperties) = self().apply { applyBtnProps(DialogButton.POSITIVE, properties) }
    fun setNeutralButton(properties: DialogButtonProperties) = self().apply { applyBtnProps(DialogButton.NEUTRAL, properties) }
    fun setNegativeButton(properties: DialogButtonProperties) = self().apply { applyBtnProps(DialogButton.NEGATIVE, properties) }

    fun doActions(block: Consumer<in BottomSheetAlertDialogActions>) = self().apply { block.accept(actions) }

    /**
     * Must be called by subclasses in their `init` block.
     */
    protected fun initDialogBehavior() {
        with(dialog.behavior) {
            state = BottomSheetBehavior.STATE_EXPANDED
            if (shouldBeFullScreen) {
                isDraggable = false
            }
        }
    }

    init {
        val isLandscape = context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        shouldBeFullScreen = isFullscreen || isLandscape
        ui = BottomSheetAlertDialogCommon.create(context, shouldBeFullScreen).apply {
            setContentView(view)
        }
        actions = BottomSheetAlertDialogActions(ui)
    }

}
