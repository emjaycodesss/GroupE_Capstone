package com.example.atmosfeel.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.atmosfeel.adapter.HourlyDataAdapter;
import com.example.atmosfeel.adapter.WeeklyDataAdapter;
import com.example.atmosfeel.databinding.FragmentWeatherDetailsBinding;
import com.example.atmosfeel.db.DatabaseHelper;
import com.example.atmosfeel.model.FinalForecastModel;
import com.example.atmosfeel.model.HourlyDataModel;
import com.example.atmosfeel.model.WeeklyDataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherDetailsFragment extends Fragment {
    FragmentWeatherDetailsBinding binding;
    List<WeeklyDataModel> weeklyDataModels = new ArrayList<>();
    private int selected = 0;

    public WeatherDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selected = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        FinalForecastModel forecastModel = databaseHelper.getAllForecasts().get(selected);
        List<HourlyDataModel> list = convertJsonToWeeklyDataList(forecastModel.getFullDayForecast());
        binding.tvTalomo.setText(forecastModel.getCity());
        binding.tvPresureValue.setText(forecastModel.getPressure() + " mb");
        binding.tvSpeedValue.setText(forecastModel.getWind() + " mp/h");
        binding.tvHumidityValue.setText(forecastModel.getHumidity() + "");
        binding.tvTemperature.setText(forecastModel.getTemperature() + "°C");
        binding.tvWeatherReport.setText(forecastModel.getStatus());
        binding.tvPrecipitationValue.setText(forecastModel.getCloud() + "%");

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        List<FinalForecastModel> allForecastByDate = databaseHelper.getAllEntriesWithDate(forecastModel.getTime());
        if (allForecastByDate.size() > 1) {
            for (FinalForecastModel finalForecastModel : allForecastByDate) {
                weeklyDataModels.add(new WeeklyDataModel(finalForecastModel.getDay(), finalForecastModel.getImage(), finalForecastModel.getTemperature()));
            }
            binding.rvWeeklyWeatherForecast.setAdapter(new WeeklyDataAdapter(weeklyDataModels));

        } else {
            binding.tvOneWeekForecast.setVisibility(View.GONE);
            binding.rvWeeklyWeatherForecast.setVisibility(View.GONE);
        }


        String weatherIconUrl = "https://openweathermap.org/img/wn/" + forecastModel.getImage() + ".png";

        Glide.with(getContext())
                .load(weatherIconUrl)
                .into(binding.ivClouds);
        HourlyDataAdapter hourlyDataAdapter = new HourlyDataAdapter(list);
        binding.rvHourlyWeather.setAdapter(hourlyDataAdapter);

    }

    private List<HourlyDataModel> convertJsonToWeeklyDataList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<HourlyDataModel>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public String getDayOfWeekFromDate(String dateString) {
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            // Parse the date string into a Date object
            Date date = dateFormat.parse(dateString);

            // Create a Calendar instance and set the date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // Get the day of the week from the Calendar instance
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            // Convert the day of the week to its corresponding name
            return new SimpleDateFormat("EEEE", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception, maybe return a default value or rethrow
            return "Invalid Date"; // Indicates an error in parsing the date
        }
    }
}