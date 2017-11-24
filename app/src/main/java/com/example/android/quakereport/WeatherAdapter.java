package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by sam on 11/6/17.
 */

public class WeatherAdapter extends ArrayAdapter<Weather> {
    public WeatherAdapter(Context context, List<Weather> earthquakes){
        super(context,0,earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.weather_list_item,parent,false);
        }

        Weather currentWeather = getItem(position);

        //Grab all the views
        TextView tempView = (TextView) listItemView.findViewById(R.id.temp);
        TextView descView = (TextView) listItemView.findViewById(R.id.description);
        TextView mainView = (TextView) listItemView.findViewById(R.id.main);
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.icon);

        //Format temp
        tempView.setText(formatTemperature(currentWeather.getTemp()));

        GradientDrawable tempCircle = (GradientDrawable) tempView.getBackground();

        int tempColor = getTemperatureColor(currentWeather.getTemp());

        tempCircle.setColor(tempColor);

        descView.setText(currentWeather.getDescription());
        mainView.setText(currentWeather.getMain());


        //Format the date/time textViews
        String[] dateTime = formatDates(currentWeather.getDate());

        dateView.setText(dateTime[0]);
        timeView.setText(dateTime[1]);

        try {
            iconView.setImageResource(R.drawable.class.getField(currentWeather.getmIconName()).getInt(null));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listItemView;
    }

    private String[] formatDates(long dateInt){
        Date dateObject = new Date(dateInt*1000L);

        String[] temp = new String[2];

        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        temp[0] = dateFormat.format(dateObject);
        temp[1] = timeFormat.format(dateObject);

        return temp;
    }


    private String formatTemperature(double temperature){
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return decimalFormat.format(temperature);
    }

    private int getTemperatureColor(double temperature){
        int tempColor;
        int tempFloor = (int) Math.floor(temperature/10);

        switch(tempFloor){
            case 0:
            case 1:
                tempColor = R.color.temperature1;
                break;
            case 2:
                tempColor = R.color.temperature2;
                break;
            case 3:
                tempColor = R.color.temperature3;
                break;
            case 4:
                tempColor = R.color.temperature4;
                break;
            case 5:
                tempColor = R.color.temperature5;
                break;
            case 6:
                tempColor = R.color.temperature6;
                break;
            case 7:
                tempColor = R.color.temperature7;
                break;
            case 8:
                tempColor = R.color.temperature8;
                break;
            case 9:
                tempColor = R.color.temperature9;
                break;
            default:
                tempColor = R.color.temperature10plus;
        }
        return ContextCompat.getColor(getContext(), tempColor);

    }

}
