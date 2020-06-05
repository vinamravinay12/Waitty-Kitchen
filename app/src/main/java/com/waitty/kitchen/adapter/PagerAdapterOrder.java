package com.waitty.kitchen.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.waitty.kitchen.fragment.NewOrderListFragment;
import com.waitty.kitchen.fragment.PreparingOrderListFragment;

public class PagerAdapterOrder extends FragmentPagerAdapter {

    private Context mContext;
    public NewOrderListFragment newOrderListFragment;
    public PreparingOrderListFragment preparingOrderListFragment;

    // Class constructor
    public PagerAdapterOrder(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        try {

            if (position == 0) {
                newOrderListFragment = new NewOrderListFragment();
                return newOrderListFragment;
            } else if (position == 1) {
                preparingOrderListFragment = new PreparingOrderListFragment();
                return preparingOrderListFragment;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        if (position == 0) {
            newOrderListFragment = (NewOrderListFragment) createdFragment;
            return newOrderListFragment;
        } else if (position == 1) {
            preparingOrderListFragment = (PreparingOrderListFragment) createdFragment;
            return preparingOrderListFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}