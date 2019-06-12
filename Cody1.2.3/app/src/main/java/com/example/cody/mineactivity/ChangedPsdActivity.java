package com.example.cody.mineactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cody.MyApplication;
import com.example.cody.R;
import com.example.cody.entity.UserLoginBean.UsermainBean;
import com.example.cody.net.CommonOkHttpClient;
import com.example.cody.net.exception.OkHttpException;
import com.example.cody.net.listener.DisposeDataHandle;
import com.example.cody.net.listener.DisposeDataListener;
import com.example.cody.net.request.CommonRequest;
import com.example.cody.net.request.RequestParams;
import com.example.cody.net.response.CommonJsonCallback;
import com.example.cody.views.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cody.utils.Constants.URL_ALTERMOBILE;
import static com.example.cody.utils.Constants.URL_ALTERPSD;
import static com.example.cody.utils.Constants.URL_LOGIN;

/**
 * Created By cyz on 2019/5/31 21:12
 * e-mail：462065470@qq.com
 */
public class ChangedPsdActivity extends AppCompatActivity {
    @BindView(R.id.tv_old_password)
    EditText tvOldPassword;
    @BindView(R.id.tv_password)
    EditText tvPassword;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    private String oldpsd;
    private String newpsd;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_changedpsd);
        Intent getintent = getIntent();
        username = getintent.getStringExtra("username");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_ok)
    public void onViewClicked() {
        oldpsd = tvOldPassword.getText().toString();
        newpsd = tvPassword.getText().toString();
        if(oldpsd.equals(newpsd)){
            Toast.makeText(MyApplication.getContext(), "新旧密码相同", Toast.LENGTH_SHORT).show();
        }else{
            Map params = new HashMap();
            params.put("newPassword",newpsd);
            params.put("oldPassword",oldpsd);
            params.put("username",username);
            CommonOkHttpClient
                    .sendRequest(CommonRequest.createPutRequest(URL_ALTERPSD, new RequestParams(params)), new CommonJsonCallback(new DisposeDataHandle(UsermainBean.class, new DisposeDataListener() {
                        @Override
                        public void onSuccess(Object responseObj) {
                            UsermainBean testBean = (UsermainBean) responseObj;
                            if(testBean.getCode()==200){
                                Toast.makeText(MyApplication.getContext(), "密码修改成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(MyApplication.getContext(), "原密码错误", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Object reasonObj) {
                            OkHttpException testBean = (OkHttpException) reasonObj;
                            Toast.makeText(MyApplication.getContext(), "网络请求错误了QAQ", Toast.LENGTH_SHORT).show();
                        }
                    })));
        }
    }
}
