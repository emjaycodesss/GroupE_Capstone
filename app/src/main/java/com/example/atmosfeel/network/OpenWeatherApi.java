package com.example.atmosfeel.network;

import com.example.atmosfeel.model.ForecastWeatherResponse;
import com.example.atmosfeel.model.Root;
import com.example.atmosfeel.model.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    @GET("data/2.5/forecast")
    Call <Root> getHourlyForecast(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
    @GET("data/2.5/forecast")
    Call <ForecastWeatherResponse> getForecast(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
    @GET("data/2.5/forecast")
    Call<Root> getHourlyForecastCity(
            @Query("q") String cityName,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
    @GET("data/2.5/forecast")
    Call<ForecastWeatherResponse> getFiveDayForecast(
            @Query("q") String cityName,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}
