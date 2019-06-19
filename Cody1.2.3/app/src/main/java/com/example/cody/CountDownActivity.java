package com.example.cody;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.WindowManager;


import com.example.cody.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by SunnyDay on 2019/03/15
 */
public class CountDownActivity extends AppCompatActivity {
    @BindView(R.id.tv_count_down)
    AppCompatTextView countDownText;
    private CountDownTimer timer;
    private SharedPreferencesUtil sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        ButterKnife.bind(this);
        setNoTitleBarAndFullScreen();
        initCountDown();
        sp = SharedPreferencesUtil.getInstance(getApplicationContext());
    }



    /**
     * 倒计时逻辑处理
     */
    private void initCountDown() {
        // 避免内存泄漏
        if (!isFinishing()) {
            timer = new CountDownTimer(1000 * 6, 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    countDownText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkToJump();
                        }
                    });
                    int time = (int) millisUntilFinished;
                    countDownText.setText(time / 1000 + " 跳过");
                }

                @Override
                public void onFinish() {
                    checkToJump();
                }
            }.start();
        }
    }

    private void setNoTitleBarAndFullScreen() {
        // requestWindowFeature(Window.FEATURE_NO_TITLE); 此句必须在setContent之前
        getSupportActionBar().hide();// 标题栏的隐藏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, //全屏处理
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 首次进入引导页判断
     */
    private void checkToJump() {
        boolean isFirstin = sp.isFirst();
        if (isFirstin) {
            startActivity(new Intent(CountDownActivity.this, GuideActivity.class));
            sp.setFirst(false);
        } else {
            startActivity(new Intent(CountDownActivity.this, MainActivity.class));
        }
        finish();
    }

}
