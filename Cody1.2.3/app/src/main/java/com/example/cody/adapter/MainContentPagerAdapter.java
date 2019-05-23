package com.example.cody.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.example.cody.utils.Constants;
import com.example.cody.utils.Fragmentcache;

public class MainContentPagerAdapter extends FragmentPagerAdapter {
    public MainContentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return Fragmentcache.getFragmentByPosition(position);
    }

    @Override
    public int getCount() {
        return Constants.TAB_COUNT;
    }
}
