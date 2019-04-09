package com.example.cody.utils;

import com.example.cody.R;
import com.example.cody.fragment.BaseFragment;
import com.example.cody.fragment.CommunityFragment;
import com.example.cody.fragment.HomeFragment;
import com.example.cody.fragment.MineFragment;
import com.example.cody.fragment.ShopFragment;

import java.util.HashMap;
import java.util.Map;



public class Fragmentcache {

    public static Map<Integer,BaseFragment> sCaches = new HashMap<>();


    public static BaseFragment getFragmentByPosition(int position){
        BaseFragment baseFragment = sCaches.get(position);
        if(baseFragment!=null){
            return baseFragment;
        }
        switch (position){
            case Constants.PAGER_HOME:
                baseFragment = new HomeFragment();
                break;
            case Constants.PAGER_COMMUNITY:
                baseFragment = new CommunityFragment();
                break;
            case Constants.PAGER_SHOP:
                baseFragment = new ShopFragment();
                break;
            case Constants.PAGER_MINE:
                baseFragment = new MineFragment();
                break;
        }

        sCaches.put(position,baseFragment);
        return  baseFragment;
    }
}
