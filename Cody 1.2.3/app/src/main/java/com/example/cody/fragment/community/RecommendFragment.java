package com.example.cody.fragment.community;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cody.R;

public class RecommendFragment extends BaseCommunityFragment{
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootview = layoutInflater.inflate(R.layout.fragment_community_recommend,container,false);
        return rootview;
    }
}
