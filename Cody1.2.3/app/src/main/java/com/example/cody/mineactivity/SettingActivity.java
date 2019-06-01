package com.example.cody.mineactivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cody.R;
import com.example.cody.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.logout)
    TextView logout;
    private SharedPreferencesUtil sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        sp = SharedPreferencesUtil.getInstance(getApplicationContext());
    }

    @OnClick({R.id.rl_about, R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_about:
                break;
            case R.id.logout:
                AlertDialog alertDialog = new AlertDialog.Builder(SettingActivity.this)
                        .setMessage("确定退出？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("Setting", "onClick: ");
                                sp.setLogin(false);
                                finish();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .create();
                alertDialog.show();
                break;
        }
    }
}
