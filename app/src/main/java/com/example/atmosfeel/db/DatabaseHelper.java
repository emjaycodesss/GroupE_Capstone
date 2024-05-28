package com.example.atmosfeel.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.atmosfeel.model.FinalForecastModel;
import com.example.atmosfeel.model.HourlyDataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "forecast_database";
    private static final String TABLE_NAME = "forecast_table";

    // Columns
    private static final String KEY_ID = "id";
    private static final String KEY_DAY = "day";
    private static final String KEY_TIME = "time";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_STATUS = "status";
    private static final String KEY_FORECAST= "daily_forecast";
    private static final String KEY_TEMPERATURE = "temperature";
    private static final String KEY_MIN_TEMPERATURE = "min_temperature";
    private static final String KEY_MAX_TEMPERATURE = "max_temperature";
    private static final String KEY_SUNRISE = "sunrise";
    private static final String KEY_SUNSET = "sunset";
    private static final String KEY_WIND = "wind";
    private static final String KEY_PRESSURE = "pressure";
    private static final String KEY_HUMIDITY = "humidity";
    private static final String KEY_CLOUD = "cloud";
    private static final String KEY_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_LOCATION + " TEXT,"
                + KEY_DAY + " TEXT,"
                + KEY_TIME + " TEXT,"
                + KEY_STATUS + " TEXT,"
                + KEY_TEMPERATURE + " TEXT,"
                + KEY_MIN_TEMPERATURE + " TEXT,"
                + KEY_MAX_TEMPERATURE + " TEXT,"
                + KEY_SUNRISE + " TEXT,"
                + KEY_SUNSET + " TEXT,"
                + KEY_WIND + " TEXT,"
                + KEY_PRESSURE + " TEXT,"
                + KEY_HUMIDITY + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_FORECAST + " TEXT,"
                + KEY_CLOUD + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addForecast(FinalForecastModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DAY, model.getDay());
        values.put(KEY_STATUS, model.getStatus());
        values.put(KEY_LOCATION, model.getCity());
        values.put(KEY_TEMPERATURE, model.getTemperature());
        values.put(KEY_MIN_TEMPERATURE, model.getMinTemperature());
        values.put(KEY_MAX_TEMPERATURE, model.getMaxTemperature());
        values.put(KEY_SUNRISE, model.getSunrise());
        values.put(KEY_SUNSET, model.getSunset());
        values.put(KEY_WIND, model.getWind());
        values.put(KEY_PRESSURE, model.getPressure());
        values.put(KEY_HUMIDITY, model.getHumidity());
        values.put(KEY_CLOUD, model.getCloud());
        values.put(KEY_IMAGE, model.getImage());
        values.put(KEY_FORECAST, model.getFullDayForecast());
        values.put(KEY_TIME, model.getTime());

        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    @SuppressLint("NewApi")
    public List<FinalForecastModel> getAllForecasts() {
        List<FinalForecastModel> forecastList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    String city = cursor.getString(cursor.getColumnIndexOrThrow(KEY_LOCATION));
                    String day = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DAY));
                    String status = cursor.getString(cursor.getColumnIndexOrThrow(KEY_STATUS));
                    String temperature = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TEMPERATURE));
                    String minTemperature = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MIN_TEMPERATURE));
                    String maxTemperature = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MAX_TEMPERATURE));
                    String sunrise = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SUNRISE));
                    String sunset = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SUNSET));
                    String wind = cursor.getString(cursor.getColumnIndexOrThrow(KEY_WIND));
                    String pressure = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRESSURE));
                    String humidity = cursor.getString(cursor.getColumnIndexOrThrow(KEY_HUMIDITY));
                    String cloud = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CLOUD));
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TIME));
                    String image = cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE));
                    String forecast = cursor.getString(cursor.getColumnIndexOrThrow(KEY_FORECAST));

                    FinalForecastModel forecastModel = new FinalForecastModel(
                            city, day, status, temperature, minTemperature, maxTemperature, sunrise, sunset, wind, pressure, humidity, cloud, id, time
                    );

                    forecastModel.setFullDayForecast(forecast);

                    forecastModel.setImage(image);


                    if (!forecastList.stream().anyMatch(f -> f.getTime().equals(time))) {
                        forecastList.add(forecastModel);
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return forecastList;
    }


    public int updateForecast(FinalForecastModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DAY, model.getDay());
        values.put(KEY_STATUS, model.getStatus());
        values.put(KEY_TEMPERATURE, model.getTemperature());
        values.put(KEY_MIN_TEMPERATURE, model.getMinTemperature());
        values.put(KEY_MAX_TEMPERATURE, model.getMaxTemperature());
        values.put(KEY_SUNRISE, model.getSunrise());
        values.put(KEY_SUNSET, model.getSunset());
        values.put(KEY_WIND, model.getWind());
        values.put(KEY_PRESSURE, model.getPressure());
        values.put(KEY_HUMIDITY, model.getHumidity());
        values.put(KEY_CLOUD, model.getCloud());

        return db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{String.valueOf(model.getId())});
    }

    public void deleteForecast(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<FinalForecastModel> getAllEntriesWithDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<FinalForecastModel> entries = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_TIME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{date});

        try {
            if (cursor.moveToFirst()) {
                do {
                    String city = cursor.getString(cursor.getColumnIndexOrThrow(KEY_LOCATION));
                    String day = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DAY));
                    String status = cursor.getString(cursor.getColumnIndexOrThrow(KEY_STATUS));
                    String temperature = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TEMPERATURE));
                    String minTemperature = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MIN_TEMPERATURE));
                    String maxTemperature = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MAX_TEMPERATURE));
                    String sunrise = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SUNRISE));
                    String sunset = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SUNSET));
                    String wind = cursor.getString(cursor.getColumnIndexOrThrow(KEY_WIND));
                    String pressure = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRESSURE));
                    String humidity = cursor.getString(cursor.getColumnIndexOrThrow(KEY_HUMIDITY));
                    String cloud = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CLOUD));
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TIME));
                    String image = cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE));

                    FinalForecastModel forecastModel = new FinalForecastModel(
                            city, day, status, temperature, minTemperature, maxTemperature, sunrise, sunset, wind, pressure, humidity, cloud, id, time
                    );
                    forecastModel.setImage(image);


                    entries.add(forecastModel);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        db.close();
        return entries;
    }
}
