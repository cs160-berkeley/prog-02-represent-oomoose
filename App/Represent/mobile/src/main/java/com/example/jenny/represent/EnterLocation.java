package com.example.jenny.represent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

public class EnterLocation extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "1eRsdWZSK9OK2OcCiYiUKKAjg";
    private static final String TWITTER_SECRET = "dqdsYTnMcTgGBmtVbbCqab7zITVjYCBTrA8VwZvO0mrtZbEwXn";

    // for now both inputs will lead to a random senator dummy page (the same page)
    private GoogleApiClient mGoogleApiClient;
    double latitude;
    double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_location);
        final EditText zip = (EditText) findViewById(R.id.zipcode);
        final Button auto = (Button) findViewById(R.id.autolocate);
        auto.setWidth(zip.getMeasuredWidth() + 40);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        //get current location
        auto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //towatch
                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                int t = 0;
                sendIntent.putExtra("type", 0); //0 type is current 1 type is user input
                sendIntent.putExtra("latitude", latitude);
                sendIntent.putExtra("longitude", longitude);
                startService(sendIntent);
                Intent custom = new Intent(EnterLocation.this, representatives.class);
                //tophone
                custom.putExtra("type", 0);
                custom.putExtra("latitude", latitude);
                custom.putExtra("longitude", longitude);
                startActivity(custom);
            }
        });
        //user input manager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        zip.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int code = Integer.parseInt(zip.getText().toString());
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    //insert action to verify zip code and get info here
                    //tophone
                    Intent gps = new Intent(EnterLocation.this, representatives.class);
                    gps.putExtra("type", 1);
                    gps.putExtra("zipcode", code);
                    startActivity(gps);
                    //towatch
                    Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                    sendIntent.putExtra("type", 1);
                    sendIntent.putExtra("zipcode", code);
                    startService(sendIntent);
                    return true;
                }

                return false;
            }
        });


    }
    //http://developer.android.com/training/location/retrieve-current.html
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public void onConnectionSuspended(int x) {}
    @Override
    public void onConnected(Bundle bundle) {
        Log.d("Yay", "Location services connected.");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }

    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("unconnected", "Location services suspended. Please reconnect.");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
