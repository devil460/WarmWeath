package com.ryan.warmweather;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.renderscript.ScriptGroup;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ryan.warmweather.gson.FutureWeather;
import com.ryan.warmweather.gson.Weather;
import com.ryan.warmweather.util.HttpUtil;
import com.ryan.warmweather.util.MyRecyclerAdapter;
import com.ryan.warmweather.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    private TextView regionText;
    private TextView humidityText;
    private TextView windText;
    private TextView temperText;
    private TextView weatherText;
    private String weatherData;
    private Button button;

    private  TextView toWeatherText;
    private TextView toTemperText;
    private TextView toWindText;

    private TextView dressingIndexText;
    private TextView dressingAdviceText;
    private TextView UVText;
    private TextView washingText;
    private TextView travelText;
    private TextView dryingText;
    private TextView excerciseText;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        regionText = (TextView)findViewById(R.id.region_text);
        humidityText = (TextView)findViewById(R.id.humidity);
        windText = (TextView)findViewById(R.id.wind);
        temperText = (TextView)findViewById(R.id.temperature);
        weatherText =(TextView)findViewById(R.id.weather);

        toWeatherText = (TextView)findViewById(R.id.today_weather);
        toTemperText = (TextView)findViewById(R.id.today_temperature);
        toWindText = (TextView)findViewById(R.id.today_wind);

        dressingAdviceText = (TextView)findViewById(R.id.dressing_advice);
        dressingIndexText=(TextView)findViewById(R.id.dressing_index);
        UVText = (TextView)findViewById(R.id.uv_index);
        washingText=(TextView)findViewById(R.id.washing_index);
        travelText=(TextView)findViewById(R.id.travel_index);
        dryingText=(TextView)findViewById(R.id.drying_index);
        excerciseText=(TextView)findViewById(R.id.exercise_index);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        button = (Button)findViewById(R.id.nav_button);

        button.setOnClickListener(this);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sp.getString("weather",null);
        if (weatherString == null) {
            String cityName = getIntent().getStringExtra("city_name");
            requestWeather(cityName);

        }else {
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        }


    }

    private void showWeatherInfo(Weather weather) {
        String cityName=weather.today.city;
        String currentTemper = weather.sk.temp;
        String windDirection = weather.sk.wind_direction;
        String windStrength = weather.sk.wind_strength;
        String humidity = "湿度："+weather.sk.humidity;
        String curweather = weather.today.weather;
        String toTemper = weather.today.temperature;
        String toWind = weather.today.wind;

        regionText.setText(cityName);
        temperText.setText(currentTemper);
        windText.setText(windDirection+" "+windStrength);
        humidityText.setText(humidity);
        weatherText.setText(curweather);

        toTemperText.setText(toTemper);
        toWeatherText.setText(curweather);
        toWindText.setText(toWind);

        dressingAdviceText.append(weather.today.dressing_advice);
        dressingIndexText.append(weather.today.dressing_index);
        dryingText.append(weather.today.drying_index);
        washingText.append(weather.today.wash_index);
        excerciseText.append(weather.today.exercise_index);
        UVText.append(weather.today.uv_index);
        travelText.append(weather.today.travel_index);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MyRecyclerAdapter(weather.future));


    }


    public void requestWeather(String cityName){
        String url = "http://v.juhe.cn/weather/index?format=2&cityname="+cityName+
                "&key= c5e049f58364e37056538eec46cbc51f";
        HttpUtil.sendOkhttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Main2Activity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                final Weather weather1 = Utility.handleWeatherResponse(responseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather1 == null) {
                            Toast.makeText(Main2Activity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        }else {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(Main2Activity.this).edit();
                            editor.putString("weather",responseData);
                            editor.apply();
                            showWeatherInfo(weather1);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_button:
                drawerLayout.openDrawer(GravityCompat.START);
                break;

            default:
                break;
        }
    }
}
