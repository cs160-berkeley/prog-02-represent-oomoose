package com.example.jenny.represent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;


import java.util.Arrays;
import java.util.List;

public class more extends representatives  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        String[] infopack= bundle.getStringArray("infopack");

        //this is where presumably, i would get info about the rep, but i just have dummy info and im not too familiar
        //with the api that i am to be using
        TextView namebox = (TextView) findViewById(R.id.name);
        namebox.setText(Html.fromHtml(infopack[0]));
        TextView term = (TextView) findViewById(R.id.termend);
        term.setText(term.getText() + " " + infopack[4]);
        TextView emailbox = (TextView) findViewById(R.id.email);
        emailbox.setText(infopack[2]);
        TextView websitebox = (TextView) findViewById(R.id.website);
        websitebox.setText(infopack[3]);
        TextView partybox = (TextView) findViewById(R.id.party);
        //set text color based on party, include more parties
        String partyrep = infopack[1];
        partybox.setText(partyrep);

        if (partyrep.equals("R")){
            partybox.setTextColor(Color.parseColor("#cc0000"));
        } else{
            partybox.setTextColor(Color.parseColor("#1E90FF"));
        }
//        //the layout description does not say we need to put the twitter stuff here
        TextView committebox = (TextView) findViewById(R.id.committeelist);
        TextView billbox = (TextView) findViewById(R.id.billslist);

        RetrieveB task1 = new RetrieveB(billbox, infopack[5]);
        task1.execute();

        RetrieveC task2 = new RetrieveC(committebox, infopack[5]);
        task2.execute();
//




    }

}
