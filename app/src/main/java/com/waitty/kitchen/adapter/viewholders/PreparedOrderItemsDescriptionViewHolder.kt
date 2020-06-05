package com.waitty.kitchen.adapter.viewholders

import androidx.core.view.ViewCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.waitty.kitchen.adapter.OrderItemCustomizationOptionsAdapter
import com.waitty.kitchen.databinding.LayoutPreparedOrderItemBinding
import com.waitty.kitchen.model.OrderItem
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PreparedOrderItemsDescriptionViewHolder(private val viewBinding : LayoutPreparedOrderItemBinding, private val variablesMap : HashMap<Int,Any?>) : GenericOrderViewHolder<OrderItem>(viewBinding) {

    override fun bind(item: OrderItem, position: Int) {
        variablesMap[BR.position] = position
        setVariables(variablesMap)
        val map = viewBinding.itemDescriptionVM?.getOptionsMap(item)
        val list = map?.let { viewBinding.itemDescriptionVM?.getAllItemsList(it) }
        setupRecyclerView(viewBinding.layoutOrderCustomization?.rvOrderCustomizationOptions,map, list, position)
        
    }

    private fun setupRecyclerView(rvOrderCustomizationOptions: RecyclerView?, map: TreeMap<String, List<String>>?, list: ArrayList<String>?, position : Int) {

        val orderItemCustomizationsAdapter = map?.let { OrderItemCustomizationOptionsAdapter(list?.toMutableList(), it) }
        rvOrderCustomizationOptions?.layoutManager = LinearLayoutManager(itemView.context)
        rvOrderCustomizationOptions?.setHasFixedSize(true)
        if (rvOrderCustomizationOptions != null) {
            ViewCompat.setNestedScrollingEnabled(rvOrderCustomizationOptions,false)
        }
        rvOrderCustomizationOptions?.adapter = orderItemCustomizationsAdapter



    }

}