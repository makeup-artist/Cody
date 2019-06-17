package com.example.cody.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cody.R;
import com.example.cody.base.BaseRvStateCell;
import com.example.cody.base.BaseRvViewHolder;
import com.example.cody.base.ItemType;


public class EmptyCell extends BaseRvStateCell {

    public EmptyCell(Object o) {
        super(o);
    }

    @Override
    public void releaseResource() {

    }

    @Override
    public int getItemType() {
        return ItemType.EMPTY_TYPE;
    }

    @Override
    protected View getDefaultView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.rv_empty_layout,null);
    }

    @Override
    public void onBindViewHolder(BaseRvViewHolder holder, int position) {

    }
}
