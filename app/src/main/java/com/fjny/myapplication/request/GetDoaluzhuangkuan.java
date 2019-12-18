package com.fjny.myapplication.request;

import android.content.Context;

import com.fjny.myapplication.config.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class GetDoaluzhuangkuan extends BaseRequest {
    private int roadId;
    public GetDoaluzhuangkuan(Context context) {
        super(context);
    }

    public void  getRoadId(int roadId){
        this.roadId = roadId;
    }
    @Override
    String getUrl() {
        return AppConfig.GET_ROAD_STATION_INFO;
    }

    @Override
    String getParams() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppConfig.KEY_ROAD_ID,roadId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    @Override
    Object analyzeResponse(String responseString) {
            int result = 0;
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            result = jsonObject.optInt(AppConfig.KEY_STATUS);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
