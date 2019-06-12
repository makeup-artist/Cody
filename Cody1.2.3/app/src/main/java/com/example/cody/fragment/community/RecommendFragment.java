package com.example.cody.fragment.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cody.R;
import com.example.cody.RichEditorActivity;
import com.example.cody.mineactivity.ViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecommendFragment extends BaseCommunityFragment {
    @BindView(R.id.fab)
    FloatingActionButton fab;
    Unbinder unbinder;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootview = layoutInflater.inflate(R.layout.fragment_community_recommend, container, false);
        unbinder = ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), RichEditorActivity.class);
        startActivity(intent);
    }
}
