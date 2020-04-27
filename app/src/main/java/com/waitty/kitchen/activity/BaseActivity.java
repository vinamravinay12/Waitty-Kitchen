package com.waitty.kitchen.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import com.waitty.kitchen.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        finish();
        super.onBackPressed();
    }

}
