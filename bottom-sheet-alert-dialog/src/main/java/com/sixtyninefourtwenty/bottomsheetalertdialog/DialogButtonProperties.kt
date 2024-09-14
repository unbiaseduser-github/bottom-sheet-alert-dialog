@file:Suppress("unused")

package com.sixtyninefourtwenty.bottomsheetalertdialog

/**
 * Represents properties that will be applied to the bottom sheet view's buttons via the builders'
 * `setXButton` methods.
 */
class DialogButtonProperties(
    text: CharSequence,
    internal val listener: Runnable? = null,
    internal val dismissAfterClick: Boolean = true
) : ButtonAppearanceProperties(text) {

    companion object {
        /**
         * Creates a [DialogButtonProperties] with only the [text] property and nothing else.
         */
        @JvmStatic
        fun ofOnlyText(text: CharSequence) = DialogButtonProperties(text)
    }

    private constructor(builder: Builder) : this(
        builder.text,
        builder.listener,
        builder.dismissAfterClick
    )

    class Builder(
        text: CharSequence
    ) : ButtonAppearanceProperties.Builder<Builder>(text) {

        internal var listener: Runnable? = null
            private set
        fun setOnClickListener(listener: Runnable) = self().apply { this.listener = listener }
        internal var dismissAfterClick = true
            private set
        fun disableDismissAfterClick() = apply { dismissAfterClick = false }
        override fun self() = this
        override fun build() = DialogButtonProperties(this)
    }

}