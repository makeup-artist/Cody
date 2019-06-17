package com.example.cody.fragment;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cody.R;
import com.example.cody.adapter.SortButtonAdapter;
import com.example.cody.base.RvSimpleAdapter;
import com.example.cody.entity.BannerBean;
import com.example.cody.entity.ItemBean;
import com.example.cody.entity.LipstickBean;
import com.example.cody.listener.OnComicClickListener;
import com.example.cody.listener.OnScrollYChangeForAlphaListener;
import com.example.cody.model.ButtonModel;
import com.example.cody.utils.ScreenUtil;
import com.example.cody.views.ListenOffsetYNestedScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fj.mtsortbutton.lib.Interface.ViewControl;
import fj.mtsortbutton.lib.SoreButton;

public class HomeFragment extends BaseFragment implements ViewControl {
    @BindView(R.id.tv_search)
    TextView tvSearch;
    Unbinder unbinder;
    @BindView(R.id.soreButton)
    SoreButton soreButton;
    @BindView(R.id.refreshLayout_fragment_home)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder1;
    @BindView(R.id.scrollView_fragment_home)
    ListenOffsetYNestedScrollView mScrollView;
    @BindView(R.id.cl_top_fragment_home)
    ConstraintLayout mClTopLayout;
    @BindView(R.id.banner_fragment_home)
    Banner mBanner;
    @BindView(R.id.ll_scroll_inner_fragment_home)
    LinearLayout mLLScrollContainer;
    private Context context;
    private List<Integer> list;

    @Override
    protected void setSubListener() {

    }



