package com.waitty.kitchen.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.translabtechnologies.visitormanagementsystem.vmshost.database.SharedPreferenceManager

import com.waitty.kitchen.R
import com.waitty.kitchen.activity.ui.home.HomeFragment
import com.waitty.kitchen.adapter.PreparedOrdersAdapter
import com.waitty.kitchen.constant.WaittyConstants
import com.waitty.kitchen.databinding.FragmentPreparedOrdersBinding
import com.waitty.kitchen.model.APIStatus
import com.waitty.kitchen.model.OrderDetails
import com.waitty.kitchen.model.apimodel.OrderResponse
import com.waitty.kitchen.utility.Utility
import com.waitty.kitchen.utility.WKItemClickListener
import com.waitty.kitchen.viewmodel.ApiErrorViewModel
import com.waitty.kitchen.viewmodel.PreparedOrdersViewModel

/**
 * A simple [Fragment] subclass.
 */
class PreparedOrdersFragment : Fragment(),WKItemClickListener {

    private lateinit var bindingPreparedOrderFragment : FragmentPreparedOrdersBinding
    private var preparedOrdersViewModel : PreparedOrdersViewModel? = null
    private var apiErrorViewModel : ApiErrorViewModel? = null
    private var homeFragment : HomeFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        bindingPreparedOrderFragment = DataBindingUtil.inflate(inflater,R.layout.fragment_prepared_orders,container,false)
        preparedOrdersViewModel = activity?.let { ViewModelProvider(it).get(PreparedOrdersViewModel::class.java) }
        apiErrorViewModel = activity?.let { ViewModelProvider(it).get(ApiErrorViewModel::class.java) }
        homeFragment = (parentFragment as? HomeFragment)
        bindingPreparedOrderFragment.lifecycleOwner = viewLifecycleOwner
        return bindingPreparedOrderFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingPreparedOrderFragment.layoutError.apiErrorVM = apiErrorViewModel
        setupRecyclerView()
        bindingPreparedOrderFragment.etOrderSearch.doAfterTextChanged { searchOrders() }
    }


    override fun onResume() {
        super.onResume()
        fetchPreparedOrders()
    }

    private fun searchOrders() {
        val searchTerm = bindingPreparedOrderFragment.etOrderSearch.text.toString()
        (preparedOrdersViewModel?.getPreparedOrderAdapter() as? PreparedOrdersAdapter)?.filter?.filter(searchTerm)
    }

    private fun setupRecyclerView() {

        val layoutManager = if(Utility.isTablet(context)) GridLayoutManager(context,2) else LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        layoutManager.stackFromEnd = false
        preparedOrdersViewModel?.getPreparedOrderAdapter()?.setVariablesMap(getVariablesMap())
        bindingPreparedOrderFragment.rvPreparedOrders.layoutManager = layoutManager
        bindingPreparedOrderFragment.rvPreparedOrders.setHasFixedSize(true)
        bindingPreparedOrderFragment.rvPreparedOrders.adapter = preparedOrdersViewModel?.getPreparedOrderAdapter()
    }

    private fun getVariablesMap(): HashMap<Int, Any?> {
        return hashMapOf(BR.preparedOrdersVM to preparedOrdersViewModel,BR.itemClickEvent to this)

    }

    fun fetchPreparedOrders() {
        homeFragment?.showProgress(true)
        context?.let { SharedPreferenceManager(it,WaittyConstants.LOGIN_SP) }?.let { preparedOrdersViewModel?.getPreparedOrders(it)?.observe(viewLifecycleOwner, Observer { response ->

            when(response.status) {
                APIStatus.ERROR ->showError(true, response.errorCode
                        ?: 404, Utility.getMessageOnErrorCode(response.errorCode, context))

                APIStatus.SUCCESS -> handleSuccessResponse(response.data)

                APIStatus.LOADING -> homeFragment?.showProgress(true)
            }
        }) }
    }

    private fun handleSuccessResponse(data: Any?) {
        preparedOrdersViewModel?.setOrdersList((data as? OrderResponse)?.data ?: ArrayList())
        showError(false, 404, "")
        showNoInvite(preparedOrdersViewModel?.getPreparedOrdersList()?.value?.size ?:0 == 0)
        preparedOrdersViewModel?.updateList()
    }

    override fun onItemClick(position: Int) {
        val selectedOrderDetails = preparedOrdersViewModel?.getPreparedOrdersList()?.value?.get(position)
        preparedOrdersViewModel?.getSelectedOrderDetails()?.value = selectedOrderDetails
        launchOrderDetailsFragment()
    }

    private fun launchOrderDetailsFragment() {
        FragmentUtils.launchFragment(activity?.supportFragmentManager,R.id.nav_host_fragment,PreparedOrderDetailsFragment(),WaittyConstants.TAG_ORDER_DETAILS)
    }

    private fun showNoInvite(isVisible : Boolean) {
        bindingPreparedOrderFragment.rvPreparedOrders.visibility = if(isVisible) View.GONE else View.VISIBLE
        bindingPreparedOrderFragment.cardView.visibility =  if(isVisible) View.GONE else View.VISIBLE
        bindingPreparedOrderFragment.ivNoInvite.visibility = if(isVisible) View.VISIBLE else View.GONE
    }

    fun showError(toShow: Boolean, errorCode: Int, errorMessage: String?) {
        homeFragment?.showRefresh(false)
        if (toShow) apiErrorViewModel?.let { FragmentUtils.showError( it, errorCode, errorMessage, true) } else apiErrorViewModel?.resetValues()
        bindingPreparedOrderFragment.rvPreparedOrders.visibility = if (toShow) View.GONE else View.VISIBLE
        bindingPreparedOrderFragment.ivNoInvite.visibility = if (toShow) View.GONE else View.VISIBLE
        if (toShow) {
            Handler().postDelayed({ showError(false, errorCode, "") }, 3000)
        }

        if(!toShow) showNoInvite(checkIfNoInvites())
    }

    private fun checkIfNoInvites() : Boolean {
        return preparedOrdersViewModel?.getPreparedOrdersList()?.value?.size ?: 0 == 0
    }


}
