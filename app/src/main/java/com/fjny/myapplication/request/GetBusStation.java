package com.fjny.myapplication.request;

import android.content.Context;

import com.fjny.myapplication.config.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetBusStation extends BaseRequest {
    int mBusStationId;
    public GetBusStation(Context context) {
        super(context);
    }

    public void getBusStationId(int BusStationId){
        this.mBusStationId = BusStationId;
    }

    @Override
    String getUrl() {
        return AppConfig.GET_BUS_STATION_INFO;
    }

    @Override
    String getParams() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppConfig.KEY_BUS_STATION_ID,mBusStationId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    @Override
    Object analyzeResponse(String responseString) {
        List<Integer> integers = new ArrayList<>();

        try {
            char [] chars = responseString.substring(0,1).toCharArray();
            if (chars[0] == '[') {
                JSONArray jsonArray = new JSONArray(responseString);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject json = jsonArray.getJSONObject(i);
                    integers.add(json.optInt(AppConfig.KEY_DISTANCE));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return integers;
    }
}










