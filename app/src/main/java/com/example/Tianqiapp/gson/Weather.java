package com.example.Tianqiapp.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Z Archer on 2017/8/1.
 */

public class Weather {
    public String status;
    public AQI aqi;
    public Basic basic;
    public Forecast forecast;
    public HourlyForecast hourlyForecast;
    public Now now;
    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
    @SerializedName("hourly_forecast")
    public List<HourlyForecast> hourlyForecastList;

}
