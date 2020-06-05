package com.waitty.kitchen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import com.waitty.kitchen.R
import com.waitty.kitchen.adapter.viewholders.GenericOrderViewHolder
import com.waitty.kitchen.adapter.viewholders.NewOrderViewHolder
import com.waitty.kitchen.databinding.CardOrderNewPreparingBinding
import com.waitty.kitchen.databinding.LayoutItemNewOrderBinding
import com.waitty.kitchen.model.OrderDetails
import com.waitty.kitchen.model.OrderItem

class NewOrderAdapter(private val layoutRes: Int,dataList : MutableList<OrderDetails>?) : GenericOrderAdapter<OrderDetails>(dataList = dataList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericOrderViewHolder<OrderDetails> {
        val inflater = LayoutInflater.from(parent.context)
        val bindingNewOrder = DataBindingUtil.inflate<CardOrderNewPreparingBinding>(inflater, layoutRes,parent,false)
        return NewOrderViewHolder(bindingNewOrder,variablesMap = getVariablesMap())
    }
}