package com.waitty.kitchen.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.waitty.kitchen.R;
import com.waitty.kitchen.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding activitySplashBinding;
    private Context mContext;
    private Thread mThread;
    private boolean isFinish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activitySplashBinding= DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mContext = this;
    //    init();
    }

    // Variable initialization
//    private void init() {
//
//        if (!Utility.getSharedPreferencesBoolean(mContext, WaittyConstants.IS_CHANNEL_PREPARED) && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
//            new NotificationHelper(mContext);
//
//        isFinish = false;
//        mThread = new Thread(mRunnable);
//        mThread.start();
//    }
//
//    // Screen sleep for some time
//    private Runnable mRunnable = new Runnable() {
//
//        public void run() {
//            SystemClock.sleep(3000);
//            mHandler.sendEmptyMessage(0);
//        }
//    };
//
//    // Screen sleep out for given time
//    private Handler mHandler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 0 && (!isFinish)) {
//                isFinish = true;
//                stop();
//            }
//            super.handleMessage(msg);
//        }
//    };
//
//    @Override
//    public void onDestroy() {
//        try {
//            mThread.interrupt();
//            mThread = null;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        super.onDestroy();
//    }
//
//    // Next screen move functionality
//    private void stop() {
//        if (isFinish) {
//
//            if (Utility.getSharedPreferencesBoolean(mContext, WaittyConstants.IS_LOGIN)){
//                if (Utility.isNetworkAvailable(mContext)) {
//                    startActivity(new Intent(mContext, HomeActivity.class));
//                    finish();
//                } else
//                    Utility.ShowSnackbar(mContext, activitySplashBinding.Cordinator, getString(R.string.check_network));
//            }else{
//                startActivity(new Intent(mContext, LoginActivity.class));
//                finish();
//            }
//        }
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            if (isFinish)
//                stop();
//        }
//        return super.onTouchEvent(event);
//    }
//
//    @Override
//    public void onBackPressed() {
//        // TODO Auto-generated method stub
//        isFinish = false;
//        System.exit(0);
//        finish();
//        super.onBackPressed();
//    }
}
