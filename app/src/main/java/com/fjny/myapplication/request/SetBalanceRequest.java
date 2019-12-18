package com.fjny.myapplication.request;

import android.content.Context;

import com.fjny.myapplication.config.AppConfig;
import com.fjny.myapplication.utils.NetUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class SetBalanceRequest extends BaseRequest{
    private int carId;
    private int money;

    public SetBalanceRequest(Context context) {
        super(context);
    }

    public void setMoney(int carId,int money){
        this.carId = carId;
        this.money = money;
    }

    @Override
    String getUrl() {
        return AppConfig.SET_CAR_BALANCE;
    }

    @Override
    String getParams() {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(AppConfig.KEY_CAR_ID,carId);
            jsonObject.put(AppConfig.KEY_MONEY,money);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    Object analyzeResponse(String responseString) {
        String result = "";
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            result = jsonObject.optString("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
