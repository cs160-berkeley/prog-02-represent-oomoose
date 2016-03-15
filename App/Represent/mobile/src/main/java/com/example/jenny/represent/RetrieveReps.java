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
public class RetrieveReps extends AsyncTask<Void,Void,String> {
    final String API_KEY = "86e2bfee983d4257b0cb39dae5faca6f";
    final String API_URL = "https://congress.api.sunlightfoundation.com";
    private Exception exception;
    TextView responseView;
    LinearLayout replist;
    int type;
    double latitude = 0;
    double longitude = 0;
    int zipcode = 0;
    Context mContext;
    Activity context;

    public RetrieveReps(TextView f, int zip, LinearLayout r, Activity m) {

        zipcode = zip;
        type = 1;
        replist =r;
        mContext = m.getApplicationContext();
        context = m;
    }
    public RetrieveReps(TextView fill, double lat, double lon, LinearLayout r, Activity m) {

        latitude = lat;
        longitude = lon;
        replist = r;
        mContext = m.getApplicationContext();
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
            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject personal = arr.getJSONObject(i);
                String name = personal.getString("first_name") + " " + personal.getString("last_name");
                String party = personal.getString("party");
                String email = personal.getString("oc_email");
                String website = personal.getString("website");
                String term = personal.getString("term_end");
                String bioguide = personal.getString("bioguide_id");
                String twit = personal.getString("twitter_id");

                TextView b = new TextView(context);


                if (party.equals("D")) {
                    name = "<font color='#1E90FF'>" + name +  "</font>";
                } else if (party.equals("R")) {
                    name = "<font color='#CC0000'>" + name +  "</font>";
                } else {
                }
                b.setText(Html.fromHtml(name + "<br />" + email + "<br />" + website));
                Linkify.addLinks(b, Linkify.ALL);
                b.setLinkTextColor(Color.parseColor("#772233"));
                b.setPadding(10, 0, 0, 10);
                //insert more info button to get to personal view
                Button m = new Button(context);
                m.setText("Details ");
                m.setId(i);
                setOnClick(m, name, party, email, website, term, bioguide);
                replist.addView(b);
                replist.addView(m);
                //insert Tweet here or notification manager? havent figured it out yet

                //update the watch
                if (type == 0) {
                    Intent sendIntent = new Intent(context.getBaseContext(), PhoneToWatchService.class);
                    sendIntent.putExtra("type", 0); //0 type is current 1 type is user input
                    sendIntent.putExtra("latitude", latitude);
                    sendIntent.putExtra("longitude", longitude);
                    context.startService(sendIntent);
                } else  {
                    Intent sendIntent = new Intent(context.getBaseContext(), PhoneToWatchService.class);
                    sendIntent.putExtra("type", 1);
                    sendIntent.putExtra("zipcode", zipcode);
                    context.startService(sendIntent);
                }

            }





        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //making textviews clickalbe
    //http://stackoverflow.com/questions/10614696/how-to-pass-parameters-to-onclicklistener
    private void setOnClick(Button m, final String name, final String party, final String email, final String website, final String term, final String bioguide) {
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do whatever you want(str can be used here)
                Intent tomore = new Intent(context, more.class);
                //refer to this from more.java
                String[] infopack = {name, party, email, website, term, bioguide};
                tomore.putExtra("infopack", infopack);
                tomore.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(tomore);
            }
        });
    }
}