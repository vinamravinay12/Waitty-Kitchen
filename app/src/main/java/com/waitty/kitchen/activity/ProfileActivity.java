package com.waitty.kitchen.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.waitty.kitchen.R;
import com.waitty.kitchen.appinterface.getResponseData;
import com.waitty.kitchen.constant.constant;
import com.waitty.kitchen.databinding.ActivityProfileBinding;
import com.waitty.kitchen.model.LoginUser;
import com.waitty.kitchen.retrofit.API;
import com.waitty.kitchen.retrofit.APICall;
import com.waitty.kitchen.retrofit.ApiClient;
import com.waitty.kitchen.retrofit.ApiInterface;
import com.waitty.kitchen.utility.Dialog;
import com.waitty.kitchen.utility.Utility;
import org.json.JSONObject;
import java.lang.reflect.Type;
import retrofit2.Call;

public class ProfileActivity extends AppCompatActivity implements getResponseData {
    private Context mContext;
    private ActivityProfileBinding activityProfileBinding;
    private ClickHandler mclickHandler;
    private LoginUser userInformation;

    @Override
    public void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        activityProfileBinding= DataBindingUtil.setContentView(this, R.layout.activity_profile);
        mContext = this;
        init();
    }

    // Variable initialization
    private void init() {
        Utility.setToolbar(mContext,activityProfileBinding.toolbarActionbar);
        mclickHandler = new ClickHandler();
        activityProfileBinding.setClickEvent(mclickHandler);
        setUserData();
    }

    // Set user information
    private void setUserData() {
        Type type = new TypeToken<LoginUser>() { }.getType();
        userInformation = new Gson().fromJson(Utility.getSharedPreferencesString(mContext, constant.USER_INFORMATION), type);

        activityProfileBinding.txtName.setText(userInformation.getName());
        activityProfileBinding.txtKitchenID.setText(getString(R.string.text_kitchenid)+" "+userInformation.getKey());

        activityProfileBinding.switchNotifications.setChecked(Utility.getSharedPreferencesBoolean(mContext, constant.NOTIFICATIONS_SHOW));

        //switch change for notification
        activityProfileBinding.switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Utility.isNetworkAvailable(mContext))
                    changeNotificationsSettingsAPI(isChecked);
                else{
                    activityProfileBinding.switchNotifications.setChecked(!isChecked);
                    Utility.ShowSnackbar(mContext, activityProfileBinding.Cordinator, getString(R.string.check_network));
                }
            }
        });
    }

    // Click event handler
    public class ClickHandler {

        public ClickHandler( ) {
        }

        public void privacyPolicyClick(View view) {
            if(Utility.isNetworkAvailable(mContext))
                Utility.openURL(mContext, constant.PRIVACY_POLICY_URL);
            else
                Utility.ShowSnackbar(mContext, activityProfileBinding.Cordinator, getString(R.string.check_network));
        }

        public void termsConditionsClick(View view) {
            if(Utility.isNetworkAvailable(mContext))
                Utility.openURL(mContext, constant.TERMS_CONDITIONS_URL);
            else
                Utility.ShowSnackbar(mContext, activityProfileBinding.Cordinator, getString(R.string.check_network));
        }

        public void logoutClick(View view) {
            Dialog.showLogoutDialog(mContext);
        }

    }

    // Change notifications settings API
    private void changeNotificationsSettingsAPI(boolean isChecked) {

        try{
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty(API.NOTIFICATION_TOGGLE, isChecked);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonElement> call = apiInterface.profileUpdate(jsonObject,Utility.getSharedPreferencesString(mContext, constant.USER_SECURITY_TOKEN));
            new APICall(mContext).Server_Interaction(call,this ,API.UPDATE_PROFILE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(JSONObject OBJ, String msg, String typeAPI) {

        try {
            if(OBJ.length()>0 && OBJ!=null){
                switch (typeAPI) {
                    case API.UPDATE_PROFILE:
                        Utility.setSharedPreferencesBoolean(mContext, constant.NOTIFICATIONS_SHOW, activityProfileBinding.switchNotifications.isChecked());
                        break;
                }
            }
        }catch (Exception e){e.printStackTrace();}

    }

    @Override
    public void onFailed(String msg, String typeAPI) {
        if(!msg.isEmpty()){
            switch (typeAPI) {
                case API.UPDATE_PROFILE:
                    activityProfileBinding.switchNotifications.setChecked(!activityProfileBinding.switchNotifications.isChecked());
                    Utility.ShowSnackbar(mContext, activityProfileBinding.Cordinator,msg);
                    break;
            }
        }
    }

}
