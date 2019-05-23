package com.example.cody.utils;

import android.util.Log;

import com.example.cody.fragment.community.AttentionFragment;
import com.example.cody.fragment.community.BaseCommunityFragment;
import com.example.cody.fragment.community.CourseFragment;
import com.example.cody.fragment.community.RecommendFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentCommunitycache {

    public static Map<Integer, BaseCommunityFragment> sCaches = new HashMap<>();


    public static BaseCommunityFragment getFragment(int index){
        BaseCommunityFragment basecommunityFragment = sCaches.get(index);
        if(basecommunityFragment!=null){
            return basecommunityFragment;
        }
        switch (index){
            case Constants.COMMUNITY_ATTENTION:
                basecommunityFragment = new AttentionFragment();
                break;
            case Constants.COMMUNITY_RECOMMAND:
                basecommunityFragment = new RecommendFragment();
                break;
            case Constants.COMMUNITY_COURSE:
                basecommunityFragment = new CourseFragment();
                break;
        }

        sCaches.put(index,basecommunityFragment);
        return  basecommunityFragment;
    }
}
