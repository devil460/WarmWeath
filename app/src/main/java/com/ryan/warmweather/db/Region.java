package com.ryan.warmweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Think on 2017/6/11.
 */

public class Region extends DataSupport{
    private int id;
    private String provinceName;
    private String cityName;
    private String districtName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}
