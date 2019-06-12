package com.example.cody.entity.UserLoginBean;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.sql.Timestamp;


/**
 * Created By cyz on 2019/5/29 21:59
 * e-mail：462065470@qq.com
 */
public class User {
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public void setIsavailable(Integer isavailable) {
        this.isavailable = isavailable;
    }

    public void setRole(String role) {
        this.role = role;
    }

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
        this.username = "登录/注册";
        this.gender = null;
        this.mobile = null;
        this.age = null;
        this.nickname = null;
        this.picture = null;
        this.description = "添加个人描述@职业";
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
