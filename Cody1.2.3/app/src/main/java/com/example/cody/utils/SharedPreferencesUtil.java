package com.example.cody.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cody.entity.UserLoginBean.User;
import com.google.gson.Gson;


public class SharedPreferencesUtil {


    private static final String TAG ="TAG";
    private static final String KEY_LOGIN ="KEY_LOGIN";
    private static final String KEY_USER="KEY_USER";
    private static final String KEY_TOKEN="KEY_TOKEN";
    private static final String KEY_PSD="KEY_PSD";

    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferencesUtil mSharedPreferencesUtil;
    private final Context context;

    public SharedPreferencesUtil(Context context) {
        this.context = context.getApplicationContext();
        mPreferences = this.context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

   /**
  * @desc 简单单例模式实现
  * @author cyz
  * @time 2019/5/22 10:50
   */
        public static SharedPreferencesUtil getInstance(Context context){
            if(mSharedPreferencesUtil ==null){
                mSharedPreferencesUtil = new SharedPreferencesUtil(context);
            }
            return mSharedPreferencesUtil;
        }

        /**
         * @desc 判断是否登陆
         * @author cyz
         * @time 2019/5/22 11:01
         */
        public boolean isLogin(){
            return getBoolean(KEY_LOGIN,false);
        }

        /**
         * @desc 更改登陆状态
         * @author cyz
         * @time 2019/5/22 11:19
         */
        public void setLogin(boolean value){
            putBoolean(KEY_LOGIN,value);
        }

        /**
         * @desc 从SharedPerences里获取User
         * @author cyz
         * @time 2019/5/31 21:57
         */
        public User getUser(){
            Gson gson = new Gson();
            String str= get(KEY_USER);
            return gson.fromJson(str,User.class);
        }

        /**
         * @desc 往sharedPerences里写入User
         * @author cyz
         * @time 2019/5/31 22:08
         */
        public void putUser(User user){
            Gson gson = new Gson();
            String jsonStr = gson.toJson(user);
            put(KEY_USER,jsonStr);
        }

        /**
         * @desc Token的读入读出
         * @author cyz
         * @time 2019/6/1 11:15
         */
    public String getToken(){
        return  get(KEY_TOKEN);
    }
    public void putToken(String token){
        put(KEY_TOKEN,token);
    }

    public String getPsd(){
        return  get(KEY_PSD);
    }
    public void putPsd(String psd){
        put(KEY_PSD,psd);
    }

//        -----私有方法
        private void put(String key, String value){
            mEditor.putString(key, value);
            mEditor.commit();
        }

        private void putBoolean (String key,boolean value){
            mEditor.putBoolean(key,value);
            mEditor.commit();
        }

        private void putInt(String key, int value){
           mEditor.putInt(key,value);
           mEditor.apply();
        }

        private String get(String key){
            return mPreferences.getString(key,"");
        }


        private boolean getBoolean(String key,boolean defaultValue){
            return mPreferences.getBoolean(key,defaultValue);
        }


        private int getInt(String key,int defaultValue){
            return mPreferences.getInt(key,defaultValue);
        }
    //      end  -----私有方法
}




