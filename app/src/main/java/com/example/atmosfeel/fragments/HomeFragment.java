package com.example.atmosfeel.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.atmosfeel.R;
import com.example.atmosfeel.adapter.HourlyDataAdapter;
import com.example.atmosfeel.databinding.FragmentHomeBinding;
import com.example.atmosfeel.db.DatabaseHelper;
import com.example.atmosfeel.model.FinalForecastModel;
import com.example.atmosfeel.model.HourlyDataModel;
import com.example.atmosfeel.model.Root;
import com.example.atmosfeel.model.WeatherResponse;
import com.example.atmosfeel.network.GetWeatherDetailsAsyncTask;
import com.example.atmosfeel.network.OpenWeatherApi;
import com.example.atmosfeel.network.RetrofitClient;
import com.example.atmosfeel.utils.LocationHelper;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements LocationHelper.LocationResultListener{

    private FragmentHomeBinding binding;
    HourlyDataAdapter adapter;
    List<HourlyDataModel> hourlyDataList = new ArrayList<>();
    private LocationHelper locationHelper;
    private static final String BASE_URL = "https://api.openweathermap.org/";
    private static final String API_KEY = "cbf26d51498f81728fd1bb69df49fa59";
    private static final String UNITS = "metric";
    private ProgressDialog progressDialog;
    WeatherResponse weatherResponse;
    String city="";
    DatabaseHelper databaseHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        setAdapters();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        progressDialog=new ProgressDialog(requireContext());
        progressDialog.setTitle(R.string.app_name);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        locationHelper = new LocationHelper(requireActivity(),this);
        progressDialog.show();
        locationHelper.requestLocation();
        databaseHelper=new DatabaseHelper(requireContext());
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("aaa", "onQueryTextSubmit: "+query);
                getWeatherByCity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        binding.ivfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weatherResponse==null){
                    Toast.makeText(requireContext(), "No Data to Save", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveToDb(weatherResponse);
            }
        });
    }

    private void setAdapters() {
        adapter = new HourlyDataAdapter(hourlyDataList);
        binding.rvHourlyWeather.setAdapter(adapter);

    }

    private void getWeather(Location location){
        progressDialog.show();
        OpenWeatherApi openWeatherApi = RetrofitClient.getClient(BASE_URL).create(OpenWeatherApi.class);
        city=locationHelper.getCityName(requireContext(),location.getLatitude(),location.getLongitude());
        GetWeatherDetailsAsyncTask getWeatherDetailsAsyncTask=new GetWeatherDetailsAsyncTask(openWeatherApi, new GetWeatherDetailsAsyncTask.Callback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(List<WeatherResponse> responses) {
                binding.tvTalomo.setText(city);
                hourlyDataList.clear();
                weatherResponse=responses.get(0);
                if (weatherResponse.getMain() != null) {
                    binding.tvHumidityValue.setText(weatherResponse.getMain().getHumidity()+"%");
                }

                binding.tvPresureValue.setText(weatherResponse.getMain().getPressure()+" mb");
                binding.tvSpeedValue.setText(weatherResponse.getWind().getSpeed()+" mp/h");
                binding.tvCloudsValue.setText(weatherResponse.getClouds().getAll()+"%");
                binding.tvTemperature.setText(weatherResponse.getMain().getTemp()+"°C");
                binding.tvWeatherReport.setText(weatherResponse.getWeatherList().get(0).getDescription());

                for (WeatherResponse weatherResponse1:responses) {
                    Log.e("aaa", "onSuccess: " + weatherResponse1.getDateTimeText());
                    if (shouldAdd(weatherResponse1.getDateTimeText())) {
                        hourlyDataList.add(new HourlyDataModel(getHourMinute(weatherResponse1.getDateTimeText()), weatherResponse1.getWeatherList().get(0).getIcon(), weatherResponse1.getMain().getFeels_like() + "°C"));

                    }
                }
                adapter.notifyDataSetChanged();
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onError() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("aaa", "onError: ");

            }
        });
        getWeatherDetailsAsyncTask.execute(location.getLatitude(),location.getLongitude());
        progressDialog.dismiss();
    }
    private boolean shouldAdd(String inputDate){
        @SuppressLint("SimpleDateFormat") String date=new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date dateObj = inputFormat.parse(inputDate);
            String compareDate= outputFormat.format(dateObj);
            Log.e("aaaaaa", "shouldAdd: "+compareDate+":"+date );
            return date.equals(compareDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void getWeatherByCity(String city){
        progressDialog.show();
        OpenWeatherApi openWeatherApi = RetrofitClient.getClient(BASE_URL).create(OpenWeatherApi.class);
        openWeatherApi.getHourlyForecastCity(city,API_KEY,UNITS).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful() && response.code()==200){
                    hourlyDataList.clear();
                    HomeFragment.this.city=city;
                    binding.tvTalomo.setText(city);

                    weatherResponse=response.body().getList().get(0);
                    if (weatherResponse.getMain() != null) {
                        binding.tvHumidityValue.setText(weatherResponse.getMain().getHumidity()+"%");
                    }

                    binding.tvPresureValue.setText(weatherResponse.getMain().getPressure()+" mb");
                    binding.tvSpeedValue.setText(weatherResponse.getWind().getSpeed()+" mp/h");
                    binding.tvCloudsValue.setText(weatherResponse.getClouds().getAll()+"%");
                    binding.tvTemperature.setText(weatherResponse.getMain().getTemp()+"°C");
                    binding.tvWeatherReport.setText(weatherResponse.getWeatherList().get(0).getDescription());
                    binding.tvSunValue.setText(weatherResponse.getMain().getGrnd_level()+"");
                    binding.tvSunValue.setText(weatherResponse.getClouds().getAll()+"");
                    for (WeatherResponse weatherResponse1:response.body().list) {
                        if (shouldAdd(weatherResponse1.getDateTimeText())) {
                            hourlyDataList.add(new HourlyDataModel(getHourMinute(weatherResponse1.getDateTimeText()), weatherResponse1.getWeatherList().get(0).getIcon(), weatherResponse1.getMain().getFeels_like() + "°C"));
                        }
                    }
                    String image=weatherResponse.getWeatherList().get(0).getIcon();

                    String weatherIconUrl = "https://openweathermap.org/img/wn/" + image + "@4x.png";
                    Glide.with(getContext())
                            .load(weatherIconUrl)
                            .into(binding.ivClouds);

                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
            progressDialog.dismiss();
                Toast.makeText(requireContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static String getHourMinute(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");

        try {
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return ""; // Handle error case
        }
    }

    @Override
    public void onLocationResult(Location location) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        getWeather(location);
    }

    @Override
    public void onLocationError(String error) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
    }
    private void saveToDb(WeatherResponse weatherResponse) {
        String currentDateTime = getCurrentDateTime();
        Gson gson = new Gson();
        String weeklyDataJson = gson.toJson(hourlyDataList);
        FinalForecastModel forecast = new FinalForecastModel(
                city,
                currentDateTime,
                weatherResponse.getWeatherList().get(0).getDescription(),
                String.valueOf(weatherResponse.getMain().getTemp()),
                String.valueOf(weatherResponse.getMain().getTemp_max()),
                String.valueOf(weatherResponse.getMain().getTemp_min()),
                convertTime(weatherResponse.getSys().getSunrise()),
                convertTime(weatherResponse.getSys().getSunset()),
                String.valueOf(weatherResponse.getWind().getSpeed()),
                String.valueOf(weatherResponse.getMain().getPressure()),
                String.valueOf(weatherResponse.getMain().getHumidity()),
                String.valueOf(weatherResponse.getClouds().getAll()),
                null,
                currentDateTime
        );
        forecast.setFullDayForecast(weeklyDataJson);
        forecast.setImage(weatherResponse.getWeatherList().get(0).getIcon());

        databaseHelper.addForecast(forecast);
        Toast.makeText(requireContext(), "Data Saved!!", Toast.LENGTH_SHORT).show();
    }
    private String convertTime(long time) {
        Date date = new Date(time * 1000L);
        SimpleDateFormat timeFormatted = new SimpleDateFormat("HH:mm", Locale.UK);
        timeFormatted.setTimeZone(TimeZone.getDefault());
        return timeFormatted.format(date);
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("aaaa", "onRequestPermissionsResult: " );
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}