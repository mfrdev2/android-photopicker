package com.mfrdev.photopicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PhotoPickerSelectionFragment(
    private val onSingle:(sheet:BottomSheetDialogFragment)->Unit,
    private val onMultiple:(sheet:BottomSheetDialogFragment)->Unit,
) :
BottomSheetDialogFragment(){
    private lateinit var rootView: View
    private lateinit var buttonSingle:Button
    private lateinit var buttonMultiple:Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_photo_picker_selector,container)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSingle = view.findViewById(R.id.buttonSingle)
        buttonMultiple = view.findViewById(R.id.buttonMultiple)
        buttonSingle.setOnClickListener { onSingle(this) }
        buttonMultiple.setOnClickListener { onMultiple(this) }
    }
}