package com.waitty.kitchen.adapter.viewholders

import android.content.res.ColorStateList
import android.view.MotionEvent
import androidx.core.view.ViewCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.waitty.kitchen.R
import com.waitty.kitchen.adapter.PreparingItemOrderAdapter
import com.waitty.kitchen.databinding.CardOrderPreparingBinding
import com.waitty.kitchen.model.OrderDetails
import com.waitty.kitchen.viewmodel.ItemDescriptionViewModel

interface CountDownListener {
    fun onCountdownChanged(position : Int,time : String)
}
class PreparingOrdersViewHolder(private val viewBinding : CardOrderPreparingBinding, private val variablesMap : HashMap<Int,Any?>, private val orderItemDoneListener: WKCheckChangeListener) : GenericOrderViewHolder<OrderDetails>(viewBinding),CountDownListener {

    override fun bind(item: OrderDetails, position: Int) {

       setupRecyclerView(item)
        variablesMap[BR.position] = position
        setVariables(variablesMap)
        setButtonAllDoneEnabled(item)
        viewBinding.orderPreparingVM?.setCountdownListener(position,this)

        viewBinding.orderPreparingVM?.startOrderEtaTimer(position)


    }

    private fun setButtonAllDoneEnabled(item: OrderDetails) {
        val areAllItemsPrepared = areAllItemsPrepared(item)
        viewBinding.btnAllDone.isEnabled = areAllItemsPrepared
        viewBinding.btnAllDone.alpha = if(areAllItemsPrepared) 1f else 0.4f
    }

    private fun setupRecyclerView(item : OrderDetails) {
        val preparingOrderItemsAdapter = PreparingItemOrderAdapter(R.layout.layout_item_order_header, item.orderItems.toMutableList(), orderItemDoneListener)
        val orderItemsRecyclerView = viewBinding.rvPreparingOrderDetails
        val layoutManager = LinearLayoutManager(viewBinding.root.context, RecyclerView.VERTICAL,false)
        layoutManager.stackFromEnd = false
        orderItemsRecyclerView.layoutManager = layoutManager
        ViewCompat.setNestedScrollingEnabled(orderItemsRecyclerView,true)
        orderItemsRecyclerView.setHasFixedSize(true)
        orderItemsRecyclerView.addOnItemTouchListener(RecylerViewOnTouchListener())
        val itemDescriptionViewModel = ItemDescriptionViewModel()
        itemDescriptionViewModel.getOrderItems().value = item.orderItems
        preparingOrderItemsAdapter.setVariablesMap(hashMapOf(BR.itemDescriptionVM to itemDescriptionViewModel,BR.itemClickEvent to orderItemDoneListener))
        orderItemsRecyclerView.adapter = preparingOrderItemsAdapter
        preparingOrderItemsAdapter.notifyDataSetChanged()
    }


    private fun areAllItemsPrepared(item : OrderDetails)  : Boolean {

        for(orderItem in item.orderItems) {
            if(!orderItem.isPrepared) return false
        }
        return true
    }



    override fun onCountdownChanged(position: Int,time: String) {
        if(adapterPosition != position) return
        if(time.isNullOrEmpty()) return
        if(time.toLowerCase() == itemView.context.getString(R.string.text_time_over)) {

            viewBinding.tvTimer.text = time
        } else  viewBinding.tvTimer.text = "Countdown : $time"

    }

    inner class RecylerViewOnTouchListener : RecyclerView.OnItemTouchListener {
        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            TODO("Not yet implemented")
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            when (e.action) {
                MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(true)
            }
            return false
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            TODO("Not yet implemented")
        }

    }


}