package com.example.android.quakereport;

/**
 * Created by Gaurav on 22/12/17.
 */

public class Earthquake {
    double mMagnitude;
    String mPlace;
    long mTime;
    String mURL;
    public Earthquake(double Magnitude, String Place, long Time, String URL)
    {
        mMagnitude=Magnitude;
        mPlace=Place;
        mTime=Time;
        mURL=URL;
    }
    public double getmMagnitude(){
        return mMagnitude;
    }

    public String getmPlace() {
        return mPlace;
    }

    public long getmTime() {
        return mTime;
    }

    public String getmURL() {
        return mURL;
    }
};
