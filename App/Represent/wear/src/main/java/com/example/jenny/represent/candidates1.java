package com.example.jenny.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class candidates1 extends Activity implements View.OnClickListener{
    final HashMap<String, String[]> repinfo = new HashMap<String, String[]>() {{
        String[] x = {"email", "website", "twit", "Republican"};
        put("name",x);
        String [] y = {"email1","website1","twit1", "Democrat"};
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
        pager.setAdapter(new ImageAdapter(this));
        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);
        //EditText x = (EditText) findViewById(0); // the dummies
        //EditText y = (EditText) findViewById(1);
        //x.setOnClickListener(this);
        //y.setOnClickListener(this);

    }
   @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent sendIntent = new Intent(getBaseContext(), WatchtoPhone.class);
        sendIntent.putExtra("PERSON", inorder[id]);
        startService(sendIntent);
    }


    public class ImageAdapter extends GridPagerAdapter{
        final Context mContext;

        public ImageAdapter(final Context context) {
            mContext = context;
        }

        @Override
        public int getRowCount() {
            return 1;
        }
        public int getColumnCount(int i) {
            return 3;
        }
        @Override
        public int getCurrentColumnForRow(int row, int currentColumn) {
            return currentColumn;
        }
        @Override
        public Object instantiateItem(ViewGroup viewGroup, int row, int col) {
            if (col ==2) {
                ImageView piechart = new ImageView(mContext);
                piechart.setImageResource(R.drawable.americanflag);
                viewGroup.addView(piechart);
                return piechart;
            }
            TextView page;
            page = new TextView(mContext);
            Drawable face = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_face_24dp);
            page.setCompoundDrawablesWithIntrinsicBounds(null, face, null, null); //dont know how to get pictures, just a dummy
            page.setPadding(0, 100, 0, 0);
            final String person = inorder[col];
            String[] relinfo = repinfo.get(person);
            page.setText(person + "\n" + relinfo[3] + col);
            page.setTag(person);
            page.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            page.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            page.setId(col);
            page.setOnClickListener(candidates1.this);
            viewGroup.addView(page);
            return page;
        }
        @Override
        public void destroyItem(ViewGroup viewGroup, int i, int i2, Object o) {
            viewGroup.removeView((View) o);
        }
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view.equals(o);
        }

    }
}
