package com.example.cody.fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cody.R;
import com.example.cody.adapter.IndicatorShopAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShopFragment extends BaseFragment {
    @BindView(R.id.shop_indicator)
    MagicIndicator shopIndicator;
    Unbinder unbinder;
    @BindView(R.id.shop_content_pager)
    ViewPager shopContentPager;


    @Override
    protected void setSubListener() {

    }

    @Override
    protected View getSubView(LayoutInflater inflater, ViewGroup container) {

        View rootView = inflater.inflate(R.layout.fragment_shop, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        shopIndicator.setBackgroundColor(this.getResources().getColor(R.color.main_color));
        //Create the adapter of the shopadapter
        IndicatorShopAdapter adapter = new IndicatorShopAdapter(rootView.getContext());
        CommonNavigator commonNavigator = new CommonNavigator(rootView.getContext());
        commonNavigator.setAdapter(adapter);
        //viewpager和indicator绑定
        shopIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(shopIndicator,shopContentPager);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}