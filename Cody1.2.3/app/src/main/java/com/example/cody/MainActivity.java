package com.example.cody;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cgfay.filterlibrary.glfilter.resource.FilterHelper;
import com.cgfay.filterlibrary.glfilter.resource.MakeupHelper;
import com.cgfay.filterlibrary.glfilter.resource.ResourceHelper;
import com.cgfay.utilslibrary.utils.PermissionUtils;
import com.example.cody.adapter.MainContentPagerAdapter;
import com.example.cody.utils.Constants;
import com.example.cody.views.NoScrollViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    private static final int REQUEST_CODE = 0;


    @BindView(R.id.tab_main)
    RadioGroup tabMain;
    @BindView(R.id.tab_home)
    RadioButton tabHome;
    @BindView(R.id.tab_community)
    RadioButton tabCommunity;
    @BindView(R.id.tab_photo)
    View tabPhoto;
    @BindView(R.id.tab_shop)
    RadioButton tabShop;
    @BindView(R.id.tab_mine)
    RadioButton tabMine;
    @BindView(R.id.content_pager)
    NoScrollViewPager contentPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d("Mainactivty","73333 ");
        checkPermissions();
        initView();
        initListener();
        initResources();
    }


    private void checkPermissions() {
        boolean cameraEnable = PermissionUtils.permissionChecking(this,
                Manifest.permission.CAMERA);
        boolean storageWriteEnable = PermissionUtils.permissionChecking(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean recordAudio = PermissionUtils.permissionChecking(this,
                Manifest.permission.RECORD_AUDIO);
        if (!cameraEnable || !storageWriteEnable || !recordAudio) {
            ActivityCompat.requestPermissions(this,
                    new String[] {
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO
                    }, REQUEST_CODE);
        }
    }
    private void initListener() {
        contentPager.addOnPageChangeListener(this);
    }

    private void initView() {
        FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        MainContentPagerAdapter fragmentAdapter = new MainContentPagerAdapter(supportFragmentManager);
        contentPager.setAdapter(fragmentAdapter);
        tabMain.check(R.id.tab_home);
    }
    /**
     * 初始化动态贴纸、滤镜等资源
     */
    private void initResources() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResourceHelper.initAssetsResource(MainActivity.this);
                FilterHelper.initAssetsFilter(MainActivity.this);
                MakeupHelper.initAssetsMakeup(MainActivity.this);
            }
        }).start();
    }

    @OnClick({R.id.tab_home, R.id.tab_community, R.id.tab_shop, R.id.tab_mine})
    public void onViewClicked(View view) {
        Drawable drawablehome =getResources().getDrawable(R.drawable.anim_main_view_home);
        AnimationDrawable animationDrawablehome =(AnimationDrawable)drawablehome;
        animationDrawablehome.setBounds(0,0,animationDrawablehome.getMinimumWidth(),animationDrawablehome.getMinimumHeight());
        tabHome.setCompoundDrawables(null,animationDrawablehome,null,null);

        Drawable drawablecommunity =getResources().getDrawable(R.drawable.anim_main_view_community);
        AnimationDrawable animationDrawablecommunity =(AnimationDrawable)drawablecommunity;
        animationDrawablecommunity.setBounds(0,0,animationDrawablecommunity.getMinimumWidth(),animationDrawablecommunity.getMinimumHeight());
        tabCommunity.setCompoundDrawables(null,animationDrawablecommunity,null,null);

        Drawable drawableshop =getResources().getDrawable(R.drawable.anim_main_view_shop);
        AnimationDrawable animationDrawableshop =(AnimationDrawable)drawableshop;
        animationDrawableshop.setBounds(0,0,animationDrawableshop.getMinimumWidth(),animationDrawableshop.getMinimumHeight());
        tabShop.setCompoundDrawables(null,animationDrawableshop,null,null);

        Drawable drawablemine =getResources().getDrawable(R.drawable.anim_main_view_mine);
        AnimationDrawable animationDrawablemine =(AnimationDrawable)drawablemine;
        animationDrawablemine.setBounds(0,0,animationDrawablemine.getMinimumWidth(),animationDrawablemine.getMinimumHeight());
        tabMine.setCompoundDrawables(null,animationDrawablemine,null,null);
        switch (view.getId()) {

            case R.id.tab_home:
                contentPager.setCurrentItem(Constants.PAGER_HOME);
                animationDrawablehome.start();
                break;
            case R.id.tab_community:
                contentPager.setCurrentItem(Constants.PAGER_COMMUNITY);
                animationDrawablecommunity.start();
                break;
            case R.id.tab_shop:
                contentPager.setCurrentItem(Constants.PAGER_SHOP);
                animationDrawableshop.start();
                break;
            case R.id.tab_mine:
                contentPager.setCurrentItem(Constants.PAGER_MINE);
                animationDrawablemine.start();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case Constants.PAGER_HOME:
                tabMain.check(R.id.tab_home);
                break;
            case Constants.PAGER_COMMUNITY:
                tabMain.check(R.id.tab_community);
                break;
            case Constants.PAGER_SHOP:
                tabMain.check(R.id.tab_shop);
                break;
            case Constants.PAGER_MINE:
                tabMain.check(R.id.tab_mine);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.tab_photo)
    public void onViewClicked() {
                startActivity(new Intent(this, StartActivity.class));
                //previewCamera();
        }

}
