package com.waitty.kitchen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.translabtechnologies.visitormanagementsystem.vmshost.database.SharedPreferenceManager
import com.waitty.kitchen.R
import com.waitty.kitchen.adapter.GenericOrderAdapter
import com.waitty.kitchen.adapter.PreparedOrdersAdapter
import com.waitty.kitchen.adapter.WKFilterResultListener
import com.waitty.kitchen.model.OrderDetails
import com.waitty.kitchen.model.OrderPreparationStatus
import com.waitty.kitchen.model.WaittyAPIResponse
import com.waitty.kitchen.model.postdata.GetOrderPostData
import com.waitty.kitchen.retrofit.ApiClient
import com.waitty.kitchen.utility.Utility
import com.waitty.kitchen.viewmodel.repository.NewOrderRepository

class PreparedOrdersViewModel : ListOrderViewModel(), WKFilterResultListener<OrderDetails> {

    private val preparedOrdersList = MutableLiveData<List<OrderDetails>>()
    private val orderId = MutableLiveData<String>()
    private val waiterId = MutableLiveData<String>()
    private val tableId = MutableLiveData<String>()
    private val orderType: MutableLiveData<String> = MutableLiveData()
    private val totalOrderItems = MutableLiveData<String>()
    private var preparedOrderAdapter : GenericOrderAdapter<OrderDetails>? = null
    private val selectedOrderDetails = MutableLiveData<OrderDetails>()


    init {
        preparedOrderAdapter = PreparedOrdersAdapter(getViewType(), preparedOrdersList.value?.toMutableList(),this)
    }


    fun setOrdersList(orders : List<OrderDetails>) {
        val recentOrders = orders.filter { orderDetails -> Utility.isCreatedToday(orderDetails.createdAt) }
        preparedOrdersList.value = recentOrders.filter { orderDetails -> Utility.hasOrderItems(orderDetails) }
    }

    fun getPreparedOrdersList() = preparedOrdersList

    fun getPreparedOrderAdapter() = preparedOrderAdapter

    fun getSelectedOrderDetails() = selectedOrderDetails


    fun getOrderId(position : Int) : MutableLiveData<String>{
        if(position >= preparedOrdersList.value?.size ?: 0) return orderId
        orderId.value =  "Order No #" + preparedOrdersList.value?.get(position)?.id ?: ""
        return orderId
    }


    fun getWaiterDetails(position: Int): LiveData<String> {
        if(position >= preparedOrdersList.value?.size ?: 0) return waiterId

        else if (preparedOrdersList.value.isNullOrEmpty()) {
            return waiterId
        }
        waiterId.value = preparedOrdersList.value?.get(position)?.waiter?.name
        return waiterId
    }

    fun getTableId(position: Int): MutableLiveData<String> {
        if (preparedOrdersList.value.isNullOrEmpty() || preparedOrdersList.value?.get(position)?.table == null) return tableId

        else if(position >= preparedOrdersList.value?.size ?: 0) return tableId
        tableId.value = StringBuilder().append("T").append(" ").append(preparedOrdersList.value?.get(position)?.table?.name).toString()
        return tableId
    }

    fun getTotalOrderItems(position: Int) : MutableLiveData<String> {
        if(position >= preparedOrdersList.value?.size ?: 0) return totalOrderItems
        totalOrderItems.value = ("x" + preparedOrdersList.value?.get(position)?.orderItems?.size ?: 0) as String?
        return totalOrderItems
    }


    fun getOrderType(position: Int): LiveData<String> {
        if(position >= preparedOrdersList.value?.size ?: 0) return orderType
        if (preparedOrdersList.value.isNullOrEmpty()) return orderType

        orderType.value = "" + preparedOrdersList.value?.get(position)?.orderType
        return orderType
    }



    override fun getViewType(): Int {
        return R.layout.layout_item_order_prepared
    }

    override fun updateList() {
         preparedOrderAdapter?.updateList(preparedOrdersList.value?.toMutableList())
    }

    fun getPreparedOrders(sharedPreferenceManager: SharedPreferenceManager) : MutableLiveData<WaittyAPIResponse> {
        val repository = NewOrderRepository(ApiClient.getAPIInterface(),orderStatus = OrderPreparationStatus.ORDER_PREPARED,token = "")
        return repository.getOrders(GetOrderPostData(20,1),sharedPreferenceManager)
    }

    override fun onResultsFiltered(filteredList: List<OrderDetails>) {
       preparedOrdersList.value = filteredList
        (preparedOrderAdapter as? PreparedOrdersAdapter)?.updateFilterList(filteredList.toMutableList())

    }
}