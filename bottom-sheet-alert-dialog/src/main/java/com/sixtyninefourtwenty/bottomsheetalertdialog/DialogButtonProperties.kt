@file:Suppress("unused")

package com.sixtyninefourtwenty.bottomsheetalertdialog

import androidx.annotation.StringRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.function.Consumer

class DialogButtonProperties private constructor(
    textRes: Int,
    text: CharSequence?,
    internal val listener: Runnable?,
    @Deprecated(message = "Unsafe API - will be removed in next major release")
    internal val listenerWithDialog: Consumer<BottomSheetDialog>?,
    internal val dismissAfterClick: Boolean
) : ButtonAppearanceProperties(textRes, text) {

    companion object {
        @JvmStatic
        fun ofOnlyText(textRes: Int) = DialogButtonProperties(textRes)
        @JvmStatic
        fun ofOnlyText(text: CharSequence) = DialogButtonProperties(text)
    }

    /**
     * [listenerWithDialog] is deprecated and will be removed in the next major release.
     */
    constructor(
        textRes: Int,
        listener: Runnable? = null,
        listenerWithDialog: Consumer<BottomSheetDialog>? = null,
        dismissAfterClick: Boolean = true
    ) : this(textRes, null, listener, listenerWithDialog, dismissAfterClick)

    /**
     * [listenerWithDialog] is deprecated and will be removed in the next major release.
     */
    constructor(
        text: CharSequence,
        listener: Runnable? = null,
        listenerWithDialog: Consumer<BottomSheetDialog>? = null,
        dismissAfterClick: Boolean = true
    ) : this(0, text, listener, listenerWithDialog, dismissAfterClick)

    private constructor(builder: Builder) : this(
        builder.textRes,
        builder.text,
        builder.listener,
        builder.listenerWithDialog,
        builder.dismissAfterClick
    )

    class Builder private constructor(
        textRes: Int,
        text: CharSequence?
    ) : ButtonAppearanceProperties.Builder<Builder>(textRes, text) {
        constructor(@StringRes textRes: Int) : this(textRes, null)
        constructor(text: CharSequence) : this(0, text)

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