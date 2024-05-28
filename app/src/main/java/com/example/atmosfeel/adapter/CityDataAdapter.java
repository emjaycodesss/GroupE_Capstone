package com.example.atmosfeel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atmosfeel.R;
import com.example.atmosfeel.model.CityDataModel;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

public class CityDataAdapter extends RecyclerView.Adapter<CityDataAdapter.CityDataViewHolder> {

    private List<CityDataModel> cityDataList;
    private List<CityDataModel> filteredCityDataList;
    private onClick onClick;

    public CityDataAdapter(List<CityDataModel> cityDataList, onClick onClick) {
        this.cityDataList = cityDataList;
        this.filteredCityDataList = new ArrayList<>(cityDataList);
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public CityDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city_data, parent, false);
        return new CityDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityDataViewHolder holder, int position) {
        CityDataModel item = filteredCityDataList.get(position);
        holder.tvCityName.setText(item.getCityName());
        holder.tvCountryName.setText(item.getCountryName());
        holder.tvTempCity.setText(item.getTemperature());
        holder.tvItemHumidity.setText(item.getHumidity());
        holder.tvAirSpeed.setText(item.getAirSpeed());
        String weatherIconUrl = "https://openweathermap.org/img/wn/" + item.getWeatherImageResId() + "@4x.png";

        Glide.with(holder.itemView.getContext())
                .load(weatherIconUrl)
                .into(holder.ivWeatherImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredCityDataList.size();
    }

    public static class CityDataViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvCityName;
        AppCompatTextView tvCountryName;
        AppCompatTextView tvTempCity;
        AppCompatTextView tvItemHumidity;
        AppCompatTextView tvAirSpeed;
        AppCompatImageView ivWeatherImage;

        public CityDataViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tvCityName);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);
            tvTempCity = itemView.findViewById(R.id.tvTempCity);
            tvItemHumidity = itemView.findViewById(R.id.tvItemHumidity);
            tvAirSpeed = itemView.findViewById(R.id.tvAirSpeed);
            ivWeatherImage = itemView.findViewById(R.id.ivWeatherImage);
        }
    }

    public interface onClick {
        void onClick(int pos);
    }

    public void filter(String query) {
        filteredCityDataList.clear();
        if (query.isEmpty()) {
            filteredCityDataList.addAll(cityDataList);
        } else {
            for (CityDataModel cityData : cityDataList) {
                if (cityData.getCityName().toLowerCase().contains(query.toLowerCase())) {
                    filteredCityDataList.add(cityData);
                }
            }
        }
        notifyDataSetChanged();
    }
}
