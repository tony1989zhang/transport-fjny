package com.fjny.myapplication.model;

public class TrafficInfo {
    private int roadId;
    private int redTime;
    private int yellowTime;
    private int greenTime;

    public int getYellowTime() {
        return yellowTime;
    }
    public int getGreenTime() {
        return greenTime;
    }
    public int getRedTime() {
        return redTime;
    }
    public int getRoadId() {
        return roadId;
    }
    public void setGreenTime(int greenTime) {
        this.greenTime = greenTime;
    }
    public void setRedTime(int redTime) {
        this.redTime = redTime;
    }
    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }
    public void setYellowTime(int yellowTime) {
        this.yellowTime = yellowTime;
    }
}
