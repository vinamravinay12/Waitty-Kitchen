package com.waitty.kitchen.adapter.viewholders

import androidx.databinding.library.baseAdapters.BR
import com.waitty.kitchen.databinding.LayoutItemNewOrderBinding
import com.waitty.kitchen.model.OrderItem

class NewOrderItemViewHolder(private val viewBinding : LayoutItemNewOrderBinding) : GenericOrderViewHolder<OrderItem>(viewBinding) {

    override fun bind(item: OrderItem, position: Int) {
        viewBinding.setVariable(BR.orderItem,item)
    }
}