package com.waitty.kitchen.adapter.viewholders

import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.library.baseAdapters.BR
import com.waitty.kitchen.databinding.LayoutItemOrderPreparedBinding
import com.waitty.kitchen.model.OrderDetails

class PreparedOrdersViewHolder(private val viewBinding : LayoutItemOrderPreparedBinding,private val variablesMap :  HashMap<Int,Any?>) : GenericOrderViewHolder<OrderDetails>(viewBinding = viewBinding) {

    override fun bind(item: OrderDetails, position: Int) {
        variablesMap[BR.position] = position
        setVariables(variablesMap)
    }






}