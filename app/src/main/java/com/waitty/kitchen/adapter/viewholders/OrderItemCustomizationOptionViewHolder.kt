package com.waitty.kitchen.adapter.viewholders

import com.waitty.kitchen.databinding.LayoutOrderCustomizationBinding
import com.waitty.kitchen.databinding.LayoutTextItemCustomizationBinding

class OrderItemCustomizationOptionViewHolder(private val viewBinding : LayoutTextItemCustomizationBinding) : GenericOrderViewHolder<String>(viewBinding) {

    override fun bind(item: String, position: Int) {
        viewBinding.tvOrderItemCustomizationOption.text = item
    }

}