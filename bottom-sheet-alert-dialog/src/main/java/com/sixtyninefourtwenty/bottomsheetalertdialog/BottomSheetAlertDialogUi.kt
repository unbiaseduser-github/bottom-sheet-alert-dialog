package com.sixtyninefourtwenty.bottomsheetalertdialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.core.widget.TextViewCompat
import com.google.android.material.button.MaterialButton
import com.sixtyninefourtwenty.bottomsheetalertdialog.databinding.BottomSheetAlertDialogUiBinding
import com.sixtyninefourtwenty.bottomsheetalertdialog.misc.getWindowHeight
import com.sixtyninefourtwenty.bottomsheetalertdialog.view.BottomSheetAlertDialogContentView

/**
 * Class abstracting UI elements. Provides methods for setting UI stuff, used by
 * [BaseDialogBuilder] and subclasses.
 */
sealed class BottomSheetAlertDialogCommonUi(private val context: Context) {
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

    fun setContentView(view: BottomSheetAlertDialogContentView?) {
        with(content) {
            removeAllViews()
            if (view != null) {
                addView(view.root)
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
        ): BottomSheetAlertDialogCommonUi =
            BottomSheetAlertDialogUiImpl(context)
    }

}

private class BottomSheetAlertDialogUiImpl(
    context: Context
) : BottomSheetAlertDialogCommonUi(context) {

    private val binding = BottomSheetAlertDialogUiBinding.inflate(LayoutInflater.from(context))

    override val title: TextView = binding.title
    override val positiveButton: MaterialButton = binding.positiveButton
    override val neutralButton: MaterialButton = binding.neutralButton
    override val negativeButton: MaterialButton = binding.negativeButton
    override val buttonContainer: ViewGroup = binding.buttonContainer
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
