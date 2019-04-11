package com.example.cody.fragment.community;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseCommunityFragment extends Fragment {
    private View rootview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = onSubViewLoaded(inflater,container);
        return rootview;
    }

    protected abstract View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container);

}
