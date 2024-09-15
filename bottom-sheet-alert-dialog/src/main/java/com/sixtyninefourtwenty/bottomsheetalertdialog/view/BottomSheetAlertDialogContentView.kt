package com.sixtyninefourtwenty.bottomsheetalertdialog.view

import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.viewbinding.ViewBinding
import com.sixtyninefourtwenty.bottomsheetalertdialog.view.BottomSheetAlertDialogContentView.Companion.unmodified
import com.sixtyninefourtwenty.bottomsheetalertdialog.view.BottomSheetAlertDialogContentView.Companion.verticallyScrollable

/**
 * Call [unmodified] for an unmodified view, or [verticallyScrollable] to wrap the view in a [NestedScrollView].
 */
@Suppress("unused")
sealed interface BottomSheetAlertDialogContentView {
    val root: View

    companion object {
        /**
         * Creates a [BottomSheetAlertDialogContentView] with the [view] unmodified.
         */
        @JvmStatic
        fun unmodified(view: View): BottomSheetAlertDialogContentView = UnmodifiedBottomSheetAlertDialogContentView(view)

        /**
         * Convenience for `unmodified(binding.root)`
         */
        @JvmStatic
        fun unmodified(binding: ViewBinding) = unmodified(binding.root)

        /**
         * Creates a [BottomSheetAlertDialogContentView] with the [contentView] wrapped in a [NestedScrollView].
         */
        @JvmStatic
        fun verticallyScrollable(contentView: View): BottomSheetAlertDialogContentView = VerticallyScrollableBottomSheetAlertDialogContentView(contentView)

        /**
         * Convenience for `verticallyScrollable(binding.root)`
         */
        @JvmStatic
        fun verticallyScrollable(binding: ViewBinding) = verticallyScrollable(binding.root)
    }
}

private class UnmodifiedBottomSheetAlertDialogContentView(override val root: View) : BottomSheetAlertDialogContentView

private class VerticallyScrollableBottomSheetAlertDialogContentView(
    contentView: View
) : BottomSheetAlertDialogContentView {

    private val scrollView: NestedScrollView = NestedScrollView(contentView.context)

    override val root: View
        get() = scrollView

    init {
        with(scrollView) {
            removeAllViews()
            addView(contentView)
        }
    }

}