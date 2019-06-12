package com.example.cody.mineactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cody.R;

import butterknife.ButterKnife;

/**
 * Created By cyz on 2019/6/1 12:03
 * e-mail：462065470@qq.com
 */
public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }
}
