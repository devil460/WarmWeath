package com.ryan.warmweather;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ryan.warmweather.util.DatabaseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Think on 2017/6/13.
 */

public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_DISTRICT = 2;

    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    private String selectedProvince;
    private String selectedCity;
    private int currentLevel;
    private String sqlitePath;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area,container,false);
        titleText = (TextView)view.findViewById(R.id.title_text);
        backButton = (Button)view.findViewById(R.id.back_button);
        listView = (ListView)view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(MyApplication.getContext(),
                android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            sqlitePath = DatabaseUtil.copyDatabase("test.db");
        } catch (IOException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel==LEVEL_PROVINCE){
                    selectedProvince = dataList.get(position);
                    queryCity();
                }else if(currentLevel==LEVEL_CITY){
                    selectedCity = dataList.get(position);
                    queryDistrict();
                }else if(currentLevel == LEVEL_DISTRICT){
                    String cityName = dataList.get(position);
                    Intent intent = new Intent(getActivity(),Main2Activity.class);
                    intent.putExtra("city_name",cityName);
                    MainActivity.TRIGGER=false;
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("weather",null).apply();
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel==LEVEL_CITY){
                    queryProvince();
                }else if(currentLevel==LEVEL_DISTRICT){
                    queryCity();
                }
            }
        });
        queryProvince();
    }

    private void queryProvince() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        SQLiteDatabase sqldb = SQLiteDatabase.openDatabase(sqlitePath,null,SQLiteDatabase.OPEN_READONLY);
        Cursor cursor= sqldb.query("region",null,null,null,null,null,null,null);
        if (!dataList.isEmpty()) {
            dataList.clear();
        }
        while (cursor.moveToNext()) {

            int columnIndex = cursor.getColumnIndex("provincename");
            String provinceName = cursor.getString(columnIndex);
            if(!dataList.contains(provinceName)){
                dataList.add(provinceName);
            }
        }
        cursor.close();
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel=LEVEL_PROVINCE;

    }
    private void queryCity(){
        titleText.setText(selectedProvince);
        backButton.setVisibility(View.VISIBLE);
        SQLiteDatabase sqldb = SQLiteDatabase.openDatabase(sqlitePath,null,SQLiteDatabase.OPEN_READONLY);
        Cursor cursor= sqldb.query("region",null,null,null,null,null,null,null);
        if (!dataList.isEmpty()) {
            dataList.clear();
        }
        while (cursor.moveToNext()) {

            int columnIndex = cursor.getColumnIndex("cityname");
            String cityName = cursor.getString(columnIndex);

            if (selectedProvince.equals(cursor.getString(cursor.getColumnIndex("provincename")))
                    && !dataList.contains(cityName)) {
                dataList.add(cityName);
            }
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel=LEVEL_CITY ;
        cursor.close();
    }
    private void queryDistrict(){
        titleText.setText(selectedCity);
        backButton.setVisibility(View.VISIBLE);
        SQLiteDatabase sqldb = SQLiteDatabase.openDatabase(sqlitePath,null,SQLiteDatabase.OPEN_READONLY);
        Cursor cursor= sqldb.query("region",null,null,null,null,null,null,null);
        if (!dataList.isEmpty()) {
           dataList.clear();
        }
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex("districtname");
            String districtName = cursor.getString(columnIndex);

            if (selectedProvince.equals(cursor.getString(cursor.getColumnIndex("provincename")))&&
                    selectedCity.equals(cursor.getString(cursor.getColumnIndex("cityname")))
                    &&!dataList.contains(districtName)) {
                dataList.add(districtName);
            }
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel=LEVEL_DISTRICT ;
        cursor.close();
    }
}
