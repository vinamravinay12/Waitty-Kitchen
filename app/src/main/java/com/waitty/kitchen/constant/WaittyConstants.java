package com.waitty.kitchen.constant;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class WaittyConstants {

    public static final String PRIVACY_POLICY_URL = "https://waitty.com/privacy-policy/";
    public static final String TERMS_CONDITIONS_URL = "https://waitty.com/terms-and-conditions/";
    public static final String HELP_URL = "https://waitty.com/help/";

    public static final SimpleDateFormat dateFormaterServer = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

    public static final String PRIMARY_CHANNEL = "PRIMARY_CHANNEL";
    public static final String IS_CHANNEL_PREPARED = "IS_CHANNEL_PREPARED";

    public static final String KEY_IS_LOGGED_IN = "IS_LOGIN";
    public static final String LOGIN_SP = "LoginSharedPrefernces";
    public static final String NOTIFICATIONS_SHOW = "NOTIFICATIONS_SHOW";
    public static final String NOTIFICATION_COUNT_NEW = "NOTIFICATION_COUNT_NEW";
    public static final String NEW_RELOAD_BY_FCM = "NEW_RELOAD_BY_FCM";

    public static final String USER_FCMTOKENID= "device_token";
    public static final String USER_DEVICEID= "device_id";
    public static final String DEVICE_TYPE = "android";

    public static final String USER_INFORMATION = "USER_INFORMATION";
    public static final String USER_SECURITY_TOKEN = "USER_SECURITY_TOKEN";

    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    /*ORDER STATUS VALUE*/
    public static final int ORDER_PLACED = 1;
    public static final int ORDER_PREPARING = 2;
    public static final int ORDER_READY_SERVE = 3;
    public static final int ORDER_DELIVERED = 4;
    public static final int ORDER_BLOCK_ADMIN = 5;
    public static final int ORDER_REFUND_INITIATED = 6;
    public static final int ORDER_AMOUNT_REFUNDED = 7;

    public static final String USER_SP = "UserSP";
    public static final String TAG_LOGIN = "LoginFragment";

    public static final String KEY_PREPARED_ORDERS = "KeyPreparedOrders";
    public static final String TAG_HOME = "HomeFragment" ;

    public static final String DISPLAY_DATE_TIME_FORMAT = "dd/MM/yyyy hh:mm a";
    public static final String TAG_ORDER_DETAILS=  "OrderDetailsFragment";
    public static final String TAG_SPLASH = "SplashFragment";
}
