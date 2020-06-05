package com.waitty.kitchen.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.translabtechnologies.visitormanagementsystem.vmshost.database.SharedPreferenceManager;
import com.waitty.kitchen.R;
import com.waitty.kitchen.constant.WaittyConstants;
import com.waitty.kitchen.fragment.FragmentUtils;
import com.waitty.kitchen.fragment.LoginFragment;
import com.waitty.kitchen.fragment.SplashFragment;
import com.waitty.kitchen.retrofit.API;
import com.waitty.kitchen.retrofit.APICall;
import com.waitty.kitchen.retrofit.ApiClient;
import com.waitty.kitchen.retrofit.ApiInterface;
import com.waitty.kitchen.utility.MyLoading;
import com.waitty.kitchen.utility.Utility;

import org.json.JSONObject;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        FragmentUtils.INSTANCE.launchFragment(getSupportFragmentManager(),R.id.fragment_container, new SplashFragment(),WaittyConstants.TAG_SPLASH);

       // activityLoginBinding= DataBindingUtil.setContentView(this, R.layout.activity_login);
      //  mContext = this;
      //  init();
    }



    // Variable initialization
//    private void init() {
//        loader = new MyLoading(mContext);
//        mclickHandler = new ClickHandler();
//        activityLoginBinding.setClickEvent(mclickHandler);
//
//        activityLoginBinding.edtKitchenID.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//                checkConditions();
//            }
//        });
//
//        activityLoginBinding.edtPassword.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//                checkConditions();
//            }
//        });
//    }
//
//    // Click event handler
//    public class ClickHandler {
//
//        public ClickHandler( ) {
//        }
//
//        public void loginClick(View view) {
//            NotificationManager nMgr = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//            nMgr.cancelAll();
//            if(Utility.isNetworkAvailable(mContext)) {
//                if (Utility.getSharedPreferencesString(mContext, WaittyConstants.USER_FCMTOKENID).trim().length() > 0 && Utility.getSharedPreferencesString(mContext, WaittyConstants.USER_DEVICEID).trim().length() > 0)
//                    loginAPI();
//                else
//                    new RegisteFCMId(mContext).execute();
//            }else
//                Utility.ShowSnackbar(mContext, activityLoginBinding.Cordinator, getString(R.string.check_network));
//        }
//
//    }
//
//    // Check form conditions
//    private void checkConditions() {
//        String waiterID=activityLoginBinding.edtKitchenID.getText().toString().trim();
//        String password=activityLoginBinding.edtPassword.getText().toString().trim();
//
//        boolean kitchenIDConditions=false;
//        if(waiterID.length()==0) {
//            activityLoginBinding.edtKitchenID.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.name_icon, 0, 0, 0);
//            activityLoginBinding.txtErrorKitchenID.setVisibility(View.GONE);
//        }else if(waiterID.length()<5){
//            activityLoginBinding.edtKitchenID.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.name_icon_red, 0, 0, 0);
//            activityLoginBinding.edtKitchenID.setTextColor(getResources().getColor(R.color.colorValidation));
//            activityLoginBinding.txtErrorKitchenID.setVisibility(View.VISIBLE);
//        }else{
//            activityLoginBinding.edtKitchenID.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.name_icon_black, 0, R.mipmap.done_icon, 0);
//            activityLoginBinding.edtKitchenID.setTextColor(getResources().getColor(R.color.colorNextWelcome));
//            activityLoginBinding.txtErrorKitchenID.setVisibility(View.GONE);
//            kitchenIDConditions=true;
//        }
//
//        boolean passwordConditions=false;
//        if(password.length()==0) {
//            activityLoginBinding.edtPassword.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.password_icon, 0, 0, 0);
//            activityLoginBinding.txtErrorPassword.setVisibility(View.GONE);
//        }else if(password.length()<5){
//            activityLoginBinding.edtPassword.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.password_icon_red, 0, 0, 0);
//            activityLoginBinding.edtPassword.setTextColor(getResources().getColor(R.color.colorValidation));
//            activityLoginBinding.txtErrorPassword.setVisibility(View.VISIBLE);
//        }else{
//            activityLoginBinding.edtPassword.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.password_icon_black, 0, R.mipmap.done_icon, 0);
//            activityLoginBinding.edtPassword.setTextColor(getResources().getColor(R.color.colorNextWelcome));
//            activityLoginBinding.txtErrorPassword.setVisibility(View.GONE);
//            passwordConditions=true;
//        }
//
//        if(kitchenIDConditions && passwordConditions){
//            activityLoginBinding.btnDiseable.setVisibility(View.INVISIBLE);
//            activityLoginBinding.btnEnable.setVisibility(View.VISIBLE);
//        }else{
//            activityLoginBinding.btnDiseable.setVisibility(View.VISIBLE);
//            activityLoginBinding.btnEnable.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    // Login API
//    private void loginAPI() {
//        try{
//            loader.show(getString(R.string.wait));
//            JsonObject jsonObject=new JsonObject();
//            jsonObject.addProperty(API.KEY, activityLoginBinding.edtKitchenID.getText().toString().trim());
//            jsonObject.addProperty(API.PASSWORD, activityLoginBinding.edtPassword.getText().toString().trim());
//            jsonObject.addProperty(API.DEVICE_TYPE, WaittyConstants.DEVICE_TYPE);
//            jsonObject.addProperty(WaittyConstants.USER_DEVICEID,  Utility.getSharedPreferencesString(mContext, WaittyConstants.USER_DEVICEID));
//            jsonObject.addProperty(WaittyConstants.USER_FCMTOKENID, Utility.getSharedPreferencesString(mContext, WaittyConstants.USER_FCMTOKENID));
//
//            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//            Call<JsonElement> call = apiInterface.login(jsonObject);
//            new APICall(mContext).Server_Interaction(call,this ,API.LOGIN);
//
//        }catch (Exception e){
//            e.printStackTrace();
//            loader.dismiss();
//        }
//
//    }
//
//    @Override
//    public void onSuccess(JSONObject OBJ, String msg, String typeAPI) {
//
//        try {
//            loader.dismiss();
//            if(OBJ.length()>0 && OBJ!=null){
//
//                switch (typeAPI) {
//                    case API.LOGIN:
//                        Utility.ShowToast(mContext,msg,0);
//                        Utility.setSharedPreferencesString(mContext, WaittyConstants.USER_INFORMATION, OBJ.getJSONObject(API.DATA).toString());
//                        Utility.setSharedPreferencesBoolean(mContext, WaittyConstants.NOTIFICATIONS_SHOW, OBJ.getJSONObject(API.DATA).getBoolean(API.NOTIFICATION_TOGGLE));
//                        startActivity(new Intent(mContext, HomeActivity.class));
//                        finish();
//                        break;
//                }
//            }
//
//        }catch (Exception e){e.printStackTrace();}
//
//    }
//
//    @Override
//    public void onFailed(String msg, String typeAPI) {
//        loader.dismiss();
//        if(!msg.isEmpty()){
//            switch (typeAPI) {
//                case API.LOGIN:
//                    Utility.ShowSnackbar(mContext, activityLoginBinding.Cordinator,msg);
//                    break;
//            }
//        }
//    }

}
