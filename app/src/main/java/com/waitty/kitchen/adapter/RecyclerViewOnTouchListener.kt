package com.waitty.kitchen.adapter

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewOnTouchListener() : RecyclerView.OnItemTouchListener {

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(false)
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}