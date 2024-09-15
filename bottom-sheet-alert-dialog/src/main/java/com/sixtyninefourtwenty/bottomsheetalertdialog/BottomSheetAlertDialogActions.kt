package com.sixtyninefourtwenty.bottomsheetalertdialog

/**
 * Class consisting of methods that allow users to manipulate some dialog UI behavior.
 */
@Suppress("unused")
class BottomSheetAlertDialogActions internal constructor(private val ui: BottomSheetAlertDialogUi) {

    fun enableButton(button: BottomSheetAlertDialogButton) {
        ui.setButtonEnabled(button, true)
    }

    fun disableButton(button: BottomSheetAlertDialogButton) {
        ui.setButtonEnabled(button, false)
    }

}