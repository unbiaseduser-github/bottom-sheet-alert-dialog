package com.sixtyninefourtwenty.bottomsheetalertdialog.misc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sixtyninefourtwenty.bottomsheetalertdialog.BottomSheetAlertDialogFragmentViewBuilder

abstract class BaseBottomSheetAlertDialogFragment<VB : ViewBinding> : BottomSheetDialogFragment() {

    private var _root: View? = null
    private val root: View get() = _root!!
    private var _binding: VB? = null
    private val binding: VB get() = _binding!!

    protected abstract fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): VB
    protected abstract fun initDialog(binding: VB): BottomSheetAlertDialogFragmentViewBuilder
    protected abstract fun onViewCreated(binding: VB, savedInstanceState: Bundle?)
    protected open fun onDestroyView(binding: VB) = Unit

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = onCreateView(inflater, container)
        _root = initDialog(binding).rootView
        return root
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewCreated(binding, savedInstanceState)
    }

    final override fun onDestroyView() {
        super.onDestroyView()
        onDestroyView(binding)
        _binding = null
        _root = null
    }

}