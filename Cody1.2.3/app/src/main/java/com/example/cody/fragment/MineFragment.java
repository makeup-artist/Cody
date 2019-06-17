package com.example.cody.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cody.R;
import com.example.cody.ShowImageActivity;
import com.example.cody.databinding.FragmentMineBinding;
import com.example.cody.entity.UserLoginBean.User;
import com.example.cody.utils.SharedPreferencesUtil;
import com.example.cody.viewmodel.ProfileVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Intent.getIntent;

public class MineFragment extends BaseFragment {
    @BindView(R.id.layout_notification)
    RelativeLayout layoutNotification;
    @BindView(R.id.layout_view)
    RelativeLayout layoutView;
    @BindView(R.id.layout_liked)
    RelativeLayout layoutLiked;
    @BindView(R.id.layout_collection_set)
    RelativeLayout layoutCollectionSet;
    @BindView(R.id.layout_heart)
    RelativeLayout layoutHeart;
    @BindView(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @BindView(R.id.layout_setting)
    RelativeLayout layoutSetting;
    @BindView(R.id.avt)
    CircleImageView avt;
    Unbinder unbinder;
    private ProfileVM profileVM;
    private SharedPreferencesUtil sp;
    private String picUrl;
    private User user;

    @Override
    protected void setSubListener() {

    }

    @Override
    protected View getSubView(LayoutInflater inflater, ViewGroup container) {
        sp = SharedPreferencesUtil.getInstance(getContext());
        user = sp.getUser();
        EventBus.getDefault().register(this);
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        FragmentMineBinding fragmentmineBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        if (sp.isLogin() == true) {
            profileVM = new ProfileVM(user, getContext());
        } else {
            profileVM = new ProfileVM(new User(), getContext());
        }
        fragmentmineBinding.setProfileVM(profileVM);
        return fragmentmineBinding.getRoot();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(User user) {
        Glide.with(this)
                .load("http://"+user.getPicture())
                .into(avt);
        profileVM.setUser(user);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String string) {
        if (string.equals("重新加载")) {
            User user = sp.getUser();
            profileVM.setUser(user);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//取消注册
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.avt)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), ShowImageActivity.class);
        int[] location = new int[2];
        avt.getLocationOnScreen(location);
        intent.putExtra(ShowImageActivity.POINTX, location[0] + avt.getMeasuredWidth() / 2);
        intent.putExtra(ShowImageActivity.POINTY, location[1] + avt.getMeasuredHeight() / 2);
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
            }
        }

