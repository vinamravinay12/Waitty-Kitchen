package com.waitty.kitchen.viewmodel

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.waitty.kitchen.R
import com.waitty.kitchen.adapter.GenericOrderAdapter
import com.waitty.kitchen.adapter.NewOrderAdapter
import com.waitty.kitchen.model.OrderDetails
import com.waitty.kitchen.model.OrderPreparationStatus
import com.waitty.kitchen.model.WaittyAPIResponse
import com.waitty.kitchen.model.postdata.GetOrderPostData
import com.waitty.kitchen.retrofit.API
import com.waitty.kitchen.retrofit.ApiClient
import com.waitty.kitchen.retrofit.ApiInterface
import com.waitty.kitchen.utility.WKItemClickListener
import com.waitty.kitchen.viewmodel.repository.NewOrderRepository
import java.lang.StringBuilder

abstract class ListOrderViewModel : ViewModel() {
    abstract fun getViewType(): Int
    abstract fun updateList()
}


class NewOrderViewModel : ListOrderViewModel() {

    private val newOrderListLiveData: MutableLiveData<List<OrderDetails>> = MutableLiveData()
    private val tableId: MutableLiveData<String> = MutableLiveData()
    private val orderType: MutableLiveData<String> = MutableLiveData()
    private val waiterId: MutableLiveData<String> = MutableLiveData()
    private val tableIdPrefix: MutableLiveData<String> = MutableLiveData("T")
    private var orderAdapter: GenericOrderAdapter<OrderDetails>? = null
    private lateinit var clickItemListener : WKItemClickListener
    private var repository : NewOrderRepository? = null
    private var selectedOrder = MutableLiveData<OrderDetails>()

    init {
        orderAdapter = NewOrderAdapter(getViewType(),newOrderListLiveData.value?.toMutableList())

    }


    fun getOrderListData() = newOrderListLiveData
    fun getOrderAdapter() = orderAdapter

    fun setSelectedOrderDetails(position: Int){
       selectedOrder.value =  newOrderListLiveData.value?.get(position)
    }

    public fun getTableId(position: Int): MutableLiveData<String> {
        if (newOrderListLiveData.value.isNullOrEmpty() || newOrderListLiveData.value?.get(position)?.table == null) return tableId
        tableId.value = StringBuilder().append(tableIdPrefix.value).append(" ").append(newOrderListLiveData.value?.get(position)?.table?.name).toString()
        return tableId
    }


    public fun getOrderType(position: Int): LiveData<String> {
        if (newOrderListLiveData.value.isNullOrEmpty()) return orderType

        orderType.value = "" + newOrderListLiveData.value?.get(position)?.orderType
        return orderType
    }

    public fun getWaiterDetails(position: Int): LiveData<String> {
        if (newOrderListLiveData.value.isNullOrEmpty()) {
            return waiterId
        }
        waiterId.value = "Waiter: " + newOrderListLiveData.value?.get(position)?.waiter?.name
        return waiterId
    }

    override fun getViewType(): Int {
        return R.layout.card_order_new_preparing
    }

    override fun updateList() {
        orderAdapter?.updateList(newOrderListLiveData.value?.toMutableList())
    }

    fun fetchNewOrders(token : String) : MutableLiveData<WaittyAPIResponse>? {

        val orderPostData = GetOrderPostData(20,1)
        repository = NewOrderRepository(ApiClient.getAPIInterface(),OrderPreparationStatus.NEW_ORDER,token)
        return repository?.let { it.getOrders(orderPostData) }
    }

    fun updateOrderPreparationStatus(token : String) : MutableLiveData<WaittyAPIResponse>? {
        repository = NewOrderRepository(ApiClient.getAPIInterface(),OrderPreparationStatus.NEW_ORDER,token)
        val jsonObject = JsonObject()
        jsonObject.addProperty(API.ORDER_ID,selectedOrder.value?.id)
        return repository?.updateOrderStatus(jsonObject)
    }




}
