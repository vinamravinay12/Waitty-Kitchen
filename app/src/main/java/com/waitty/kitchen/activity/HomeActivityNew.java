package com.waitty.kitchen.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.waitty.kitchen.R;
import com.waitty.kitchen.activity.ui.home.HomeFragment;
import com.waitty.kitchen.constant.WaittyConstants;
import com.waitty.kitchen.fragment.FragmentUtils;

public class HomeActivityNew extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView tvPageTitle;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.ibBtnProfile).setOnClickListener(this);
        backButton = findViewById(R.id.ibBtnBack);
        backButton.setOnClickListener(this);

        tvPageTitle = findViewById(R.id.tvPageTitle);

        FragmentUtils.INSTANCE.launchFragment(getSupportFragmentManager(),R.id.nav_host_fragment, new HomeFragment(), WaittyConstants.TAG_HOME);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibBtnBack :
            FragmentUtils.INSTANCE.goBackToPreviousScreen(this,null,null);
            break;
        }
    }


    public void setBackButtonVisibility(boolean toShow) {
        backButton.setVisibility(toShow ? View.VISIBLE : View.GONE);
    }

    public void setPageTitle(String title) {
        tvPageTitle.setText(title);
    }


}
