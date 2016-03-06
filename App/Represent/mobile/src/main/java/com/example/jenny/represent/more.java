package com.example.jenny.represent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import java.util.Arrays;
import java.util.List;

public class more extends representatives  {
    String[] dummycommittees = {"Committee1", "Commitee2"};
    String[] dummybills = {"bill1", "bill2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("whichrep");
        //this is where presumably, i would get info about the rep, but i just have dummy info and im not too familiar
        //with the api that i am to be using
        String[] infopack = repinfo.get(name);
        TextView namebox = (TextView) findViewById(R.id.name);
        namebox.setText(name);
        TextView emailbox = (TextView) findViewById(R.id.email);
        emailbox.setText(infopack[0]);
        TextView websitebox = (TextView) findViewById(R.id.website);
        websitebox.setText(infopack[1]);
        TextView partybox = (TextView) findViewById(R.id.party);
        //set text color based on party, include more parties
        String partyrep = infopack[3];
        partybox.setText(partyrep);
        if (partyrep.equals("Republican")){
            partybox.setTextColor(Color.parseColor("#cc0000"));
        } else{
            partybox.setTextColor(Color.parseColor("#1E90FF"));
        }
        //the layout description does not say we need to put the twitter stuff here
        TextView committebox = (TextView) findViewById(R.id.committeelist);
        String s="";
        for (String committe: dummycommittees) {
            s += committe + "\n";
        }
        committebox.setText(s);
        TextView billbox = (TextView) findViewById(R.id.billslist);
        String b="";
        for (String bill: dummybills) {
            b += bill + "\n";
        }
        billbox.setText(b);

        //doing some calculation to find term end but not really
        TextView term = (TextView) findViewById(R.id.termend);
        term.setText(term.getText() + "2016");


    }

}
