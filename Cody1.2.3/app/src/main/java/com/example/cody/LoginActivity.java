package com.example.cody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cody.entity.MsgBean;
import com.example.cody.entity.UserLoginBean.UsermainBean;
import com.example.cody.net.CommonOkHttpClient;
import com.example.cody.net.exception.OkHttpException;
import com.example.cody.net.listener.DisposeDataHandle;
import com.example.cody.net.listener.DisposeDataListener;
import com.example.cody.net.request.CommonRequest;
import com.example.cody.net.request.RequestParams;
import com.example.cody.net.response.CommonJsonCallback;
import com.example.cody.utils.SharedPreferencesUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cody.utils.Constants.URL_LOGIN;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.ignore_psd)
    TextView mignorePsd;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.username)
    EditText musername;
    @BindView(R.id.password)
    EditText mpassword;
    String username;
    String password;
    private SharedPreferencesUtil sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sp = SharedPreferencesUtil.getInstance(getApplicationContext());
    }

    @OnClick({R.id.ignore_psd, R.id.register,R.id.login})
    public void onViewClicked(View view) {
        password = mpassword.getText().toString();
        username = musername.getText().toString();
        Map params = new HashMap();
        switch (view.getId()) {
            case R.id.ignore_psd:
                startActivity(new Intent(this, LoginphoneActivity.class));
                break;
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login:
                params.put("username",username);
                params.put("password",password);
                CommonOkHttpClient
                        .sendRequest(CommonRequest.createPostRequest(URL_LOGIN, new RequestParams(params)), new CommonJsonCallback(new DisposeDataHandle(UsermainBean.class, new DisposeDataListener() {
                            @Override
                            public void onSuccess(Object responseObj) {
                             UsermainBean testBean = (UsermainBean) responseObj;
                               if(testBean.getCode()==200){
                                   Gson gson = new Gson();
                                   sp.putUser(testBean.getData().getUser());
                                    Toast.makeText(MyApplication.getContext(), "已成功登陆", Toast.LENGTH_SHORT).show();
                          sp.setLogin(true);
                                    finish();
                           }else{
                                  Toast.makeText(MyApplication.getContext(), "登陆失败", Toast.LENGTH_SHORT).show();
                               }
                            }

                            @Override
                            public void onFailure(Object reasonObj) {
                                OkHttpException testBean = (OkHttpException) reasonObj;
                                Toast.makeText(MyApplication.getContext(), "网络请求错误了QAQ", Toast.LENGTH_SHORT).show();
                            }
                        })));
                break;
        }
    }
}
