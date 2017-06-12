package com.ryan.warmweather.util;

import android.text.TextUtils;

import com.ryan.warmweather.db.Region;

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
    public static boolean handleResponse(String response){
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allRegins = new JSONArray(response);
                for (int i = 0; i < allRegins.length(); i++) {
                    JSONObject RegionObject = allRegins.getJSONObject(i);
                    Region region = new Region();
                    region.setProvinceName(RegionObject.getString("province"));
                    region.setCityName(RegionObject.getString("city"));
                    region.setDistrictName(RegionObject.getString("district"));
                    region.setId(RegionObject.getInt("id"));
                    region.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
