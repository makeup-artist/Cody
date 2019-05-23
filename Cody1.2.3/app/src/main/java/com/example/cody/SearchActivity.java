package com.example.cody;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created By cyz on 2019/5/22 22:57
 * e-mail：462065470@qq.com
 */
public class SearchActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search);
    }
}
