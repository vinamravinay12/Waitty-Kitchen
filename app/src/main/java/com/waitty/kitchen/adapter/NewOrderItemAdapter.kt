package com.waitty.kitchen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import com.waitty.kitchen.adapter.viewholders.GenericOrderViewHolder
import com.waitty.kitchen.adapter.viewholders.NewOrderItemViewHolder
import com.waitty.kitchen.databinding.LayoutItemNewOrderBinding
import com.waitty.kitchen.model.OrderItem

class NewOrderItemAdapter(@LayoutRes private val layoutRes: Int, private val dataList: MutableList<OrderItem>) : GenericOrderAdapter<OrderItem>(dataList = dataList) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericOrderViewHolder<OrderItem> {

        val viewBinding = DataBindingUtil.inflate<LayoutItemNewOrderBinding>(LayoutInflater.from(parent.context),layoutRes,parent,false)
        return NewOrderItemViewHolder(viewBinding)

    }


}