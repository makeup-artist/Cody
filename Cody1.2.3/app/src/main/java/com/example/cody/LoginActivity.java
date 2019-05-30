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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cody.utils.Constants.URL_LOGIN;
import static com.example.cody.utils.Constants.URL_SENDSMS;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.ignore_psd)
    TextView ignorePsd;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ignore_psd, R.id.register,R.id.login})
    public void onViewClicked(View view) {
        password = mpassword.getText().toString();
        username = musername.getText().toString();
        Map params = new HashMap();
        switch (view.getId()) {
            case R.id.ignore_psd:
                break;
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login:
                params.put("username",username);
                params.put("password",password);
                CommonOkHttpClient
                        .sendRequest(CommonRequest.createPostRequest(URL_LOGIN, new RequestParams(params)), new CommonJsonCallback(new DisposeDataHandle(MsgBean.class, new DisposeDataListener() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                Log.i("Login",responseObj.toString());
//                                UsermainBean testBean = (UsermainBean) responseObj;
//                                Log.i("RegitserActivity", "onSuccess: "+testBean.getCode());
//                                if(testBean.getCode()==200){
//                                    Toast.makeText(MyApplication.getContext(), "已成功登陆", Toast.LENGTH_SHORT).show();
//                                }else{
//                                    Toast.makeText(MyApplication.getContext(), "发送失败", Toast.LENGTH_SHORT).show();
//                                }
                            }

                            @Override
                            public void onFailure(Object reasonObj) {
                                OkHttpException testBean = (OkHttpException) reasonObj;
                                Log.i("RegitserActivity", "onFFF: " +testBean.getEmsg());
                                Toast.makeText(MyApplication.getContext(), "网络请求错误了QAQ", Toast.LENGTH_SHORT).show();
                            }
                        })));
                break;
        }
    }
}
