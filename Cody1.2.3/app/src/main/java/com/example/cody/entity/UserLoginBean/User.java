package com.example.cody.entity.UserLoginBean;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

/**
 * Created By cyz on 2019/5/29 21:59
 * e-mail：462065470@qq.com
 */
public class User {
    private BigInteger id;
    private String username;
    private short gender;
    private String mobile;
    private short age;
    private String nickname;
    private String picture;
    private String description;
    private String tag;
    private String email;
    @SerializedName("updateTime")
    private String updatetime;
    @SerializedName("createTime")
    private String createtime;
    @SerializedName("isavAilable")
    private short isavailable;
    private String role;


    public void setId(BigInteger id) {
        this.id = id;
    }
    public BigInteger getId() {
        return id;
    }


    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }


    public void setGender(short gender) {
        this.gender = gender;
    }
    public short getGender() {
        return gender;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getMobile() {
        return mobile;
    }


    public void setAge(short age) {
        this.age = age;
    }
    public short getAge() {
        return age;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public Object getNickname() {
        return nickname;
    }


    public void setPicture(String picture) {
        this.picture = picture;
    }
    public Object getPicture() {
        return picture;
    }


    public void setDescription(String description) {
        this.description = description;
    }
    public Object getDescription() {
        return description;
    }


    public void setTag(String tag) {
        this.tag = tag;
    }
    public Object getTag() {
        return tag;
    }


    public void setEmail(String email) {
        this.email = email;
    }
    public Object getEmail() {
        return email;
    }


    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
    public String getUpdatetime() {
        return updatetime;
    }


    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getCreatetime() {
        return createtime;
    }


    public void setIsavailable(short isavailable) {
        this.isavailable = isavailable;
    }
    public short getIsavailable() {
        return isavailable;
    }


    public void setRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }

}
