package com.example.android.quakereport;

/**
 * Created by sam on 11/6/17.
 */

public class Weather {

    private double mTemp;
    private String mMain;
    private long mDate;
    private String mDescription;
    private String mIconName;

    public Weather(double temp, String main, long date, String desc, String iconName){
        mTemp = temp;
        mMain = main;
        mDate = date;
        mDescription = desc;
        mIconName = iconName;
    }


    long getDate() {
        return mDate;
    }


    String getMain() {
        return mMain;
    }


    double getTemp() {
        return mTemp;
    }

    String getDescription(){
        return mDescription;
    }

    String getmIconName() {
        return mIconName;
    }
}
