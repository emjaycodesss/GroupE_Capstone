package com.example.atmosfeel.network;

import android.os.AsyncTask;

import com.example.atmosfeel.model.Root;
import com.example.atmosfeel.model.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class GetWeatherDetailsAsyncTask extends AsyncTask<Double, Void, List<WeatherResponse>> {
    private final OpenWeatherApi serviceApi;
    private final Callback callback;

    public GetWeatherDetailsAsyncTask(OpenWeatherApi serviceApi, Callback callback) {
        this.serviceApi = serviceApi;
        this.callback = callback;
    }

    @Override
    protected List<WeatherResponse> doInBackground(Double... params) {
        Double latitude = params[0];
        Double longitude = params[1];
        Call<Root> call = serviceApi.getHourlyForecast(
                latitude != null ? latitude : 0.0,
                longitude != null ? longitude : 0.0,
                "cbf26d51498f81728fd1bb69df49fa59", "metric"
        );

        try {
            Response<Root> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().getList();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<WeatherResponse> result) {
        super.onPostExecute(result);
        if (result != null) {
            callback.onSuccess(result);
        } else {
            callback.onError();
        }
    }

    public interface Callback {
        void onSuccess(List<WeatherResponse> result);
        void onError();
    }
}
