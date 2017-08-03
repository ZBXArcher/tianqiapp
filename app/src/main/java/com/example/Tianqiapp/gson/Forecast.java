package com.example.Tianqiapp.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Z Archer on 2017/8/1.
 */

public class Forecast {
    public String date;

    public Astro astro;    //天文数据
    public class Astro{
        @SerializedName("mr")
        public String moonRise;

        @SerializedName("ms")
        public String moonSet;

        @SerializedName("sr")
        public String sunRise;

        @SerializedName("ss")
        public String sunSet;
    }

    @SerializedName("cond")
    public Condition condition;
    public class Condition{
        @SerializedName("code_d")
        public String weatherCodeDay;
        @SerializedName("code_n")
        public String weatherCodeNight;
        @SerializedName("txt_d")
        public String weatherTextDay;
        @SerializedName("txt_n")
        public String weatherTextNight;
    }
    @SerializedName("hum")
    public String humidity;           //相对湿度

    @SerializedName("pcpn")
    public String precipitation;      //降水量

    @SerializedName("pop")
    public String probability;        //降水概率

    @SerializedName("pres")
    public String pressure;

    @SerializedName("tmp")
    public Temperature temperature;
    public class Temperature{

        public String max;

        public String min;
    }

    @SerializedName("uv")
    public String ultravioletRays;     //紫外线

    @SerializedName("vis")
    public String visibility;           //能见度


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
