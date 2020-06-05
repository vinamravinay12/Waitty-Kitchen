package com.waitty.kitchen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.waitty.kitchen.BR
import com.waitty.kitchen.R
import com.waitty.kitchen.activity.HomeActivityNew
import com.waitty.kitchen.databinding.FragmentPreparedOrderDetailsBinding
import com.waitty.kitchen.viewmodel.ItemDescriptionViewModel
import com.waitty.kitchen.viewmodel.PreparedItemOrdersViewModel
import com.waitty.kitchen.viewmodel.PreparedOrdersViewModel

/**
 * A simple [Fragment] subclass.
 */
class PreparedOrderDetailsFragment : Fragment() {

    private lateinit var bindingFragmentPreparedOrderDetais : FragmentPreparedOrderDetailsBinding
    private var preparedItemOrdersViewModel : PreparedItemOrdersViewModel? = null
    private var itemDescriptionViewModel : ItemDescriptionViewModel? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        bindingFragmentPreparedOrderDetais = DataBindingUtil.inflate(inflater,R.layout.fragment_prepared_order_details,container,false)

        val viewModelProvider = activity?.let { ViewModelProvider(it) }
        preparedItemOrdersViewModel = viewModelProvider?.get(PreparedItemOrdersViewModel::class.java)
        itemDescriptionViewModel = viewModelProvider?.get(ItemDescriptionViewModel::class.java)
        val preparedOrdersViewModel = viewModelProvider?.get(PreparedOrdersViewModel::class.java)

        preparedItemOrdersViewModel?.getOrderDetails()?.value = preparedOrdersViewModel?.getSelectedOrderDetails()?.value

        itemDescriptionViewModel?.getOrderItems()?.value = preparedItemOrdersViewModel?.getOrderDetails()?.value?.orderItems
        itemDescriptionViewModel?.setRupeeSymbol(getString(R.string.rupee_symbol))
        bindingFragmentPreparedOrderDetais.orderDetailsVM = preparedItemOrdersViewModel
        bindingFragmentPreparedOrderDetais.lifecycleOwner = viewLifecycleOwner
        return bindingFragmentPreparedOrderDetais.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparedItemOrdersViewModel?.setRupeeSymbol(getString(R.string.rupee_symbol))
        setupRecyclerView()
        setPageDetails()
        FragmentUtils.hideKeyboard(bindingFragmentPreparedOrderDetais.rvPreparedOrderItems,context)

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.stackFromEnd = false
       bindingFragmentPreparedOrderDetais.rvPreparedOrderItems.layoutManager = layoutManager
        bindingFragmentPreparedOrderDetais.rvPreparedOrderItems.setHasFixedSize(true)
        preparedItemOrdersViewModel?.getAdapter()?.setVariablesMap(hashMapOf(BR.itemDescriptionVM to itemDescriptionViewModel))
        bindingFragmentPreparedOrderDetais.rvPreparedOrderItems.adapter = preparedItemOrdersViewModel?.getAdapter()
        preparedItemOrdersViewModel?.updateList()
    }


    fun setPageDetails() {
        if (activity is HomeActivityNew) {
            (activity as HomeActivityNew).setBackButtonVisibility(true)
            (activity as HomeActivityNew).setPageTitle(String.format(getString(R.string.title_order_details),"#" +preparedItemOrdersViewModel?.getOrderDetails()?.value?.id ?: ""))
        }
    }


}
