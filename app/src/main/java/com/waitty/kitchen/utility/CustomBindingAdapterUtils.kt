package com.waitty.kitchen.utility

import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

class CustomBindingAdapterUtils {

    @BindingAdapter("app:backgroundTint")
    fun setBackgroundTint(view: View, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.backgroundTintList = ColorStateList.valueOf(color)
        }
    }

    @BindingAdapter("app:textColor")
    fun setTextColor(textView : TextView, color : Int) {
        textView.setTextColor(color)
    }

}