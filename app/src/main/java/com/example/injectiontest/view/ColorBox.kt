package com.example.injectiontest.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.example.injectiontest.R
import com.example.injectiontest.databinding.ColorBoxBinding

class ColorBox @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    val inflate = ColorBoxBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.withStyledAttributes(
            attrs,
            R.styleable.ColorBox
        ) {
            val color = this.getColor(R.styleable.ColorBox_bgColor, Color.MAGENTA)
            val text = this.getString(R.styleable.ColorBox_text) ?: "ColorBox"
            setBgColor(color)
            setText(text)
        }
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_purple))
        setWillNotDraw(false)
    }

    fun setBgColor(color: Int) {
        inflate.colorView.setBackgroundColor(color)
    }

    fun setText(text: String){
        inflate.textView.text = text
    }
//    fun setBgColor(@ColorRes color: Int){
//        this@ColorBox.setBackgroundColor(color)
//    }
}
