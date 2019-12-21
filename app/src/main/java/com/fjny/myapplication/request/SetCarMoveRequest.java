package com.fjny.myapplication.request;

import android.content.Context;

import com.fjny.myapplication.config.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class SetCarMoveRequest extends BaseRequest {
    private int carId;
    private String carAction;
    public SetCarMoveRequest(Context context) {
        super(context);
    }
    public void getCarId(int carId){
        this.carId = carId;
    }
    public void getCarAction(String carAction){
        this.carAction = carAction;
    }
    @Override
    String getUrl() {
        return AppConfig.SET_CAR_ACTION;
    }

    @Override
    String getParams() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppConfig.KEY_CAR_ID,carId);
            jsonObject.put(AppConfig.KEY_CAR_ACTION,carAction);
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
