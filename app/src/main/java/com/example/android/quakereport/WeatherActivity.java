/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class WeatherActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Weather>> {

    private WeatherAdapter adapter;

    public static final String LOG_TAG = WeatherActivity.class.getName();

    private static final String AUTO_COMPLETE_URL = "http://autocomplete.wunderground.com/aq?query=";

    private static final String WEATHER_URL = "http://api.wunderground.com/api/48328dbbfb350a2f/hourly/q/MN/Minneapolis.json";

    private static final int WEATHER_LOADER_ID = 1;

    private TextView emptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG,"Main activity started.");

        //Default setup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);


        // Find the ListView view to use with array adapter
        ListView weatherListView = (ListView) findViewById(R.id.list);

        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        weatherListView.setEmptyView(emptyStateTextView);


        // Create a new adapter for the listView
        adapter = new WeatherAdapter(
                this, new ArrayList<Weather>());

        // Link the listview with the adapter
        weatherListView.setAdapter(adapter);

        //OnClickListener for items displayed in the list
        weatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                Weather current = adapter.getItem(position);
/*
                Uri earthquakeUri = Uri.parse(current.getmUrl());

                Intent earthquakeIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                startActivity(earthquakeIntent); */
            }
        });

        //Initialize a loader manager
        LoaderManager loaderManager = getLoaderManager();

        //Start the loader manager
        Log.i(LOG_TAG, "loadManager initializing");
        loaderManager.initLoader(WEATHER_LOADER_ID, null, this);
    }

    //Implement the method onCreateLoader which executes when initialized
    @Override
    public Loader<List<Weather>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPreferences.getString(getString(R.string.key),getString(R.string.location));
        Uri baseUri = Uri.parse(WEATHER_URL);

        return new WeatherLoader(this, WEATHER_URL);
    }

    //Execute this method when the load is finished
    @Override
    public void onLoadFinished(Loader<List<Weather>> loader, List<Weather> weathers) {
        emptyStateTextView.setText(R.string.empty_view);
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null || ni.isConnectedOrConnecting()){
            emptyStateTextView.setText("No connection.");
        }
        adapter.clear();

        if (weathers != null && !weathers.isEmpty()) adapter.addAll(weathers);
        findViewById(R.id.loading_bar).setVisibility(GONE);
    }

    //Reset the loader for the next time we want to use it
    @Override
    public void onLoaderReset(Loader<List<Weather>> loader) {
        Log.i(LOG_TAG,"Loader reset");
        adapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.settings){
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





}
