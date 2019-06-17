package com.example.cody;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cody.entity.UserLoginBean.User;
import com.example.cody.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created By cyz on 2019/6/12 22:06
 * e-mail：462065470@qq.com
 */
public class ShowImageActivity extends AppCompatActivity {
    public static final String POINTX = "POINTX";
    public static final String POINTY = "POINTY";
    private User user;
    private SharedPreferencesUtil sp;
    @BindView(R.id.iv_img)
    ImageView ivImg;

    private float pointx;
    private float pointy;
    private float pointXValue;
    private float pointYValue;
private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimage);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        sp = SharedPreferencesUtil.getInstance(getApplicationContext());
        url = sp.getUser().getPicture();
        if(url!=null){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.g_default_avatar);
            Glide.with(ivImg.getContext()).load("https://"+url).apply(requestOptions).into(ivImg);
        }else{
            Glide.with(ivImg.getContext()).load(R.drawable.g_default_avatar).into(ivImg);
        }
        pointx = getIntent().getIntExtra(POINTX, 0);
        pointy = getIntent().getIntExtra(POINTY, 0);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        if (pointx == 0) {

            pointXValue = 0.5f;
        } else {
            pointXValue = pointx / metrics.widthPixels;

        }
        if (pointy == 0) {
            pointYValue = 0.5f;
        } else {
            pointYValue = pointy / metrics.heightPixels;
        }

        Log.e("cheng", "pointXValue=" + pointXValue + ",pointYValue=" + pointYValue);


        AnimationSet animationSet = new AnimationSet(true);


        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f, ScaleAnimation.RELATIVE_TO_PARENT,
                pointXValue, ScaleAnimation.RELATIVE_TO_PARENT, pointYValue);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        animationSet.setDuration(500);
        animationSet.setFillAfter(true);
        ivImg.startAnimation(animationSet);
    }


    @Override
    public void onBackPressed() {

        AnimationSet animationSet = new AnimationSet(true);


        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0, 1f, 0, ScaleAnimation.RELATIVE_TO_PARENT,
                pointXValue, ScaleAnimation.RELATIVE_TO_PARENT, pointYValue);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        animationSet.setDuration(500);
        animationSet.setFillAfter(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ShowImageActivity.super.onBackPressed();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ivImg.startAnimation(animationSet);

    }
}
