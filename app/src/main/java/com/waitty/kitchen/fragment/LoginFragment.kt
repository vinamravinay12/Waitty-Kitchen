package com.waitty.kitchen.fragment


import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.translabtechnologies.visitormanagementsystem.vmshost.database.SharedPreferenceManager
import com.waitty.kitchen.R
import com.waitty.kitchen.activity.HomeActivityNew
import com.waitty.kitchen.constant.WaittyConstants
import com.waitty.kitchen.databinding.FragmentLoginBinding
import com.waitty.kitchen.model.APIStatus
import com.waitty.kitchen.model.apimodel.LoginResponse
import com.waitty.kitchen.retrofit.API
import com.waitty.kitchen.utility.Utility
import com.waitty.kitchen.utility.WKClickListener
import com.waitty.kitchen.viewmodel.ApiErrorViewModel
import com.waitty.kitchen.viewmodel.ClickType
import com.waitty.kitchen.viewmodel.LoginViewModel

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(), WKClickListener {

    private lateinit var bindingLoginFragment: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var apiErrorViewModel: ApiErrorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        bindingLoginFragment = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val viewModelProvider = activity?.let { ViewModelProvider(it) }

        if (viewModelProvider != null) {
            loginViewModel = viewModelProvider.get(LoginViewModel::class.java)
            apiErrorViewModel = viewModelProvider.get(ApiErrorViewModel::class.java)
        }

        FragmentUtils.setBindingVariables(hashMapOf(BR.LoginVM to loginViewModel, BR.clickEvent to this), bindingLoginFragment)
        bindingLoginFragment.layoutLoader.apiErrorVM = apiErrorViewModel
        bindingLoginFragment.layoutError.apiErrorVM = apiErrorViewModel

        bindingLoginFragment.lifecycleOwner = this

        return bindingLoginFragment.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeListeners()
    }

    private fun initializeListeners() {

//        bindingLoginFragment.etKitchenId.doAfterTextChanged {
//
//            val isValidated = loginViewModel.validateUserName()
//            setErrorProperties(bindingLoginFragment.txtInputKitchenIdLayout,bindingLoginFragment.etKitchenId,!isValidated)
//        }
//
//        bindingLoginFragment.etPassword.doAfterTextChanged {
//            val isValidated = loginViewModel.validatePassword()
//            bindingLoginFragment.txtInputPasswordLayout?.let { it1 -> setErrorProperties(it1,bindingLoginFragment.etPassword,!isValidated) }
//        }

        bindingLoginFragment.btnLogin.setOnClickListener { onClick(ClickType.Login) }
        bindingLoginFragment.tvForgotPassword.setOnClickListener { onClick(ClickType.ForgotPassword) }

    }


    private fun setErrorProperties(textInputLayout: TextInputLayout,textInputEditText: TextInputEditText,isError: Boolean) {
        setHintColor(textInputLayout,isError)
        textInputLayout.endIconDrawable = getEndIcon(isError)
        textInputLayout.isEndIconVisible = true

    }

    private fun setHintColor(view: TextInputLayout, isError: Boolean) {

        if (isError) view.markRequiredInRed()
        view.hintTextColor = getHintTextColor(isError)

        view.boxStrokeColor = getBoxStrokeColor(isError) ?: R.color.colorWaiterIdText
    }

    private fun getEndIcon(isError: Boolean) : Drawable? {
        return context?.let { context ->
           if(isError) AppCompatResources.getDrawable(context,R.drawable.ic_error_red_24dp) else AppCompatResources.getDrawable(context,R.drawable.ic_done_tick_green_24dp)

        }


    }


    private fun getHintTextColor(isError: Boolean): ColorStateList? {
        return context?.let { context ->
            if (isError) ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorCountdownTimer)) else ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorTabItemTextSelected))
        }
    }

    private fun getBoxStrokeColor(isError: Boolean): Int? {
        return context?.let { context ->
            if (isError) ContextCompat.getColor(context, R.color.colorCountdownTimer) else ContextCompat.getColor(context, R.color.colorWaiterIdText)
        }

    }

    private fun loginUser() {
        val isValidated = loginViewModel.validateUserName()
        setErrorProperties(bindingLoginFragment.txtInputKitchenIdLayout,bindingLoginFragment.etKitchenId,!isValidated)

        val isPasswordValidated = loginViewModel.validatePassword()
        bindingLoginFragment.txtInputPasswordLayout?.let { it1 -> setErrorProperties(it1,bindingLoginFragment.etPassword,!isPasswordValidated) }


        FragmentUtils.hideKeyboard(bindingLoginFragment.root,context)
        val deviceId = context?.let { context -> SharedPreferenceManager(context, WaittyConstants.LOGIN_SP).getStringPreference(WaittyConstants.USER_DEVICEID) }

        val fcmToken = context?.let { context -> SharedPreferenceManager(context, WaittyConstants.LOGIN_SP).getStringPreference(WaittyConstants.USER_FCMTOKENID) }


        bindingLoginFragment.lifecycleOwner?.let { lifecycleOwner ->
            if (deviceId != null && fcmToken != null) {
                loginViewModel.login(deviceId, fcmToken)?.observe(lifecycleOwner, Observer {
                    it?.let { response ->
                        when (response.status) {
                            APIStatus.LOADING -> FragmentUtils.showProgress(parentView = bindingLoginFragment.viewLogin, toShow = true, apiErrorViewModel = apiErrorViewModel, progressMessage = getString(R.string.wait), isSwipeRefreshed = false)

                            APIStatus.ERROR ->  showError(true,errorCode = response.errorCode ?: 405, errorMessage = Utility.getMessageOnErrorCode(response.errorCode, context))

                            APIStatus.SUCCESS -> {
                                if (response.data == null) showError(false,404,"")
                                else {
                                    bindingLoginFragment.viewLogin?.visibility = View.VISIBLE
                                    apiErrorViewModel.resetValues()
                                    handleLoginResponse(response.data)

                                }
                            }
                        }
                    }
                })
            }
        }


    }

    private fun handleLoginResponse(response: Any) {
        context?.let { context ->
            val sharedPreferenceManager = SharedPreferenceManager(context, WaittyConstants.LOGIN_SP)
            (response as? LoginResponse).let { loginResponse ->
                sharedPreferenceManager.storeStringPreference(API.AUTHORIZATION, loginResponse?.token
                        ?: "")
            }
            sharedPreferenceManager.storeBooleanPreference(WaittyConstants.KEY_IS_LOGGED_IN, true)
            launchHomeActivity()
        }
    }

    private fun launchHomeActivity() {
        Utility.ShowToast(context,getString(R.string.login_success),Toast.LENGTH_LONG)
        startActivity(Intent(activity,HomeActivityNew::class.java))
        activity?.finish()
    }

    override fun onClick(clickType: ClickType) {
        if (clickType == ClickType.Login) loginUser()
    }

    fun TextInputLayout.markRequiredInRed() {

        hint = if (hint?.contains("*") == true) hint?.substring(0, (hint?.length
                ?: 0) - 1) else hint?.substring(0, hint?.length ?: 0)
        hint = buildSpannedString {

            append(hint)
            color(Color.RED) { append("*") } // Mind the space prefix.
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hintTextColor = ColorStateList.valueOf(resources.getColor(R.color.colorCountdownTimer, null))
        }
    }

    fun showError(toShow: Boolean, errorCode: Int, errorMessage: String?) {

        if (toShow) apiErrorViewModel.let { FragmentUtils.showError( it, errorCode, errorMessage, true) } else apiErrorViewModel?.resetValues()
        bindingLoginFragment.viewLogin.visibility = if (toShow) View.GONE else View.VISIBLE
        if (toShow) {
            Handler().postDelayed({ showError(false, errorCode, "") }, 3000)
        }


    }


}
