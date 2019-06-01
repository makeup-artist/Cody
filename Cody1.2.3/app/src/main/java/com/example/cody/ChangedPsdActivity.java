package com.example.cody;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created By cyz on 2019/5/31 21:12
 * e-mail：462065470@qq.com
 */
public class ChangedPsdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_changedpsd);
        ButterKnife.bind(this);
    }
}
