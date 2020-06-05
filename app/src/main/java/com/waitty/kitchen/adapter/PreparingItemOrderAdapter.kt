package com.waitty.kitchen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.waitty.kitchen.adapter.viewholders.GenericOrderViewHolder
import com.waitty.kitchen.adapter.viewholders.PreparingItemOrdersViewHolder
import com.waitty.kitchen.adapter.viewholders.WKCheckChangeListener
import com.waitty.kitchen.databinding.LayoutItemOrderHeaderBinding
import com.waitty.kitchen.model.OrderItem

class PreparingItemOrderAdapter(private val layoutRes: Int, private val dataList: MutableList<OrderItem>, private val orderItemDoneListener: WKCheckChangeListener) : GenericOrderAdapter<OrderItem>(dataList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericOrderViewHolder<OrderItem> {
        val inflater = LayoutInflater.from(parent.context)
        val bindingPreparingItemOrders = DataBindingUtil.inflate<LayoutItemOrderHeaderBinding>(inflater, layoutRes,parent,false)
        return PreparingItemOrdersViewHolder(bindingPreparingItemOrders,getVariablesMap())
    }



}