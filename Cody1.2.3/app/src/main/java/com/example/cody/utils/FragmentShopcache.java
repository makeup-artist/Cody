package com.example.cody.utils;


import com.example.cody.fragment.shop.BaseShopFragment;
import com.example.cody.fragment.shop.BasemakeupFragment;
import com.example.cody.fragment.shop.EyeBrowFragment;
import com.example.cody.fragment.shop.HaircolorFragment;
import com.example.cody.fragment.shop.LensesFragment;
import com.example.cody.fragment.shop.LipstickFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentShopcache {
    public static Map<Integer, BaseShopFragment> sCaches = new HashMap<>();


    public static BaseShopFragment getFragment(int index){
        BaseShopFragment baseshopFragment = sCaches.get(index);
        if(baseshopFragment!=null){
            return baseshopFragment;
        }
        switch (index){
            case Constants.SHOP_LIPSTICK:
                baseshopFragment = new LipstickFragment();
                break;
            case Constants.SHOP_BASEMAKEUP:
                baseshopFragment = new BasemakeupFragment();
                break;
            case Constants.SHOP_EYEBROW:
                baseshopFragment = new EyeBrowFragment();
                break;
            case Constants.SHOP_LENSES:
                baseshopFragment = new LensesFragment();
                break;
            case Constants.SHOP_HAIRCOLOR:
                baseshopFragment = new HaircolorFragment();
                break;
        }
        sCaches.put(index,baseshopFragment);
        return  baseshopFragment;
    }

}
