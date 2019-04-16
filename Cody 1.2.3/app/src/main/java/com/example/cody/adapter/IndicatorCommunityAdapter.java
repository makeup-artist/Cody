package com.example.cody.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.cody.R;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;


public class IndicatorCommunityAdapter  extends CommonNavigatorAdapter {
    private final String[] Titles;
    private OnIndicatorTapClickListener mOnTabClickListener;

    public IndicatorCommunityAdapter(Context context) {
        Titles=context.getResources().getStringArray(R.array.indicatorcommunity_name);
    }
    @Override
    public int getCount() {
        if (Titles != null) {
            return Titles.length;
        }
        return 0;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
        clipPagerTitleView.setText(Titles[index]);
        clipPagerTitleView.setTextColor(Color.parseColor("#e94220"));
        clipPagerTitleView.setClipColor(Color.WHITE);
        clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //                mViewPager.setCurrentItem(index);
                if (mOnTabClickListener != null) {
                    mOnTabClickListener.OnTabClick(index);
                }
            }
        });
        return clipPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        float navigatorHeight = context.getResources().getDimension(R.dimen.common_navigator_height);
        float borderWidth = UIUtil.dip2px(context, 1);
        float lineHeight = navigatorHeight - 2 * borderWidth;
        indicator.setLineHeight(lineHeight);
        indicator.setRoundRadius(lineHeight / 2);
        indicator.setYOffset(borderWidth);
        indicator.setColors(Color.parseColor("#bc2a2a"));
        return indicator;
    }

    public void setOnIndicatorTapClickListener(OnIndicatorTapClickListener listener) {

        this.mOnTabClickListener = listener;
    }


    public interface OnIndicatorTapClickListener {

        void OnTabClick(int index);
    }


}
