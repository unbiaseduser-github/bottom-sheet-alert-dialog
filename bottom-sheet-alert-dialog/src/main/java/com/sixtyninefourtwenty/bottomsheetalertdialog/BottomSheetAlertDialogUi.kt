package com.sixtyninefourtwenty.bottomsheetalertdialog

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.sixtyninefourtwenty.bottomsheetalertdialog.databinding.BottomSheetAlertDialogFullscreenUiBinding
import com.sixtyninefourtwenty.bottomsheetalertdialog.databinding.BottomSheetAlertDialogNotFullscreenUiBinding
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed interface BottomSheetAlertDialogCommon {
    val context: Context
    val root: View
    val title: TextView
    val content: ScrollView
    val buttonContainer: RelativeLayout
    val positiveButton: Button
    val neutralButton: Button
    val negativeButton: Button

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

    fun getButton(whichButton: DialogButton): Button {
        return when (whichButton) {
            DialogButton.POSITIVE -> positiveButton
            DialogButton.NEGATIVE -> negativeButton
            DialogButton.NEUTRAL -> neutralButton
        }
    }

    fun setButtonAppearance(whichButton: DialogButton, properties: ButtonAppearanceProperties) {
        with(getButton(whichButton)) {
            visibility = View.VISIBLE
            when {
                properties.textRes != 0 -> setText(properties.textRes)
                properties.text != null -> text = properties.text
            }
        }
    }

    @SuppressLint("Recycle")
    fun init() {
        context.obtainStyledAttributes(intArrayOf(R.attr.bsadTitleStyle)).useCompat {
            val textAppearance = it.getResourceId(0, com.google.android.material.R.style.TextAppearance_MaterialComponents_Headline5)
            title.setTextAppearanceCompat(context, textAppearance)
        }
    }

    companion object {
        fun create(context: Context, isFullscreen: Boolean) =
            if (isFullscreen) BottomSheetAlertDialogFullscreenUi(context) else BottomSheetAlertDialogNotFullscreenUi(context)
    }

}

private class BottomSheetAlertDialogFullscreenUi(override val context: Context) :
    BottomSheetAlertDialogCommon {

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

@SuppressLint("Recycle")
private class BottomSheetAlertDialogNotFullscreenUi(override val context: Context) :
    BottomSheetAlertDialogCommon {

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

@OptIn(ExperimentalContracts::class)
private inline fun <R> TypedArray.useCompat(block: (TypedArray) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return if (this is AutoCloseable) {
        use(block)
    } else {
        try {
            block(this)
        } finally {
            recycle()
        }
    }
}

private fun TextView.setTextAppearanceCompat(context: Context, @StyleRes resId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(resId)
    } else {
        @Suppress("DEPRECATION")
        setTextAppearance(context, resId)
    }
}
