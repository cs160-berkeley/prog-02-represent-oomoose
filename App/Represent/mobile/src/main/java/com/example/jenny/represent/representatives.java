package com.example.jenny.represent;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class representatives extends AppCompatActivity implements View.OnClickListener {
    final HashMap<String, String[]> repinfo = new HashMap<String, String[]>() {{
        String[] x = {"email", "website", "twit", "Republican"};
        put("name",x);
        String [] y = {"email1","website1","twit1", "Democrat"};
        put("name1", y);
    }};

    private int reps = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representatives);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinearLayout replist = (LinearLayout) findViewById(R.id.replist);
        int i = 1;
        for (String person:repinfo.keySet()) {
            Button example = new Button(this);
            example.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            String[] dummy = repinfo.get(person);
            example.setText(dummy[3] + dummy[0] + "\n" + dummy[1] + "\n" + dummy[2]);
            Drawable face = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_face_24dp);
            example.setId(i);
            example.setTag(person);
            example.setCompoundDrawablesWithIntrinsicBounds(face, null, null, null); //dummy picture for now
            i++;
            replist.addView(example);
            example.setOnClickListener(this);
            reps++;
        }



    }

    @Override
    public void onClick(View v) {
        int person = v.getId();
        Button b = (Button) findViewById(person);
        String name = (String) b.getTag();
        Intent intent = new Intent(representatives.this, more.class);
        intent.putExtra("whichrep", name);
        startActivity(intent);
    }


}
