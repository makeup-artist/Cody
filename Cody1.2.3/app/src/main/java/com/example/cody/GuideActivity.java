package com.example.cody;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.WindowManager;

import com.example.cody.utils.SharedPreferencesUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GuideActivity extends AppCompatActivity {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.btn_start)
    AppCompatTextView btnStart;
    private List<Integer> images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        setNoTitleBarAndFullScreen();
        initBannerData();
        initViews();
    }

    private void initViews() {
        banner.setImageLoader(new ModelImageLoader())
                .setImages(images)
                .isAutoPlay(false)
                .start();
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i == images.size() - 1) {
                    btnStart.setVisibility(View.VISIBLE);
                    btnStart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(GuideActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                } else {
                    btnStart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void initBannerData() {
        images = new ArrayList<>();
        images.add(R.drawable.launcher_01);
        images.add(R.drawable.launcher_02);
        images.add(R.drawable.launcher_03);
        images.add(R.drawable.launcher_04);
        images.add(R.drawable.launcher_05);

    }

    private void setNoTitleBarAndFullScreen() {
        // requestWindowFeature(Window.FEATURE_NO_TITLE); 此句必须在setContent之前
        getSupportActionBar().hide();// 标题栏的隐藏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, //全屏处理
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
