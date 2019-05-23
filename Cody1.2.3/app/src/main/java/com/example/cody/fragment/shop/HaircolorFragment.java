package com.example.cody.fragment.shop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cody.R;

public class HaircolorFragment extends BaseShopFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootview = layoutInflater.inflate(R.layout.fragment_shop_haircolor, container, false);
        return rootview;
    }
}