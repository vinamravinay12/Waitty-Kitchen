package com.waitty.kitchen.adapter

import android.text.TextUtils
import android.widget.Filter
import com.waitty.kitchen.model.OrderDetails

class OrdersFilter(private val completeList : MutableList<OrderDetails>?,private val filterResultListener : WKFilterResultListener<OrderDetails>) : Filter() {

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val filteredList = ArrayList<OrderDetails>()
        if(TextUtils.isEmpty(constraint)) {
            completeList?.let { filteredList.addAll(it) }
        }
        else {
            val filterPattern = constraint.toString().toLowerCase().trim()
            completeList?.let { itemList ->
                for(item  in  itemList) {
                    if(item.id.toString().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
        }

        val filteredResults = FilterResults()
        filteredResults.values = filteredList
        return filteredResults
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
       filterResultListener.onResultsFiltered(results?.values as List<OrderDetails>)
    }
}