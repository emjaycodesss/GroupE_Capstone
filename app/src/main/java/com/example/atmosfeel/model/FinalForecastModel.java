package com.example.atmosfeel.model;

import java.io.Serializable;

public class FinalForecastModel implements Serializable {
    private String city;
    private String day;
    private String status;
    private String temperature;
    private String minTemperature;
    private String maxTemperature;
    private String sunrise;
    private String sunset;
    private String wind;
    private String pressure;
    private String humidity;
    private String cloud;
    private Integer id;
    private String time;
    private String image;
    private String fullDayForecast;

    public FinalForecastModel(String city, String day, String status, String temperature, String minTemperature, String maxTemperature, String sunrise, String sunset, String wind, String pressure, String humidity, String cloud, Integer id, String time) {
        this.city = city;
        this.day = day;
        this.status = status;
        this.temperature = temperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.wind = wind;
        this.pressure = pressure;
        this.humidity = humidity;
        this.cloud = cloud;
        this.id = id;
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getWind() {
        return wind;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getCloud() {
        return cloud;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "FinalForecastModel{" +
                "city='" + city + '\'' +
                ", day='" + day + '\'' +
                ", status='" + status + '\'' +
                ", temperature='" + temperature + '\'' +
                ", minTemperature='" + minTemperature + '\'' +
                ", maxTemperature='" + maxTemperature + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", wind='" + wind + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                ", cloud='" + cloud + '\'' +
                ", id=" + id +
                ", time='" + time + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinalForecastModel that = (FinalForecastModel) o;

        if (!city.equals(that.city)) return false;
        if (!day.equals(that.day)) return false;
        if (!status.equals(that.status)) return false;
        if (!temperature.equals(that.temperature)) return false;
        if (!minTemperature.equals(that.minTemperature)) return false;
        if (!maxTemperature.equals(that.maxTemperature)) return false;
        if (!sunrise.equals(that.sunrise)) return false;
        if (!sunset.equals(that.sunset)) return false;
        if (!wind.equals(that.wind)) return false;
        if (!pressure.equals(that.pressure)) return false;
        if (!humidity.equals(that.humidity)) return false;
        if (!cloud.equals(that.cloud)) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return time != null ? time.equals(that.time) : that.time == null;
    }

    @Override
    public int hashCode() {
        int result = city.hashCode();
        result = 31 * result + day.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + temperature.hashCode();
        result = 31 * result + minTemperature.hashCode();
        result = 31 * result + maxTemperature.hashCode();
        result = 31 * result + sunrise.hashCode();
        result = 31 * result + sunset.hashCode();
        result = 31 * result + wind.hashCode();
        result = 31 * result + pressure.hashCode();
        result = 31 * result + humidity.hashCode();
        result = 31 * result + cloud.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFullDayForecast() {
        return fullDayForecast;
    }

    public void setFullDayForecast(String fullDayForecast) {
        this.fullDayForecast = fullDayForecast;
    }
}
