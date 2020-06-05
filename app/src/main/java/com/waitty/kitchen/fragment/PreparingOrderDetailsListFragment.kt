package com.waitty.kitchen.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.translabtechnologies.visitormanagementsystem.vmshost.database.SharedPreferenceManager
import com.waitty.kitchen.R
import com.waitty.kitchen.activity.ui.home.HomeFragment
import com.waitty.kitchen.adapter.PreparingOrdersAdapter
import com.waitty.kitchen.adapter.viewholders.WKCheckChangeListener
import com.waitty.kitchen.constant.WaittyConstants
import com.waitty.kitchen.databinding.FragmentPreparingOrderDetailsListBinding
import com.waitty.kitchen.model.APIStatus
import com.waitty.kitchen.model.OrderItem
import com.waitty.kitchen.model.apimodel.OrderResponse
import com.waitty.kitchen.utility.Utility
import com.waitty.kitchen.utility.WKItemClickListener
import com.waitty.kitchen.viewmodel.ApiErrorViewModel
import com.waitty.kitchen.viewmodel.PreparingOrdersViewModel

/**
 * A simple [Fragment] subclass.
 */
class PreparingOrderDetailsListFragment : Fragment(), WKItemClickListener, WKCheckChangeListener {

    private lateinit var bindingPreparingOrderDetails: FragmentPreparingOrderDetailsListBinding
    private var preparingOrderViewModel: PreparingOrdersViewModel? = null
    private var homeFragment: HomeFragment? = null
    private var apiErrorViewModel: ApiErrorViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        bindingPreparingOrderDetails = DataBindingUtil.inflate(inflater, R.layout.fragment_preparing_order_details_list, container, false)
        homeFragment = (parentFragment as? HomeFragment)
        preparingOrderViewModel = activity?.let { ViewModelProvider(it).get(PreparingOrdersViewModel::class.java) }
        apiErrorViewModel = activity?.let { ViewModelProvider(it).get(ApiErrorViewModel::class.java) }
        context?.let { preparingOrderViewModel?.setContext(it) }
        bindingPreparingOrderDetails.lifecycleOwner = viewLifecycleOwner
        return bindingPreparingOrderDetails.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingPreparingOrderDetails.layoutError.apiErrorVM = apiErrorViewModel
        preparingOrderViewModel?.getOrderAdapter()?.setVariablesMap(getVariablesMap())
        bindingPreparingOrderDetails.rvPreparingOrders.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        bindingPreparingOrderDetails.rvPreparingOrders.setHasFixedSize(true)
        (preparingOrderViewModel?.getOrderAdapter() as? PreparingOrdersAdapter)?.setCheckChangeListener(this)
        bindingPreparingOrderDetails.rvPreparingOrders.adapter = preparingOrderViewModel?.getOrderAdapter()
        initializeListeners()
    }

    override fun onResume() {
        super.onResume()
        FragmentUtils.hideKeyboard(bindingPreparingOrderDetails.root,context)
        refreshPreparingOrdersList()
    }

    private fun initializeListeners() {
        preparingOrderViewModel?.getEtaMinutesSetData()?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                APIStatus.LOADING -> homeFragment?.showProgress(true)
                APIStatus.ERROR -> {
                    homeFragment?.showProgress(false)
                    Utility.ShowToast(context, getString(R.string.unable_to_set_eta), Toast.LENGTH_LONG)
                }
                APIStatus.SUCCESS -> {
                    homeFragment?.showProgress(false)
                    refreshPreparingOrdersList()
                }
            }

        })
    }


    override fun onItemClick(position: Int) {
        preparingOrderViewModel?.setSelectedOrderDetails(position)
        preparingOrderViewModel?.updateOrderPreparationStatus(Utility.getToken(context))?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                APIStatus.LOADING -> homeFragment?.showProgress(true)
                APIStatus.ERROR -> showError(true, it.errorCode
                        ?: 404, Utility.getMessageOnErrorCode(it.errorCode, context))
                APIStatus.SUCCESS -> handleUpdateOrderStatusSuccessResponse(it.data)
            }
        })
    }

    private fun handleUpdateOrderStatusSuccessResponse(data: Any?) {
        context?.let {
            preparingOrderViewModel?.saveToPreparedOrders(SharedPreferenceManager(it, WaittyConstants.LOGIN_SP))
            refreshPreparingOrdersList()
        }

    }

    private fun getVariablesMap(): HashMap<Int, Any?> {
        return hashMapOf(BR.orderPreparingVM to preparingOrderViewModel, BR.clickItemEvent to this, BR.keyActionListener to preparingOrderViewModel)

    }

    fun refreshPreparingOrdersList() {
        preparingOrderViewModel?.fetchPreparingOrders(Utility.getToken(context))?.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                APIStatus.LOADING -> homeFragment?.showProgress(true)
                APIStatus.ERROR -> showError(true, response.errorCode
                        ?: 404, Utility.getMessageOnErrorCode(response.errorCode, context))
                APIStatus.SUCCESS -> handleOrdersFetched(response.data)
            }
        })
    }

    private fun handleOrdersFetched(data: Any?) {
        preparingOrderViewModel?.getOrderDetailsList()?.value = (data as? OrderResponse)?.data
        showError(false, 404, "")
        showNoInvite(preparingOrderViewModel?.getOrderDetailsList()?.value?.size ?: 0 == 0)
        preparingOrderViewModel?.updateList()
        preparingOrderViewModel?.startOrderEtaTimerForAllOrders()

    }

    override fun onCheckChanged(item: Any?) {
        var orderItem = item as? OrderItem

        preparingOrderViewModel?.setOrderItemDone(Utility.getToken(context), orderItem)?.observe(viewLifecycleOwner, Observer { response ->

            if (response.status == APIStatus.ERROR) Utility.ShowToast(context, getString(R.string.txt_complete_item_error), Toast.LENGTH_LONG)
            else if (response.status == APIStatus.SUCCESS) {
                orderItem?.isPrepared = true
                val parentAdapterPosition = findOrderItemPosition(orderItem)
                if (parentAdapterPosition >= 0) preparingOrderViewModel?.getOrderAdapter()?.notifyItemChanged(parentAdapterPosition)
            }
        })

    }

    private fun findOrderItemPosition(orderItem: OrderItem?): Int {
        preparingOrderViewModel?.getOrderDetailsList()?.value?.let {
            for (parentAdapterPosition in it.indices) {
                val orderDetails = preparingOrderViewModel?.getOrderDetailsList()?.value?.get(parentAdapterPosition)

                orderDetails?.let {
                    for (item in it.orderItems) {
                        if (item.id == orderItem?.id) {
                            orderItem.isPrepared = orderItem.isPrepared
                            return parentAdapterPosition
                        }
                    }
                }
            }
        }
        return -1

    }

    private fun showNoInvite(isVisible: Boolean) {
        bindingPreparingOrderDetails.rvPreparingOrders.visibility = if (isVisible) View.GONE else View.VISIBLE
        bindingPreparingOrderDetails.ivNoInvite.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun showError(toShow: Boolean, errorCode: Int, errorMessage: String?) {
        homeFragment?.showRefresh(false)
        if (toShow) apiErrorViewModel?.let { FragmentUtils.showError(it, errorCode, errorMessage, true) } else apiErrorViewModel?.resetValues()
        bindingPreparingOrderDetails.rvPreparingOrders.visibility = if (toShow) View.GONE else View.VISIBLE
        bindingPreparingOrderDetails.ivNoInvite.visibility = if (toShow) View.GONE else View.VISIBLE

        if (toShow) {
            Handler().postDelayed({ showError(false, errorCode, "") }, 3000)
        }

        if (!toShow) showNoInvite(checkIfNoInvites())
    }

    private fun checkIfNoInvites(): Boolean {
        return preparingOrderViewModel?.getOrderDetailsList()?.value?.size ?: 0 == 0
    }


}
