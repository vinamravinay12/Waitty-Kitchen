package com.waitty.kitchen.model

import com.google.gson.annotations.SerializedName
import com.waitty.kitchen.retrofit.API

enum class APIStatus {
    SUCCESS,
    ERROR,
    LOADING
}

enum class ErrorType(val errorCode : Int) {

    OTHER_FAILED(422),
    SESSION_EXPIRE(401),
    BLOCK_ADMIN(403),
    BAD_CREDENTIALS(400),
    NETWORK_NOT_FOUND(404),
    NO_ERROR(0);

    companion object {
        fun valueOf(value: Int): ErrorType? = ErrorType.values().find { it.errorCode == value }
    }
}

data class WaittyAPIResponse(val data : Any?, val status : APIStatus, val errorCode : Int?, val message: String?)

data class ErrorResponse( val errorCode : Int, @SerializedName(API.MESSAGE) val errorMessage : String?)

