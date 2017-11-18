package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by sam on 11/6/17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes){
        super(context,0,earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item,parent,false);
        }

        Earthquake currentEarthquake = getItem(position);

        //Grab all the views
        TextView magView = (TextView) listItemView.findViewById(R.id.magnitude);
        TextView directionView = (TextView) listItemView.findViewById(R.id.direction);
        TextView placeView = (TextView) listItemView.findViewById(R.id.place);
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);

        //Format magnitude
        magView.setText(formatMagnitude(currentEarthquake.getmMagnitude()));

        GradientDrawable magnitudeCircle = (GradientDrawable) magView.getBackground();

        int magnitudeColor = getMagnitudeColor(currentEarthquake.getmMagnitude());

        magnitudeCircle.setColor(magnitudeColor);

        //Format location
        String[] locationStrings = formatLocation(currentEarthquake.getmPlace());

        directionView.setText(locationStrings[0]);
        placeView.setText(locationStrings[1]);


        //Format the date/time textViews
        String[] dateTime = formatDates(currentEarthquake.getmDate());

        dateView.setText(dateTime[0]);
        timeView.setText(dateTime[1]);

        return listItemView;
    }

    private String[] formatDates(long dateInt){
        Date dateObject = new Date(dateInt);

        String[] temp = new String[2];

        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        temp[0] = dateFormat.format(dateObject);
        temp[1] = timeFormat.format(dateObject);

        return temp;
    }

    private String[] formatLocation(String location){
        String[] temp = new String[2];
        if (location.contains(" of ")){
            String[] locationSplit = location.split(" of ");
            temp[0] = String.format("%s of", locationSplit[0]);
            temp[1] = locationSplit[1];
        } else {
            temp[0] = "Near the";
            temp[1] = location;
        }
        return temp;
    }

    private String formatMagnitude(double magnitude){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude){
        int magColor;
        int magFloor = (int) Math.floor(magnitude);

        switch(magFloor){
            case 0:
            case 1:
                magColor = R.color.magnitude1;
                break;
            case 2:
                magColor = R.color.magnitude2;
                break;
            case 3:
                magColor = R.color.magnitude3;
                break;
            case 4:
                magColor = R.color.magnitude4;
                break;
            case 5:
                magColor = R.color.magnitude5;
                break;
            case 6:
                magColor = R.color.magnitude6;
                break;
            case 7:
                magColor = R.color.magnitude7;
                break;
            case 8:
                magColor = R.color.magnitude8;
                break;
            case 9:
                magColor = R.color.magnitude9;
                break;
            default:
                magColor = R.color.magnitude10plus;
        }
        return ContextCompat.getColor(getContext(), magColor);

    }

}
