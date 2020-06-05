package com.waitty.kitchen.retrofit;

import android.content.Context;
import com.google.gson.JsonElement;
import com.waitty.kitchen.R;
import com.waitty.kitchen.appinterface.getResponseData;
import com.waitty.kitchen.constant.WaittyConstants;
import com.waitty.kitchen.utility.Dialog;
import com.waitty.kitchen.utility.Utility;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICall {

    private Context mContext;

    // Class constructor
    public APICall(Context ctx) {
        this.mContext = ctx;
    }

    // Call API
//    public void Server_Interaction(Call<JsonElement> call, final getResponseData returnData, final String typeAPI) {
//
//        try {
//
//            call.enqueue(new Callback<JsonElement>() {
//
//                @Override
//                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//
//                        try {
//
//                            if (response.isSuccessful()) {
//                                JSONObject MainOBJ = new JSONObject(response.body().toString());
//                                returnData.onSuccess(MainOBJ,MainOBJ.getString(API.MESSAGE).trim(),typeAPI);
//
//                                if(typeAPI.equals(API.LOGIN))
//                                    Utility.setSharedPreferencesString(mContext, WaittyConstants.USER_SECURITY_TOKEN,response.headers().get(API.AUTHORIZATION));
//                            } else {
//                                if (response.code() == API.SESSION_EXPIRE) {
//                                    Dialog.showAdminBlockTokenExpireDialog(mContext, mContext.getString(R.string.session_expire), mContext.getString(R.string.session_expire_msg));
//                                    returnData.onFailed("",typeAPI);
//                                }else if(response.code() == API.BLOCK_ADMIN){
//                                    Dialog.showAdminBlockTokenExpireDialog(mContext, mContext.getString(R.string.block_admin), mContext.getString(R.string.block_admin_msg));
//                                    returnData.onFailed("",typeAPI);
//                                }else if (response.code() == API.OTHER_FAILED) {
//                                    JSONObject MainOBJ = new JSONObject(response.errorBody().string());
//                                    if(MainOBJ.getString(API.MESSAGE).trim().length()>0)
//                                        returnData.onFailed(MainOBJ.getString(API.MESSAGE).trim(),typeAPI);
//                                    else
//                                        returnData.onFailed("",typeAPI);
//                                }else
//                                    nullCase(false,returnData,typeAPI);
//
//                            }
//
//                        } catch (Exception e) {e.printStackTrace();
//                            nullCase(false,returnData,typeAPI);}
//
//                }
//
//                @Override
//                public void onFailure(Call<JsonElement> call, Throwable t) {
//                    nullCase(true,returnData,typeAPI);
//                }
//            });
//
//        } catch (Exception e) {e.printStackTrace();
//            nullCase(false,returnData,typeAPI);}
//    }
//
//    private void nullCase(boolean isNetwork, getResponseData returnData, String typeAPI) {
//        if (isNetwork)
//            returnData.onFailed(mContext.getString(R.string.check_network),typeAPI);
//        else
//            returnData.onFailed( mContext.getString(R.string.try_again),typeAPI);
//    }

}

