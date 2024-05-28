package com.example.atmosfeel.model;


public class WeeklyDataModel {
    private String day;
    private String weatherIconResId;
    private String temperature;

    public WeeklyDataModel() {
    }

    public WeeklyDataModel(String day, String  weatherIconResId, String temperature) {
        this.day = day;
        this.weatherIconResId = weatherIconResId;
        this.temperature = temperature;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeatherIconResId() {
        return weatherIconResId;
    }

    public void setWeatherIconResId(String weatherIconResId) {
        this.weatherIconResId = weatherIconResId;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

}
