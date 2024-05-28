package com.example.atmosfeel;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atmosfeel.databinding.ActivitySplashBinding;
import com.example.atmosfeel.utils.LocationHelper;

public class SplashActivity extends AppCompatActivity implements LocationHelper.LocationResultListener {
    ActivitySplashBinding binding;
    private LocationHelper locationHelper;
    Animation fromTop, fromBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locationHelper = new LocationHelper(this, this);
        fromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        binding.ivSplash.setAnimation(fromTop);
        binding.tvSplash.setAnimation(fromBottom);

        new android.os.Handler().postDelayed(() -> locationHelper.requestLocation(), 3000);
    }

    @Override
    public void onLocationResult(android.location.Location location) {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onLocationError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
