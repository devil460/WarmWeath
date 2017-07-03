package com.ryan.warmweather.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryan.warmweather.R;
import com.ryan.warmweather.gson.FutureWeather;

import java.util.List;

/**
 * Created by Think on 2017/7/2.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    private List<FutureWeather> mWeatherList;
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView weatherImage;
        private ImageView weatherImage1;
        private TextView weatherText;
        private TextView temperatureText;
        private TextView windText;
        private TextView weekText;
        public ViewHolder(View itemView) {
            super(itemView);
            weatherImage = (ImageView)itemView.findViewById(R.id.weatherIcon);
            weatherImage1 =(ImageView)itemView.findViewById(R.id.weatherIcon1);
            weatherText = (TextView)itemView.findViewById(R.id.future_weather);
            temperatureText= (TextView)itemView.findViewById(R.id.future_temperature);
            windText = (TextView)itemView.findViewById(R.id.future_wind);
            weekText = (TextView)itemView.findViewById(R.id.future_week);

        }
    }
    public MyRecyclerAdapter(List<FutureWeather> weatherList){
        mWeatherList = weatherList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FutureWeather futureWeather = mWeatherList.get(position);
        holder.weekText.setText(futureWeather.week);
        holder.windText.setText(futureWeather.wind);
        holder.temperatureText.setText(futureWeather.temperature);
        holder.weatherText.setText(futureWeather.weather);
        if(futureWeather.weather_id.fb.equals(futureWeather.weather_id.fa)) {
            holder.weatherImage1.setVisibility(View.GONE);
            holder.weatherImage.setImageResource(ImageUtil.getImageId(futureWeather.weather_id.fa));
        }else {
            holder.weatherImage.setImageResource(ImageUtil.getImageId(futureWeather.weather_id.fa));
            holder.weatherImage1.setImageResource(ImageUtil.getImageId(futureWeather.weather_id.fb));
        }

    }



    @Override
    public int getItemCount() {
        return mWeatherList.size();
    }
}
