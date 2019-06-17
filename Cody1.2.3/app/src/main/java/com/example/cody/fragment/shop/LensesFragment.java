package com.example.cody.fragment.shop;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.cody.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LensesFragment extends BaseShopFragment {
        private String url = "https://so.m.jd.com/ware/search.action?keyword=美瞳";
        Unbinder unbinder;
        @BindView(R.id.webview_layout)
        WebView webviewLayout;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootview = layoutInflater.inflate(R.layout.fragment_shop_lenses,container,false);
        unbinder = ButterKnife.bind(this, rootview);
        WebSettings webSetting = webviewLayout.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webviewLayout.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webviewLayout.loadUrl(url);
        return rootview;
    }
}
