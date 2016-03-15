package com.example.jenny.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jenny on 3/14/2016.
 */
public class ClickableFragment extends CardFragment{
    static String bio;
    static Activity context;
    static ClickableFragment create(CharSequence title, CharSequence text, int iconRes, String b, Activity a) {
        ClickableFragment f = (ClickableFragment) CardFragment.create(title, text, iconRes);
        bio = b;
        context = a;
        return f;
    }

    @Override
    public View onCreateContentView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateContentView(inflater, container, savedInstanceState);
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //towatch
                Intent update = new Intent (context, WatchtoPhone.class);
                update.putExtra("biocode", bio);
                update.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(update);
            }
        });
        return v;
    }
}
