package com.example.Tianqiapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.Tianqiapp.gson.BingPic;
import com.example.Tianqiapp.gson.Forecast;
import com.example.Tianqiapp.gson.HourlyForecast;
import com.example.Tianqiapp.gson.Weather;
import com.example.Tianqiapp.service.AutoUpdateService;
import com.example.Tianqiapp.util.HttpUtil;
import com.example.Tianqiapp.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Z Archer on 2017/8/3.
 */

public class WeatherActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    private Button navButton;
    public SwipeRefreshLayout swipeRefresh;
    private String mWeatherId;
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private TextView windDirectionText;
    private TextView aqiBriefingText;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private TextView dressText;
    private ImageView bingPicImg;
    private LinearLayout forecastLayout;
    private LinearLayout hourlyForecastLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT>=21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navButton=(Button)findViewById(R.id.nav_button);
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        aqiBriefingText = (TextView)findViewById(R.id.aqi_info_text);
        aqiText=(TextView)findViewById(R.id.aqi_text);
        pm25Text = (TextView)findViewById(R.id.pm25_text);
        comfortText = (TextView)findViewById(R.id.comfort_text);
        carWashText = (TextView)findViewById(R.id.car_wash_text);
        sportText = (TextView)findViewById(R.id.sport_text);
        dressText = (TextView)findViewById(R.id.dress_text);
        windDirectionText = (TextView)findViewById(R.id.wind_direction_text);
        forecastLayout = (LinearLayout)findViewById(R.id.forecast_layout);
        hourlyForecastLayout = (LinearLayout)findViewById(R.id.hourly_forecast_layout);
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather",null);
        if(weatherString !=null){
            Weather weather= Utility.handleWeatherResponse(weatherString);
            mWeatherId =weather.basic.weatherId;
            showWeatherInfo(weather);
        }else {
            mWeatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);

        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                requestWeather(mWeatherId);
            }
        });
        navButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        String bingPic = prefs.getString("bing_pic",null);
        if (bingPic!=null){
            Glide.with(this).load(bingPic).into(bingPicImg);
        }else {
            loadBingPic();
        }
    }

    public void requestWeather(final String weatherId){
        String weatherUrl = "https://free-api.heweather.com/v5/weather?city="+weatherId+"&key=4805091134c24c3ca5382e2b29746d8f";
        HttpUtil.sendOKHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather!=null&&"ok".equals(weather.status)){
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        }else {
                            Toast.makeText(WeatherActivity.this,"获取天气失败"+weatherId,Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });

            }
        });
        loadBingPic();
    }
    private void showWeatherInfo(Weather weather){
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.localTime.split(" ")[1];
        String degree = weather.now.temperature+"℃";
        String weatherInfo = weather.now.condition.weatherText;
        String windDirection = weather.now.wind.direction+" "+weather.now.wind.scale;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        windDirectionText.setText(windDirection);
        forecastLayout.removeAllViews();
        for (Forecast forecast:weather.forecastList){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView dateText = (TextView)view.findViewById(R.id.data_text);
            TextView infoText = (TextView)view.findViewById(R.id.info_text);
            TextView maxText = (TextView)view.findViewById(R.id.max_text);
            TextView minText = (TextView)view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.condition.weatherTextDay);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }
        hourlyForecastLayout.removeAllViews();
        for (HourlyForecast hourlyForecast:weather.hourlyForecastList){
            View hourlyView = LayoutInflater.from(this).inflate(R.layout.hourly_forecast_item,hourlyForecastLayout,false);
            TextView hourlyDateText = (TextView)hourlyView.findViewById(R.id.hourly_data_text);
            TextView hourlyInfoText = (TextView)hourlyView.findViewById(R.id.hourly_info_text);
            TextView hourlyTemperatureText = (TextView)hourlyView.findViewById(R.id.hourly_tem_text);
            TextView hourlyWindText = (TextView)hourlyView.findViewById(R.id.hourly_wind_text);
            hourlyDateText.setText(hourlyForecast.date);
            hourlyInfoText.setText(hourlyForecast.condition.weatherText);
            hourlyTemperatureText.setText(hourlyForecast.temperature);
            hourlyWindText.setText(hourlyForecast.wind.direction+" "+hourlyForecast.wind.scale);
            hourlyForecastLayout.addView(hourlyView);
        }
        if (weather.aqi!=null){
            aqiText.setText(weather.aqi.city.aqi);
            aqiBriefingText.setText(weather.aqi.city.quality);
            pm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度: "+weather.suggestion.comfort.brf+"  "+weather.suggestion.comfort.info;
        String carWash = "洗车指数: "+weather.suggestion.carWash.brf+" "+weather.suggestion.carWash.info;
        String sport="运动建议: "+weather.suggestion.sport.brf+" "+weather.suggestion.sport.info;
        String dress="穿衣建议: "+weather.suggestion.dress.brf+" "+weather.suggestion.dress.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        dressText.setText(dress);
        weatherLayout.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }
    private void loadBingPic(){
        final String bingUrl = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
        HttpUtil.sendOKHttpRequest(bingUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingText = response.body().string();
                final BingPic bingPic = Utility.handleBingPicResponse(bingText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final String bingPicUrl = "http://cn.bing.com"+bingPic.url;
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                        editor.putString("bing_pic",bingPicUrl);
                        editor.apply();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(WeatherActivity.this).load(bingPicUrl).into(bingPicImg);
                            }
                        });
                    }
                });

            }
        });
    }
}
