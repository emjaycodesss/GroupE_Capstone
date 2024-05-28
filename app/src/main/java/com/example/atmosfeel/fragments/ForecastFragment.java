package com.example.atmosfeel.fragments;

import static com.example.atmosfeel.fragments.HomeFragment.getHourMinute;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.atmosfeel.R;
import com.example.atmosfeel.adapter.HourlyDataAdapter;
import com.example.atmosfeel.adapter.WeeklyDataAdapter;
import com.example.atmosfeel.databinding.FragmentForecastBinding;
import com.example.atmosfeel.db.DatabaseHelper;
import com.example.atmosfeel.model.FinalForecastModel;
import com.example.atmosfeel.model.ForecastWeatherResponse;
import com.example.atmosfeel.model.HourlyDataModel;
import com.example.atmosfeel.model.WeeklyDataModel;
import com.example.atmosfeel.network.OpenWeatherApi;
import com.example.atmosfeel.network.RetrofitClient;
import com.example.atmosfeel.utils.LocationHelper;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForecastFragment extends Fragment implements LocationHelper.LocationResultListener {
    private FragmentForecastBinding binding;
    HourlyDataAdapter adapter;
    List<HourlyDataModel> hourlyDataList = new ArrayList<>();
    WeeklyDataAdapter weeklyDataAdapter;
    List<WeeklyDataModel> weeklyDataList = new ArrayList<>();
    List<FinalForecastModel> finalForecastModels = new ArrayList<>();
    private static final String BASE_URL = "https://api.openweathermap.org/";
    private static final String API_KEY = "cbf26d51498f81728fd1bb69df49fa59";
    private static final String UNITS = "metric"; // Use "imperial" for Fahrenheit
    private ProgressDialog progressDialog;
    private LocationHelper locationHelper;
    ForecastWeatherResponse forecastWeatherResponse;
    String city = "";
    DatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForecastBinding.inflate(getLayoutInflater());
        hourlyDataList.clear();
        weeklyDataList.clear();
        setAdapters();
        databaseHelper = new DatabaseHelper(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle(R.string.app_name);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        locationHelper = new LocationHelper(requireActivity(), this);
        locationHelper.requestLocation();

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("aaa", "onQueryTextSubmit: " + query);
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
                Log.d("CheckClicked", "onClick: 1");
                if (forecastWeatherResponse != null) {
                    Log.d("CheckClicked", "onClick: 2 " + forecastWeatherResponse);
                    for (FinalForecastModel forecastModel : finalForecastModels) {
                        databaseHelper.addForecast(forecastModel);
                    }
                    Toast.makeText(requireContext(), "Data Saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Response is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean shouldAdd(String inputDate) {
        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date dateObj = inputFormat.parse(inputDate);
            String compareDate = outputFormat.format(dateObj);
            Log.e("aaaaaa", "shouldAdd: " + compareDate + ":" + date);
            return date.equals(compareDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void getWeatherByCity(String query) {
        progressDialog.show();
        OpenWeatherApi openWeatherApi = RetrofitClient.getClient(BASE_URL).create(OpenWeatherApi.class);
        binding.tvTalomo.setText(query);

        // Clear finalForecastModels list before fetching new data
        finalForecastModels.clear();

        openWeatherApi.getFiveDayForecast(query, API_KEY, UNITS).enqueue(new Callback<ForecastWeatherResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ForecastWeatherResponse> call, Response<ForecastWeatherResponse> response) {
                weeklyDataList.clear();
                if (response.isSuccessful() && response.code() == 200) {
                    city = query;
                    forecastWeatherResponse = response.body();
                    String image = forecastWeatherResponse.getList().get(0).getWeather().get(0).getIcon();
                    binding.tvTemperature.setText(forecastWeatherResponse.getList().get(0).getMain().getTemp() + "°C");
                    binding.tvWeatherReport.setText(forecastWeatherResponse.getList().get(0).getWeather().get(0).getDescription() + "°C");
                    String weatherIconUrl = "https://openweathermap.org/img/wn/" + image + "@4x.png";
                    Glide.with(getContext())
                            .load(weatherIconUrl)
                            .into(binding.ivClouds);

                    weeklyDataList.addAll(processWeatherData(forecastWeatherResponse));
                    weeklyDataAdapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ForecastWeatherResponse> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void setAdapters() {

        weeklyDataAdapter = new WeeklyDataAdapter(weeklyDataList);
        binding.rvWeeklyWeatherForecast.setAdapter(weeklyDataAdapter);

        adapter = new HourlyDataAdapter(hourlyDataList);
        binding.rvHourlyWeather.setAdapter(adapter);
    }

    @Override
    public void onLocationResult(Location location) {
        OpenWeatherApi openWeatherApi = RetrofitClient.getClient(BASE_URL).create(OpenWeatherApi.class);
        openWeatherApi.getForecast(location.getLatitude(), location.getLongitude(), API_KEY, UNITS).enqueue(new Callback<ForecastWeatherResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ForecastWeatherResponse> call, Response<ForecastWeatherResponse> response) {
                weeklyDataList.clear();
                if (response.isSuccessful() && response.code() == 200) {
                    city = locationHelper.getCityName(requireContext(), location.getLatitude(), location.getLongitude());
                    binding.tvTalomo.setText(city);
                    forecastWeatherResponse = response.body();
                    String image = forecastWeatherResponse.getList().get(0).getWeather().get(0).getIcon();
                    binding.tvTemperature.setText(forecastWeatherResponse.getList().get(0).getMain().getTemp() + "°C");
                    binding.tvWeatherReport.setText(forecastWeatherResponse.getList().get(0).getWeather().get(0).getDescription() + "°C");
                    String weatherIconUrl = "https://openweathermap.org/img/wn/" + image + ".png";
                    Glide.with(getContext())
                            .load(weatherIconUrl)
                            .into(binding.ivClouds);

                    weeklyDataList.addAll(processWeatherData(forecastWeatherResponse));
                    weeklyDataAdapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ForecastWeatherResponse> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onLocationError(String error) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Parse the input string
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        // Extract date and hours separately
        return dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<WeeklyDataModel> processWeatherData(ForecastWeatherResponse weather) {
        List<WeeklyDataModel> forecastList = new ArrayList<>();
        Set<String> addedDays = new HashSet<>();
        finalForecastModels.clear();
        for (ForecastWeatherResponse.WeatherItem weatherData : weather.getList()) {
            if (shouldAdd(weatherData.getDt_txt())) {
                hourlyDataList.add(new HourlyDataModel(getHourMinute(weatherData.getDt_txt()), weatherData.getWeather().get(0).getIcon(), weatherData.getMain().getFeels_like() + "°C"));
            }
            String day = getDate(weatherData.getDt_txt());
            String currentDateTime = getCurrentDateTime();
            Gson gson = new Gson();
            String weeklyDataJson = gson.toJson(hourlyDataList);
            if (!addedDays.contains(day)) {
                addedDays.add(day);
                forecastList.add(
                        new WeeklyDataModel(day, weatherData.getWeather().get(0).getIcon(), weatherData.getMain().getTemp() + "C")
                );
                FinalForecastModel forecast = new FinalForecastModel(
                        city,
                        day,
                        weatherData.getWeather().get(0).getDescription(),
                        String.valueOf(weatherData.getMain().getTemp()),
                        String.valueOf(weatherData.getMain().getTemp_max()),
                        String.valueOf(weatherData.getMain().getTemp_min()),
                        "",
                        "",
                        String.valueOf(weatherData.getWind().getSpeed()),
                        String.valueOf(weatherData.getMain().getPressure()),
                        String.valueOf(weatherData.getMain().getHumidity()),
                        String.valueOf(weatherData.getClouds().getAll()),
                        null,
                        currentDateTime
                );
                forecast.setFullDayForecast(weeklyDataJson);
                forecast.setImage(weatherData.getWeather().get(0).getIcon());

                finalForecastModels.add(forecast);

            }
        }
        adapter.notifyDataSetChanged();

        return forecastList;
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }


}
