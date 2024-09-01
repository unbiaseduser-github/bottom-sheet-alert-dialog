package com.sixtyninefourtwenty.bottomsheetalertdialog.misc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sixtyninefourtwenty.bottomsheetalertdialog.BottomSheetAlertDialogFragmentViewBuilder

@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class ViewBindingBottomSheetAlertDialogFragment<VB : ViewBinding>(
    private val bindingCreation: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BottomSheetDialogFragment() {

    private var _root: View? = null
    private val root: View get() = _root!!
    private var _binding: VB? = null

    fun requireBinding() = _binding ?: error("Binding object accessed before onCreateView or after onDestroyView")

    open fun onCreateView(binding: VB, inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = Unit

    protected abstract fun createDialog(binding: VB): BottomSheetAlertDialogFragmentViewBuilder

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = bindingCreation(inflater, container, false)
        onCreateView(requireBinding(), inflater, container, savedInstanceState)
        _root = createDialog(requireBinding()).rootView
        return root
    }

    open fun onDestroyView(binding: VB) = Unit

    final override fun onDestroyView() {
        onDestroyView(requireBinding())
        _binding = null
        _root = null
        super.onDestroyView()
    }

}