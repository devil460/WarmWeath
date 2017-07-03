package com.ryan.warmweather.gson;

/**
 * Created by Think on 2017/6/17.
 */

public class TodayWeather {
    public String city;
    public String date_y;
    public String week;
    public String temperature;
    public String weather;
    public WeatherId weather_id;
    public String wind;
    public String dressing_index;
    public String dressing_advice;

    public String uv_index;
    public String comfort_index;
    public String wash_index;
    public String travel_index;
    public String exercise_index;
    public String drying_index;

    public class WeatherId {
        public String fa;
        public String fb;
    }

}

