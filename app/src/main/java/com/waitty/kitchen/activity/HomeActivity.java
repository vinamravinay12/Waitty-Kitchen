package com.waitty.kitchen.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.waitty.kitchen.R;
import com.waitty.kitchen.adapter.PagerAdapterOrder;
import com.waitty.kitchen.constant.constant;
import com.waitty.kitchen.databinding.ActivityHomeBinding;
import com.waitty.kitchen.utility.Utility;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity {
    private Context mContext;
    private ActivityHomeBinding activityHomeBinding;
    private boolean doubleBackToExitPressedOnce = false;
    public PagerAdapterOrder adapter;
    private ClickHandler mclickHandler;
    private Typeface type,typeBold;
    private int currentTab;

    private Handler handler;
    private Runnable update;
    private Timer timer;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        activityHomeBinding= DataBindingUtil.setContentView(this, R.layout.activity_home);
        mContext = this;
        init();
    }

    // Variable initialization
    private void init() {
        try {
            Utility.setSharedPreferencesBoolean(this, constant.IS_LOGIN, true);
            Utility.setSharedPreferencesBoolean(mContext,constant.NEW_RELOAD_BY_FCM,false);
            type = Typeface.createFromAsset(mContext.getAssets(), "p_medium.TTF");
            typeBold = Typeface.createFromAsset(mContext.getAssets(), "p_semibold.TTF");
            mclickHandler = new ClickHandler();
            activityHomeBinding.setClickEvent(mclickHandler);
            Utility.setSharedPreferencesInteger(mContext,constant.NOTIFICATION_COUNT_NEW,0);

            setToolbar();
            Utility.CheckApplicationVersion(mContext);
            setNotificationCount();
            setPager();
        }catch (Exception e){e.printStackTrace();}
    }

    // Set toolbar
    private void setToolbar() {
        setSupportActionBar(activityHomeBinding.toolbarActionbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemProfile) {
            startActivity(new Intent(mContext, ProfileActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    // Set notification badge on top tab
    private void setNotificationCount() {

        timer=new Timer();
        handler = new Handler();

        update = new Runnable() {
            public void run() {
                int totalUnreadNewCount = Utility.getSharedPreferencesInteger(mContext,constant.NOTIFICATION_COUNT_NEW);
                if(totalUnreadNewCount>0)
                    newBadgeAction(true);
                else
                    newBadgeAction(false);

                if(Utility.getSharedPreferencesBoolean(mContext,constant.NEW_RELOAD_BY_FCM)){
                    Utility.setSharedPreferencesBoolean(mContext,constant.NEW_RELOAD_BY_FCM,false);
                    if(Utility.isNetworkAvailable(mContext))
                        adapter.newOrderListFragment.refreshList(false);
                }

            }
        };

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 5000, 5000);

    }

    // Click event handler
    public class ClickHandler {

        public ClickHandler( ) {
        }

        public void txtNewOrderClick(View view) {
            InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            currentTab=0;
            newBadgeAction(false);
            Utility.setSharedPreferencesInteger(mContext, constant.NOTIFICATION_COUNT_NEW,0);
            activityHomeBinding.pager.setCurrentItem(0,false);
            activityHomeBinding.txtNewOrder.setBackgroundResource(R.drawable.round_selected_submenu);
            activityHomeBinding.txtNewOrder.setTypeface(typeBold);

            activityHomeBinding.txtPreparingOrder.setBackgroundResource(R.color.colorTransparent);
            activityHomeBinding.txtPreparingOrder.setTypeface(type);

            adapter.newOrderListFragment.checkListSize();
        }

        public void txtPreparingOrderClick(View view) {
            InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            currentTab=1;
            activityHomeBinding.pager.setCurrentItem(1,false);
            activityHomeBinding.txtNewOrder.setBackgroundResource(R.color.colorTransparent);
            activityHomeBinding.txtNewOrder.setTypeface(type);
            activityHomeBinding.txtPreparingOrder.setBackgroundResource(R.drawable.round_selected_submenu);
            activityHomeBinding.txtPreparingOrder.setTypeface(typeBold);
            adapter.preparingOrderListFragment.checkListSize();
        }

    }

    // Set tab and pager data
    private void setPager() {
        currentTab=0;
        adapter=new PagerAdapterOrder(getSupportFragmentManager(),mContext);
        activityHomeBinding.pager.setAdapter(adapter);
        activityHomeBinding.pager.setCurrentItem(0);
        activityHomeBinding.pager.setOffscreenPageLimit(2);
        adapter.notifyDataSetChanged();

        activityHomeBinding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(activityHomeBinding.pager.getWindowToken(), 0);
                activityHomeBinding.pager.setCurrentItem(i,false);
                currentTab=i;
                if(i==0){
                    newBadgeAction(false);
                    Utility.setSharedPreferencesInteger(mContext, constant.NOTIFICATION_COUNT_NEW,0);
                    activityHomeBinding.txtNewOrder.setBackgroundResource(R.drawable.round_selected_submenu);
                    activityHomeBinding.txtNewOrder.setTypeface(typeBold);
                    activityHomeBinding.txtPreparingOrder.setBackgroundResource(R.color.colorTransparent);
                    activityHomeBinding.txtPreparingOrder.setTypeface(type);
                    adapter.newOrderListFragment.checkListSize();
                }else if(i==1){
                    activityHomeBinding.txtNewOrder.setBackgroundResource(R.color.colorTransparent);
                    activityHomeBinding.txtNewOrder.setTypeface(type);
                    activityHomeBinding.txtPreparingOrder.setBackgroundResource(R.drawable.round_selected_submenu);
                    activityHomeBinding.txtPreparingOrder.setTypeface(typeBold);
                    adapter.preparingOrderListFragment.checkListSize();
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    // Show and hide new notification badge
    public void newBadgeAction(boolean status){
        if(status)
            activityHomeBinding.layLinCountNew.setVisibility(View.VISIBLE);
        else
            activityHomeBinding.layLinCountNew.setVisibility(View.GONE);

    }

    // Change tab screen background color
    public void changeBackgroundColor(int myTab,boolean listItem){
        if(currentTab==myTab){
            if(listItem) {
                activityHomeBinding.Cordinator.setBackgroundColor(getResources().getColor(R.color.colorStatusBackground));
                activityHomeBinding.tabLayoutMain.setBackgroundResource(R.drawable.status_tab_background);
            }else {
                activityHomeBinding.Cordinator.setBackgroundColor(getResources().getColor(R.color.colorCardProfile));
                activityHomeBinding.tabLayoutMain.setBackgroundResource(R.drawable.status_tab_background_empty);
            }
        }

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        if (doubleBackToExitPressedOnce) {
            finish();
            System.exit(0);
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Utility.ShowSnackbar(mContext,activityHomeBinding.Cordinator,getString(R.string.back_msg));

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
