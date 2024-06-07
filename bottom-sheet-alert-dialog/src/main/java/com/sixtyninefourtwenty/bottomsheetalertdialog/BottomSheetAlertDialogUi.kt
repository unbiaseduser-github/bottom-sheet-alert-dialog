package com.sixtyninefourtwenty.bottomsheetalertdialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.core.widget.TextViewCompat
import com.google.android.material.button.MaterialButton
import com.sixtyninefourtwenty.bottomsheetalertdialog.databinding.BottomSheetAlertDialogUiBinding
import com.sixtyninefourtwenty.bottomsheetalertdialog.misc.getWindowHeight

/**
 * Class abstracting UI elements. Provides methods for setting UI stuff, used by
 * [BaseDialogBuilder] and subclasses.
 */
sealed class BottomSheetAlertDialogCommonUi(private val context: Context) {
    abstract val root: View
    protected abstract val title: TextView
    protected abstract val content: ScrollView
    protected abstract val buttonContainer: ViewGroup
    protected abstract val positiveButton: Button
    protected abstract val neutralButton: Button
    protected abstract val negativeButton: Button

    internal var fullScreenListener: (() -> Unit)? = null

    fun setTitle(@StringRes titleRes: Int) {
        with(title) {
            visibility = View.VISIBLE
            setText(titleRes)
        }
    }

    fun setTitle(titleText: CharSequence) {
        with(title) {
            visibility = View.VISIBLE
            text = titleText
        }
    }

    fun setContentView(view: View) {
        with(content) {
            removeAllViews()
            addView(view)
        }
    }

    private fun getButton(whichButton: DialogButton): Button {
        return when (whichButton) {
            DialogButton.POSITIVE -> positiveButton
            DialogButton.NEGATIVE -> negativeButton
            DialogButton.NEUTRAL -> neutralButton
        }
    }

    fun setButtonAppearance(whichButton: DialogButton, textRes: Int, text: CharSequence?) {
        with(getButton(whichButton)) {
            visibility = View.VISIBLE
            when {
                textRes != 0 -> setText(textRes)
                text != null -> setText(text)
            }
        }
    }

    fun setButtonAppearance(whichButton: DialogButton, properties: ButtonAppearanceProperties) {
        setButtonAppearance(whichButton, properties.textRes, properties.text)
    }

    fun setButtonOnClickListener(whichButton: DialogButton, onClickListener: View.OnClickListener) {
        getButton(whichButton).setOnClickListener(onClickListener)
    }

    fun setButtonEnabled(whichButton: DialogButton, enabled: Boolean) {
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
        internal fun create(context: Context): BottomSheetAlertDialogCommonUi =
            BottomSheetAlertDialogUiImpl(context)
    }

}

private class BottomSheetAlertDialogUiImpl(context: Context) :
    BottomSheetAlertDialogCommonUi(context) {

    private val binding = BottomSheetAlertDialogUiBinding.inflate(LayoutInflater.from(context))

    override val title: TextView = binding.title
    override val positiveButton: MaterialButton = binding.positiveButton
    override val neutralButton: MaterialButton = binding.neutralButton
    override val negativeButton: MaterialButton = binding.negativeButton
    override val buttonContainer: ViewGroup = binding.buttonContainer
    override val content: ScrollView = binding.content
    override val root: View = binding.root

    init {
        init()
        binding.root.post {
            content.updateLayoutParams<ConstraintLayout.LayoutParams> {
                this.matchConstraintMaxHeight = binding.root.height - binding.title.height - binding.buttonContainer.height
            }
            val windowHeight = context.getWindowHeight()
            if (binding.root.height >= windowHeight * 0.95) {
                fullScreenListener?.invoke()
            }
        }
    }

}
