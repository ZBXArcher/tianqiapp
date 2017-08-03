package com.example.Tianqiapp.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Z Archer on 2017/8/1.
 */

public class HourlyForecast{
    public String date;
    @SerializedName("cond")
    public Condition condition;
    public class Condition{
        @SerializedName("code")
        public String weatherCode;
        @SerializedName("txt")
        public String weatherText;
    }
    @SerializedName("hum")
    public String humidity;           //相对湿度
    @SerializedName("pop")
    public String probability;        //降水概率
    @SerializedName("pres")
    public String pressure;
    @SerializedName("tmp")
    public String temperature;
    public Wind wind;
    public class Wind{
        @SerializedName("dir")
        public String direction;
        @SerializedName("sc")       //风力等级
        public String scale;
        @SerializedName("spd")
        public String speed;
    }
}
