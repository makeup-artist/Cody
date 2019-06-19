package com.example.cody.fragment.shop;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.cody.R;
import com.prim.primweb.core.PrimWeb;
import com.prim.primweb.core.jsloader.CommonJSListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BasemakeupFragment extends BaseShopFragment  implements CommonJSListener {
    private String url = "https://so.m.jd.com/ware/search.action?keyword=底妆";
    Unbinder unbinder;
    @BindView(R.id.webParent)
    FrameLayout webParent;
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootview = layoutInflater.inflate(R.layout.fragment_shop_eyebrow,container,false);
        unbinder = ButterKnife.bind(this, rootview);
        PrimWeb.with(getActivity())
                .setWebParent(webParent, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultUI()
                .useDefaultTopIndicator()
                .setWebViewType(PrimWeb.WebViewType.X5)
                .setListenerCheckJsFunction(this)
                .buildWeb()
                .lastGo()
                .launch(url);
        return rootview;
    }

    @Override
    public void jsFunExit(Object data) {
        Toast.makeText(getActivity(), data.toString() + "方法存在", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void jsFunNoExit(Object data) {
        Toast.makeText(getActivity(), data.toString() + "方法不存在", Toast.LENGTH_SHORT).show();
    }
}
