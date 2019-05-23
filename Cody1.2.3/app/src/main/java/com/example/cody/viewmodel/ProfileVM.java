package com.example.cody.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.cody.LoginActivity;
import com.example.cody.R;
import com.example.cody.mineactivity.CollectionActivity;
import com.example.cody.mineactivity.FeedbackActivity;
import com.example.cody.mineactivity.HeartActivity;
import com.example.cody.mineactivity.LikedActivity;
import com.example.cody.mineactivity.NotificationActivity;
import com.example.cody.mineactivity.ProfileActivity;
import com.example.cody.mineactivity.SettingActivity;
import com.example.cody.mineactivity.ViewActivity;
import com.example.cody.user.UserBean;
import com.example.cody.utils.SharedPreferencesUtil;

public class ProfileVM {

    private String name;

    private String password;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    private UserBean user;
    private SharedPreferencesUtil sp;




    public ProfileVM(String name, String password, Context context) {
        this.name = name;
        this.password = password;
        sp = SharedPreferencesUtil.getInstance(context);
    }

    public void toGo(View view) {

        Intent intent = null;
        if (sp.isLogin()){
            Log.d("ProfileVM", "toGo:23333 ");
            switch (view.getId()) {
                case R.id.profile:
                    Log.d("ProfileVM", "toGo:332223 ");
                    intent = new Intent(view.getContext(), ProfileActivity.class);
                case R.id.layout_notification:
                    intent = new Intent(view.getContext(), NotificationActivity.class);
                case R.id.layout_view:
                    intent = new Intent(view.getContext(), ViewActivity.class);
                case R.id.layout_liked:
                    intent = new Intent(view.getContext(), LikedActivity.class);
                case R.id.layout_collection_set:
                    intent = new Intent(view.getContext(), CollectionActivity.class);
                case R.id.layout_heart:
                    intent = new Intent(view.getContext(), HeartActivity.class);
                case R.id.rl_feedback:
                    intent = new Intent(view.getContext(), FeedbackActivity.class);
                case R.id.layout_setting:
                    intent = new Intent(view.getContext(), SettingActivity.class);
            }
        }else{
            Log.d("ProfileVM", "toGo:333 ");
            intent = new Intent(view.getContext(), LoginActivity.class);
        }
        view.getContext().startActivity(intent);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
