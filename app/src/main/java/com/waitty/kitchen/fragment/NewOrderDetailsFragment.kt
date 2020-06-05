package com.waitty.kitchen.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.waitty.kitchen.R
import com.waitty.kitchen.activity.ui.home.HomeFragment
import com.waitty.kitchen.databinding.FragmentOrderDetailsNewBinding

import com.waitty.kitchen.model.APIStatus
import com.waitty.kitchen.model.apimodel.OrderResponse
import com.waitty.kitchen.utility.Utility
import com.waitty.kitchen.utility.WKItemClickListener
import com.waitty.kitchen.viewmodel.ApiErrorViewModel
import com.waitty.kitchen.viewmodel.NewOrderViewModel


class NewOrderDetailsFragment : Fragment(), WKItemClickListener {

    companion object {
        fun newInstance() = NewOrderDetailsFragment()
    }

    private var viewModel: NewOrderViewModel? = null
    private var homeFragment : HomeFragment? = null
    private var apiErrorViewModel : ApiErrorViewModel? = null
    private lateinit var bindingNewOrderDetailsFragment : FragmentOrderDetailsNewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        bindingNewOrderDetailsFragment = DataBindingUtil.inflate(inflater,R.layout.fragment_order_details_new, container, false)
        viewModel = activity?.let { ViewModelProvider(it).get(NewOrderViewModel::class.java) }
        apiErrorViewModel = activity?.let { ViewModelProvider(it).get(ApiErrorViewModel::class.java) }
        homeFragment = (parentFragment as? HomeFragment)
        return bindingNewOrderDetailsFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onActivityCreated(savedInstanceState)

        bindingNewOrderDetailsFragment.layoutError.apiErrorVM = apiErrorViewModel
        bindingNewOrderDetailsFragment.rvNewOrders?.layoutManager = if(!Utility.isTablet(context)) LinearLayoutManager(context)
                                                                    else GridLayoutManager(context,2)

        bindingNewOrderDetailsFragment.rvNewOrders?.setHasFixedSize(true)
        viewModel?.getOrderAdapter()?.setVariablesMap(getVariablesMap())
        bindingNewOrderDetailsFragment.rvNewOrders?.adapter = viewModel?.getOrderAdapter()
        bindingNewOrderDetailsFragment.rvNewOrders?.addOnScrollListener(RecyclerViewScrollListener())
        scheduleAlarm()


    }


    override fun onResume() {
        super.onResume()
        FragmentUtils.hideKeyboard(bindingNewOrderDetailsFragment.root,context)
        refreshNewOrdersList()
    }

    private fun getVariablesMap(): HashMap<Int, Any?> {
        return hashMapOf(BR.listOrderVM to viewModel, BR.clickItemEvent to this)

    }

    override fun onItemClick(position: Int) {
       viewModel?.setSelectedOrderDetails(position)
        viewModel?.updateOrderPreparationStatus(Utility.getToken(context))?.observe(viewLifecycleOwner, Observer { response ->
            when(response.status) {
                APIStatus.LOADING -> homeFragment?.showProgress(true)
                APIStatus.ERROR -> showError(true,response.errorCode ?: 404, response.message)
                APIStatus.SUCCESS ->  {
                    showError(false,404,"")
                    refreshNewOrdersList()
                }
            }
        })

    }


    fun refreshNewOrdersList() {

        val previousListCount = viewModel?.getOrderListData()?.value?.size ?: 0

        viewModel?.fetchNewOrders(Utility.getToken(context))?.observe(viewLifecycleOwner, Observer { response ->

            when(response.status) {
                APIStatus.LOADING -> homeFragment?.showProgress(true)
                APIStatus.ERROR -> showError(true,response.errorCode ?: 404,Utility.getMessageOnErrorCode(response.errorCode,context))
                APIStatus.SUCCESS -> handleOrdersFetched(response.data,previousListCount)
            }
        })
    }

    private fun handleOrdersFetched(data: Any?, previousListCount: Int) {
        viewModel?.getOrderListData()?.value = (data as? OrderResponse)?.data
         showError(false,404,"")
        showNoInvite(viewModel?.getOrderListData()?.value?.size ?:0 == 0)
        if(previousListCount != viewModel?.getOrderListData()?.value?.size ?: 0 && viewModel?.getOrderListData()?.value?.size ?: 0 > 0) showNewInviteIndicator()
        else homeFragment?.showBadge(false)
        viewModel?.updateList()

    }

    private fun showNewInviteIndicator() {
        homeFragment?.showBadge(true)
    }

    private fun checkIfNoInvites() : Boolean {
       return viewModel?.getOrderListData()?.value?.size ?: 0 == 0
    }


    private fun showNoInvite(isVisible : Boolean) {
        bindingNewOrderDetailsFragment.rvNewOrders.visibility = if(isVisible) View.GONE else View.VISIBLE
        bindingNewOrderDetailsFragment.ivNoInvite.visibility = if(isVisible) View.VISIBLE else View.GONE
    }


    inner class RecyclerViewScrollListener : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val topRowVerticalPosition =
            if(bindingNewOrderDetailsFragment.rvNewOrders == null || bindingNewOrderDetailsFragment.rvNewOrders?.childCount == 0) 0 else bindingNewOrderDetailsFragment?.rvNewOrders?.getChildAt(0)?.top ?: 0;
           homeFragment?.enableSwipeRefresh(topRowVerticalPosition >= 0)
        }
    }


    fun showError(toShow: Boolean, errorCode: Int, errorMessage: String?) {
        homeFragment?.showRefresh(false)

        if (toShow) apiErrorViewModel?.let { FragmentUtils.showError( it, errorCode, errorMessage, true) } else apiErrorViewModel?.resetValues()
        bindingNewOrderDetailsFragment.rvNewOrders.visibility = if (toShow) View.GONE else View.VISIBLE
        bindingNewOrderDetailsFragment.ivNoInvite.visibility = if (toShow) View.GONE else View.VISIBLE
        if (toShow) {
            Handler().postDelayed({ showError(false, errorCode, "") }, 3000)
        }

        if(!toShow) showNoInvite(checkIfNoInvites())

    }

    fun scheduleAlarm() {

        val intent = Intent(context?.applicationContext, MyAlarmReceiver::class.java)
        // Create a PendingIntent to be triggered when the alarm goes off
        val pIntent = PendingIntent.getBroadcast(context, 111,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val firstMillis = System.currentTimeMillis() // alarm is set right away
        val alarm = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                2*60*1000, pIntent)
    }


    inner class MyAlarmReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent?) {
            refreshNewOrdersList()
        }

    }
}
