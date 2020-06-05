package com.waitty.kitchen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.waitty.kitchen.R
import com.waitty.kitchen.adapter.PreparedOrderItemsAdapter
import com.waitty.kitchen.constant.WaittyConstants
import com.waitty.kitchen.model.OrderDetails
import com.waitty.kitchen.utility.Utility

class PreparedItemOrdersViewModel : ListOrderViewModel() {

    private val orderDetails = MutableLiveData<OrderDetails>()
    private val orderId = MutableLiveData<String>()
    private val waiterId = MutableLiveData<String>()
    private val orderArrivingTime = MutableLiveData<String>()
    private val tableId = MutableLiveData<String>()
    private val orderQuantity = MutableLiveData<String>()
    private val orderType = MutableLiveData<String>()
    private val comment = MutableLiveData<String>()
    private val itemOrderTotal = MutableLiveData<String>()
    private val taxTotal = MutableLiveData<String>()
    private val orderTotal = MutableLiveData<String>()
    private var rupeeSymbol : String = ""
    private var preparedItemsOrderAdapter : PreparedOrderItemsAdapter? = null


    init { preparedItemsOrderAdapter =  PreparedOrderItemsAdapter(getViewType(), orderDetails.value?.orderItems?.toMutableList())
    }

    fun getAdapter() = preparedItemsOrderAdapter

    fun getOrderDetails() = orderDetails

    fun setRupeeSymbol(symbol : String) {
        this.rupeeSymbol = symbol
    }

    fun getOrderId() : MutableLiveData<String>{
       orderId.value = "Order No #" + orderDetails.value?.id ?: ""
        return orderId
    }

    fun getWaiterDetails(): LiveData<String> {

        waiterId.value = orderDetails.value?.waiter?.name ?: ""
        return waiterId
    }

    fun getTableId(): MutableLiveData<String> {

        tableId.value = StringBuilder().append("T").append(" ").append(orderDetails.value?.table?.name ?: "").toString()
        return tableId
    }

    fun getTotalOrderItems() : MutableLiveData<String> {
        orderQuantity.value = StringBuilder().append("x").append(orderDetails.value?.orderItems?.size ?: 0).toString()
        return orderQuantity
    }


    fun getOrderType(): MutableLiveData<String> {
        orderType.value = "" + orderDetails.value?.orderType
        return orderType
    }

    fun getArrivingTime() : MutableLiveData<String> {
        if(orderDetails.value?.orderArrivingTime?.trim().isNullOrEmpty()) orderArrivingTime.value = ""

        else {
            val arrivalDate = WaittyConstants.dateFormaterServer.parse(Utility.chageUTC_ToLocalDate(WaittyConstants.dateFormaterServer, orderDetails.value?.orderArrivingTime?.trim(), WaittyConstants.dateFormaterServer));
            orderArrivingTime.value = Utility.getStringFrom(arrivalDate,WaittyConstants.DISPLAY_DATE_TIME_FORMAT)
        }

        return orderArrivingTime
    }

    fun getComment() = orderDetails.value?.comment

    fun getItemOrderTotal() : MutableLiveData<String> {
        itemOrderTotal.value = "Item Total  " + rupeeSymbol + " " + (orderDetails.value?.total ?: 0)
        return itemOrderTotal
    }

    fun getTaxTotal() : MutableLiveData<String> {
        taxTotal.value = "Tax(" + orderDetails.value?.taxPercent + ")  " + rupeeSymbol + " " + (orderDetails.value?.tax ?: 0)
        return taxTotal
    }

    fun getOrderTotal() : MutableLiveData<String> {
        orderTotal.value = "Total   " + rupeeSymbol + " " + (orderDetails.value?.orderAmount ?: 0)
        return orderTotal
    }

    override fun getViewType(): Int {
        return R.layout.layout_prepared_order_item
    }

    override fun updateList() {
        orderDetails.value?.orderItems?.toMutableList()?.let { preparedItemsOrderAdapter?.updateList(it) }
    }


}