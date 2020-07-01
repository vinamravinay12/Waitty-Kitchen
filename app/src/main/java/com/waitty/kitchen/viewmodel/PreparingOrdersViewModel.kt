package com.waitty.kitchen.viewmodel

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.JsonObject
import com.translabtechnologies.visitormanagementsystem.vmshost.database.SharedPreferenceManager
import com.waitty.kitchen.R
import com.waitty.kitchen.adapter.GenericOrderAdapter
import com.waitty.kitchen.adapter.PreparingOrdersAdapter
import com.waitty.kitchen.adapter.viewholders.CountDownListener
import com.waitty.kitchen.constant.WaittyConstants
import com.waitty.kitchen.model.OrderDetails
import com.waitty.kitchen.model.OrderItem
import com.waitty.kitchen.model.OrderPreparationStatus
import com.waitty.kitchen.model.WaittyAPIResponse
import com.waitty.kitchen.model.postdata.EtaMinutesPostData
import com.waitty.kitchen.model.postdata.GetOrderPostData
import com.waitty.kitchen.retrofit.API
import com.waitty.kitchen.retrofit.ApiClient
import com.waitty.kitchen.utility.Utility
import com.waitty.kitchen.viewmodel.repository.NewOrderRepository
import java.util.*
import java.util.concurrent.TimeUnit

interface ViewInteractionHandler {
    fun setContext(context: Context)
}


interface KeyItemActionListener {
    fun onKeyEvent(valueEntered: String, data: Any?)
}

class PreparingOrdersViewModel : ListOrderViewModel(), ViewInteractionHandler, KeyItemActionListener {

    private val orderDetailsList = MutableLiveData<List<OrderDetails>>()
    private val tableId: MutableLiveData<String> = MutableLiveData()
    private val orderType: MutableLiveData<String> = MutableLiveData()
    private val waiterId: MutableLiveData<String> = MutableLiveData()
    private val tableIdPrefix: MutableLiveData<String> = MutableLiveData("T")
    private var orderAdapter: GenericOrderAdapter<OrderDetails>? = null
    private var repository: NewOrderRepository? = null
    private val selectedOrder = MutableLiveData<OrderDetails>()

    private val etaInMinutes = MutableLiveData<String>("")
    private val countdownTimer: CountDownTimer? = null
    private var context: Context? = null
    private val setEtaResponseData = MediatorLiveData<WaittyAPIResponse>()
    private var countdownListenerList = LinkedList<CountDownListener>()
    private var etaMinutesSetData = MutableLiveData< WaittyAPIResponse>()
    private var countdownTimerArray = LinkedList<EtaCountDownTimer>()



    init {
        orderAdapter = PreparingOrdersAdapter(getViewType(), orderDetailsList.value?.toMutableList())
    }


    fun setOrdersList(orders : List<OrderDetails>) {
        val recentOrders = orders.filter { orderDetails -> Utility.isCreatedToday(orderDetails.createdAt) }
        orderDetailsList.value = recentOrders.filter { orderDetails -> Utility.hasOrderItems(orderDetails) }
    }

    fun setCountdownListener(position: Int, countDownListener: CountDownListener) {
        countdownListenerList.add(position, countDownListener)
    }

    fun getEtaMinutesSetData() = etaMinutesSetData

    fun getEtaInMinutes(position: Int): MutableLiveData<String> {
        return MutableLiveData(orderDetailsList.value?.get(position)?.orderArrivingTimeMinits ?: "")
    }

    fun setEtaInMinutes(eta: String, position: Int) {
        orderDetailsList.value?.get(position)?.orderArrivingTimeMinits = eta
    }

    fun setSelectedOrderDetails(position: Int) {
        selectedOrder.value = orderDetailsList.value?.get(position)
    }

    fun getOrderDetailsList() = orderDetailsList

    fun getOrderAdapter() = orderAdapter

    fun getTableId(position: Int): MutableLiveData<String> {
        if (orderDetailsList.value.isNullOrEmpty() || orderDetailsList.value?.get(position)?.table == null) return tableId
        tableId.value = StringBuilder().append(tableIdPrefix.value).append(" ").append(orderDetailsList.value?.get(position)?.table?.name).toString()
        return tableId
    }


    fun getOrderType(position: Int): LiveData<String> {
        if (orderDetailsList.value.isNullOrEmpty()) return orderType

        orderType.value = "" + orderDetailsList.value?.get(position)?.orderType
        return orderType
    }

    fun getWaiterDetails(position: Int): LiveData<String> {
        if (orderDetailsList.value.isNullOrEmpty()) {
            return waiterId
        }
        waiterId.value = "Waiter: " + orderDetailsList.value?.get(position)?.waiter?.name
        return waiterId
    }


    override fun getViewType(): Int {
        return R.layout.card_order_preparing
    }

    override fun updateList() {
        orderAdapter?.updateList(orderDetailsList.value?.toMutableList())
    }

    fun fetchPreparingOrders(token: String): MutableLiveData<WaittyAPIResponse>? {

        val orderPostData = GetOrderPostData(20, 1)
        repository = NewOrderRepository(ApiClient.getAPIInterface(), OrderPreparationStatus.ORDER_PREPARING, token)
        return repository?.let { it.getOrders(orderPostData) }
    }

