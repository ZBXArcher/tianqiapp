package com.example.Tianqiapp.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Z Archer on 2017/8/1.
 */

public class AQI {
    public AQICity city;
    public class AQICity{
        public String aqi;
        public String co;
        public String no2;
        public String o3;
        public String pm10;
        public String pm25;
        @SerializedName("qlty")
        public String quality;
        public String so2;
    }
}
