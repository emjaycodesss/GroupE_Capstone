package com.example.atmosfeel.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.atmosfeel.R;
import com.example.atmosfeel.adapter.CityDataAdapter;
import com.example.atmosfeel.adapter.HourlyDataAdapter;
import com.example.atmosfeel.databinding.FragmentHomeBinding;
import com.example.atmosfeel.databinding.FragmentSearchBinding;
import com.example.atmosfeel.db.DatabaseHelper;
import com.example.atmosfeel.model.CityDataModel;
import com.example.atmosfeel.model.FinalForecastModel;
import com.example.atmosfeel.model.HourlyDataModel;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    CityDataAdapter adapter;
    DatabaseHelper databaseHelper;
    List<CityDataModel> cityDataList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityDataList.clear();
        databaseHelper=new DatabaseHelper(requireContext());
        for (FinalForecastModel forecastModel:databaseHelper.getAllForecasts()) {
            cityDataList.add(new CityDataModel(forecastModel.getCity(),"",forecastModel.getTemperature()+"°C", forecastModel.getHumidity()+"%",
                    forecastModel.getWind()+"km/h",forecastModel.getImage()
                    ));
        }
        setAdapters();
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("aaa", "onQueryTextSubmit: "+query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });
    }

    private void setAdapters() {
        adapter = new CityDataAdapter(cityDataList, new CityDataAdapter.onClick() {
            @Override
            public void onClick(int pos) {
                Bundle bundle=new Bundle();
                bundle.putInt("id",pos);
                findNavController(SearchFragment.this).navigate(R.id.action_searchFragment_to_weatherDetailsFragment,bundle);
            }
        });
        binding.rvCityData.setAdapter(adapter);

    }
}


