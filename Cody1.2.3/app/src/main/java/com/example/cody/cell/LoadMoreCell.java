package com.example.cody.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cody.R;
import com.example.cody.base.BaseRvStateCell;
import com.example.cody.base.BaseRvViewHolder;
import com.example.cody.base.ItemType;
import com.example.cody.utils.Utils;


public class LoadMoreCell extends BaseRvStateCell {
    public static final int mDefaultHeight = 80;//dp
    public LoadMoreCell(Object o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return ItemType.LOAD_MORE_TYPE;
    }


    @Override
    public void onBindViewHolder(BaseRvViewHolder holder, int position) {

    }

    @Override
    protected View getDefaultView(Context context) {
        // 设置LoadMore View显示的默认高度
        setHeight(Utils.dpToPx(context,mDefaultHeight));
        return LayoutInflater.from(context).inflate(R.layout.rv_load_more_layout,null);
    }
}
