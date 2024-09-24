package com.sixtyninefourtwenty.bottomsheetalertdialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.core.widget.TextViewCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.sixtyninefourtwenty.bottomsheetalertdialog.databinding.BottomSheetAlertDialogUiBinding
import com.sixtyninefourtwenty.bottomsheetalertdialog.misc.getWindowHeight
import com.sixtyninefourtwenty.bottomsheetalertdialog.view.BottomSheetAlertDialogContentView

/**
 * Class abstracting UI elements. Provides methods for setting UI stuff, used by
 * [BaseDialogBuilder] and subclasses.
 */
sealed class BottomSheetAlertDialogUi(private val context: Context) {
    abstract val root: View
    protected abstract val title: TextView
    protected abstract val content: ViewGroup
    protected abstract val buttonContainer: ViewGroup
    protected abstract val positiveButton: Button
    protected abstract val neutralButton: Button
    protected abstract val negativeButton: Button

    fun setTitle(titleText: CharSequence?) {
        with(title) {
            visibility = if (titleText != null) View.VISIBLE else View.GONE
            text = titleText
        }
    }

    /**
     * @param layoutParams If `null`, `addView(View)` will be called on the content frame, else
     * `addView(View, ViewGroup.LayoutParams)`. Note that when using a [BottomSheetDialogFragment],
     * the fragment breaks the view's layout params:
     * ```
     * override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
     *     val view = inflater.inflate(R.layout..., container, false) // Breaks the root view's layout params
     * }
     * ```
     *  thus [layoutParams] is highly recommended to be non-null when using a [BottomSheetDialogFragment].
     */
    @JvmOverloads
    fun setContentView(
        view: BottomSheetAlertDialogContentView?,
        layoutParams: ViewGroup.LayoutParams? = null
    ) {
        with(content) {
            removeAllViews()
            if (view != null) {
                if (layoutParams != null) {
                    addView(view.root, layoutParams)
                } else {
                    addView(view.root)
                }
            }
        }
    }

    private fun getButton(whichButton: BottomSheetAlertDialogButton): Button {
        return when (whichButton) {
            BottomSheetAlertDialogButton.POSITIVE -> positiveButton
            BottomSheetAlertDialogButton.NEGATIVE -> negativeButton
            BottomSheetAlertDialogButton.NEUTRAL -> neutralButton
        }
    }

    fun setButtonAppearance(whichButton: BottomSheetAlertDialogButton, text: CharSequence?) {
        with(getButton(whichButton)) {
            visibility = if (text != null) View.VISIBLE else View.GONE
            setText(text)
        }
        var isAnyButtonVisible = false
        for (b in BottomSheetAlertDialogButton.entries) {
            if (getButton(b).visibility == View.VISIBLE) {
                isAnyButtonVisible = true
                val paddingVertical = context.resources.getDimensionPixelSize(R.dimen.bsad_button_container_padding_vertical)
                buttonContainer.updatePadding(
                    top = paddingVertical,
                    bottom = paddingVertical
                )
                break
            }
        }
        if (!isAnyButtonVisible) {
            buttonContainer.updatePadding(
                top = 0,
                bottom = 0
            )
        }
    }

    fun setButtonOnClickListener(whichButton: BottomSheetAlertDialogButton, onClickListener: View.OnClickListener) {
        getButton(whichButton).setOnClickListener(onClickListener)
    }

    fun setButtonEnabled(whichButton: BottomSheetAlertDialogButton, enabled: Boolean) {
        getButton(whichButton).isEnabled = enabled
    }

    protected fun init() {
        val ta = context.obtainStyledAttributes(intArrayOf(R.attr.bsadTitleStyle))
        val textAppearance = ta.getResourceId(0, com.google.android.material.R.style.TextAppearance_MaterialComponents_Headline5)
        TextViewCompat.setTextAppearance(title, textAppearance)
        ta.recycle()
    }

    companion object {
        @JvmSynthetic
        internal fun create(
            context: Context
        ): BottomSheetAlertDialogUi =
            BottomSheetAlertDialogUiImpl(context)
    }

}

private class BottomSheetAlertDialogUiImpl(
    context: Context
) : BottomSheetAlertDialogUi(context) {

    private val binding = BottomSheetAlertDialogUiBinding.inflate(LayoutInflater.from(context))

    override val title: TextView = binding.title
    override val positiveButton: MaterialButton = binding.positiveButton
    override val neutralButton: MaterialButton = binding.neutralButton
    override val negativeButton: MaterialButton = binding.negativeButton
    override val buttonContainer: ViewGroup = binding.buttonBar
    override val content: ViewGroup = binding.content
    override val root: View = binding.root

    init {
        init()
        val windowHeight = context.getWindowHeight()
        root.post {
            content.updateLayoutParams<ConstraintLayout.LayoutParams> {
                this.matchConstraintMaxHeight = windowHeight - title.height - buttonContainer.height
            }
        }
    }

}
