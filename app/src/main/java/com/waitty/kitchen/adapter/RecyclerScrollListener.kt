package com.waitty.kitchen.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


interface ScrollListenerDelegate {

    fun onScrolledToTop(isAtTop : Boolean)

}
class RecyclerScrollListener(private val scrollListenerDelegate: ScrollListenerDelegate) : RecyclerView.OnScrollListener() {


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        var firstVisiblePosition : Int = -1
        if(recyclerView.layoutManager is GridLayoutManager) {
            val gridLayoutManager = recyclerView.layoutManager as? GridLayoutManager
            firstVisiblePosition = gridLayoutManager?.findFirstVisibleItemPosition() ?: -1
        }

        else {
            val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager
            firstVisiblePosition = linearLayoutManager?.findFirstVisibleItemPosition() ?: -1
        }

        val isAtTop = firstVisiblePosition == 0 && recyclerView.getChildAt(0).paddingTop - (0 + recyclerView.paddingTop) == 0
        scrollListenerDelegate.onScrolledToTop(isAtTop)
    }
}