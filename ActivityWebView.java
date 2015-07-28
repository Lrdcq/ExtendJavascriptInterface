package com.lrdcq.example;

import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ActivityWebView extends ActivityBase {
    public class JsBridge {
        @JavascriptInterface
        public String send(String str) {
            wvBox.loadUrl("javascript:do2('-"+str+"-')");
            return "javagot:" + str;
        }

        @JavascriptInterface
        public void toast(String message) {
            Toast.makeText(wvBox.getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private WebView wvBox;
    private String bridgeName = "JSBridge";

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        JsBridge bridge = new JsBridge();

        wvBox = (WebView) findViewById(R.id.webview);
        wvBox.removeJavascriptInterface("searchBoxJavaBridge_");
        wvBox.loadUrl(fromUrl());

        //
        wvBox.getSettings().setJavaScriptEnabled(true);
        //!!!!!!!!!!!!!!!!!!!!!!使用方法!!!!!!!!!!!!!!!!!
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN_MR1) {//4.2以下危险webview的使用本方法
            ExtendJavascriptInterface<JsBridge> t=new ExtendJavascriptInterface(bridge,bridgeName);
            wvBox.setWebChromeClient(new ExtendJavascriptInterfaceClient(t));
        }else{//正常用法
            wvBox.addJavascriptInterface(bridge,bridgeName);
            wvBox.setWebChromeClient(new WebChromeClient());
        }
		//!!!!!!!!!!!!!!!!!!!!!使用方法结束!!!!!!!!!!!!!!
        wvBox.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

            }
        });

    }

    protected String fromUrl() {
        return "";
    }

    @Override
    protected int fromLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onDestroy() {
        wvBox.removeAllViews();
        wvBox.destroy();
        super.onDestroy();
    }

    @Override
    public void onEvent() {

    }
}
