package com.fjny.myapplication.request;

import android.content.Context;

import com.fjny.myapplication.config.AppConfig;
import com.fjny.myapplication.model.TrafficInfo;
import com.fjny.myapplication.ui.adapter.TrafficsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

public class GetTrafficRequest extends BaseRequest {
    private int roadId;

    public GetTrafficRequest(Context context) {
        super(context);
    }

    public void setRoadId(int roadId){
        this.roadId =roadId;
    }

    @Override
    String getUrl() {
        return AppConfig.GET_LIGHT_MSG;
    }

    @Override
    String getParams() {
        JSONObject json =new JSONObject();
        try{
            json.put(AppConfig.KEY_TRAFFIC_LIGHT_ID,roadId);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return json.toString();
    }

    @Override
    Object analyzeResponse(String responseString) {
        TrafficInfo trafficInfo =new TrafficInfo();
        try {
            JSONObject json =new JSONObject(responseString);
            trafficInfo.setRoadId(roadId);
            trafficInfo.setRedTime(json.optInt(AppConfig.KEY_LIGHT_RED));
            trafficInfo.setYellowTime(json.optInt(AppConfig.KEY_LIGHT_YELLOW));
            trafficInfo.setGreenTime(json.optInt(AppConfig.KEY_LIGHT_GREEN));
        }catch (JSONException e){
            e.printStackTrace();
        }
        return trafficInfo;
    }
}
