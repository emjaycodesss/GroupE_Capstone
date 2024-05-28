package com.example.atmosfeel.model;

public class CityDataModel {
    private String cityName;
    private String countryName;
    private String temperature;
    private String humidity;
    private String airSpeed;
    private String  weatherImageResId;

    public CityDataModel(String cityName, String countryName, String temperature, String humidity, String airSpeed, String weatherImageResId) {
        this.cityName = cityName;
        this.countryName = countryName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.airSpeed = airSpeed;
        this.weatherImageResId = weatherImageResId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getAirSpeed() {
        return airSpeed;
    }

    public String getWeatherImageResId() {
        return weatherImageResId;
    }
}
