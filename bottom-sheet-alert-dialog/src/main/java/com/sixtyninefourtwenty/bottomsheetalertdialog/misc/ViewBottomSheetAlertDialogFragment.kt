package com.sixtyninefourtwenty.bottomsheetalertdialog.misc

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sixtyninefourtwenty.bottomsheetalertdialog.BottomSheetAlertDialogFragmentViewBuilder

/**
 * Convenience [BottomSheetDialogFragment] subclass for use with [BottomSheetAlertDialogFragmentViewBuilder].
 *
 * Mirrors `ViewFragment` from [base-fragments](https://gitlab.com/unbiaseduser/base-fragments).
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class ViewBottomSheetAlertDialogFragment<V : View>(
    private val viewCreation: (Context) -> V
) : BottomSheetDialogFragment() {

    private var _root: View? = null
    private val root: View get() = _root!!
    private var _view: V? = null

    fun requireTypedView() = _view ?: error("View accessed before onCreateView or after onDestroyView")

    open fun onCreateView(view: V, inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = Unit

    protected abstract fun createDialog(view: V): BottomSheetAlertDialogFragmentViewBuilder

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _view = viewCreation(requireContext())
        onCreateView(requireTypedView(), inflater, container, savedInstanceState)
        _root = createDialog(requireTypedView()).rootView
        return root
    }

    open fun onDestroyView(view: V) = Unit

    final override fun onDestroyView() {
        onDestroyView(requireTypedView())
        _view = null
        _root = null
        super.onDestroyView()
    }

}