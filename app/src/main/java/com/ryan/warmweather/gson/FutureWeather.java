package com.ryan.warmweather.gson;

/**
 * Created by Think on 2017/6/17.
 */

public class FutureWeather {
    public String temperature;
    public String weather;
    public WeatherId weather_id;
    public String wind;
    public String week;
    public String date;

    public class WeatherId{
        public String fa;
        public String fb;
    }


}
