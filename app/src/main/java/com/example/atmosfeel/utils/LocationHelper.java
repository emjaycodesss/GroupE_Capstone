package com.example.atmosfeel.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import android.app.Activity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationHelper {
    public static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private FragmentActivity activity;
    private LocationCallback locationCallback;
    private LocationResultListener listener;
    Location location;

    public interface LocationResultListener {
        void onLocationResult(Location location);
        void onLocationError(String error);
    }

    public LocationHelper(FragmentActivity activity, LocationResultListener listener) {
        this.activity = activity;
        this.listener = listener;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
    }

    public void requestLocation() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            getLocation();
        }
    }
    @SuppressLint("MissingPermission")
    private void getLocation() {
        if (location == null) {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setNumUpdates(1);
            locationRequest.setFastestInterval(5000);

            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        listener.onLocationError("Location not found");
                        return;
                    }
                    for (Location locations : locationResult.getLocations()) {
                        location = locations;
                        listener.onLocationResult(locations);
                    }
                    fusedLocationClient.removeLocationUpdates(locationCallback);
                }
            };

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            listener.onLocationResult(location);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                listener.onLocationError("Permission denied");
            }
        }
    }
    public  String getCityName(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                String cityName = addresses.get(0).getLocality();
                return cityName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
