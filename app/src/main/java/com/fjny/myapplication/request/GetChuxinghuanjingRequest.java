package com.fjny.myapplication.request;

import android.content.Context;

import com.fjny.myapplication.config.AppConfig;
import com.fjny.myapplication.model.ChuxinghuanjingInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class GetChuxinghuanjingRequest extends BaseRequest{

    public GetChuxinghuanjingRequest(Context context) {
        super(context);
    }

    @Override
    String getUrl() {
        return AppConfig.GET_ALL_SENSOR;
    }
    //传递的参数
    @Override
    String getParams() {
        return "";
    }

    @Override
    Object analyzeResponse(String responseString) {
        //实例化 出行环境 实体类
        ChuxinghuanjingInfo chuxinghuanjingInfo = new ChuxinghuanjingInfo();
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            chuxinghuanjingInfo.setPm(jsonObject.optInt(AppConfig.KEY_SENSOR_PM));
            chuxinghuanjingInfo.setCo2(jsonObject.optInt(AppConfig.KEY_SENSOR_CO2));
            chuxinghuanjingInfo.setTemp(jsonObject.optInt(AppConfig.KEY_SENSOR_TEMP));
            chuxinghuanjingInfo.setLight(jsonObject.optInt(AppConfig.KEY_SENSOR_LIGHT));
            chuxinghuanjingInfo.setHumidity(jsonObject.optInt(AppConfig.KEY_SENSOR_HUM));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chuxinghuanjingInfo;
    }
}
