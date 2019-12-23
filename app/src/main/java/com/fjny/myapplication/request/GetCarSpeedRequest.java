package com.fjny.myapplication.request;

import android.content.Context;

import com.fjny.myapplication.config.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class GetCarSpeedRequest extends BaseRequest {
    private int carId;
    public GetCarSpeedRequest(Context context) {
        super(context);
    }

    public void getCarId(int carId){
        this.carId = carId;
    }
    @Override
    String getUrl() {
        return AppConfig.GET_CAR_SPEED;
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
        int speed = 0;
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            speed = jsonObject.optInt(AppConfig.KEY_CAR_SPEED);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return speed;
    }
}
