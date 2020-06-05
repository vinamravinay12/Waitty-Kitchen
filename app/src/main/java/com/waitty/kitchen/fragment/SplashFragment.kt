package com.waitty.kitchen.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.translabtechnologies.visitormanagementsystem.vmshost.database.SharedPreferenceManager
import com.waitty.kitchen.R
import com.waitty.kitchen.activity.HomeActivityNew
import com.waitty.kitchen.constant.WaittyConstants
import com.waitty.kitchen.fragment.FragmentUtils.launchFragment

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed(Runnable {
            checkUserLoginStatus()
        },2000)

    }

    private fun checkUserLoginStatus() {
        val isLoggedIn = context?.let { SharedPreferenceManager(it, WaittyConstants.LOGIN_SP).getBooleanPreference(WaittyConstants.KEY_IS_LOGGED_IN, false) }
        if (isLoggedIn == true) launchHomeActivity() else launchFragment(activity?.supportFragmentManager, R.id.fragment_container, LoginFragment(), WaittyConstants.TAG_LOGIN)
    }

    private fun launchHomeActivity() {
        activity?.startActivity(Intent(activity,HomeActivityNew::class.java))
        activity?.finish()
    }

}
