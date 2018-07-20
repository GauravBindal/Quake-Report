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

/**
 * Created by Gaurav on 22/12/17.
 */

public class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    static String LOG_TAG = "URL building error";
    private static URL createUrl(String StringUrl){
        URL url =null;
        try {
            url = new URL(StringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line!=null)
            {
                stringBuilder.append(line);
                line=bufferedReader.readLine();
            }

        }
        return stringBuilder.toString();
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String json = "";

        if(url==null)
            return json;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200)
            {
                inputStream = urlConnection.getInputStream();
                json = readFromStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }
        finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();
        }
        return json;

    }
    private static ArrayList<Earthquake> getEarthquakes(String JsonResponse)
    {
        if(TextUtils.isEmpty(JsonResponse))
            return null;
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root = new JSONObject(JsonResponse);
            JSONObject metadata = root.getJSONObject("metadata");
            int count = metadata.getInt("count");
            JSONArray jsonArray = root.getJSONArray("features");
            for(int i=0;i<count;i++)
            {
                JSONObject current_earthquake = jsonArray.getJSONObject(i);
                JSONObject current_earthquake_properties = current_earthquake.getJSONObject("properties");
                double magnitude = current_earthquake_properties.getDouble("mag");
                String place = current_earthquake_properties.getString("place");
                Long timeInUnix = current_earthquake_properties.getLong("time");
                String URL = current_earthquake_properties.getString("url");
                earthquakes.add(new Earthquake(magnitude,place,timeInUnix,URL));


            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results"+e.getMessage(), e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
    public static ArrayList<Earthquake> extractEarthquakes(String StringUrl) {

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        Log.v("Hello","I am in extractEarthquakes ");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL url = createUrl(StringUrl);
        String JsonResponse=null;
        try {
            JsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return getEarthquakes(JsonResponse);
    }
}
