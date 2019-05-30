package com.example.cody;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cody.entity.MsgBean;
import com.example.cody.net.CommonOkHttpClient;
import com.example.cody.net.listener.DisposeDataHandle;
import com.example.cody.net.listener.DisposeDataListener;
import com.example.cody.net.request.CommonRequest;
import com.example.cody.net.request.RequestParams;
import com.example.cody.net.response.CommonJsonCallback;
import com.example.cody.utils.RegularVerification;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

import static com.example.cody.net.CommonOkHttpClient.sendRequest;
import static com.example.cody.utils.Constants.URL_CHECKNAME;
import static com.example.cody.utils.Constants.URL_CHECKNUMBER;
import static com.example.cody.utils.Constants.URL_REGISTER;
import static com.example.cody.utils.Constants.URL_SENDSMS;

/**
 * Created By cyz on 2019/5/22 17:54
 * e-mail：462065470@qq.com
 */
public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.username)
    EditText musername;
    @BindView(R.id.password)
    EditText mpassword;
    @BindView(R.id.number)
    EditText mnumber;
    @BindView(R.id.SMScode)
    EditText mSMScode;
    @BindView(R.id.SendSMS)
    TextView mSendSMS;
    String username;
    String password;
    String number;
    String SMScode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

     private boolean checkUserNumber(){
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
        username = musername.getText().toString();
        number = mnumber.getText().toString();
        password = mpassword.getText().toString();
        SMScode = mSMScode.getText().toString();
        if (number.isEmpty() ||!RegularVerification.isMobile(number)) {
            mnumber.setError("请输入正确的手机号码");
            isPass = false;
        }
        if (password.isEmpty() || password.length() < 6) {
            mpassword.setError("请输入至少6位密码");
            isPass = false;
        }
        if (SMScode.isEmpty()) {
            isPass = false;
            Toast.makeText(MyApplication.getContext(), "验证码怎么不填！", Toast.LENGTH_SHORT).show();
        }
        return isPass;
    }

    @OnFocusChange({R.id.username,R.id.number})
    public  void onFocusChange(View view, boolean hasFocus) {
        username = musername.getText().toString();
        number = mnumber.getText().toString();
        Map params = new HashMap();
        if (!hasFocus){
            switch (view.getId()) {
                case R.id.number:
                    params.put("mobile",number);
                    CommonOkHttpClient
                            .sendRequest(CommonRequest.createGetRequest(URL_CHECKNUMBER, new RequestParams(params)), new CommonJsonCallback(new DisposeDataHandle(MsgBean.class, new DisposeDataListener() {
                                @Override
                                public void onSuccess(Object responseObj) {
                                    MsgBean testBean = (MsgBean) responseObj;
                                    Log.i("RegitserActivity", "onSuccess: " + testBean.getMsg());
                                    if(!testBean.getMsg().equals("手机号可用")){
                                        mnumber.setError("手机号不可用");
                                    }

                                }

                                @Override
                                public void onFailure(Object reasonObj) {
                                    Log.i("RegitserActivity", "onSuccess: " + reasonObj.toString());
                                    Toast.makeText(MyApplication.getContext(), "网络请求错误了QAQ", Toast.LENGTH_SHORT).show();
                                }
                            })));
break;
                case R.id.username :
                    params.put("username",username );
                    CommonOkHttpClient
                            .sendRequest(CommonRequest.createGetRequest(URL_CHECKNAME, new RequestParams(params)), new CommonJsonCallback(new DisposeDataHandle(MsgBean.class, new DisposeDataListener() {
                                @Override
                                public void onSuccess(Object responseObj) {
                                    MsgBean testBean = (MsgBean) responseObj;
                                    Log.i("RegitserActivity", "onSuccess: " + testBean.getMsg());
                                    if(!testBean.getMsg().equals("用户名可用")){
                                        musername.setError("用户名不可用或已注册");
                                    }

                                }

                                @Override
                                public void onFailure(Object reasonObj) {
                                    Log.i("RegitserActivity", "onSuccess: " + reasonObj.toString());
                                    Toast.makeText(MyApplication.getContext(), "网络请求错误了QAQ", Toast.LENGTH_SHORT).show();
                                }
                            })));
                    break;

            }
        }

    }


    @OnClick({R.id.SendSMS,R.id.register})
    public void onViewClicked(View view) {
        username = musername.getText().toString();
        number = mnumber.getText().toString();
        password = mpassword.getText().toString();
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
                                    Log.i("RegitserActivity", "onSuccess: "+testBean.getCode());
                                    if(testBean.getCode()==200){
                                        Toast.makeText(MyApplication.getContext(), "验证码已成功发送", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(MyApplication.getContext(), "发送失败", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Object reasonObj) {

                                    Log.i("RegitserActivity", "onSuccess: " + reasonObj.toString());
                                    Toast.makeText(MyApplication.getContext(), "网络请求错误了QAQ", Toast.LENGTH_SHORT).show();
                                }
                            })));
                }
break;
            case R.id.register:
                if (checkUserInfo()) {
                    params.put("mobile",number);
                    params.put("password",password);
                    params.put("code",SMScode);
                    params.put("username",username);
                    CommonOkHttpClient
                            .sendRequest(CommonRequest.createPostRequest(URL_REGISTER, new RequestParams(params)), new CommonJsonCallback(new DisposeDataHandle(MsgBean.class, new DisposeDataListener() {
                                @Override
                                public void onSuccess(Object responseObj) {
                                    MsgBean testBean = (MsgBean) responseObj;
                                    Log.i("RegitserActivity", "onSuccess: "+testBean.getCode()+ testBean.getMsg());
                                    if(testBean.getCode()==200){
                                        Toast.makeText(MyApplication.getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                                @Override
                                public void onFailure(Object reasonObj) {
                                    Log.i("RegitserActivity", "onSuccess: " + reasonObj.toString());
                                    Toast.makeText(MyApplication.getContext(), "网络请求错误了QAQ", Toast.LENGTH_SHORT).show();
                                }
                            })));
                }
                break;
        }
    }
}
