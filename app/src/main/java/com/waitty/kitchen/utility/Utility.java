package com.waitty.kitchen.utility;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.translabtechnologies.visitormanagementsystem.vmshost.database.SharedPreferenceManager;
import com.waitty.kitchen.R;
import com.waitty.kitchen.constant.WaittyConstants;
import com.waitty.kitchen.retrofit.API;
import com.waitty.kitchen.retrofit.ApiClient;
import com.waitty.kitchen.retrofit.ApiInterface;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;

public class Utility {

    private static long mLastClickTime = 0;
    private static Snackbar snackbar;

    // String value SET on SharedPreferences


    // Check internet connection
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Show Snackbar for alert
    public static void ShowSnackbar(Context context, View v, String msg) {

        snackbar = Snackbar.make(v, msg, Snackbar.LENGTH_LONG)
                .setAction(context.getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });

        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.colorYellow));

        View snackbarView = snackbar.getView();
        int snackbarTextId = R.id.snackbar_text;
        TextView textView = (TextView) snackbarView.findViewById(snackbarTextId);
        textView.setMaxLines(3);

        snackbar.show();

    }

    // Show Toast
    public static void ShowToast(Context context, String msg, int duration) {

        int gravity = Gravity.CENTER; // the position of toast
        int xOffset = 0; // horizontal offset from current gravity
        int yOffset = 0;

        Toast toast = Toast.makeText(context, msg, duration);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();

    }

    // Application logout function
//    public static void LogOut(Context context) {
//
//        if(Utility.isNetworkAvailable(context))
//            logoutAPI(context);
//
//        NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        nMgr.cancelAll();
//
//        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.remove(context.getString(R.string.app_name));
//        editor.clear();
//        editor.commit();
//        context.getSharedPreferences(context.getString(R.string.app_name), 0).edit().clear().commit();
//
//        Intent intent = new Intent(context, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }


    // Increase Clicking Time
    public static boolean buttonClickTime() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();/**/
        return true;
    }

    // Check application version API
    public static void CheckApplicationVersion(final Context context) {

        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(API.PLATFORM_TYPE, WaittyConstants.DEVICE_TYPE);
            jsonObject.addProperty(API.USER_TYPE, "KITCHEN");
            jsonObject.addProperty(API.VERSION, context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonElement> call = apiInterface.checkVersion(jsonObject);
//            new APICall(context).Server_Interaction(call, new getResponseData() {
//                @Override
//                public void onSuccess(JSONObject OBJ, String msg, String typeAPI) {
//                    Dialog.showApplicationUpdateDialog(context,msg);
//                }
//
//                @Override
//                public void onFailed(String msg, String typeAPI) {
//
//                }
//            }, API.APPVERSION);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Check value null and return
    public static String checkNull(String value) {
        return value == null ? "" : value;
    }

    // Set toolbar with back icon
    public static void setToolbar(Context context, Toolbar mToolbar) {
        final AppCompatActivity activity = (AppCompatActivity) context;
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.mipmap.back_black);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    // Change date on specific format
    public static String ChangeDateFormat(SimpleDateFormat dateFormatterServer, String date, SimpleDateFormat dateFormatterConvert) {
        try {
            Date NewDate = dateFormatterServer.parse(date);
            return dateFormatterConvert.format(NewDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // UTC to local date
    public static String chageUTC_ToLocalDate(SimpleDateFormat dateFormaterServer, String date, SimpleDateFormat dateFormaterConvert) {
        String returnValue = "";
        try {

            if (date == null || date.isEmpty())
                return "";

            dateFormaterServer.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = dateFormaterServer.parse(date);

            dateFormaterConvert.setTimeZone(TimeZone.getDefault());
            returnValue = dateFormaterConvert.format(value);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    // Getting how much time after from now
    public static String getTimeAfter(Context context, Calendar yourCalenderDate) {
        Calendar currentCal = Calendar.getInstance();
        CharSequence ago = DateUtils.getRelativeTimeSpanString(currentCal.getTimeInMillis(), yourCalenderDate.getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS);
        return ago.toString();
    }

    // Open url
    public static void openURL(Context ctx, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        ctx.startActivity(browserIntent);
    }

    public static void doShakeAnimation(View view) {
        ObjectAnimator
                .ofFloat(view, "translationX", 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
                .setDuration(200)
                .start();
    }


    public static boolean isTablet(Context context) {
        try {
            DisplayMetrics dm =
                    context.getResources().getDisplayMetrics();
            float screenWidth = dm.widthPixels / dm.xdpi;
            float screenHeight = dm.heightPixels / dm.ydpi;
            double size = Math.sqrt(Math.pow(screenWidth, 2) +
                    Math.pow(screenHeight, 2));

            return size >= 7;
        } catch (Throwable t) {
            Log.e("LogError", t.toString());
            return false;
        }

    }

    public static String getToken(Context context) {
           return new SharedPreferenceManager(context,WaittyConstants.LOGIN_SP).getStringPreference(API.AUTHORIZATION);
    }


    public static String getMessageOnErrorCode(@Nullable Integer errorCode,Context context) {
        switch (errorCode) {
            case API.NETWORK_ERROR : return context.getString(R.string.network_not_found);
            case API.BLOCK_ADMIN : return context.getString(R.string.block_admin_msg);
            case API.SESSION_EXPIRE : return context.getString(R.string.session_expire_msg);
            default: return context.getString(R.string.something_went_wrong);
        }

    }


    public static String getStringFrom(@Nullable Date arrivalDate, @NotNull String displayDateTimeFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(displayDateTimeFormat);
        return simpleDateFormat.format(arrivalDate);
    }
}// final class ends here

