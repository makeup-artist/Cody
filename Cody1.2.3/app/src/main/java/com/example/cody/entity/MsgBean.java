package com.example.cody.entity;


/**
 * Created By cyz on 2019/5/28 11:07
 * e-mail：462065470@qq.com
 */
 public class MsgBean {
    private Integer code;

    public String getMsg() {
        return msg;
    }

    public String getData() {
        return data;
    }

    private String msg;
    private String data;

    public MsgBean() {
    }

    public MsgBean(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }
}
