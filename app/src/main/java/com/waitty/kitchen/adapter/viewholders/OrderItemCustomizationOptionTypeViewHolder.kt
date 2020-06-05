package com.waitty.kitchen.adapter.viewholders

import com.waitty.kitchen.adapter.viewholders.GenericOrderViewHolder
import com.waitty.kitchen.databinding.LayoutOrderCustomizationBinding
import com.waitty.kitchen.databinding.LayoutOrderCustomizationHeaderBinding

class OrderItemCustomizationOptionTypeViewHolder(private val viewBinding : LayoutOrderCustomizationHeaderBinding) : GenericOrderViewHolder<String>(viewBinding) {

    override fun bind(item: String, position: Int) {
        viewBinding.tvOrderItemDescriptionHeader.text = item
    }
}