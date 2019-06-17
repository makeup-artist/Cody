package com.example.cody.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cody.R;
import com.example.cody.base.BaseRvStateCell;
import com.example.cody.base.BaseRvViewHolder;
import com.example.cody.base.ItemType;


public class LoadingCell extends BaseRvStateCell {

    public LoadingCell(Object o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return ItemType.LOADING_TYPE;
    }

    @Override
    public void onBindViewHolder(BaseRvViewHolder holder, int position) {

    }

    @Override
    protected View getDefaultView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.rv_loading_layout,null);
    }
}
