package com.example.cody.mineactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cody.MyApplication;
import com.example.cody.R;
import com.example.cody.entity.MsgBean;
import com.example.cody.entity.UserLoginBean.User;
import com.example.cody.entity.UserLoginBean.UsermainBean;
import com.example.cody.net.CommonOkHttpClient;
import com.example.cody.net.exception.OkHttpException;
import com.example.cody.net.listener.DisposeDataHandle;
import com.example.cody.net.listener.DisposeDataListener;
import com.example.cody.net.request.CommonRequest;
import com.example.cody.net.request.HeaderParams;
import com.example.cody.net.request.RequestParams;
import com.example.cody.net.response.CommonJsonCallback;
import com.example.cody.utils.RegularVerification;
import com.example.cody.utils.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cody.utils.Constants.URL_ALTERMOBILE;
import static com.example.cody.utils.Constants.URL_ALTERPSD;
import static com.example.cody.utils.Constants.URL_SENDSMS;

/**
 * Created By cyz on 2019/6/1 12:05
 * e-mail：462065470@qq.com
 */
public class ChangedNumberActivity extends AppCompatActivity {
    @BindView(R.id.tv_oldphone)
    TextView tvOldphone;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.tv_code)
    EditText tvCode;
    @BindView(R.id.tv_request_code)
    TextView tvRequestCode;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    private String oldphone;
    private String phone;
    private String SMScode;
    private String token;
    private SharedPreferencesUtil sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_changenumber);
        sp = SharedPreferencesUtil.getInstance(getApplicationContext());
        Intent intent = getIntent();
        oldphone = intent.getStringExtra("oldphone");
        ButterKnife.bind(this);
        tvOldphone.setText(oldphone);
        token = sp.getToken();
    }

    private boolean checkUserNumber() {
        boolean isPass = true;
        phone = tvPhone.getText().toString();
        if (phone.isEmpty() || !RegularVerification.isMobile(phone)) {
            tvPhone.setError("请输入正确的手机号码");
            isPass = false;
        }

        return isPass;
    }

    private boolean checkUserInfo(){
        boolean isPass = true;
        phone = tvPhone.getText().toString();
        SMScode = tvCode.getText().toString();
        if (phone.isEmpty() ||!RegularVerification.isMobile(phone)) {
            tvPhone.setError("请输入正确的手机号码");
            isPass = false;
        }
        if (SMScode.isEmpty()) {
            isPass = false;
            Toast.makeText(MyApplication.getContext(), "验证码怎么不填！", Toast.LENGTH_SHORT).show();
        }
        return isPass;
    }

    @OnClick({R.id.tv_ok, R.id.tv_request_code})
    public void onViewClicked(View view) {
        phone = tvPhone.getText().toString();
        SMScode = tvCode.getText().toString();
        Map params = new HashMap();
        Map harams = new HashMap();
        switch (view.getId()) {
            case R.id.tv_ok:
                if (checkUserInfo()) {
                    params.put("mobile",phone);
                    params.put("code",SMScode);
                    harams.put("Authorization",token);
                    CommonOkHttpClient
                            .sendRequest(CommonRequest.createPutRequest(URL_ALTERMOBILE, params,new HeaderParams(harams)), new CommonJsonCallback(new DisposeDataHandle(UsermainBean.class, new DisposeDataListener() {
                                @Override
                                public void onSuccess(Object responseObj) {
                                    UsermainBean testBean = (UsermainBean) responseObj;
                                    if(testBean.getCode()==200){
                                        Toast.makeText(MyApplication.getContext(), "手机号码修改成功", Toast.LENGTH_SHORT).show();
                                        User user = sp.getUser();
                                        user.setMobile(phone);
                                        sp.putUser(user);
                                        Intent intent = new Intent();
                                        intent.putExtra("phone",phone);
                                        setResult(RESULT_OK,intent);
                                        finish();
                                    }else{
                                        if(testBean.getMsg().equals("手机号已注册")){
                                            Toast.makeText(MyApplication.getContext(), "该手机号已注册", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(MyApplication.getContext(), "原密码或验证码有误", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Object reasonObj) {
                                    OkHttpException testBean = (OkHttpException) reasonObj;
                                    Toast.makeText(MyApplication.getContext(), "网络请求错误了QAQ", Toast.LENGTH_SHORT).show();
                                }
                            })));
                }

                break;
            case R.id.tv_request_code:
                if (checkUserNumber()) {
                    params.put("mobile", phone);
                    CommonOkHttpClient
                            .sendRequest(CommonRequest.createGetRequest(URL_SENDSMS, new RequestParams(params),null), new CommonJsonCallback(new DisposeDataHandle(MsgBean.class, new DisposeDataListener() {
                                @Override
                                public void onSuccess(Object responseObj) {
                                    MsgBean testBean = (MsgBean) responseObj;
                                    if (testBean.getCode() == 200) {
                                        Toast.makeText(MyApplication.getContext(), "验证码已成功发送", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MyApplication.getContext(), "发送失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Object reasonObj) {
                                    Toast.makeText(MyApplication.getContext(), "网络请求错误了QAQ", Toast.LENGTH_SHORT).show();
                                }
                            })));
                    break;
                }
        }
    }
}
