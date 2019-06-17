package com.example.cody.entity;

import org.litepal.crud.LitePalSupport;

/**
 * Created By cyz on 2019/6/16 20:56
 * e-mail：462065470@qq.com
 */
public class LipstickBean  extends LitePalSupport {
    private String name;//商品名
    private String coverUrl;//封面URL
    private String color;// 颜色
    private String price ;//价格
    private String senario;//场景

    public LipstickBean(String name, String coverUrl, String color, String price, String senario) {
        this.name = name;
        this.coverUrl = coverUrl;
        this.color = color;
        this.price = price;
        this.senario = senario;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSenario() {
        return senario;
    }

    public void setSenario(String senario) {
        this.senario = senario;
    }
}
