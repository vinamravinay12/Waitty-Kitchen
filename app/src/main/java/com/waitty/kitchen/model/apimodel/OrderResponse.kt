package com.waitty.kitchen.model.apimodel

import com.google.gson.annotations.SerializedName
import com.waitty.kitchen.model.OrderDetails

data class OrderResponse(
        @SerializedName("success")
        val success : Boolean,
        @SerializedName("data")
        val data : List<OrderDetails>,
        @SerializedName("message")
        val message : String?
)