package com.example.jenny.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.List;

/**
 * Created by Jenny on 3/4/2016.
 */
public class PagerAdapter extends FragmentGridPagerAdapter {
    private final Context mContext;
    private List mrows;
    public PagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;

    }
    private static class Page {
        // static resources
        int nameRes;
        int partyRes;
        int iconRes;
    }
    private final Page[][] PAGES = new Page[2][3];
    @Override
    public Fragment getFragment(int row, int col) {
        Page page = PAGES[row][col];
        String name =
                page.nameRes != 0 ? mContext.getString(page.nameRes) : null;
        String party =
                page.partyRes != 0 ? mContext.getString(page.partyRes) : null;
        CardFragment fragment = CardFragment.create(name, party, page.iconRes);

        // Advanced settings (card gravity, card expansion/scrolling)

        return fragment;
    }
    @Override
    public int getRowCount() {
        return PAGES.length;
    }
    // Obtain the number of pages (horizontal)
    @Override
    public int getColumnCount(int rowNum) {
        return PAGES[rowNum].length;
    }
}
