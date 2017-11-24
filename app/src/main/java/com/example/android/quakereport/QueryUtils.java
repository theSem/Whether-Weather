package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.quakereport.WeatherActivity.LOG_TAG;

public final class QueryUtils {

    private QueryUtils() {
    }

    public static ArrayList<Weather> extractEarthquakes(String JSON_RESPONSE) {

        if (TextUtils.isEmpty(JSON_RESPONSE)) return null;

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Weather> weatherArrayList = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject jsonObject = new JSONObject(JSON_RESPONSE);
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            System.out.println(jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("hourly_forecast");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentWeather = jsonArray.getJSONObject(i);
                JSONObject currentWeatherTemp = currentWeather.getJSONObject("temp");
                weatherArrayList.add(new Weather(currentWeatherTemp.getDouble("english"),currentWeather.getString("wx"),currentWeather.getJSONObject("FCTTIME").getLong("epoch"),currentWeather.getString("condition"),currentWeather.getString("icon")));
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the weather JSON results", e);
        }

        // Return the list of earthquakes
        return weatherArrayList;
    }

    private static URL formatURL(String stringURL){
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building url ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if (url == null) return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving earthquake JSON", e);
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                stringBuilder.append(line);
                line = reader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    static List<Weather> fetchWeatherData(String requestUrl) {
        Log.i(LOG_TAG, "Fetching weather data");

        URL url = formatURL(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Request failed", e);
        }

        return extractEarthquakes(jsonResponse);
    }

}