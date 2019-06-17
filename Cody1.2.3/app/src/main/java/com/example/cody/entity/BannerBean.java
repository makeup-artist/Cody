package com.example.cody.entity;

/**
 * 首页轮播图实体
 */
public class BannerBean {

    private String title;
    private int imageUrl;



    public BannerBean(String title, int imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public BannerBean(){

    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public int getImageUrl() {
        return imageUrl ;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

}
