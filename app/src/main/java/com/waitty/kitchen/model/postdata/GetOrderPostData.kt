package com.waitty.kitchen.model.postdata

import com.google.gson.annotations.SerializedName
import com.waitty.kitchen.retrofit.API

data class GetOrderPostData(
        @SerializedName(API.LIMIT)
        val limit : Int,
        @SerializedName(API.PAGE)
        val page : Int
)