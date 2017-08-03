package com.example.Tianqiapp.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Z Archer on 2017/8/1.
 */

public class Suggestion {
    public Air air;
    public class Air{
        public String brf;
        @SerializedName("txt")
        public String info;
    }
    @SerializedName("comf")
    public Comfort comfort;
    public class Comfort{
        public String brf;
        @SerializedName("txt")
        public String info;
    }
    @SerializedName("cw")
    public CarWash carWash;
    public class CarWash{
        public String brf;
        @SerializedName("txt")
        public String info;
    }
    @SerializedName("drsg")
    public Dress dress;
    public class Dress{
        public String brf;
        @SerializedName("txt")
        public String info;
    }
    public Flu flu;
    public class Flu{
        public String brf;
        @SerializedName("txt")
        public String info;
    }
    public Sport sport;
    public class Sport{
        public String brf;
        @SerializedName("txt")
        public String info;
    }
    @SerializedName("trav")
    public Travel travel;
    public class Travel{
        public String brf;
        @SerializedName("txt")
        public String info;
    }
    @SerializedName("uv")
    public UltravioletRays ultravioletRays;
    public class UltravioletRays{
        public String brf;
        @SerializedName("txt")
        public String info;
    }
}
