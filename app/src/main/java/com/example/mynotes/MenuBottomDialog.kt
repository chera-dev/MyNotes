package com.example.mynotes

import android.content.Context
import android.widget.LinearLayout
import com.example.mynotes.databinding.BottomMenuItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MenuBottomDialog(context: Context): BottomSheetDialog(context) {
    private val linearLayout = LinearLayout(context)
    init {
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        setContentView(linearLayout)
    }
    fun addItem(operation: Operation) = apply {
        val item = BottomMenuItemBinding.inflate(layoutInflater).root
        item.setText(operation.textId)
        item.setOnClickListener {
            dismiss()
            operation.operation.invoke()
        }
        //item.setCompoundDrawablesRelativeWithIntrinsicBounds(operation.drawableId, 0, 0, 0)
        linearLayout.addView(item)
    }
    data class Operation(val textId: String, val operation: () -> Unit)
}