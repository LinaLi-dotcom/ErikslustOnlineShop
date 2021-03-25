package com.example.erikslustonlineshop.Utilils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.util.jar.Attributes

class TextViewBold(context: Context, attrs: AttributeSet): AppCompatTextView(context, attrs) {
    init {
        applyFont()
    }
    private fun applyFont() {
        val typeface = Typeface.createFromAsset(context.assets, "Montserrat/Montserrat-Bold.ttf")
        setTypeface(typeface)
    }
}