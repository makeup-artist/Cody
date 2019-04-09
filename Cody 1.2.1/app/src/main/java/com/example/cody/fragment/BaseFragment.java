package com.example.cody.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment  {

    private View rootView = null;


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootView = getSubView(inflater,container);
        setSubListener();
        return rootView;
    }

    protected abstract void setSubListener();

    protected abstract View getSubView(LayoutInflater inflater, ViewGroup container);
}
