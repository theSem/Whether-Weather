package com.example.android.quakereport;

/**
 * Created by sam on 11/6/17.
 */

public class Earthquake {

    private String mPlace;
    private double mMagnitude;
    private long mDate;
    private String mUrl;

    public Earthquake(String place, double magnitude, long date, String url){
        mPlace = place;
        mMagnitude = magnitude;
        mDate = date;
        mUrl = url;
    }


    long getmDate() {
        return mDate;
    }


    double getmMagnitude() {
        return mMagnitude;
    }


    String getmPlace() {
        return mPlace;
    }

    String getmUrl(){
        return mUrl;
    }



}
