package com.fjny.myapplication.request;

import android.content.Context;

import com.fjny.myapplication.config.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class GetBalanceRequest extends BaseRequest {
    private int carId;
    public GetBalanceRequest(Context context) {
        super(context);
    }
    public void getId(int carId){
        this.carId = carId;
    }
    @Override
    String getUrl() {
        return AppConfig.GET_CAR_BALANCE;
    }
    @Override
    String getParams() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppConfig.KEY_CAR_ID,carId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    @Override
    Object analyzeResponse(String responseString) {
        int money = 0;
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            money = jsonObject.optInt(AppConfig.KEY_CAR_BALANCE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return money;
    }
}
