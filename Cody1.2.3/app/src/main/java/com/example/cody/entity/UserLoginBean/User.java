package com.example.cody.entity.UserLoginBean;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.sql.Timestamp;


/**
 * Created By cyz on 2019/5/29 21:59
 * e-mail：462065470@qq.com
 */
public class User {
    private Long id;
    private String username;
    private Integer gender;
    private String mobile;
    private Integer age;
    private String nickname;
    private String picture;
    private String description;
    private String tag;
    private String email;
    @SerializedName("updateTime")
    private String updatetime;
    @SerializedName("createTime")
    private String createtime;
    @SerializedName("isAvailable")
    private Integer isavailable;
    private String role;


    public User() {
        this.id = null;
        this.username = null;
        this.gender = null;
        this.mobile = null;
        this.age = null;
        this.nickname = null;
        this.picture = null;
        this.description = null;
        this.tag = null;
        this.email = null;
        this.updatetime = null;
        this.createtime = null;
        this.isavailable = null;
        this.role = null;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Integer getGender() {
        return gender;
    }

    public String getMobile() {
        return mobile;
    }

    public Integer getAge() {
        return age;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    public String getTag() {
        return tag;
    }

    public String getEmail() {
        return email;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public Integer getIsavailable() {
        return isavailable;
    }

    public String getRole() {
        return role;
    }

}
