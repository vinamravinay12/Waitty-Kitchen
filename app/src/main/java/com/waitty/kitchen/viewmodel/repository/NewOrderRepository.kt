package com.waitty.kitchen.viewmodel.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.*
import com.translabtechnologies.visitormanagementsystem.vmshost.database.SharedPreferenceManager
import com.waitty.kitchen.constant.WaittyConstants
import com.waitty.kitchen.model.*
import com.waitty.kitchen.model.apimodel.OrderResponse
import com.waitty.kitchen.model.postdata.EtaMinutesPostData
import com.waitty.kitchen.model.postdata.GetOrderPostData
import com.waitty.kitchen.retrofit.API
import com.waitty.kitchen.retrofit.ApiInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class NewOrderRepository(private val apiInterface: ApiInterface,private val orderStatus: OrderPreparationStatus, private val token : String) {

    fun getOrders( orderPostData: GetOrderPostData, sharedPreferenceManager: SharedPreferenceManager? = null): MutableLiveData<WaittyAPIResponse> {
        val responseData = MutableLiveData<WaittyAPIResponse>()
        return when (orderStatus) {
            OrderPreparationStatus.NEW_ORDER -> getNewOrders(token, orderPostData, responseData)
            OrderPreparationStatus.ORDER_PREPARING -> getPreparingOrders(token, orderPostData, responseData)

            else -> getPreparedOrders(responseData,sharedPreferenceManager)
        }
    }



    fun setOrderETA(etaPostData: EtaMinutesPostData, responseData: MutableLiveData<WaittyAPIResponse>) : MutableLiveData<WaittyAPIResponse>{

        if(orderStatus != OrderPreparationStatus.ORDER_PREPARING) return responseData

        var waittyAPIResponse = WaittyAPIResponse(null, APIStatus.LOADING,null,null)

        apiInterface.setOrderETA(etaPostData,token).enqueue(object : Callback<JsonElement> {

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                waittyAPIResponse = WaittyAPIResponse(null,APIStatus.ERROR,404,t.localizedMessage)
                responseData.value = waittyAPIResponse
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

               if(!response.isSuccessful) {
                   waittyAPIResponse = WaittyAPIResponse(null,APIStatus.ERROR,response.code(),"")
                   responseData.value = waittyAPIResponse
                   return
               }

                waittyAPIResponse = try {
                    val jsonResponse = JSONObject(response.body().toString());
                    val arrivingTime = jsonResponse.getJSONObject(API.DATA).getString(API.ORDER_ARRIVING_TIME)
                    WaittyAPIResponse(arrivingTime,APIStatus.SUCCESS,null,null)
                } catch (exception: JsonParseException) {
                    WaittyAPIResponse(null,APIStatus.ERROR,405,exception.localizedMessage)
                }
                responseData.value = waittyAPIResponse
            }

        })

        responseData.value = waittyAPIResponse
        return responseData
    }



    fun updateOrderStatus(postData : JsonObject) : MutableLiveData<WaittyAPIResponse> {
        val responseData = MutableLiveData<WaittyAPIResponse>()
        if(orderStatus == OrderPreparationStatus.NEW_ORDER) return startNewOrderPreparation(postData,responseData)
        else if(orderStatus == OrderPreparationStatus.ORDER_PREPARING) return setOrderPrepared(postData,responseData)
        return responseData
    }


    fun updateOrderItem(postData : JsonObject) : MutableLiveData<WaittyAPIResponse> {
        val responseData = MutableLiveData<WaittyAPIResponse>()
        var waittyAPIResponse = WaittyAPIResponse(null, APIStatus.LOADING,null,null)

        apiInterface.doneOrderItem(postData,token).enqueue(object : Callback<JsonElement> {

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                waittyAPIResponse = WaittyAPIResponse(null,APIStatus.ERROR,404,t.localizedMessage)
                responseData.value = waittyAPIResponse
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if(!response.isSuccessful) {
                    waittyAPIResponse = WaittyAPIResponse(null,APIStatus.ERROR,response.code(),"")
                    responseData.value = waittyAPIResponse
                    return
                }

                waittyAPIResponse = WaittyAPIResponse(response.body(),APIStatus.SUCCESS,null,null)
                responseData.value = waittyAPIResponse
            }

        })

        responseData.value = waittyAPIResponse
        return responseData
    }


    private fun setOrderPrepared(postData: JsonObject, responseData: MutableLiveData<WaittyAPIResponse>): MutableLiveData<WaittyAPIResponse> {

        var waittyAPIResponse = WaittyAPIResponse(null,APIStatus.LOADING,null,null)

        apiInterface.doneOrder(postData,token).enqueue( object : Callback<JsonElement> {

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                waittyAPIResponse = WaittyAPIResponse(null,APIStatus.ERROR,404,t.localizedMessage)
                responseData.value = waittyAPIResponse
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if(!response.isSuccessful) {
                    waittyAPIResponse = WaittyAPIResponse(null,APIStatus.ERROR,response.code(),"")
                    responseData.value = waittyAPIResponse
                    return
                }

                waittyAPIResponse = WaittyAPIResponse(response.body().toString(),APIStatus.SUCCESS,null,null)
                responseData.value = waittyAPIResponse
            }


        })
        responseData.value = waittyAPIResponse
       return responseData
    }

    private fun startNewOrderPreparation(postData: JsonObject, responseData: MutableLiveData<WaittyAPIResponse>): MutableLiveData<WaittyAPIResponse> {
        var waittyAPIResponse = WaittyAPIResponse(null,APIStatus.LOADING,null,null)

        apiInterface.orderStartPreparing(postData,token).enqueue( object : Callback<JsonElement> {

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                waittyAPIResponse = WaittyAPIResponse(null,APIStatus.ERROR,404,t.localizedMessage)
                responseData.value = waittyAPIResponse
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if(!response.isSuccessful) {
                    waittyAPIResponse = WaittyAPIResponse(null,APIStatus.ERROR,response.code(),"")
                    responseData.value = waittyAPIResponse
                    return
                }

                waittyAPIResponse = WaittyAPIResponse(response.body().toString(),APIStatus.SUCCESS,null,null)
                responseData.value = waittyAPIResponse
            }


        })
        responseData.value = waittyAPIResponse
        return responseData
    }

    fun savePreparedOrders(orderDetails: OrderDetails, sharedPreferenceManager: SharedPreferenceManager)  {
        val preparedOrders = getPreparedOrdersList(sharedPreferenceManager)
        preparedOrders.add(orderDetails)
        sharedPreferenceManager.storeComplexObjectPreference(WaittyConstants.KEY_PREPARED_ORDERS,preparedOrders)

    }


    private fun getPreparedOrders(responseData: MutableLiveData<WaittyAPIResponse>, sharedPreferenceManager: SharedPreferenceManager?): MutableLiveData<WaittyAPIResponse> {

        var waittyAPIResponse: WaittyAPIResponse

        waittyAPIResponse = try {
            val preparedListJson = sharedPreferenceManager?.getStringPreference(WaittyConstants.KEY_PREPARED_ORDERS)
            if(preparedListJson.isNullOrEmpty()) {
                WaittyAPIResponse(ArrayList<OrderDetails>(),APIStatus.SUCCESS,405, "");
            } else {
                val preparedList = GsonBuilder().create().fromJson(preparedListJson, Array<OrderDetails>::class.java).toList()
                WaittyAPIResponse(preparedList, APIStatus.SUCCESS, null, null)
            }
        } catch (exception : Exception) {
            WaittyAPIResponse(null,APIStatus.ERROR,405,exception.localizedMessage)
        }

        responseData.value = waittyAPIResponse
        return responseData

    }

    private fun getPreparedOrdersList(sharedPreferenceManager: SharedPreferenceManager) : MutableList<OrderDetails> {

        return try {
            val preparedListJson = sharedPreferenceManager.getStringPreference(WaittyConstants.KEY_PREPARED_ORDERS)
            GsonBuilder().create().fromJson(preparedListJson, Array<OrderDetails>::class.java).toMutableList()

        } catch (exception : Exception) {
            ArrayList<OrderDetails>().toMutableList()
        }

    }

    private fun getPreparingOrders(token: String, orderPostData: GetOrderPostData, responseData: MutableLiveData<WaittyAPIResponse>): MutableLiveData<WaittyAPIResponse> {
        var apiResponse = WaittyAPIResponse(null, APIStatus.LOADING, null, null)

        apiInterface.getPreparingOrder(orderPostData,token).enqueue(object : Callback<OrderResponse> {

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                apiResponse = WaittyAPIResponse(null,APIStatus.ERROR,404,t.localizedMessage)
                responseData.value = apiResponse
            }

            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if(!response.isSuccessful) {
                    apiResponse = WaittyAPIResponse(null,APIStatus.ERROR,response.code(),"")
                    responseData.value = apiResponse
                    return
                }

                apiResponse = WaittyAPIResponse(response.body(),APIStatus.SUCCESS,null,null)
                responseData.value = apiResponse

            }
        })

        responseData.value = apiResponse
        return responseData

    }

    private fun getNewOrders(token: String, orderPostData: GetOrderPostData, responseData: MutableLiveData<WaittyAPIResponse>): MutableLiveData<WaittyAPIResponse> {

        var apiResponse = WaittyAPIResponse(null, APIStatus.LOADING, null, null)

        apiInterface.getNewOrder(orderPostData,token).enqueue(object : Callback<OrderResponse> {

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                apiResponse = WaittyAPIResponse(null,APIStatus.ERROR,404,t.localizedMessage)
                responseData.value = apiResponse
            }

            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if(!response.isSuccessful) {

                    apiResponse = WaittyAPIResponse(null,APIStatus.ERROR,response.code(),"")

                    return
                }

                apiResponse = WaittyAPIResponse(response.body(),APIStatus.SUCCESS,null,null)
                responseData.value = apiResponse

            }
        })

        responseData.value = apiResponse
        return responseData
    }
}