package com.example.atmosfeel.model;


import java.util.ArrayList;

public class Root{
    public String cod;
    public int message;
    public int cnt;
    public ArrayList<WeatherResponse> list;
    public ForecastWeatherResponse.City city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public ArrayList<WeatherResponse> getList() {
        return list;
    }

    public void setList(ArrayList<WeatherResponse> list) {
        this.list = list;
    }

    public ForecastWeatherResponse.City getCity() {
        return city;
    }

    public void setCity(ForecastWeatherResponse.City city) {
        this.city = city;
    }
}





