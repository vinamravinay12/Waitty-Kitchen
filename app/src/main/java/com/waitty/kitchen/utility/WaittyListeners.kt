package com.waitty.kitchen.utility

import android.view.View
import com.waitty.kitchen.viewmodel.ClickType

interface WKClickListener {

    fun onClick(clickType: ClickType)
}

interface WKItemClickListener {
    fun onItemClick(position: Int)
}