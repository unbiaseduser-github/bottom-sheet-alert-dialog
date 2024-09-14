package com.sixtyninefourtwenty.bottomsheetalertdialog

import android.content.Context
import android.view.View
import androidx.core.util.Consumer
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * Base builder class. Can be publicly extended to create your own builders.
 *
 * @param view The content view, will be put into a [NestedScrollView].
 * @param context Context that will be used to create the root view. Default is the supplied view's
 * context.
 */
abstract class BaseDialogBuilder<T : BaseDialogBuilder<T>>(
    view: View,
    context: Context = view.context
) {

    protected val ui: BottomSheetAlertDialogCommonUi = BottomSheetAlertDialogCommonUi.create(context, view)

    /**
     * The builder's [BottomSheetAlertDialogActions] object. You might want to use [doActions]
     * instead of accessing this directly to keep the fluent syntax.
     */
    val actions: BottomSheetAlertDialogActions = BottomSheetAlertDialogActions(ui)
    protected abstract val dialog: BottomSheetDialog

    /**
     * Subclasses must override this method to `return this`
     */
    protected abstract fun self(): T

    fun setTitle(titleText: CharSequence) = self().apply { ui.setTitle(titleText) }

    private fun applyBtnProps(
        whichButton: BottomSheetAlertDialogButton,
        text: CharSequence,
        listener: (() -> Unit)?,
        dismissAfterClick: Boolean
    ) {
        ui.setButtonAppearance(whichButton, text)
        ui.setButtonOnClickListener(whichButton) {
            listener?.invoke()
            if (dismissAfterClick) {
                dialog.dismiss()
            }
        }
    }

    private fun applyBtnProps(whichButton: BottomSheetAlertDialogButton, props: DialogButtonProperties) {
        ui.setButtonAppearance(whichButton, props)
        ui.setButtonOnClickListener(whichButton) {
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
    ) = self().apply { applyBtnProps(BottomSheetAlertDialogButton.POSITIVE, text, listener, dismissAfterClick) }
    fun setPositiveButton(properties: DialogButtonProperties) = self().apply { applyBtnProps(BottomSheetAlertDialogButton.POSITIVE, properties) }
    @JvmSynthetic
    fun setNeutralButton(
        text: CharSequence,
        listener: (() -> Unit)? = null,
        dismissAfterClick: Boolean = true
    ) = self().apply { applyBtnProps(BottomSheetAlertDialogButton.NEUTRAL, text, listener, dismissAfterClick) }
    fun setNeutralButton(properties: DialogButtonProperties) = self().apply { applyBtnProps(BottomSheetAlertDialogButton.NEUTRAL, properties) }
    @JvmSynthetic
    fun setNegativeButton(
        text: CharSequence,
        listener: (() -> Unit)? = null,
        dismissAfterClick: Boolean = true
    ) = self().apply { applyBtnProps(BottomSheetAlertDialogButton.NEGATIVE, text, listener, dismissAfterClick) }
    fun setNegativeButton(properties: DialogButtonProperties) = self().apply { applyBtnProps(BottomSheetAlertDialogButton.NEGATIVE, properties) }

    /**
     * Fluent method for accessing [actions].
     *
     * Calling this:
     * ```
     * private lateinit var actions: BottomSheetAlertDialogActions
     *
     * fun foo() {
     *     dialogBuilder
     *         .doActions { actions -> this.actions = actions }
     *         .someMethod()
     * }
     * ```
     * is equivalent to
     * ```
     * private lateinit var actions: BottomSheetAlertDialogActions
     *
     * fun foo() {
     *      val dialogBuilder: BaseDialogBuilder<*> = ...
     *      this.actions = dialogBuilder.actions
     *      dialogBuilder.someMethod()
     * }
     * ```
     */
    fun doActions(block: Consumer<in BottomSheetAlertDialogActions>) = self().apply { block.accept(actions) }

    fun expandDialog() = self().apply { dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED }

}
