package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gaurav on 22/12/17.
 */

public class earthquakeAdapter extends ArrayAdapter<Earthquake> {

    String LOCATION_OFFSET = "of";

    public earthquakeAdapter(Context context, ArrayList<Earthquake> words) {
        super(context,0,words);
    }

    private int getMagnitudeColor(double magnitude)
    {
        int mag = (int)Math.floor(magnitude);
        int magnitudeColor;


        switch (mag)
        {
            case 0:
            case 1:
                magnitudeColor = R.color.magnitude1;
                break;
            case 2:
                magnitudeColor = R.color.magnitude2;
                break;
            case 3:
                magnitudeColor = R.color.magnitude3;
                break;
            case 4:
                magnitudeColor = R.color.magnitude4;
                break;
            case 5:
                magnitudeColor = R.color.magnitude5;
                break;
            case 6:
                magnitudeColor = R.color.magnitude6;
                break;
            case 7:
                magnitudeColor = R.color.magnitude7;
                break;
            case 8:
                magnitudeColor = R.color.magnitude8;
                break;
            case 9:
                magnitudeColor = R.color.magnitude9;
                break;
            default:
                magnitudeColor = R.color.magnitude10plus;
        }
        return ContextCompat.getColor(getContext(),magnitudeColor);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;
        if(currentView==null)
        {
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_report_list,parent,false);
        }
        Earthquake current_report = getItem(position);
        Double magnitude = current_report.getmMagnitude();
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String mag = decimalFormat.format(magnitude);

        TextView textView = (TextView)currentView.findViewById(R.id.magnitude);
        textView.setText(mag);
        int color = getMagnitudeColor(magnitude);
        GradientDrawable gradientDrawable = (GradientDrawable)textView.getBackground();
        gradientDrawable.setColor(color);


        String originalLocation = current_report.getmPlace();
        String location_offset;
        String primary_location;

        if (originalLocation.contains(LOCATION_OFFSET))
        {
            String[] parts = originalLocation.split("of ");
            location_offset=parts[0]+LOCATION_OFFSET;
            primary_location=parts[1];
        }
        else{
            location_offset=getContext().getString(R.string.Near_the);
            primary_location=originalLocation;
        }


        textView = (TextView)currentView.findViewById(R.id.location_offset);
        textView.setText(location_offset);


        textView = (TextView)currentView.findViewById(R.id.primary_location);
        textView.setText(primary_location);

        long timeinUnix = current_report.getmTime();
        Date date = new Date(timeinUnix);
        String date_report = dateformat(date);
        textView = (TextView)currentView.findViewById(R.id.date);
        textView.setText(date_report);
        String time_report = timeformat(date);
        textView = (TextView)currentView.findViewById(R.id.time);
        textView.setText(time_report);

        return currentView;

    }
    private String dateformat(Date date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd YYYY");
        return simpleDateFormat.format(date);
    }
    private String timeformat(Date date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh mm a");
        return simpleDateFormat.format(date);

    }
}
