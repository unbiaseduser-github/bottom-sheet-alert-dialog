package com.sixtyninefourtwenty.bottomsheetalertdialog

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Consumer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sixtyninefourtwenty.bottomsheetalertdialog.view.BottomSheetAlertDialogContentView

/**
 * Base builder class. Can be publicly extended to create your own builders.
 */
abstract class BaseDialogBuilder<T : BaseDialogBuilder<T>>(context: Context) {

    /**
     * Convenience constructor that calls [setContentView] with [BottomSheetAlertDialogContentView.verticallyScrollable].
     */
    @JvmOverloads
    constructor(view: View, context: Context = view.context) : this(context) {
        setContentView(BottomSheetAlertDialogContentView.verticallyScrollable(view))
    }

    protected val ui: BottomSheetAlertDialogUi = BottomSheetAlertDialogUi.create(context)

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

    /**
     * @see BottomSheetAlertDialogUi.setContentView
     */
    @JvmOverloads
    fun setContentView(
        view: BottomSheetAlertDialogContentView?,
        layoutParams: ViewGroup.LayoutParams? = null
    ) = self().apply { ui.setContentView(view, layoutParams) }

    fun setTitle(titleText: CharSequence?) = self().apply { ui.setTitle(titleText) }

    private fun applyBtnProps(
        whichButton: BottomSheetAlertDialogButton,
        text: CharSequence?,
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

    private fun applyBtnProps(whichButton: BottomSheetAlertDialogButton, props: DialogButtonProperties?) {
        applyBtnProps(
            whichButton,
            props?.text,
            props?.listener?.let { { it.run() } },
            props?.dismissAfterClick ?: true
        )
    }

    @JvmSynthetic
    fun setPositiveButton(
        text: CharSequence?,
        listener: (() -> Unit)? = null,
        dismissAfterClick: Boolean = true
    ) = self().apply { applyBtnProps(BottomSheetAlertDialogButton.POSITIVE, text, listener, dismissAfterClick) }

    fun setPositiveButton(properties: DialogButtonProperties?) = self().apply { applyBtnProps(BottomSheetAlertDialogButton.POSITIVE, properties) }

    @JvmSynthetic
    fun setNeutralButton(
        text: CharSequence?,
        listener: (() -> Unit)? = null,
        dismissAfterClick: Boolean = true
    ) = self().apply { applyBtnProps(BottomSheetAlertDialogButton.NEUTRAL, text, listener, dismissAfterClick) }

    fun setNeutralButton(properties: DialogButtonProperties?) = self().apply { applyBtnProps(BottomSheetAlertDialogButton.NEUTRAL, properties) }

    @JvmSynthetic
    fun setNegativeButton(
        text: CharSequence?,
        listener: (() -> Unit)? = null,
        dismissAfterClick: Boolean = true
    ) = self().apply { applyBtnProps(BottomSheetAlertDialogButton.NEGATIVE, text, listener, dismissAfterClick) }

    fun setNegativeButton(properties: DialogButtonProperties?) = self().apply { applyBtnProps(BottomSheetAlertDialogButton.NEGATIVE, properties) }

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
    fun doActions(block: Consumer<in BottomSheetAlertDialogActions>?) = self().apply { block?.accept(actions) }

    fun expandDialog() = self().apply { dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED }

    fun collapseDialog() = self().apply { dialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED }

}
