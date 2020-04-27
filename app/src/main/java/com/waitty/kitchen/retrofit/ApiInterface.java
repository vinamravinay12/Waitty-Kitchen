package com.waitty.kitchen.retrofit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    @POST(API.LOGIN)
    Call<JsonElement> login(@Body JsonObject OBJ);

    @POST(API.APPVERSION)
    Call<JsonElement> checkVersion(@Body JsonObject OBJ);

    @PUT(API.UPDATE_PROFILE)
    Call<JsonElement> profileUpdate(@Body JsonObject OBJ, @Header(API.AUTHORIZATION) String token);

    @GET(API.LOGOUT)
    Call<JsonElement> logoutApplication(@Header(API.AUTHORIZATION) String token);

    @POST(API.GET_NEW_ORDER)
    Call<JsonElement> getNewOrder(@Body JsonObject OBJ,@Header(API.AUTHORIZATION) String token);

    @POST(API.GET_PREPARING_ORDER)
    Call<JsonElement> getPreparingOrder(@Body JsonObject OBJ,@Header(API.AUTHORIZATION) String token);

    @POST(API.ORDER_START_PREPARING)
    Call<JsonElement> orderStartPreparing(@Body JsonObject OBJ,@Header(API.AUTHORIZATION) String token);

    @POST(API.DONE_ORDER_ITEM)
    Call<JsonElement> doneOrderItem(@Body JsonObject OBJ,@Header(API.AUTHORIZATION) String token);

    @POST(API.DONE_ORDER)
    Call<JsonElement> doneOrder(@Body JsonObject OBJ,@Header(API.AUTHORIZATION) String token);

    @PUT(API.SET_ORDER_ETA)
    Call<JsonElement> setOrderETA(@Body JsonObject OBJ,@Header(API.AUTHORIZATION) String token);

}

