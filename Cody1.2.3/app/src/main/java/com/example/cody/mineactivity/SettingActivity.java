package com.example.cody.mineactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cody.R;
import com.example.cody.entity.UserLoginBean.User;
import com.example.cody.utils.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.logout)
    TextView logout;
    @BindView(R.id.rl_phone)
    RelativeLayout rlPhone;
    @BindView(R.id.rl_modify)
    RelativeLayout rlModify;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    private SharedPreferencesUtil sp;
    private String mobile;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setting);
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        username = intent.getStringExtra("username");
        ButterKnife.bind(this);
        tvPhone.setText(mobile);
        sp = SharedPreferencesUtil.getInstance(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String returndata = data.getStringExtra("phone");
                    tvPhone.setText(returndata);
                }
                break;
        }
    }

    @OnClick({R.id.rl_about, R.id.logout, R.id.rl_modify, R.id.rl_phone})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.logout:
                AlertDialog alertDialog = new AlertDialog.Builder(SettingActivity.this)
                        .setMessage("确定退出？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("Setting", "onClick: ");
                                sp.setLogin(false);
                                sp.putUser(null);
                                EventBus.getDefault().post(new User());
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
            case R.id.rl_modify:
                intent = new Intent(view.getContext(), ChangedPsdActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.rl_phone:
                intent = new Intent(view.getContext(), ChangedNumberActivity.class);
                intent.putExtra("oldphone",mobile);
                startActivityForResult(intent,1);
                break;
        }
    }


}
