package com.waitty.kitchen.adapter.viewholders

import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.waitty.kitchen.R
import com.waitty.kitchen.adapter.NewOrderItemAdapter
import com.waitty.kitchen.databinding.CardOrderNewPreparingBinding
import com.waitty.kitchen.model.OrderDetails

class NewOrderViewHolder(private val viewDataBinding: CardOrderNewPreparingBinding, private val variablesMap : HashMap<Int,Any?>) : GenericOrderViewHolder<OrderDetails>(viewDataBinding) {


    override fun bind(item: OrderDetails,position : Int) {

        val newOrderItemAdapter = NewOrderItemAdapter(R.layout.layout_item_new_order, item.orderItems.toMutableList())
        val orderItemsRecyclerView = viewDataBinding.rvOrderItems
        variablesMap[BR.position] = position
        setVariables(variablesMap)
        orderItemsRecyclerView?.layoutManager = LinearLayoutManager(viewDataBinding.root.context, RecyclerView.VERTICAL,false)
        orderItemsRecyclerView?.setHasFixedSize(true)
        orderItemsRecyclerView?.adapter = newOrderItemAdapter
        newOrderItemAdapter.notifyDataSetChanged()
    }

}