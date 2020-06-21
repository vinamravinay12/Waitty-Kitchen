package com.waitty.kitchen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import com.waitty.kitchen.adapter.viewholders.GenericOrderViewHolder
import com.waitty.kitchen.adapter.viewholders.NewOrderViewHolder
import com.waitty.kitchen.adapter.viewholders.PreparingOrdersViewHolder
import com.waitty.kitchen.databinding.CardOrderNewPreparingBinding
import com.waitty.kitchen.databinding.CardOrderPreparingBinding
import com.waitty.kitchen.model.OrderDetails
import com.waitty.kitchen.model.OrderItem
import com.waitty.kitchen.utility.WKCheckChangeListener


class PreparingOrdersAdapter(@LayoutRes private val layoutRes : Int,private val dataList : MutableList<OrderDetails>?) : GenericOrderAdapter<OrderDetails>(dataList){


    private lateinit var checkChangeListener : WKCheckChangeListener

    fun setCheckChangeListener(checkChangeListener : WKCheckChangeListener) {
        this.checkChangeListener = checkChangeListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericOrderViewHolder<OrderDetails> {
        val inflater = LayoutInflater.from(parent.context)
        val bindingPreparingOrders = DataBindingUtil.inflate<CardOrderPreparingBinding>(inflater, layoutRes,parent,false)
        return PreparingOrdersViewHolder(bindingPreparingOrders,variablesMap = getVariablesMap(),orderItemDoneListener = checkChangeListener)
    }




}