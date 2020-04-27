package com.waitty.kitchen.fcm;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.waitty.kitchen.constant.constant;
import com.waitty.kitchen.utility.Utility;

public class RegisteFCMId extends AsyncTask<Void, Void, Void> {

    public Context mContext;
    public String fcm_id;

    // Class constructor
    public RegisteFCMId(Context context) {
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        fcm_id = FirebaseInstanceId.getInstance().getToken();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (fcm_id == null || fcm_id.isEmpty()) {
            new RegisteFCMId(mContext).execute();
        } else {
            Utility.setSharedPreferencesString(mContext, constant.USER_FCMTOKENID,fcm_id);
            Log.d("token",fcm_id);
            Utility.setSharedPreferencesString(mContext, constant.USER_DEVICEID, Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID));
            Log.d("token", Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID));
        }
        super.onPostExecute(aVoid);
    }
}
