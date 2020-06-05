package com.waitty.kitchen.activity.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.waitty.kitchen.R;
import com.waitty.kitchen.activity.HomeActivity;
import com.waitty.kitchen.activity.HomeActivityNew;
import com.waitty.kitchen.adapter.OrderPagerAdapter;
import com.waitty.kitchen.adapter.PagerAdapterOrder;
import com.waitty.kitchen.databinding.FragmentHomeBinding;
import com.waitty.kitchen.fragment.FragmentUtils;
import com.waitty.kitchen.fragment.NewOrderDetailsFragment;
import com.waitty.kitchen.fragment.PreparedOrdersFragment;
import com.waitty.kitchen.fragment.PreparingOrderDetailsListFragment;
import com.waitty.kitchen.viewmodel.ApiErrorViewModel;

import java.lang.ref.WeakReference;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private FragmentHomeBinding fragmentHomeBinding;
    private ApiErrorViewModel apiErrorViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        fragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        apiErrorViewModel = new ViewModelProvider(getActivity()).get(ApiErrorViewModel.class);
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        fragmentHomeBinding.swipeRefresh.setOnRefreshListener(this);
        fragmentHomeBinding.ordersViewPager.setAdapter(new OrderPagerAdapter(this));
        hideBackButton();
        setPageTitle();



        new TabLayoutMediator(fragmentHomeBinding.orderTabLayout, fragmentHomeBinding.ordersViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0 : tab.setText(getString(R.string.txt_new)); break;
                    case 1 : tab.setText(getString(R.string.txt_preparing)); break;
                    case 2 : tab.setText(getString(R.string.txt_served)); break;
                }
            }
        }).attach();


        fragmentHomeBinding.ordersViewPager.registerOnPageChangeCallback(new PageChangeCallbackHandler());
    }


    @Override
    public void onResume() {
        super.onResume();
        FragmentUtils.INSTANCE.hideKeyboard(fragmentHomeBinding.ordersViewPager.getRootView(),getContext());
    }

    @Override
    public void onRefresh() {
        refreshVisibleFragment(fragmentHomeBinding.ordersViewPager.getCurrentItem());
    }

    private class PageChangeCallbackHandler extends ViewPager2.OnPageChangeCallback {

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            refreshVisibleFragment(position);
        }
    }

    private void refreshVisibleFragment(int position) {
        if(getChildFragmentManager().getFragments().size() <= position) return ;
       Fragment fragment = getChildFragmentManager().getFragments().get(position);
        switch (position){
            case 0 : if(fragment instanceof NewOrderDetailsFragment && fragment.isVisible()) {
                ((NewOrderDetailsFragment) fragment).refreshNewOrdersList();

            }  else fragmentHomeBinding.swipeRefresh.setRefreshing(false);
            break;

            case 1 : if(fragment instanceof PreparingOrderDetailsListFragment && fragment.isVisible()) {
                ((PreparingOrderDetailsListFragment) fragment).refreshPreparingOrdersList();
            } else fragmentHomeBinding.swipeRefresh.setRefreshing(false);
            break;

            case 2 : if(fragment instanceof PreparedOrdersFragment && fragment.isVisible()) {
                ((PreparedOrdersFragment) fragment).fetchPreparedOrders();
            } else fragmentHomeBinding.swipeRefresh.setRefreshing(false);
                break;

        }

    }

    public void showProgress(boolean toShow) {
        fragmentHomeBinding.swipeRefresh.setRefreshing(toShow);
    }



    public void enableSwipeRefresh(boolean isEnabled) {
        fragmentHomeBinding.swipeRefresh.setEnabled(isEnabled);
    }

    private void hideBackButton() {
        if(getActivity() instanceof HomeActivityNew){
            ((HomeActivityNew)getActivity()).setBackButtonVisibility(false);
        }
    }

    private void setPageTitle() {
        if(getActivity() instanceof HomeActivityNew){
            ((HomeActivityNew)getActivity()).setPageTitle(getString(R.string.title_order_history));
        }
    }


    public void showRefresh(boolean toShow) {
        fragmentHomeBinding.swipeRefresh.setRefreshing(toShow);
    }

    public void showBadge(boolean toShow) {
        BadgeDrawable badgeDrawable = fragmentHomeBinding.orderTabLayout.getTabAt(0).getOrCreateBadge();
        badgeDrawable.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorCountdownTimer, null));
        if(toShow) badgeDrawable.setVisible(true); else fragmentHomeBinding.orderTabLayout.getTabAt(0).removeBadge();
    }




}
