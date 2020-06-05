package com.waitty.kitchen.viewmodel.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.waitty.kitchen.model.APIStatus
import com.waitty.kitchen.model.ErrorResponse
import com.waitty.kitchen.model.WaittyAPIResponse
import com.waitty.kitchen.model.apimodel.LoginResponse
import com.waitty.kitchen.model.postdata.LoginData
import com.waitty.kitchen.retrofit.API
import com.waitty.kitchen.retrofit.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository<E>(private val apiInterface: ApiInterface) {

    public fun loginUser(loginData: LoginData): MutableLiveData<WaittyAPIResponse> {

        val loginResponseLiveData = MutableLiveData<WaittyAPIResponse>()
        var waittyAPIResponse = WaittyAPIResponse(data = null, status = APIStatus.LOADING,errorCode = null, message = null)

        apiInterface.login(loginData).enqueue(object : Callback<LoginResponse> {

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                waittyAPIResponse = WaittyAPIResponse(data = null, status = APIStatus.ERROR, errorCode = null,message = t.localizedMessage)
                loginResponseLiveData.value = waittyAPIResponse
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (!response.isSuccessful) {
                    val errorResponse = Gson().fromJson(response.errorBody()?.charStream(),ErrorResponse::class.java)
                    waittyAPIResponse = WaittyAPIResponse(data = null,status = APIStatus.ERROR,errorCode = response.code(),message = errorResponse.errorMessage)
                    loginResponseLiveData.value = waittyAPIResponse
                    return
                }

                val loginResponse = response.body()
                loginResponse?.let { it.token = response.headers().get(API.AUTHORIZATION) }

                waittyAPIResponse = WaittyAPIResponse(data = loginResponse,status = APIStatus.SUCCESS,errorCode = null, message = null)
                loginResponseLiveData.value = waittyAPIResponse

            }
        })

        loginResponseLiveData.value = waittyAPIResponse
        return loginResponseLiveData
    }



}