package com.sixtyninefourtwenty.bottomsheetalertdialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
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
    protected abstract val content: ViewGroup
    protected abstract val buttonContainer: ViewGroup
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

    @Suppress("DEPRECATION")
    private fun getButton(whichButton: DialogButtonEnum): Button {
        return when (whichButton) {
            is DialogButton -> {
                when (whichButton) {
                    DialogButton.POSITIVE -> positiveButton
                    DialogButton.NEGATIVE -> negativeButton
                    DialogButton.NEUTRAL -> neutralButton
                }
            }
            is BottomSheetAlertDialogButton -> {
                when (whichButton) {
                    BottomSheetAlertDialogButton.POSITIVE -> positiveButton
                    BottomSheetAlertDialogButton.NEGATIVE -> negativeButton
                    BottomSheetAlertDialogButton.NEUTRAL -> neutralButton
                }
            }
        }
    }

    fun setButtonAppearance(whichButton: DialogButtonEnum, textRes: Int, text: CharSequence?) {
        with(getButton(whichButton)) {
            visibility = View.VISIBLE
            when {
                textRes != 0 -> setText(textRes)
                text != null -> setText(text)
            }
        }
    }

    fun setButtonAppearance(whichButton: DialogButtonEnum, properties: ButtonAppearanceProperties) {
        setButtonAppearance(whichButton, properties.textRes, properties.text)
    }

    fun setButtonOnClickListener(whichButton: DialogButtonEnum, onClickListener: View.OnClickListener) {
        getButton(whichButton).setOnClickListener(onClickListener)
    }

    fun setButtonEnabled(whichButton: DialogButtonEnum, enabled: Boolean) {
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
            context: Context,
            view: View,
            isViewHeightDynamic: Boolean,
            notFullscreenListener: () -> Unit,
            fullscreenListener: () -> Unit
        ): BottomSheetAlertDialogCommonUi =
            BottomSheetAlertDialogUiImpl(context, view, isViewHeightDynamic, notFullscreenListener, fullscreenListener)
    }

}

private class BottomSheetAlertDialogUiImpl(
    context: Context,
    view: View,
    isViewHeightDynamic: Boolean,
    notFullscreenListener: () -> Unit,
    fullscreenListener: () -> Unit
) :
    BottomSheetAlertDialogCommonUi(context) {

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
        setContentView(view)
        val windowHeight = context.getWindowHeight()
        if (!isViewHeightDynamic) {
            root.post {
                content.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    this.matchConstraintMaxHeight = windowHeight - title.height - buttonContainer.height
                }
                val contentView = content[0]
                if (contentView.height > content.height) {
                    fullscreenListener()
                } else {
                    notFullscreenListener()
                }
            }
        } else {
            root.post {
                content.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    this.matchConstraintMaxHeight = windowHeight - title.height - buttonContainer.height
                }
                content.viewTreeObserver.addOnGlobalLayoutListener {
                    val contentView = content[0]
                    if (contentView.height > content.height) {
                        fullscreenListener()
                    } else {
                        notFullscreenListener()
                    }
                }
            }
        }
    }

}
