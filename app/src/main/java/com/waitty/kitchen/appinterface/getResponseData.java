package com.waitty.kitchen.appinterface;

import org.json.JSONObject;

public interface getResponseData {
    public void onSuccess(JSONObject OBJ, String message, String typeAPI);
    public void onFailed(String message, String typeAPI);
}
