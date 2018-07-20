
package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Gaurav on 27/12/17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {


    private String mUrl;

    public EarthquakeLoader(Context context, String Url)
    {
        super(context);
        Log.v("Hello","I am in EarthquakeLoader");
        mUrl=Url;
    }

    @Override
    protected void onStartLoading() {

        Log.v("Hello","I am in onStartLoading");
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        Log.v("Hello","I am in loadInBackground");
        if(mUrl==null)
            return null;

        return QueryUtils.extractEarthquakes(mUrl);
    }
}
