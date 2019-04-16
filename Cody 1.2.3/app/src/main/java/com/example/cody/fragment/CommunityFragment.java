package com.example.cody.fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cody.R;
import com.example.cody.adapter.CommunityContentAdapter;
import com.example.cody.adapter.IndicatorCommunityAdapter;

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
    private IndicatorCommunityAdapter madapter;

    @Override
    protected void setSubListener() {
        madapter.setOnIndicatorTapClickListener(new IndicatorCommunityAdapter.OnIndicatorTapClickListener() {
            @Override
            public void OnTabClick(int index) {
                if (communityContentPager != null) {
                    communityContentPager.setCurrentItem(index);
                }
            }
        });

    }

    @Override
    protected View getSubView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    public void initView() {
        communityIndicator.setBackgroundResource(R.drawable.round_indicator_bg);
        //Create the adapter of the shopadapter
        madapter = new IndicatorCommunityAdapter(getContext());
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(madapter);
        //viewpager和indicator绑定
        communityIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(communityIndicator, communityContentPager);
        //shopContentPager的适配器
        //FragmentManager supportFragmentAdapter= getChildFragmentManager();
        CommunityContentAdapter communityContentAdapter = new CommunityContentAdapter(getChildFragmentManager());
        communityContentPager.setAdapter(communityContentAdapter);
        //把viewpager和indicator绑在一起
        ViewPagerHelper.bind(communityIndicator, communityContentPager);
        communityContentPager.setCurrentItem(0);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}