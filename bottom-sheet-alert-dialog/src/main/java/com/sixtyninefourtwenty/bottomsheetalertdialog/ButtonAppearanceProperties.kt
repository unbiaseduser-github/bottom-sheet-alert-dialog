package com.sixtyninefourtwenty.bottomsheetalertdialog

import androidx.annotation.StringRes

sealed class ButtonAppearanceProperties(
    @StringRes internal val textRes: Int,
    internal val text: CharSequence?
) {
    sealed class Builder<T : Builder<T>>(
        @StringRes internal val textRes: Int,
        internal val text: CharSequence?
    ) {
        abstract fun self(): T
        abstract fun build(): ButtonAppearanceProperties
    }
}
