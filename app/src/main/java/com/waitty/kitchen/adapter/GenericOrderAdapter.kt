package com.waitty.kitchen.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.waitty.kitchen.adapter.viewholders.GenericOrderViewHolder

abstract class GenericOrderAdapter<T>(private var dataList: MutableList<T>?)
    : RecyclerView.Adapter<GenericOrderViewHolder<T>>() {

     private var variablesMap =  HashMap<Int,Any?>()

    init {
        if(dataList == null)dataList = ArrayList()
    }

    fun getVariablesMap() = variablesMap

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : GenericOrderViewHolder<T>

    override fun onBindViewHolder(holder: GenericOrderViewHolder<T>, position: Int) {
        val item = dataList?.get(position)
        item?.let { holder.bind(it,position) }
    }

    fun setVariablesMap(variablesMap : HashMap<Int,Any?> ) {
        this.variablesMap = variablesMap
    }



//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericOrderViewHolder<T> {
//
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
//        return GenericOrderViewHolder(binding)
//    }


    override fun getItemCount(): Int {
        return dataList?.count() ?: 0
    }



    open fun updateList(newList : MutableList<T>?) {
        dataList?.clear()
        newList?.let { dataList?.addAll(it) }
        notifyDataSetChanged()

    }


    fun getItem(position: Int) : T? {
        return dataList?.get(position)
    }

    fun clear() {
        dataList?.clear()
        notifyDataSetChanged()
    }

}