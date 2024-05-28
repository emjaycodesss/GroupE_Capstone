package com.example.atmosfeel;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.atmosfeel.databinding.ActivityMainBinding;
import com.example.atmosfeel.fragments.HomeFragment;
import com.example.atmosfeel.utils.LocationHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        setBottomNavigation();
    }

    private void setBottomNavigation() {
        binding.bottomNav.bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_home_24));
        binding.bottomNav.bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.weather_atmos));
        binding.bottomNav.bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_manage_search_24));

        if (navController != null) {
            navController.navigate(R.id.homeFragment);
            binding.bottomNav.bottomNavigation.show(1, true);
        }

        binding.bottomNav.bottomNavigation.setOnClickMenuListener(model -> {
            if (navController != null) {
                switch (model.getId()) {
                    case 1:
                        navController.navigate(R.id.homeFragment);
                        break;
                    case 2:
                        navController.navigate(R.id.forecastFragment);
                        break;
                    case 3:
                        navController.navigate(R.id.searchFragment);
                        break;
                }
            }
            return null;
        });

        binding.bottomNav.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navController != null) {
                    navController.navigate(R.id.homeFragment);
                    binding.bottomNav.bottomNavigation.show(1, true);
                }
            }
        });
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId()==R.id.weatherDetailsFragment){
                    binding.bottomNav.bottomNavigation.setVisibility(View.GONE);
                    binding.bottomNav.top.setVisibility(View.GONE);
                }else {
                    binding.bottomNav.bottomNavigation.setVisibility(View.VISIBLE);
                    binding.bottomNav.top.setVisibility(View.VISIBLE);

                }
            }
        });

        binding.bottomNav.weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navController != null) {
                    binding.bottomNav.bottomNavigation.show(2, true);
                    navController.navigate(R.id.forecastFragment);
                }
            }
        });

        binding.bottomNav.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navController != null) {
                    navController.navigate(R.id.searchFragment);
                    binding.bottomNav.bottomNavigation.show(3, true);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            Fragment currentFragment = navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment();            if (currentFragment instanceof HomeFragment){
                currentFragment.onRequestPermissionsResult(requestCode,permissions,grantResults);
            }

        }

    }}
