package com.example.cody.mineactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.cody.R;
import com.example.cody.entity.UserLoginBean.User;
import com.example.cody.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends Activity {
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_followee_count)
    TextView tvFolloweeCount;
    @BindView(R.id.tv_follower_count)
    TextView tvFollowerCount;



    private SharedPreferencesUtil sp;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        initResources();
        initView();
        initListener();
    }

    private void initListener() {
    }

    private void initView() {
        String url = user.getPicture();
        if (url != null) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.g_default_avatar);
            Glide.with(ivAvatar.getContext()).load("https://" + url).apply(requestOptions).into(ivAvatar);
        } else {
            Glide.with(ivAvatar.getContext()).load(R.drawable.g_default_avatar).into(ivAvatar);
        }
        tvUsername.setText(sp.getUser().getUsername());
        tvCompany.setText(sp.getUser().getNickname());
        tvDescription.setText(sp.getUser().getDescription());

    }

    private void initResources() {
        sp = SharedPreferencesUtil.getInstance(getApplicationContext());
        user = sp.getUser();
    }

    @OnClick({R.id.tv_edit, R.id.tv_username, R.id.tv_company, R.id.tv_description, R.id.tv_followee_count, R.id.tv_follower_count})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_edit:
                intent = new Intent(view.getContext(), EditProfileActivity.class);
                break;
            case R.id.tv_followee_count:
                intent = new Intent(view.getContext(), FolloweeActivity.class);
                break;
            case R.id.tv_follower_count:
                intent = new Intent(view.getContext(), FollowerActivity.class);
                break;
        }
        view.getContext().startActivity(intent);
    }
}
