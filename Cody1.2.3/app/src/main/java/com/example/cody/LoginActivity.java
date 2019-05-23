package com.example.cody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.ignore_psd)
    TextView ignorePsd;
    @BindView(R.id.register)
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ignore_psd, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ignore_psd:
                break;
            case R.id.register:
        startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
