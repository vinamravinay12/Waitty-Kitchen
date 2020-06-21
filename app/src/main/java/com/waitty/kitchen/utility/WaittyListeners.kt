package com.waitty.kitchen.utility

import android.content.Context
import android.view.View
import com.waitty.kitchen.viewmodel.ClickType

interface WKClickListener {

    fun onClick(clickType: ClickType)
}

interface WKItemClickListener {
    fun onItemClick(position: Int)
}


interface ViewInteractionHandler {
    fun setContext(context: Context)
}


interface KeyItemActionListener {
    fun onKeyEvent(valueEntered: String, data: Any?)
}

interface WKCheckChangeListener {
    fun onCheckChanged(item : Any?)
}

interface WKFilterResultListener<E> {
    fun onResultsFiltered(filteredList : List<E>)
}