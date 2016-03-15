package com.example.jenny.represent;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Set;

public class candidates extends Activity {
    final HashMap<String, String[]> repinfo = new HashMap<String, String[]>() {{
        String[] x = {"Republican", String.valueOf(R.drawable.ic_face_24dp)};
        put("name",x);
        String [] y = {"Democrat", String.valueOf(R.drawable.ic_face_24dp)};
        put("name1", y);
    }};
    final String[] inorder = {"name", "name1"};
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates);
        final Resources res = getResources();
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        FragmentManager fm = getFragmentManager();
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        if (type == 0) {
            double latitude = intent.getDoubleExtra("latitude", 0);
            double longitude = intent.getDoubleExtra("longitude", 0);
            RetrieveRepsWear task = new RetrieveRepsWear(latitude, longitude, pager, fm, dotsPageIndicator, this );
            task.execute();
        } else if (type ==1) {
            int zip = intent.getIntExtra("zipcode", 0);
            RetrieveRepsWear task = new RetrieveRepsWear(zip, pager,fm,dotsPageIndicator, this);
            task.execute();
        }
//        pager.setAdapter(new PagerAdapter(this, getFragmentManager(),repinfo));
//
//        dotsPageIndicator.setPager(pager);
        //EditText x = (EditText) findViewById(0); // the dummies
        //EditText y = (EditText) findViewById(1);
        //x.setOnClickListener(this);
        //y.setOnClickListener(this);

    }



}
