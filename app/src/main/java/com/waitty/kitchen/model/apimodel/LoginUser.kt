package com.waitty.kitchen.model.apimodel

import android.os.Build.ID
import com.google.gson.annotations.SerializedName
import com.waitty.kitchen.retrofit.API

data class LoginUser(@SerializedName(API.USER_ID) val userId : Int, @SerializedName(API.PARENT_ID) val parentId : Int,
                     @SerializedName(API.COUNTRYCODE) val countryCode : String?, @SerializedName(API.EMAIL) val email : String?,
                     @SerializedName(API.MOBILE) val mobileNumber : String?, @SerializedName(API.KITCHEN_NAME) val kitchenName : String?,
                     @SerializedName(API.PROFILE_IMAGE) val profileImage : String?, @SerializedName(API.KEY) val key : String?,
                     @SerializedName(API.RESTAURANT) val restaurants : List<RestaurantDetails>) {
}



data class RestaurantDetails(@SerializedName(API.USER_ID) val restaurantId : String, @SerializedName(API.PARENT_ID) val parentId : String,
                            @SerializedName(API.COUNTRYCODE) val countryCode : String?, @SerializedName(API.EMAIL) val email : String?,
                            @SerializedName(API.MOBILE) val mobileNumber: String?, @SerializedName(API.KITCHEN_NAME) val restaurantName : String?,
                            @SerializedName(API.PROFILE_IMAGE) val profileImage: String?, @SerializedName(API.KEY) val key: String?)



data class LoginResponse(@SerializedName(API.DATA) val data : LoginUser?, @SerializedName(API.AUTHORIZATION) var token : String?)