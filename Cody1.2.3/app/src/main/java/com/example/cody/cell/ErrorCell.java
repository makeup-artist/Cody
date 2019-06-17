package com.example.cody.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cody.R;
import com.example.cody.base.BaseRvStateCell;
import com.example.cody.base.BaseRvViewHolder;
import com.example.cody.base.ItemType;

public class ErrorCell extends BaseRvStateCell {

    public ErrorCell(Object o) {
        super(o);
    }

    @Override
    protected View getDefaultView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.rv_error_layout,null);
    }

    @Override
    public int getItemType() {
        return ItemType.ERROR_TYPE;
    }

    @Override
    public void onBindViewHolder(BaseRvViewHolder holder, int position) {

    }
}
