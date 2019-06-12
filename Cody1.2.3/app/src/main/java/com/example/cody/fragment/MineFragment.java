package com.example.cody.fragment;


import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cody.MyApplication;
import com.example.cody.R;
import com.example.cody.databinding.FragmentMineBinding;
import com.example.cody.entity.UserLoginBean.User;
import com.example.cody.makeUpActivity;
import com.example.cody.mineactivity.NotificationActivity;
import com.example.cody.utils.SharedPreferencesUtil;
import com.example.cody.viewmodel.ProfileVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    private ProfileVM profileVM;
    private SharedPreferencesUtil sp;

    @Override
    protected void setSubListener() {

    }

    @Override
    protected View getSubView(LayoutInflater inflater, ViewGroup container) {
        EventBus.getDefault().register(this);
        sp = SharedPreferencesUtil.getInstance(getContext());
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        FragmentMineBinding fragmentmineBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        if(sp.isLogin()==true){
            profileVM = new ProfileVM(sp.getUser(),getContext());
            Log.d("MineFragment", "getSubView: "+sp.getUser().getUsername()+sp.getUser().getDescription()+sp.getUser().toString());
        }else {
            profileVM = new ProfileVM(new User(),getContext());
        }
        fragmentmineBinding.setProfileVM(profileVM);
        return fragmentmineBinding.getRoot();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(User user){
        profileVM.setUser(user);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String string){
        if(string.equals("重新加载")){
            User user= sp.getUser();
            profileVM.setUser(user);
        }
    }

    public void onDestroyView(){
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//取消注册
    }
}
