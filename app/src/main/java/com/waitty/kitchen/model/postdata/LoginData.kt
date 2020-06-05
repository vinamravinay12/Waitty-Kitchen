package com.waitty.kitchen.model.postdata

import com.google.gson.annotations.SerializedName
import com.waitty.kitchen.retrofit.API
import com.waitty.kitchen.constant.WaittyConstants


data class LoginData(@SerializedName(API.KEY) val kitchenId : String, @SerializedName(API.PASSWORD) val password : String,
                     @SerializedName(API.DEVICE_TYPE) val deviceType : String = WaittyConstants.DEVICE_TYPE,
                     @SerializedName(WaittyConstants.USER_DEVICEID) val deviceID : String, @SerializedName(WaittyConstants.USER_FCMTOKENID) val fcmTokenID : String)