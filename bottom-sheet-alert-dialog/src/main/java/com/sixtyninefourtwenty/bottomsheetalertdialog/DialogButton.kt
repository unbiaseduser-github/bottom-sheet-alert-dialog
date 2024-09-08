package com.sixtyninefourtwenty.bottomsheetalertdialog

/**
 * Intermediate interface to help code referring to [DialogButton] switch to [BottomSheetAlertDialogButton].
 * This, as well as [DialogButton] will be removed in the next major version.
 */
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