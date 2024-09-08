package com.sixtyninefourtwenty.bottomsheetalertdialog

sealed interface DialogButtonEnum

@Deprecated(
    message = "Name's too generic, use BottomSheetAlertDialogButton instead.",
    replaceWith = ReplaceWith(
        expression = "BottomSheetAlertDialogButton",
        imports = ["com.sixtyninefourtwenty.bottomsheetalertdialog.BottomSheetAlertDialogButton"]
    )
)
enum class DialogButton : DialogButtonEnum {
    POSITIVE, NEGATIVE, NEUTRAL
}

enum class BottomSheetAlertDialogButton : DialogButtonEnum {
    POSITIVE, NEGATIVE, NEUTRAL
}