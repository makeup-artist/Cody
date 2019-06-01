package com.example.cody.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.example.cody.BR;
import com.example.cody.LoginActivity;
import com.example.cody.R;
import com.example.cody.entity.UserLoginBean.User;
import com.example.cody.mineactivity.CollectionActivity;
import com.example.cody.mineactivity.FeedbackActivity;
import com.example.cody.mineactivity.HeartActivity;
import com.example.cody.mineactivity.LikedActivity;
import com.example.cody.mineactivity.NotificationActivity;
import com.example.cody.mineactivity.ProfileActivity;
import com.example.cody.mineactivity.SettingActivity;
import com.example.cody.mineactivity.ViewActivity;
import com.example.cody.utils.SharedPreferencesUtil;

public class ProfileVM extends BaseObservable {

    @Bindable
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    notifyPropertyChanged(BR.user);
    }

    private User user;
    private SharedPreferencesUtil sp;


public  ProfileVM(){

}

    public ProfileVM(User user, Context context) {
        this.user = user;
        sp = SharedPreferencesUtil.getInstance(context);
    }

    public void toGo(View view) {

        Intent intent = null;
        if (sp.isLogin()){
            switch (view.getId()) {
                case R.id.profile:
                    intent = new Intent(view.getContext(), ProfileActivity.class);
                    break;
                case R.id.layout_notification:
                    intent = new Intent(view.getContext(), NotificationActivity.class);
                    break;
                case R.id.layout_view:
                    intent = new Intent(view.getContext(), ViewActivity.class);
                    break;
                case R.id.layout_liked:
                    intent = new Intent(view.getContext(), LikedActivity.class);
                    break;
                case R.id.layout_collection_set:
                    intent = new Intent(view.getContext(), CollectionActivity.class);
                    break;
                case R.id.layout_heart:
                    intent = new Intent(view.getContext(), HeartActivity.class);
                    break;
                case R.id.rl_feedback:
                    intent = new Intent(view.getContext(), FeedbackActivity.class);
                    break;
                case R.id.layout_setting:
                    intent = new Intent(view.getContext(), SettingActivity.class);
                    break;
            }
        }else{
            intent = new Intent(view.getContext(), LoginActivity.class);
        }
        view.getContext().startActivity(intent);
    }



}
