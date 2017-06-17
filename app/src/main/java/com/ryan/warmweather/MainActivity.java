package com.ryan.warmweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ryan.warmweather.util.DatabaseUtil;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            DatabaseUtil.copyDatabase("test.db");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
    }
}
