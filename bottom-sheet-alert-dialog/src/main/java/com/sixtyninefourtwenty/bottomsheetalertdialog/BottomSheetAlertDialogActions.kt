package com.sixtyninefourtwenty.bottomsheetalertdialog

class BottomSheetAlertDialogActions internal constructor(private val ui: BottomSheetAlertDialogCommon) {

    fun enableButton(button: DialogButton) {
        ui.setButtonEnabled(button, true)
    }

    fun disableButton(button: DialogButton) {
        ui.setButtonEnabled(button, false)
    }

}