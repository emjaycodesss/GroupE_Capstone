package com.example.atmosfeel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atmosfeel.R;
import com.example.atmosfeel.model.HourlyDataModel;

import java.util.List;

public class HourlyDataAdapter extends RecyclerView.Adapter<HourlyDataAdapter.HourlyDataViewHolder> {

    private List<HourlyDataModel> data;

    public HourlyDataAdapter(List<HourlyDataModel> weatherItemList) {
        this.data = weatherItemList;
    }

    @NonNull
    @Override
    public HourlyDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather, parent, false);
        return new HourlyDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyDataViewHolder holder, int position) {
        HourlyDataModel item = data.get(position);
        holder.tvTime.setText(item.getTime());
        String weatherIconUrl = "https://openweathermap.org/img/wn/" + item.getIconResId() + "@4x.png";

        Glide.with(holder.itemView.getContext())
                .load(weatherIconUrl)
                .into(holder.ivIconImage);
        holder.tvTemperature.setText(item.getTemperature());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class HourlyDataViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvTime;
        AppCompatImageView ivIconImage;
        AppCompatTextView tvTemperature;
        CardView cardItem;

        public HourlyDataViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvPresure);
            ivIconImage = itemView.findViewById(R.id.ivIconImage);
            tvTemperature = itemView.findViewById(R.id.tvPresureValue);
            cardItem = itemView.findViewById(R.id.cardItem);
        }
    }
}
