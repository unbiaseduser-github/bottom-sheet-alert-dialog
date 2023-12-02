@file:Suppress("unused")

package com.sixtyninefourtwenty.bottomsheetalertdialog

import androidx.annotation.StringRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.function.Consumer

/**
 * Represents properties that will be applied to the bottom sheet view's buttons via the builders'
 * `setXButton` methods.
 */
class DialogButtonProperties private constructor(
    @StringRes textRes: Int,
    text: CharSequence?,
    internal val listener: Runnable?,
    @Deprecated(message = "Unsafe API - will be removed in next major release")
    internal val listenerWithDialog: Consumer<BottomSheetDialog>?,
    internal val dismissAfterClick: Boolean
) : ButtonAppearanceProperties(textRes, text) {

    companion object {
        /**
         * Creates a [DialogButtonProperties] with only the [textRes] property and nothing else.
         * @throws IllegalArgumentException if [textRes] is 0
         */
        @JvmStatic
        fun ofOnlyText(@StringRes textRes: Int) = DialogButtonProperties(textRes)
        /**
         * Creates a [DialogButtonProperties] with only the [text] property and nothing else.
         * @throws IllegalArgumentException if [text] is blank
         */
        @JvmStatic
        fun ofOnlyText(text: CharSequence) = DialogButtonProperties(text)
    }

    /**
     * [listenerWithDialog] is deprecated and will be removed in the next major release.
     * @throws IllegalArgumentException if [textRes] is 0
     */
    constructor(
        @StringRes textRes: Int,
        listener: Runnable? = null,
        listenerWithDialog: Consumer<BottomSheetDialog>? = null,
        dismissAfterClick: Boolean = true
    ) : this(textRes, null, listener, listenerWithDialog, dismissAfterClick) {
        require(textRes != 0) { "textRes must not be 0" }
    }

    /**
     * [listenerWithDialog] is deprecated and will be removed in the next major release.
     * @throws IllegalArgumentException if [text] is blank
     */
    constructor(
        text: CharSequence,
        listener: Runnable? = null,
        listenerWithDialog: Consumer<BottomSheetDialog>? = null,
        dismissAfterClick: Boolean = true
    ) : this(0, text, listener, listenerWithDialog, dismissAfterClick) {
        require(text.isNotBlank()) { "text must not be blank" }
    }

    private constructor(builder: Builder) : this(
        builder.textRes,
        builder.text,
        builder.listener,
        builder.listenerWithDialog,
        builder.dismissAfterClick
    )

    class Builder private constructor(
        @StringRes textRes: Int,
        text: CharSequence?
    ) : ButtonAppearanceProperties.Builder<Builder>(textRes, text) {
        constructor(@StringRes textRes: Int) : this(textRes, null) {
            require(textRes != 0) { "textRes must not be 0" }
        }
        constructor(text: CharSequence) : this(0, text) {
            require(text.isNotBlank()) { "text must not be blank" }
        }

        internal var listener: Runnable? = null
            private set
        fun setOnClickListener(listener: Runnable) = self().apply { this.listener = listener }
        internal var listenerWithDialog: Consumer<BottomSheetDialog>? = null
            private set
        internal var dismissAfterClick = true
            private set
        @Deprecated(message = "Unsafe API - will be removed in next major release")
        fun setOnClickListener(listener: Consumer<BottomSheetDialog>) = apply { listenerWithDialog = listener }
        fun disableDismissAfterClick() = apply { dismissAfterClick = false }
        override fun self() = this
        override fun build() = DialogButtonProperties(this)
    }

}