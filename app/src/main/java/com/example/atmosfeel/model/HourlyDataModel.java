package com.example.atmosfeel.model;

public class HourlyDataModel {
    private String time;
    private String iconResId;
    private String temperature;

    public HourlyDataModel() {
    }

    public HourlyDataModel(String time, String iconResId, String temperature) {
        this.time = time;
        this.iconResId = iconResId;
        this.temperature = temperature;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIconResId() {
        return iconResId;
    }

    public void setIconResId(String iconResId) {
        this.iconResId = iconResId;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }


}
