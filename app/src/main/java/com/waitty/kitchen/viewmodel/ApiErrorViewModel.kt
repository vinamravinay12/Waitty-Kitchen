package com.waitty.kitchen.viewmodel

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.waitty.kitchen.R
import com.waitty.kitchen.model.ErrorType


public class ApiErrorViewModel : ViewModel() {

    private val progressVisibility = MutableLiveData<Boolean>(false)
    private val progressMessage = MutableLiveData<String>()
    private val errorType = MutableLiveData(ErrorType.NO_ERROR)
    private val errorMessage = MutableLiveData<String>()
    private val errorTypeImage = MutableLiveData<Int>()
    private val errorTypeString = MutableLiveData<Int>()
    private val errorViewVisibility = MutableLiveData<Int>()


    public fun getProgressVisibility() : MutableLiveData<Boolean>{
       return progressVisibility
    }

    public fun getProgressMessage() : MutableLiveData<String> {
       return progressMessage
    }

    public fun getErrorType() : MutableLiveData<Int> {

        when(errorType.value) {
            ErrorType.NETWORK_NOT_FOUND -> errorTypeString.value = R.string.check_network
            ErrorType.BAD_CREDENTIALS -> errorTypeString.value = R.string.bad_credentials
            ErrorType.BLOCK_ADMIN -> errorTypeString.value = R.string.block_admin
            ErrorType.SESSION_EXPIRE -> errorTypeString.value = R.string.session_expire
            ErrorType.OTHER_FAILED -> errorTypeString.value = R.string.try_again
            ErrorType.NO_ERROR ->  errorTypeString.value = 0

        }
        return errorTypeString
    }


    public fun setErrorType(code : Int) {
        errorType.value = ErrorType.valueOf(code)
    }

     fun getErrorTypeImage() : MutableLiveData<Int> {

            when (errorType.value) {
                ErrorType.NETWORK_NOT_FOUND -> errorTypeImage.value = R.drawable.connection_error
                ErrorType.NO_ERROR ->  errorTypeImage.value =null
                else ->  errorTypeImage.value =R.drawable.api_error
            }


        return errorTypeImage
    }

    public fun getErrorMessage() : MutableLiveData<String> {
       return errorMessage
    }


    public fun getErrorViewVisibility() : MutableLiveData<Int> {
        errorViewVisibility.value = View.GONE
        errorViewVisibility.value = if (TextUtils.isEmpty(errorMessage.value)) View.GONE else View.VISIBLE
       return errorViewVisibility
    }


    public fun getProgressViewVisibility() : MediatorLiveData<Int> {
        val progressViewVisibility = MediatorLiveData<Int>()
        progressViewVisibility.addSource(progressVisibility, Observer {
            progressViewVisibility.value = if(progressVisibility.value == true) View.VISIBLE else View.GONE
        })
        return progressViewVisibility
    }

    fun resetValues() {
        progressVisibility.value = false
        progressMessage.value = ""
        errorMessage.value = ""
    }



    fun showError() {
        errorViewVisibility.value = getErrorViewVisibility().value
        errorTypeImage.value = getErrorTypeImage().value
        errorTypeString.value = getErrorType().value

    }

}
