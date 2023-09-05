package com.sixtyninefourtwenty.bottomsheetalertdialog

sealed class ButtonAppearanceProperties(
    internal val textRes: Int,
    internal val text: CharSequence?
) {
    sealed class Builder<T : Builder<T>>(
        internal val textRes: Int,
        internal val text: CharSequence?
    ) {
        abstract fun self(): T
        abstract fun build(): ButtonAppearanceProperties
    }
}
