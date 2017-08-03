package com.example.Tianqiapp.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Z Archer on 2017/8/1.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("cnty")
    public String country;

    @SerializedName("id")
    public String weatherId;

    public Update update;
    public class Update{
        @SerializedName("loc")
        public String localTime;

        @SerializedName("utc")
        public String utcTime;
    }
}
