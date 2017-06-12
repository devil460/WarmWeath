package com.ryan.warmweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Think on 2017/6/12.
 */

public class Abroad extends DataSupport {
    private int id;
    private String abroadName;
    private String abroadId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbroadName() {
        return abroadName;
    }

    public void setAbroadName(String abroadName) {
        this.abroadName = abroadName;
    }

    public String getAbroadId() {
        return abroadId;
    }

    public void setAbroadId(String abroadId) {
        this.abroadId = abroadId;
    }
}
