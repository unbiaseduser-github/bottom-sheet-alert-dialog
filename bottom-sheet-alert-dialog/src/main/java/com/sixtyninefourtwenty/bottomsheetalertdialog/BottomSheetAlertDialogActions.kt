package com.sixtyninefourtwenty.bottomsheetalertdialog

import com.sixtyninefourtwenty.bottomsheetalertdialog.internal.BottomSheetAlertDialogCommon

class BottomSheetAlertDialogActions internal constructor(private val ui: BottomSheetAlertDialogCommon) {

    fun enableButton(button: DialogButton) {
        ui.getButton(button).isEnabled = true
    }

    fun disableButton(button: DialogButton) {
        ui.getButton(button).isEnabled = false
    }

}