package com.fjny.myapplication.model;

public class HonlvdengInfo {
    public int getRoadId() {
        return roadId;
    }

    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    public int getRedTime() {
        return redTime;
    }

    public void setRedTime(int redTime) {
        this.redTime = redTime;
    }

    public int getYellowTime() {
        return yellowTime;
    }

    public void setYellowTime(int yellowTime) {
        this.yellowTime = yellowTime;
    }

    public int getGreenTime() {
        return greenTime;
    }

    public void setGreenTime(int greenTime) {
        this.greenTime = greenTime;
    }

    private int roadId;
    private int redTime;
    private int yellowTime;
    private int greenTime;

}