    @Override
    protected View getSubView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        showdata();
        return rootView;
    }

    private void showdata() {
        List<BannerBean> bannerList = new ArrayList<BannerBean>();
        BannerBean banner1 = new BannerBean("持久唇妆点亮你",R.drawable.banner_1);
        BannerBean banner2 = new BannerBean("高亮彩妆美无禁忌",R.drawable.banner_2);
        BannerBean banner3 = new BannerBean("轻吻经典快乐玩色",R.drawable.banner_3);
        BannerBean banner4 = new BannerBean("高定美妆美无禁忌",R.drawable.banner_4);
        BannerBean banner5 = new BannerBean("一抹上色唇色持久",R.drawable.banner_5);
        bannerList.add(banner1);
        bannerList.add(banner2);
        bannerList.add(banner3);
        bannerList.add(banner4);
        bannerList.add(banner5);
        if (mLLScrollContainer.getChildCount() > 2){
            mLLScrollContainer.removeViews(2,mLLScrollContainer.getChildCount()-2);
        }
        initBanner(bannerList);
        List<ItemBean> itemBeanList = new ArrayList<ItemBean>();
        List<LipstickBean> lipsticklist = new ArrayList<LipstickBean>();
        LipstickBean listick1 = new LipstickBean("DIOR迪奥变色唇膏","https://www.dior.cn/zh_cn/products/beauty-Y0027010-dior%E8%BF%AA%E5%A5%A5%E5%8F%98%E8%89%B2%E5%94%87%E8%86%8F-%E7%84%95%E7%8E%B0%E8%87%AA%E7%84%B6%E5%94%87%E8%89%B2","001 Pink","￥295.00","外出");
        LipstickBean listick2 = new LipstickBean("DIOR迪奥变色唇膏","https://www.dior.cn/zh_cn/products/beauty-Y0027010-dior%E8%BF%AA%E5%A5%A5%E5%8F%98%E8%89%B2%E5%94%87%E8%86%8F-%E7%84%95%E7%8E%B0%E8%87%AA%E7%84%B6%E5%94%87%E8%89%B2","007 Raspberry","￥295.00","舞会");
        LipstickBean listick3 = new LipstickBean("DIOR迪奥魅惑磨砂美唇膏","https://www.dior.cn/zh_cn/products/beauty-Y0028880-dior%E8%BF%AA%E5%A5%A5%E9%AD%85%E6%83%91%E7%A3%A8%E7%A0%82%E7%BE%8E%E5%94%87%E8%86%8F-%E8%BD%BB%E8%BD%BB%E4%B8%80%E6%8A%BF%EF%BC%8C%E6%9F%94%E5%94%87%E7%84%95%E7%8E%B0","001 Pink","￥295.00","学生");
        LipstickBean listick4 = new LipstickBean("DIOR迪奥魅惑釉唇膏","https://www.dior.cn/zh_cn/products/beauty-Y0028808-dior%E8%BF%AA%E5%A5%A5%E9%AD%85%E6%83%91%E9%87%89%E5%94%87%E8%86%8F-%E6%B5%93%E9%83%81%E6%BC%86%E5%85%89%EF%BC%8C%E8%BD%BB%E7%9B%88%E6%8C%81%E5%A6%86","877 给我Dior","￥315.00","魅惑");
        LipstickBean listick5 = new LipstickBean("DIOR迪奥魅惑釉唇膏","https://www.dior.cn/zh_cn/products/beauty-Y0028808-dior%E8%BF%AA%E5%A5%A5%E9%AD%85%E6%83%91%E9%87%89%E5%94%87%E8%86%8F-%E6%B5%93%E9%83%81%E6%BC%86%E5%85%89%EF%BC%8C%E8%BD%BB%E7%9B%88%E6%8C%81%E5%A6%86","648 人生赢家","￥315.00","活动");
        LipstickBean listick6 = new LipstickBean("烈艳蓝金唇膏限量版","https://www.dior.cn/zh_cn/products/beauty-Y0088003-%E7%83%88%E8%89%B3%E8%93%9D%E9%87%91%E5%94%87%E8%86%8F-%E9%99%90%E9%87%8F%E7%89%88-%E9%AB%98%E8%AE%A2%E8%89%B2%E6%B3%BD%EF%BC%8C%E8%88%92%E6%82%A6%E6%8C%81%E8%89%B2-%E9%99%90%E9%87%8F%E7%89%88","551 Joyeuse","￥315.00","日常");
        lipsticklist.add(listick1);
        lipsticklist.add(listick2);
        lipsticklist.add(listick3);
        lipsticklist.add(listick4);
        lipsticklist.add(listick5);
        lipsticklist.add(listick6);
        itemBeanList.add(new ItemBean("推荐口红","独家精品私人定制","https://www.dior.cn/zh_cn/products/beauty-Y0027010-dior%E8%BF%AA%E5%A5%A5%E5%8F%98%E8%89%B2%E5%94%87%E8%86%8F-%E7%84%95%E7%8E%B0%E8%87%AA%E7%84%B6%E5%94%87%E8%89%B2",lipsticklist,R.drawable.g_like));
        addAllComicItem(itemBeanList);
        mRefreshLayout.finishRefresh();
    }

    /**
     * 设置标题栏及其内容透明度
     * 0:完全透明
     * 255:完全不透明
     * @param alpha
     */
    public void setTopLayoutTransparency(int alpha){
        mClTopLayout.getBackground().mutate().setAlpha(alpha);
        float scale = alpha/255f;
    }
    private void initView() {
        // 200dp为banner高度,75dp为标题栏高度
        mScrollView.setOffsetDistance(ScreenUtil.dip2px(getActivity(),200-75));
        mScrollView.setOnScrollYChangeForAlphaListener(new OnScrollYChangeForAlphaListener() {
            @Override
            public void changeTopLayoutTransport(int alpha) {
                setTopLayoutTransparency(alpha);
            }
        });
        setTopLayoutTransparency(0);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
//                mPresenter.getHomePageData();
            }
        });
        soreButton.setViewControl(this);
        list = new ArrayList<>();
        list.add(R.layout.viewpager_page);
        list.add(R.layout.viewpager_page);
        soreButton.setView(list).init();
    }
    /**
     * 初始化Banner
     * @param bannerList
     */
    private void initBanner(final List<BannerBean> bannerList) {
        List<Integer> imageUrlList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        for (BannerBean bannerBean : bannerList) {
            imageUrlList.add(bannerBean.getImageUrl());
            titleList.add(bannerBean.getTitle());
        }
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        mBanner.setImages(imageUrlList);
        mBanner.setBannerTitles(titleList);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
            }
        });
        mBanner.start();
    }
    @Override
    public void setView(View view, int i) {
        switch (i) {
            case 0://第一个界面
                GridView gridView = (GridView) view.findViewById(R.id.gridView);
                SortButtonAdapter adapter = new SortButtonAdapter(getContext(), setData());
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(context, "第" + id + "页" + position, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 1://第二个界面
                GridView gridView2 = (GridView) view.findViewById(R.id.gridView);
                SortButtonAdapter adapter2 = new SortButtonAdapter(getContext(), setData2());
                gridView2.setAdapter(adapter2);
                gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(context, "第" + id + "页" + position, Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }

    private List<ButtonModel> setData() {
        List<ButtonModel> data = new ArrayList<>();
        ButtonModel buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_1);
        buttonModel.setName("洋码头");
        data.add(buttonModel);

        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_2);
        buttonModel.setName("biyabi海淘");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_3);
        buttonModel.setName("蘑菇街");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_4);
        buttonModel.setName("京东");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_5);
        buttonModel.setName("快美妆");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_6);
        buttonModel.setName("淘宝");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_7);
        buttonModel.setName("唯品会");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_8);
        buttonModel.setName("豌豆公主");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_9);
        buttonModel.setName("小红书");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_10);
        buttonModel.setName("闲鱼");
        data.add(buttonModel);
        return data;
    }

    private List<ButtonModel> setData2() {
        List<ButtonModel> data = new ArrayList<>();
        ButtonModel buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_11);
        buttonModel.setName("品质酒店");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_12);
        buttonModel.setName("生活服务");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_13);
        buttonModel.setName("足疗按摩");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_14);
        buttonModel.setName("母婴亲子");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_15);
        buttonModel.setName("结婚");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_16);
        buttonModel.setName("景点");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_17);
        buttonModel.setName("温泉");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_18);
        buttonModel.setName("学习培训");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_19);
        buttonModel.setName("洗浴/汗蒸");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_20);
        buttonModel.setName("全部分类");
        data.add(buttonModel);
        return data;
    }


    /**
     * 添加分类漫画项
     * @param itemBeanList
     */
    private void addAllComicItem(List<ItemBean> itemBeanList) {
        for (ItemBean bean : itemBeanList) {
            //初始化View
            View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.cell_item,null);
            ImageView ivIcon = rootView.findViewById(R.id.iv_icon_cell_item);
            TextView tvTitle = rootView.findViewById(R.id.tv_title_cell_item);
            TextView tvIntro = rootView.findViewById(R.id.tv_intro_cell_item);
            TextView tvMore = rootView.findViewById(R.id.tv_more_cell_item);
            RecyclerView rvComicList = rootView.findViewById(R.id.rv_book_cell_item);
            //赋值
            Glide.with(getActivity()).load(bean.getIconResId()).into(ivIcon);
            tvTitle.setText(bean.getTitle());
            tvIntro.setText(bean.getIntro());
            rvComicList.setLayoutManager(new LinearLayoutManager(
                    getActivity(),
                    LinearLayoutManager.HORIZONTAL,
                    false));
            RvSimpleAdapter rvSimpleAdapter = new RvSimpleAdapter();
            rvComicList.setAdapter(rvSimpleAdapter);
            rvSimpleAdapter.addAll(bean.getComicCellList(new OnComicClickListener() {
                @Override
                public void onClick(String comicId) {
                }
            }));
//            添加进布局
            mLLScrollContainer.addView(rootView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}
