package com.ryan.warmweather.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ryan.warmweather.db.Region;
import com.ryan.warmweather.gson.Weather;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Think on 2017/6/12.
 */

public class Utility {
    /**
     * 解析服务器返回的数据
     */
    public static Weather handleWeatherResponse(String response){
        try {


                JSONObject jsonObject = new JSONObject(response);

                String weatherContent = jsonObject.getString("result");



            return new Gson().fromJson(weatherContent,Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
