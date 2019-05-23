package com.example.cody.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.cody.utils.Constants;
import com.example.cody.utils.FragmentCommunitycache;


public class CommunityContentAdapter extends FragmentPagerAdapter {
    public CommunityContentAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("CommunityContentAdapter", "getFragment: ");
        return FragmentCommunitycache.getFragment(position);
    }

    @Override
    public int getCount() {
        return Constants.TAB_COMMUNITY_COUNT;
    }
}
