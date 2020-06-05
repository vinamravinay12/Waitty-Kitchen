package com.waitty.kitchen.retrofit;

public class API {
    public static final int NETWORK_ERROR = 404;
    public static final int OTHER_FAILED = 422;
    public static final int SESSION_EXPIRE = 401;
    public static final int BLOCK_ADMIN = 403;
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
    public static final String AUTHORIZATION = "Authorization";
    public static final String NOTIFICATION_TOGGLE = "notification_toggle";
    public static final String APPVERSION = "appversion";

    // Base URL
    //public static final String BASE_URL = "http://18.224.15.124/";
    public static final String BASE_URL = "https://admin.waitty.com/api/";

    // API name
    public static final String LOGIN = "login_kitchen";
    public static final String UPDATE_PROFILE = "patch/user";
    public static final String LOGOUT = "logout";
    public static final String GET_NEW_ORDER = "getneworder_kitchen";
    public static final String GET_PREPARING_ORDER = "getrunningorder_kitchen";
    public static final String ORDER_START_PREPARING = "order_start_preparing";
    public static final String DONE_ORDER_ITEM = "prepared_orderitem_kitchen";
    public static final String DONE_ORDER = "mark_complete_order_kitchen";
    public static final String SET_ORDER_ETA = "arriving_time";

    // API input parameter
    public static final String KEY= "key";
    public static final String PASSWORD= "password";
    public static final String DEVICE_TYPE= "device_type";
    public static final String ORDER_STATUS_ID= "orderstatusId";
    public static final String PLATFORM_TYPE= "platform_type";
    public static final String USER_TYPE= "user_type";
    public static final String VERSION= "version";
    public static final String PAGE= "page";
    public static final String LIMIT= "limit";
    public static final String ORDER_ITEM_ID= "orderitemId";
    public static final String ORDER_ID= "orderId";
    public static final String ORDER_ARRIVING_TIME= "order_arriving_time";

    //LOGIN response
    public static final String USER_ID = "id";
    public static final String PARENT_ID = "parentId";
    public static final String COUNTRYCODE = "country_code";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String KITCHEN_NAME = "name";
    public static final String PROFILE_IMAGE = "profile_image";
    public static final String KITCHEN_ID = "key";
    public static final String RESTAURANT = "restaurant";

}

