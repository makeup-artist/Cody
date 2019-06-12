package com.example.cody.entity.UserPost;

import com.google.gson.annotations.SerializedName;

/**
 * Created By cyz on 2019/6/5 17:01
 * e-mail：462065470@qq.com
 */
public class Dataa {
    @SerializedName("postId")
    private int postid;

    public void setPostid(int postid) {
        this.postid = postid;
    }
    public int getPostid() {
        return postid;
    }
}
