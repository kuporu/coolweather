package com.hgc.coolweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hgc.coolweather.gson.Forecast;

import java.util.List;

public class ForecastAdapter extends ArrayAdapter<Forecast> {

    private final int resourceId;

    public ForecastAdapter(@NonNull Context context, int resource, @NonNull List<Forecast> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Forecast forecast = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView dataText = view.findViewById(R.id.date_text);
        TextView infoText = view.findViewById(R.id.info_text);
        TextView maxText = view.findViewById(R.id.max_text);
        TextView minText = view.findViewById(R.id.min_text);
        dataText.setText(forecast.date);
        infoText.setText(forecast.more.info);
        maxText.setText(forecast.temperature.max);
        minText.setText(forecast.temperature.min);

        return view;
    }
}