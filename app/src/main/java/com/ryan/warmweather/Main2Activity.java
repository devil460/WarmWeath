package com.ryan.warmweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ryan.warmweather.gson.TodayWeather;
import com.ryan.warmweather.gson.Weather;
import com.ryan.warmweather.service.MyService;
import com.ryan.warmweather.util.HttpUtil;
import com.ryan.warmweather.util.ImageUtil;
import com.ryan.warmweather.util.MyRecyclerAdapter;
import com.ryan.warmweather.util.Utility;

import java.io.IOException;


import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    private TextView regionText;
    private TextView humidityText;
    private TextView windText;
    private TextView temperText;
    private TextView weatherText;

    private ImageView weatherImg;

    private CircleImageView navImg;
    private ImageView weatherIcon;

    SwipeRefreshLayout refreshLayout;
    Button button;
    Button changeCity;

    private  TextView toWeatherText;
    private TextView toTemperText;
    private TextView toWindText;

    private TextView dressingIndexText;
    private TextView dressingAdviceText;
    private TextView UVText;
    private TextView washingText;
    private TextView travelText;

    private TextView excerciseText;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            View decroView = getWindow().getDecorView();
            decroView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main2);
        regionText = (TextView)findViewById(R.id.region_text);
        humidityText = (TextView)findViewById(R.id.humidity);
        windText = (TextView)findViewById(R.id.wind);
        temperText = (TextView)findViewById(R.id.temperature);
        weatherText =(TextView)findViewById(R.id.weather);

        weatherIcon=(ImageView) findViewById(R.id.weather_big_image);
        weatherImg = (ImageView)findViewById(R.id.weather_background);
        navImg = (CircleImageView) findViewById(R.id.circle_image);


        toWeatherText = (TextView)findViewById(R.id.today_weather);
        toTemperText = (TextView)findViewById(R.id.today_temperature);
        toWindText = (TextView)findViewById(R.id.today_wind);

        dressingAdviceText = (TextView)findViewById(R.id.dressing_advice);
        dressingIndexText=(TextView)findViewById(R.id.dressing_index);
        UVText = (TextView)findViewById(R.id.uv_index);
        washingText=(TextView)findViewById(R.id.washing_index);
        travelText=(TextView)findViewById(R.id.travel_index);

        excerciseText=(TextView)findViewById(R.id.exercise_index);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        button = (Button)findViewById(R.id.nav_button);
        changeCity = (Button)findViewById(R.id.add_city);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        button.setOnClickListener(this);
        changeCity.setOnClickListener(this);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sp.getString("weather",null);
        final String cityName;

        if (weatherString == null) {
            cityName = getIntent().getStringExtra("city_name");

            requestWeather(cityName);
        }else {
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
            cityName = weather.today.city;
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(cityName);
                loadPicture();
            }
        });

        String weatherPic = sp.getString("weatherPic",null);
        if (weatherPic == null) {
            loadPicture();
        }else {
            Glide.with(this).load(weatherPic).into(weatherImg);
        }

    }

    private void loadPicture() {
        String requestPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkhttpRequest(requestPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(Main2Activity.this).edit();
                editor.putString("weatherPic",bingPic).apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(Main2Activity.this).load(bingPic).into(weatherImg);
                    }
                });
            }
        });
    }

    private void showWeatherInfo(Weather weather) {
        String cityName=weather.today.city;
        String currentTemper = weather.sk.temp+"℃";
        String windDirection = weather.sk.wind_direction;
        String windStrength = weather.sk.wind_strength;
        String humidity = "湿度："+weather.sk.humidity;
        String curweather = weather.today.weather;
        String toTemper = weather.today.temperature;
        String toWind = weather.today.wind;
        if (weather != null) {
            regionText.setText(cityName);
            temperText.setText(currentTemper);
            windText.setText(windDirection+" "+windStrength);
            humidityText.setText(humidity);
            weatherText.setText(curweather);

            weatherIcon.setImageResource(ImageUtil.getImageId(weather.today.weather_id.fa));

            Glide.with(this).load(R.mipmap.yn1).into(navImg);

            toTemperText.setText(toTemper);
            toWeatherText.setText(curweather);
            toWindText.setText(toWind);

            dressingAdviceText.setText("穿衣建议："+weather.today.dressing_advice);
            dressingIndexText.setText("穿衣指数："+weather.today.dressing_index);
            washingText.setText("洗车指数："+weather.today.wash_index);
            excerciseText.setText("晨练指数："+weather.today.exercise_index);
            UVText.setText("紫外线："+weather.today.uv_index);
            travelText.setText("旅游指数："+weather.today.travel_index);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new MyRecyclerAdapter(weather.future));

            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        }else {
            Toast.makeText(this,"获取天气信息失败",Toast.LENGTH_LONG).show();
        }




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
                        refreshLayout.setRefreshing(false);
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
                        refreshLayout.setRefreshing(false);
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
            case R.id.add_city:
                MainActivity.TRIGGER = true;
                Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
