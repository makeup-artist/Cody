package com.example.cody.fragment.shop;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cody.R;

public class LipstickFragment  extends BaseShopFragment{
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {

        View rootview = layoutInflater.inflate(R.layout.fragment_shop_lipstick,container,false);
        return rootview;
    }
}
