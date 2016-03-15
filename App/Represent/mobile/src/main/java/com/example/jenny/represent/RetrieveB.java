package com.example.jenny.represent;

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

/**
 * Created by Jenny on 3/14/2016.
 */
public class RetrieveB extends AsyncTask<Void,Void,String> {
    final String API_KEY = "86e2bfee983d4257b0cb39dae5faca6f";
    final String API_URL = "https://congress.api.sunlightfoundation.com";
    private Exception exception;
    TextView bills;
    String b;
    Activity context;

    public RetrieveB(TextView bill, String bioguide) {
        bills = bill;
        bill.setText("");
        b = bioguide;
    }

    protected String doInBackground(Void... params) {

        //getjson
        try {
            Log.d("INFO",b);
            URL url = new URL(API_URL + "/bills?sponsor_id=" + b + "&cosponsor_ids=" + b + "&apikey=" + API_KEY);
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
            Log.d("INFO", "null");
            return;
        }
        //progressBar.setVisibility(View.GONE);
        Log.d("INFO", response);
        try {
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
            JSONArray arr = object.getJSONArray("results");
            int len = arr.length();
            Log.d("whee", "updatecheck");
            if (arr.length() == 0) {
                bills.setText("No bills found.");
                return;
            } else if (arr.length() > 5) {
                len = 5;
            }
            for (int i = 0; i < len; i++)
            {
                JSONObject personal = arr.getJSONObject(i);
                String introduce = personal.getString("introduced_on");
                String name = personal.getString("short_title");
                bills.setText(bills.getText() + introduce + ": " + name + "\n");
                //insert Tweet here or notification manager? havent figured it out yet
            }





        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}