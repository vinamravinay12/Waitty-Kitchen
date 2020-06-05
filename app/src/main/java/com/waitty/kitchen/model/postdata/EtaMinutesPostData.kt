package com.waitty.kitchen.model.postdata

import com.google.gson.annotations.SerializedName
import com.waitty.kitchen.retrofit.API

data class EtaMinutesPostData (
        @SerializedName(API.ORDER_ARRIVING_TIME)
        val orderArrivingTime : String,
        @SerializedName(API.ORDER_ID)
        val orderId : Int
)