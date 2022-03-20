package com.beko.coex.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beko.coex.databinding.FragmentErrorDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ErrorDialog(
    private var title:String ,
    private var content:String) : BottomSheetDialogFragment() {
    private lateinit var binding :FragmentErrorDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentErrorDialogBinding.inflate(layoutInflater)
        val view = binding.root
        binding.errorTitle.text = title
        binding.errorContent.text = content

        return view
    }

}