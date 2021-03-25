package com.example.erikslustonlineshop.Utilils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView

class EOPEditText(context: Context, attrs: AttributeSet): AppCompatEditText(context, attrs) {
    init {
        applyFont()
    }
    private fun applyFont() {
        val typeface = Typeface.createFromAsset(context.assets, "Montserrat/Montserrat-Regular.ttf")
        setTypeface(typeface)
    }
}