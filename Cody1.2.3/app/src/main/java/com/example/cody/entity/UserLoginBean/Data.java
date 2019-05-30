package com.example.cody.entity.UserLoginBean;

/**
 * Created By cyz on 2019/5/29 21:59
 * e-mail：462065470@qq.com
 */
public class Data {
    private User user;
    private String token;


    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }


    public void setToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}
