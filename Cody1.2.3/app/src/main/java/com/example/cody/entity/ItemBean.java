package com.example.cody.entity;

import android.view.View;

import com.example.cody.base.BaseRvCell;
import com.example.cody.base.BookCell;
import com.example.cody.base.OnClickViewRvListener;
import com.example.cody.listener.OnComicClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By cyz on 2019/6/16 21:05
 * e-mail：462065470@qq.com
 */
public class ItemBean {
    private String title;//标题
    private String intro;//分类简介
    private String moreUrl;//加载更多Url
    private List<LipstickBean> lipstickBeanList;//漫画列表
    private int iconResId;//图标本地ID

    private List<BaseRvCell> comicCellList;

    public ItemBean(String title, String intro, String moreUrl, List<LipstickBean> lipstickBeanList, int iconResId) {
        this.title = title;
        this.intro = intro;
        this.moreUrl = moreUrl;
        this.lipstickBeanList = lipstickBeanList;
        this.iconResId = iconResId;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", moreUrl='" + moreUrl + '\'' +
                ", bookBeanList=" + lipstickBeanList +
                ", iconResId=" + iconResId +
                '}';
    }

    public List<BaseRvCell> getComicCellList(final OnComicClickListener listener) {
        if (comicCellList == null
                && lipstickBeanList != null){
            comicCellList = new ArrayList<>();
            for (final LipstickBean bookBean : lipstickBeanList) {
                BookCell bookCell = new BookCell(bookBean);
                bookCell.setListener(new OnClickViewRvListener() {
                    @Override
                    public void onClick(View view, int position) {

                    }

                    @Override
                    public <C> void onClickItem(C data, int position) {
                        if (listener != null){
//                            listener.onClick(bookBean.getBookId());
                        }
                    }
                });
                comicCellList.add(bookCell);
            }
        }
        return comicCellList;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public String getIntro() {
        return intro == null ? "" : intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? "" : intro;
    }

    public String getMoreUrl() {
        return moreUrl == null ? "" : moreUrl;
    }

    public void setMoreUrl(String moreUrl) {
        this.moreUrl = moreUrl == null ? "" : moreUrl;
    }

    public List<LipstickBean> getBookBeanList() {
        return lipstickBeanList;
    }

    public void setBookBeanList(List<LipstickBean> bookBeanList) {
        this.lipstickBeanList = bookBeanList;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
