package com.sixtyninefourtwenty.bottomsheetalertdialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.widget.TextViewCompat
import com.sixtyninefourtwenty.bottomsheetalertdialog.databinding.BottomSheetAlertDialogFullscreenUiBinding
import com.sixtyninefourtwenty.bottomsheetalertdialog.databinding.BottomSheetAlertDialogNotFullscreenUiBinding

/**
 * Class abstracting UI elements. A concrete implementation will be created by [BaseDialogBuilder]
 * depending on [BaseDialogBuilder.shouldBeFullScreen].
 *
 * Provides methods for setting UI stuff, used by
 * [BaseDialogBuilder] and subclasses.
 */
sealed class BottomSheetAlertDialogCommonUi(protected val context: Context) {
    abstract val root: View
    protected abstract val title: TextView
    protected abstract val content: ScrollView
    protected abstract val buttonContainer: RelativeLayout
    protected abstract val positiveButton: Button
    protected abstract val neutralButton: Button
    protected abstract val negativeButton: Button

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
        internal fun create(context: Context, isFullscreen: Boolean) =
            if (isFullscreen) BottomSheetAlertDialogFullscreenUi(context) else BottomSheetAlertDialogNotFullscreenUi(context)
    }

}

private class BottomSheetAlertDialogFullscreenUi(context: Context) :
    BottomSheetAlertDialogCommonUi(context) {

    private val binding = BottomSheetAlertDialogFullscreenUiBinding.inflate(LayoutInflater.from(context))

    override val title: TextView = binding.title
    override val positiveButton: Button = binding.positiveButton
    override val neutralButton: Button = binding.neutralButton
    override val negativeButton: Button = binding.negativeButton
    override val buttonContainer: RelativeLayout = binding.buttonContainer
    override val content: ScrollView = binding.content
    override val root: RelativeLayout = binding.root

    init {
        init()
    }

}

private class BottomSheetAlertDialogNotFullscreenUi(context: Context) :
    BottomSheetAlertDialogCommonUi(context) {

    private val binding = BottomSheetAlertDialogNotFullscreenUiBinding.inflate(LayoutInflater.from(context))

    override val title: TextView = binding.title
    override val positiveButton: Button = binding.positiveButton
    override val neutralButton: Button = binding.neutralButton
    override val negativeButton: Button = binding.negativeButton
    override val buttonContainer: RelativeLayout = binding.buttonContainer
    override val content: ScrollView = binding.content
    override val root: LinearLayout = binding.root

    init {
        init()
    }

}
