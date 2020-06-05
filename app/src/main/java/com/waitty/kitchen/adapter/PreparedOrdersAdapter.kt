package com.waitty.kitchen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import com.waitty.kitchen.adapter.viewholders.GenericOrderViewHolder
import com.waitty.kitchen.adapter.viewholders.PreparedOrdersViewHolder
import com.waitty.kitchen.adapter.viewholders.PreparingOrdersViewHolder
import com.waitty.kitchen.databinding.CardOrderPreparingBinding
import com.waitty.kitchen.databinding.LayoutItemOrderPreparedBinding
import com.waitty.kitchen.model.OrderDetails

interface WKFilterResultListener<E> {
    fun onResultsFiltered(filteredList : List<E>)
}
class PreparedOrdersAdapter(private val layoutRes : Int, private var dataList: MutableList<OrderDetails>?, private val filterResultListener: WKFilterResultListener<OrderDetails>) : GenericOrderAdapter<OrderDetails>(dataList),Filterable {


    private val completeList = ArrayList<OrderDetails>()
    init {
        if(dataList == null)dataList = ArrayList()
        completeList.clear()
        dataList?.let { completeList.addAll(it) }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericOrderViewHolder<OrderDetails> {
        val inflater = LayoutInflater.from(parent.context)
        val bindingPreparedOrders = DataBindingUtil.inflate<LayoutItemOrderPreparedBinding>(inflater, layoutRes,parent,false)
        return PreparedOrdersViewHolder(bindingPreparedOrders,variablesMap = getVariablesMap())
    }

    override fun getFilter(): Filter {
        return OrdersFilter(completeList,filterResultListener)
    }

     fun updateFilterList(newList: MutableList<OrderDetails>?) {
        dataList?.clear()
        newList?.let {
            dataList?.addAll(it)

        }

        notifyDataSetChanged()
    }

    override fun updateList(newList : MutableList<OrderDetails>?) {
        dataList?.clear()
        completeList.clear()
        newList?.let {
            dataList?.addAll(it)
            completeList.addAll(it)
        }

        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

     override fun onBindViewHolder(holder: GenericOrderViewHolder<OrderDetails>, position: Int) {
        val item = dataList?.get(position)
        item?.let { holder.bind(it,position) }
    }

}