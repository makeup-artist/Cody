package com.example.cody.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cody.R;
import com.example.cody.adapter.CommunityContentAdapter;
import com.example.cody.adapter.IndicatorCommunityAdapter;
import com.example.cody.adapter.IndicatorShopAdapter;
import com.example.cody.adapter.ShopContentAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CommunityFragment extends BaseFragment {
    @BindView(R.id.community_indicator)
    MagicIndicator communityIndicator;
    @BindView(R.id.community_content_pager)
    ViewPager communityContentPager;
    Unbinder unbinder;

    @Override
    protected void setSubListener() {

    }

    @Override
    protected View getSubView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        communityIndicator.setBackgroundResource(R.drawable.round_indicator_bg);
        //Create the adapter of the shopadapter
        IndicatorCommunityAdapter adapter = new IndicatorCommunityAdapter(getContext());
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(adapter);
        //viewpager和indicator绑定
        communityIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(communityIndicator,communityContentPager);
        //shopContentPager的适配器
//        FragmentManager supportFragmentAdapter= getChildFragmentManager();
        CommunityContentAdapter communityContentAdapter = new CommunityContentAdapter(getChildFragmentManager());
        communityContentPager.setAdapter(communityContentAdapter);
        //把viewpager和indicator绑在一起
        ViewPagerHelper.bind(communityIndicator,communityContentPager);
        communityContentPager.setCurrentItem(0);
        return rootView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}