    fun updateOrderPreparationStatus(token: String): MutableLiveData<WaittyAPIResponse>? {
        repository = NewOrderRepository(ApiClient.getAPIInterface(), OrderPreparationStatus.ORDER_PREPARING, token)
        val jsonObject = JsonObject()
        jsonObject.addProperty(API.ORDER_ID, selectedOrder.value?.id)
        return repository?.updateOrderStatus(jsonObject)
    }

    fun saveToPreparedOrders(sharedPreferenceManager: SharedPreferenceManager) {
        repository = NewOrderRepository(ApiClient.getAPIInterface(), OrderPreparationStatus.ORDER_PREPARED, "")
        selectedOrder.value?.let {
            repository?.savePreparedOrders(it, sharedPreferenceManager)
        }
    }

    fun setOrderItemDone(token: String?, orderItem: OrderItem?): MutableLiveData<WaittyAPIResponse>? {
        val repository = token?.let { NewOrderRepository(ApiClient.getAPIInterface(), OrderPreparationStatus.ORDER_PREPARING, it) }
        val postData = JsonObject()
        postData.addProperty(API.ORDER_ITEM_ID, orderItem?.id)
        return repository?.updateOrderItem(postData)
    }

    fun isOrderDoneButtonEnabled(position: Int): MediatorLiveData<Boolean> {
        val allItemsPrepared = MediatorLiveData<Boolean>()
        allItemsPrepared.addSource(orderDetailsList, Observer {
            allItemsPrepared.value = areAllOrdersItemsPrepared(it, position)
        })
        return allItemsPrepared
    }

    private fun areAllOrdersItemsPrepared(orderDetailsList: List<OrderDetails>, position: Int): Boolean {
        val orderDetail = orderDetailsList.get(position)
        for (item in orderDetail.orderItems) {
            if (!item.isPrepared) return false
        }

        return true
    }


    fun setOrderArrivingTime(position: Int, arrivingTime: String?) {
        orderDetailsList.value?.get(position)?.orderArrivingTime = arrivingTime
    }


    fun startOrderEtaTimerForAllOrders() {

        if (orderDetailsList.value == null) return

        orderDetailsList.value?.let {
            for (position in it.indices) {
                startOrderEtaTimer(position)
            }
        }
    }


    fun startOrderEtaTimer(position: Int) {

        val orderDetail = orderDetailsList.value?.get(position)
        if (orderDetail?.orderArrivingTime.isNullOrEmpty()) return
        val arrivalDate = WaittyConstants.dateFormaterServer.parse(Utility.chageUTC_ToLocalDate(WaittyConstants.dateFormaterServer, orderDetail?.orderArrivingTime?.trim(), WaittyConstants.dateFormaterServer));
        val currentDate = WaittyConstants.dateFormaterServer.parse(WaittyConstants.dateFormaterServer.format(Calendar.getInstance().time))
        if (arrivalDate.before(currentDate)) {
            orderDetail?.orderEta = context?.getString(R.string.text_time_over)
            if (countdownListenerList.size <= position) return
            countdownListenerList[position].onCountdownChanged(position, orderDetail?.orderEta
                    ?: "")
        } else {
            val countDownTimer = orderDetail?.let { EtaCountDownTimer(arrivalDate.time - currentDate.time, 1000, position, orderDetail) }
            if (position < countdownTimerArray.size) countdownTimerArray[position].cancel()
            countDownTimer?.let { countdownTimerArray.add(position, it) }
            countDownTimer?.start()
        }


    }


    override fun setContext(context: Context) {
        this.context = context
    }


    inner class EtaCountDownTimer(private val timeDifference: Long, private val interval: Long, private val position: Int, private val orderDetails: OrderDetails) : CountDownTimer(timeDifference, interval) {

        override fun onFinish() {
            context?.getString(R.string.text_time_over)?.let {
                orderDetails.orderEta = it
                if (countdownListenerList.size <= position) return
                countdownListenerList[position].onCountdownChanged(position, it)

            }
        }

        override fun onTick(millisUntilFinished: Long) {

            orderDetails.orderEta = "" + String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))

            if (countdownListenerList.size <= position) return
            countdownListenerList[position].onCountdownChanged(position, orderDetails.orderEta)


        }

    }

    override fun onKeyEvent(valueEntered: String, data: Any?) {
        val position = (data as? Int)
        val token = Utility.getToken(context)
        position?.let {
            val orderDetail = orderDetailsList.value?.get(it)
            orderDetail?.orderArrivingTimeMinits = valueEntered
            repository = NewOrderRepository(ApiClient.getAPIInterface(), OrderPreparationStatus.ORDER_PREPARING, token)
            val etaMinutesPostData = orderDetail?.orderArrivingTimeMinits?.let { eta -> EtaMinutesPostData(eta, orderDetail.id) }
            if (etaMinutesPostData != null && token != null) {

                etaMinutesSetData.value = ( repository?.setOrderETA(etaMinutesPostData,etaMinutesSetData)?.value)
            }
        }
    }
}