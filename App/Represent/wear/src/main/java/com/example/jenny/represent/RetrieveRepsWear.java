package com.example.jenny.represent;

/**
 * Created by Jenny on 3/14/2016.
 */

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Jenny on 3/14/2016.
 */
public class RetrieveRepsWear extends AsyncTask<Void,Void,String> {
    final String API_KEY = "86e2bfee983d4257b0cb39dae5faca6f";
    final String API_URL = "https://congress.api.sunlightfoundation.com";
    private Exception exception;
    int type;
    double latitude = 0;
    double longitude = 0;
    int zipcode = 0;
    Activity context;
    GridViewPager grid;
    FragmentManager fma;
    DotsPageIndicator dpi;


    public RetrieveRepsWear(int zip, GridViewPager g, FragmentManager fm, DotsPageIndicator dp, Activity m) {

        zipcode = zip;
        type = 1;
        grid = g;
        fma = fm;
        dpi = dp;
        context = m;
    }
    public RetrieveRepsWear(double lat, double lon, GridViewPager g, FragmentManager fm, DotsPageIndicator dp, Activity m) {

        latitude = lat;
        longitude = lon;
        type = 0;
        grid = g;
        fma = fm;
        dpi = dp;
        context = m;
    }


    protected String doInBackground(Void... params) {
        String location;
        // fill in for two cases
        if (type == 0)
            location = "latitude=" + latitude + "longitude=" + longitude;
        else
            location = "zip=" + zipcode;
        //getjson
        try {
            URL url = new URL(API_URL + "/legislators/locate?" + location + "&apikey=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }
    //basic repinfoo!
    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
            return;
        }
        //progressBar.setVisibility(View.GONE);
        Log.i("INFO", response);
        try {
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
            JSONArray arr = object.getJSONArray("results");
            HashMap<String, String[]> info = new HashMap<String, String[]>();
            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject personal = arr.getJSONObject(i);
                String name = personal.getString("first_name") + " " + personal.getString("last_name");
                String party = personal.getString("party");
                String bioguide = personal.getString("bioguide_id");

                String[] essential = {party, String.valueOf(R.drawable.ic_face_24dp),  bioguide};
                info.put(name, essential);


            }
            grid.setAdapter(new PagerAdapter(context, fma, info));
            dpi.setPager(grid);




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
