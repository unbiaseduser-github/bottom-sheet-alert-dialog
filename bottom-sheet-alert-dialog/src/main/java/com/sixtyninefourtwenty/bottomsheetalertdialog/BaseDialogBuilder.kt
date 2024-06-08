package com.sixtyninefourtwenty.bottomsheetalertdialog

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.function.Consumer

/**
 * Base builder class. Can be publicly extended to create your own builders.
 *
 * Implementation notes: Subclasses must call [initDialogBehavior] to get correct dialog behavior.
 *
 * @param context Context that will be used to create the root view. Default is the supplied view's
 * context.
 * @param isFullscreen Currently ignored and will be removed in the next major release.
 * @param isContentViewHeightDynamic Whether the content view's height can be changed after being
 * displayed on screen. Defaults to `false`. When kept at `false`, the layout works much more
 * efficiently, but when the content view's height changes, the root view's height will not change
 * with it.
 */
abstract class BaseDialogBuilder<T : BaseDialogBuilder<T>>(
    view: View,
    context: Context = view.context,
    isFullscreen: Boolean = false,
    isContentViewHeightDynamic: Boolean = false
) {

    protected val ui: BottomSheetAlertDialogCommonUi
    val actions: BottomSheetAlertDialogActions
    protected abstract val dialog: BottomSheetDialog

    /**
     * Subclasses must override this method to `return this`
     */
    protected abstract fun self(): T

    fun setTitle(@StringRes titleRes: Int) = self().apply { ui.setTitle(titleRes) }
    fun setTitle(titleText: CharSequence) = self().apply { ui.setTitle(titleText) }

    private fun applyBtnProps(
        whichButton: DialogButton,
        text: CharSequence,
        listener: (() -> Unit)?,
        dismissAfterClick: Boolean
    ) {
        require(text.isNotBlank()) { "text must not be blank" }
        ui.setButtonAppearance(whichButton, 0, text)
        ui.setButtonOnClickListener(whichButton) {
            listener?.invoke()
            if (dismissAfterClick) {
                dialog.dismiss()
            }
        }
    }

    private fun applyBtnProps(whichButton: DialogButton, props: DialogButtonProperties) {
        ui.setButtonAppearance(whichButton, props)
        ui.setButtonOnClickListener(whichButton) {
            props.listenerWithDialog?.accept(dialog)
            props.listener?.run()
            if (props.dismissAfterClick) {
                dialog.dismiss()
            }
        }
    }

    @JvmSynthetic
    fun setPositiveButton(
        text: CharSequence,
        listener: (() -> Unit)? = null,
        dismissAfterClick: Boolean = true
    ) = self().apply { applyBtnProps(DialogButton.POSITIVE, text, listener, dismissAfterClick) }
    fun setPositiveButton(properties: DialogButtonProperties) = self().apply { applyBtnProps(DialogButton.POSITIVE, properties) }
    @JvmSynthetic
    fun setNeutralButton(
        text: CharSequence,
        listener: (() -> Unit)? = null,
        dismissAfterClick: Boolean = true
    ) = self().apply { applyBtnProps(DialogButton.NEUTRAL, text, listener, dismissAfterClick) }
    fun setNeutralButton(properties: DialogButtonProperties) = self().apply { applyBtnProps(DialogButton.NEUTRAL, properties) }
    @JvmSynthetic
    fun setNegativeButton(
        text: CharSequence,
        listener: (() -> Unit)? = null,
        dismissAfterClick: Boolean = true
    ) = self().apply { applyBtnProps(DialogButton.NEGATIVE, text, listener, dismissAfterClick) }
    fun setNegativeButton(properties: DialogButtonProperties) = self().apply { applyBtnProps(DialogButton.NEGATIVE, properties) }

    fun doActions(block: Consumer<in BottomSheetAlertDialogActions>) = self().apply { block.accept(actions) }

    /**
     * Must be called by subclasses when they are initialized.
     */
    protected fun initDialogBehavior() {
        with(dialog.behavior) {
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    init {
        ui = BottomSheetAlertDialogCommonUi.create(
            context,
            view,
            isContentViewHeightDynamic,
            {
                dialog.behavior.isDraggable = true
            },
            {
                dialog.behavior.isDraggable = false
            }
        )
        actions = BottomSheetAlertDialogActions(ui)
    }

}
