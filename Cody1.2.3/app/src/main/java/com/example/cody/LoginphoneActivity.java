package com.example.cody;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cody.entity.MsgBean;
import com.example.cody.entity.UserLoginBean.UsermainBean;
import com.example.cody.net.CommonOkHttpClient;
import com.example.cody.net.listener.DisposeDataHandle;
import com.example.cody.net.listener.DisposeDataListener;
import com.example.cody.net.request.CommonRequest;
import com.example.cody.net.request.RequestParams;
import com.example.cody.net.response.CommonJsonCallback;
import com.example.cody.utils.RegularVerification;
import com.example.cody.utils.SharedPreferencesUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cody.utils.Constants.URL_LOGINBYNUMBER;
import static com.example.cody.utils.Constants.URL_REGISTER;
import static com.example.cody.utils.Constants.URL_SENDSMS;

/**
 * Created By cyz on 2019/5/30 21:28
 * e-mail：462065470@qq.com
 */
public class LoginphoneActivity extends AppCompatActivity {
    @BindView(R.id.number)
    EditText mnumber;
    @BindView(R.id.SMScode)
    EditText mSMScode;
    @BindView(R.id.SendSMS)
    TextView mSendSMS;
    @BindView(R.id.login)
    Button mlogin;
    String number;
    String SMScode;
    private SharedPreferencesUtil sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_loginphone);
        ButterKnife.bind(this);
        sp = SharedPreferencesUtil.getInstance(getApplicationContext());
    }

    @OnClick({R.id.SendSMS, R.id.login})
    public void onViewClicked(View view) {
        number = mnumber.getText().toString();
        SMScode = mSMScode.getText().toString();
        Map params = new HashMap();
        switch (view.getId()) {
            case R.id.SendSMS:
                if (checkUserNumber()) {
                    params.put("mobile",number);
                    CommonOkHttpClient
                            .sendRequest(CommonRequest.createGetRequest(URL_SENDSMS, new RequestParams(params)), new CommonJsonCallback(new DisposeDataHandle(MsgBean.class, new DisposeDataListener() {
                                @Override
                                public void onSuccess(Object responseObj) {
                                    MsgBean testBean = (MsgBean) responseObj;
                                    if(testBean.getCode()==200){
                                        Toast.makeText(MyApplication.getContext(), "验证码已成功发送", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(MyApplication.getContext(), "发送失败", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Object reasonObj) {
                                    Toast.makeText(MyApplication.getContext(), "网络请求错误了QAQ", Toast.LENGTH_SHORT).show();
                                }
                            })));
                }
                break;
            case R.id.login:
                if (checkUserInfo()) {
                    params.put("mobile",number);
                    params.put("code",SMScode);
                    CommonOkHttpClient
                            .sendRequest(CommonRequest.createPostRequest(URL_LOGINBYNUMBER, new RequestParams(params)), new CommonJsonCallback(new DisposeDataHandle(UsermainBean.class, new DisposeDataListener() {
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
                                    Toast.makeText(MyApplication.getContext(), "网络请求错误了QAQ", Toast.LENGTH_SHORT).show();
                                }
                            })));
                }
                break;
        }
    }

    private boolean checkUserNumber(){
        number = mnumber.getText().toString();
        boolean isPass = true;
        number = mnumber.getText().toString();

        if (number.isEmpty() ||!RegularVerification.isMobile(number)) {
            mnumber.setError("请输入正确的手机号码");
            isPass = false;
        }

        return isPass;
    }

    private boolean checkUserInfo(){
        boolean isPass = true;
        number = mnumber.getText().toString();
        SMScode = mSMScode.getText().toString();
        if (number.isEmpty() ||!RegularVerification.isMobile(number)) {
            mnumber.setError("请输入正确的手机号码");
            isPass = false;
        }
        if (SMScode.isEmpty()) {
            isPass = false;
            Toast.makeText(MyApplication.getContext(), "验证码怎么不填！", Toast.LENGTH_SHORT).show();
        }
        return isPass;
    }

}
