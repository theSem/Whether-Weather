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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    private EarthquakeAdapter adapter;

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=7&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);



        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new EarthquakeAdapter(
                this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                Earthquake current = adapter.getItem(position);

                Uri earthquakeUri = Uri.parse(current.getmUrl());

                Intent earthquakeIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                startActivity(earthquakeIntent);
            }
        });
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>>{

        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            if (urls.length == 0) return null;

            return QueryUtils.fetchEarthquakeData(urls[0]);
        }

        @Override
        protected void onPostExecute(List<Earthquake> list){
            adapter.clear();

            if (list != null && !list.isEmpty()) adapter.addAll(list);
        }
    }
}
