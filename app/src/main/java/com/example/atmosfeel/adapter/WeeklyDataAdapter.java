package com.example.atmosfeel.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atmosfeel.R;
import com.example.atmosfeel.model.WeeklyDataModel;
import java.util.List;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

public class WeeklyDataAdapter extends RecyclerView.Adapter<WeeklyDataAdapter.WeeklyDataViewHolder> {

    private List<WeeklyDataModel> weeklyDataList;
    public WeeklyDataAdapter(List<WeeklyDataModel> weeklyDataList) {
        this.weeklyDataList = weeklyDataList;
    }


    @NonNull
    @Override
    public WeeklyDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weekly_weather, parent, false);
        return new WeeklyDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyDataViewHolder holder, int position) {
        WeeklyDataModel item = weeklyDataList.get(position);
        holder.tvDay.setText(item.getDay());
        String weatherIconUrl = "https://openweathermap.org/img/wn/" + item.getWeatherIconResId() + "@4x.png";

        Glide.with(holder.itemView.getContext())
                .load(weatherIconUrl)
                .into(holder.ivWeatherIcon);
        holder.tvTemperature.setText(item.getTemperature());

    }

    @Override
    public int getItemCount() {
        return weeklyDataList.size();
    }

    public  class WeeklyDataViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvDay;
        AppCompatImageView ivWeatherIcon;
        AppCompatTextView tvTemperature;

        public WeeklyDataViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            ivWeatherIcon = itemView.findViewById(R.id.imgWeeklyAdapter);
            tvTemperature = itemView.findViewById(R.id.tvTemp);

        }
    }

}
