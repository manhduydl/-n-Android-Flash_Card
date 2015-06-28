package com.example.duy.flashcard_v10.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.example.duy.flashcard_v10.R;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;

/**
 * Created by duy on 22/06/15.
 */
public class WebViewFragment extends Fragment {

    private ObservableWebView mWebView;
    private Button btnsearch;
    private Button btntrans;
    public static WebViewFragment newInstance() {
        return new WebViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_webview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView = (ObservableWebView) view.findViewById(R.id.webView);
        btnsearch = (Button)view.findViewById(R.id.btnsearch);
        btntrans = (Button)view.findViewById(R.id.btntrans);
        //must be called before loadUrl()
        MaterialViewPagerHelper.preLoadInjectHeader(mWebView);

        //have to inject header when WebView page loaded
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                MaterialViewPagerHelper.injectHeader(mWebView, true);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.loadUrl("http://translate.google.com.vn/m/translate");

        MaterialViewPagerHelper.registerWebView(getActivity(), mWebView, null);
    }
}
