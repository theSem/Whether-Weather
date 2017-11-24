package com.example.android.quakereport;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * Created by sam on 11/21/17.
 */

public class WeatherLoader extends AsyncTaskLoader<List<Weather>> {

    private final static String LOG_TAG = WeatherLoader.class.getName();
    private String url;

    //The task to do in the background
    @Override
    public List<Weather> loadInBackground() {
        Log.i(LOG_TAG, "Load in background started");
        if (TextUtils.isEmpty(url) || url == null) return null;

        return QueryUtils.fetchWeatherData(url);
    }

    //What to do when told to start
    @Override
    protected void onStartLoading(){
        Log.i(LOG_TAG, "Started loading");
        forceLoad();
    }

    //Initializer method
    WeatherLoader(Context context, String url){
        super(context);
        this.url = url;
    }
}
