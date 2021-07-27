package com.example.mynotes

import android.content.Context
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import com.example.mynotes.ui.Label

class PopUpLabelLayout(private val context: Context): PopupWindow() {
    private val linearLayout = LinearLayout(context)
    init {
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        contentView = linearLayout
    }

    fun addItem(label:Label) = apply {
        val item = TextView(context)
        item.textSize = 20F
        item.text = label.labelName
        item.setOnClickListener{
            Toast.makeText(context,"clicked ${label.labelName} in pop up",Toast.LENGTH_SHORT).show()
        }
        linearLayout.addView(item)
    }

}