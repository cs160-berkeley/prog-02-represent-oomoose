package com.example.jenny.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.view.Gravity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jenny on 3/4/2016.
 */
//https://android.googlesource.com/platform/frameworks/base/+/android-5.0.1_r1/docs/html/training/wearables/ui/2d-picker.jd?autodive=0%2F%2F%2F
public class PagerAdapter extends FragmentGridPagerAdapter {
    private final Context mContext;
    private HashMap<String, String[]> data = new HashMap<String, String[]>();
    private String[] cardorder;
    // string should have,  name --- part, icon

    public PagerAdapter(Context ctx, FragmentManager fm, HashMap<String, String[]> d) {
        super(fm);
        mContext = ctx;
        data = d;
        cardorder = d.keySet().toArray(new String[d.size()]);
    }
    public void updateAdapter(HashMap<String, String[]> d2) {
        data = d2;
        this.notifyDataSetChanged();
        cardorder = d2.keySet().toArray(new String[d2.size()]);
    }


    @Override
    public Fragment getFragment(int row, int col) {
        if (row == 2) {
            CardFragment regional = CardFragment.create("2012 Presidential Vote", "blaaah", R.drawable.americanflag);
            regional.setCardGravity(Gravity.CENTER);
        }
        String name = cardorder[col];
        String[] unpack = data.get(name);
        CardFragment fragment = CardFragment.create(name, unpack[0], Integer.parseInt(unpack[1]));

        // Advanced settings (card gravity, card expansion/scrolling)
        fragment.setCardGravity(Gravity.CENTER);
        return fragment;
    }
    @Override
    public int getRowCount() {
        return 2;
    }
    // Obtain the number of pages (horizontal)
    @Override
    public int getColumnCount(int rowNum) {
        if (rowNum ==1) {
            return cardorder.length;
        } else if (rowNum ==2) {
            return 1;
        }
        return 0;
    }
}
