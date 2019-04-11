package com.example.cody.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.cody.utils.Constants;
import com.example.cody.utils.FragmentShopcache;

public class ShopContentAdapter extends FragmentPagerAdapter {
    public ShopContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentShopcache.getFragment(position);
    }

    @Override
    public int getCount() {
        return Constants.TAB_SHOP_COUNT;
    }
}
