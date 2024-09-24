package com.sixtyninefourtwenty.bottomsheetalertdialog.view

import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.core.view.updatePadding
import androidx.core.widget.NestedScrollView
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sixtyninefourtwenty.bottomsheetalertdialog.view.BottomSheetAlertDialogContentView.Companion.unmodified

/**
 * Call [unmodified] for an unmodified view, or [verticallyScrollable] to wrap the view in a [NestedScrollView].
 */
@Suppress("unused")
interface BottomSheetAlertDialogContentView {
    val root: View

    fun verticallyScrollable(layoutParams: ViewGroup.LayoutParams?) = verticallyScrollable(root, layoutParams)
    fun bottomPadded(@Px bottomPadding: Int) = bottomPadded(root, bottomPadding)

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
         *
         * @param layoutParams If `null`, `addView(View)` will be called on the scroll view, else
         * `addView(View, ViewGroup.LayoutParams)`. Note that when using a [BottomSheetDialogFragment],
         * the fragment breaks the view's layout params:
         * ```
         * override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
         *     val view = inflater.inflate(R.layout..., container, false) // Breaks root view's layout params
         * }
         * ```
         * thus [layoutParams] is highly recommended to be non-null when using a [BottomSheetDialogFragment].
         * @see ViewGroup.addView
         */
        @JvmStatic
        @JvmOverloads
        fun verticallyScrollable(
            contentView: View,
            layoutParams: ViewGroup.LayoutParams? = null
        ): BottomSheetAlertDialogContentView = VerticallyScrollableBottomSheetAlertDialogContentView(contentView, layoutParams)

        /**
         * Convenience for `verticallyScrollable(binding.root, layoutParams)`
         */
        @JvmStatic
        @JvmOverloads
        fun verticallyScrollable(
            binding: ViewBinding,
            layoutParams: ViewGroup.LayoutParams? = null
        ) = verticallyScrollable(binding.root, layoutParams)

        /**
         * Creates a [BottomSheetAlertDialogContentView] with the [bottomPadding] applied to the [view].
         *
         * Useful for when no button is set, to prevent the content view from running into the bottom edge.
         */
        @JvmStatic
        fun bottomPadded(
            view: View,
            @Px bottomPadding: Int
        ): BottomSheetAlertDialogContentView = BottomPaddedBottomSheetAlertDialogContentView(view, bottomPadding)

        /**
         * Convenience for `bottomPadded(binding.root, bottomPadding)`
         */
        @JvmStatic
        fun bottomPadded(
            binding: ViewBinding,
            @Px bottomPadding: Int
        ) = bottomPadded(binding.root, bottomPadding)

    }
}

private class UnmodifiedBottomSheetAlertDialogContentView(override val root: View) : BottomSheetAlertDialogContentView

private class VerticallyScrollableBottomSheetAlertDialogContentView(
    contentView: View,
    layoutParams: ViewGroup.LayoutParams?
) : BottomSheetAlertDialogContentView {

    private val scrollView: NestedScrollView = NestedScrollView(contentView.context)

    override val root: View
        get() = scrollView

    init {
        with(scrollView) {
            removeAllViews()
            if (layoutParams != null) {
                addView(contentView, layoutParams)
            } else {
                addView(contentView)
            }
        }
    }

}

private class BottomPaddedBottomSheetAlertDialogContentView(
    override val root: View,
    @Px bottomPadding: Int
) : BottomSheetAlertDialogContentView {

    init {
        root.updatePadding(bottom = bottomPadding)
    }

}