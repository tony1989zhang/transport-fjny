package com.fjny.myapplication.request;

import android.content.Context;

import com.fjny.myapplication.config.AppConfig;
import com.fjny.myapplication.model.HonlvdengInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class GetHonlvdnegRequest extends BaseRequest {
    private int roadId;

    public GetHonlvdnegRequest(Context context) {
        super(context);
    }

    public void setRoadId(int roadId){
        this.roadId = roadId;
    }

    @Override
    String getUrl() {
        return AppConfig.GET_LIGHT_MSG;
    }

    @Override
    String getParams() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(AppConfig.KEY_TRAFFIC_LIGHT_ID,roadId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    @Override
    Object analyzeResponse(String responseString) {
        HonlvdengInfo honlvdengInfo = new HonlvdengInfo();
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            honlvdengInfo.setRoadId(roadId);
            honlvdengInfo.setRedTime(jsonObject.optInt(AppConfig.KEY_LIGHT_RED));
            honlvdengInfo.setGreenTime(jsonObject.optInt(AppConfig.KEY_LIGHT_GREEN));
            honlvdengInfo.setYellowTime(jsonObject.optInt(AppConfig.KEY_LIGHT_YELLOW));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return honlvdengInfo;
    }
}
