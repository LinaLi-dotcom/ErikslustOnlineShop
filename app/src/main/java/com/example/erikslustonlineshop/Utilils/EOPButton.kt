package com.example.erikslustonlineshop.Utilils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class EOPButton (context: Context, attrs: AttributeSet) : AppCompatButton(context, attrs) {
    init {
        applyfont()
    }
    private fun applyfont() {
        val typeface = Typeface.createFromAsset(context.assets, "Montserrat/Montserrat-SemiBoldItalic.ttf")
                setTypeface(typeface)
    }
}