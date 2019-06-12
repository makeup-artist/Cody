package com.example.cody.entity.UserPost;

/**
 * Created By cyz on 2019/6/5 17:01
 * e-mail：462065470@qq.com
 */
public class PostBean {
    private int code;
    private String msg;
    private Dataa dataa;

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }


    public void setData(Dataa dataa) {
        this.dataa = dataa;
    }
    public Dataa getData() {
        return dataa;
    }
}
