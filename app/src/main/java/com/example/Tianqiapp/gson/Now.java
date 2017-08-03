package com.example.Tianqiapp.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Z Archer on 2017/8/1.
 */

public class Now {
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
    @SerializedName("pcpn")
    public String precipitation;      //降水量
    @SerializedName("pres")
    public String pressure;
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("vis")
    public String visibility;           //能见度
    @SerializedName("fl")
    public String feel;                      //体感温度
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
