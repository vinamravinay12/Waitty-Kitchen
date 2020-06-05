package com.waitty.kitchen.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.waitty.kitchen.fragment.NewOrderDetailsFragment
import com.waitty.kitchen.fragment.PreparedOrdersFragment
import com.waitty.kitchen.fragment.PreparingOrderDetailsListFragment
import com.waitty.kitchen.fragment.PreparingOrderListFragment
import java.lang.ref.WeakReference

class OrderPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var currentFragmentWeakReference: WeakReference<Fragment>? = null
    override fun getItemCount(): Int {
       return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                val newOrderDetailsListFragment: Fragment = NewOrderDetailsFragment()
                currentFragmentWeakReference = WeakReference(newOrderDetailsListFragment)
                return newOrderDetailsListFragment
            }
            1-> {
                val preparingOrderListFragment: Fragment = PreparingOrderDetailsListFragment()
                currentFragmentWeakReference = WeakReference(preparingOrderListFragment)
                return preparingOrderListFragment
            }
            2 -> {
                val preparedOrdersFragment: Fragment = PreparedOrdersFragment()
                currentFragmentWeakReference = WeakReference(preparedOrdersFragment)
                return preparedOrdersFragment
            }
        }
        return NewOrderDetailsFragment()
    }
}