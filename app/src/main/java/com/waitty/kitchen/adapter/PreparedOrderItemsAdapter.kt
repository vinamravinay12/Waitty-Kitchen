package com.waitty.kitchen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.waitty.kitchen.adapter.viewholders.GenericOrderViewHolder
import com.waitty.kitchen.adapter.viewholders.PreparedOrderItemsDescriptionViewHolder
import com.waitty.kitchen.databinding.LayoutPreparedOrderItemBinding
import com.waitty.kitchen.model.OrderItem

class PreparedOrderItemsAdapter(private val layoutRes: Int, private val dataList: MutableList<OrderItem>?) : GenericOrderAdapter<OrderItem>(dataList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericOrderViewHolder<OrderItem> {
        val inflater = LayoutInflater.from(parent.context)
        val bindingPreparedItemsOrder = DataBindingUtil.inflate<LayoutPreparedOrderItemBinding>(inflater, layoutRes,parent,false)
        return PreparedOrderItemsDescriptionViewHolder(bindingPreparedItemsOrder,getVariablesMap())
    }
}