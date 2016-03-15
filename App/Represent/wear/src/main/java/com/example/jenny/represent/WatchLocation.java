package com.example.jenny.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WatchLocation extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_location);

//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
        Button zipbutton = (Button) findViewById(R.id.autolocate);
        EditText zipinput = (EditText) findViewById(R.id.zipcode);

//        if (extras != null) {
//            String zip = extras.getString("ZIPCODE");
//            zipbutton.setText(zip); //for testing purposes
//
//        }

        zipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent custom = new Intent(WatchLocation.this, candidates.class); //dummy candidates
                startActivity(custom);
            }
        });
        zipinput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    //insert action to verify zip code and get info here
                    Intent gps = new Intent(WatchLocation.this, candidates.class);
                    startActivity(gps);
                    return true;
                }

                return false;
            }
        });




    }

}
