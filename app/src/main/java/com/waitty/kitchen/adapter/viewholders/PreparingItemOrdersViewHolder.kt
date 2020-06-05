package com.waitty.kitchen.adapter.viewholders

import androidx.core.view.ViewCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.waitty.kitchen.adapter.OrderItemCustomizationOptionsAdapter
import com.waitty.kitchen.databinding.LayoutItemOrderHeaderBinding
import com.waitty.kitchen.model.OrderItem
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


interface WKCheckChangeListener {
    fun onCheckChanged(item : Any?)
}
class PreparingItemOrdersViewHolder(private val viewBinding: LayoutItemOrderHeaderBinding,private val variablesMap : HashMap<Int,Any?>) : GenericOrderViewHolder<OrderItem>(viewBinding) {


    override fun bind(item: OrderItem, position: Int) {
        viewBinding.layoutItemDescription.orderItem = item
        variablesMap[BR.position] = position
        setVariables(variablesMap)
        val map = viewBinding.itemDescriptionVM?.getOptionsMap(item)
        val list = map?.let { viewBinding.itemDescriptionVM?.getAllItemsList(it) }
        setupRecyclerView(viewBinding.layoutOrderDescription?.rvOrderCustomizationOptions,map,list)

    }

    private fun setupRecyclerView(rvOrderCustomizationOptions: RecyclerView?, map: TreeMap<String, List<String>>?, list: ArrayList<String>?) {

        val orderItemCustomizationsAdapter = map?.let { OrderItemCustomizationOptionsAdapter(list?.toMutableList(), it) }
        rvOrderCustomizationOptions?.layoutManager = LinearLayoutManager(itemView.context)
        rvOrderCustomizationOptions?.setHasFixedSize(true)
        if (rvOrderCustomizationOptions != null) {
            ViewCompat.setNestedScrollingEnabled(rvOrderCustomizationOptions,false)
        }
        rvOrderCustomizationOptions?.adapter = orderItemCustomizationsAdapter

    }




}