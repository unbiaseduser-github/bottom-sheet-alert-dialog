package com.sixtyninefourtwenty.bottomsheetalertdialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ScrollView
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
    protected abstract val content: ScrollView
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
    override val content: ScrollView = binding.content
    override val root: View = binding.root

    init {
        init()
        setContentView(view)
        if (!isViewHeightDynamic) {
            root.post {
                content.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    this.matchConstraintMaxHeight = root.height - title.height - buttonContainer.height
                }
                val contentView = content[0]
                if (contentView.height > content.height) {
                    fullscreenListener()
                } else {
                    notFullscreenListener()
                }
            }
        } else {
            val windowHeight = context.getWindowHeight()
            content.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    content.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val contentView = content[0]
                    val maxHeightAllowedForContentFrame = windowHeight - title.height - buttonContainer.height
                    if (contentView.height > maxHeightAllowedForContentFrame) {
                        content.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            this.matchConstraintMaxHeight = maxHeightAllowedForContentFrame
                        }
                    } else {
                        content.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            this.matchConstraintMaxHeight = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        }
                    }
                    if (contentView.height > content.height) {
                        fullscreenListener()
                    } else {
                        notFullscreenListener()
                    }
                    content.viewTreeObserver.addOnGlobalLayoutListener(this)
                }
            })
        }
    }

}
