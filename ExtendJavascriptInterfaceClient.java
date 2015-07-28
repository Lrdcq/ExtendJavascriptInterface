package com.lrdcq.ExtendJavascriptInterface;


import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class ExtendJavascriptInterfaceClient extends WebChromeClient {

    private final String TAG = "ExtendJsInterfaceClient";
    private ExtendJavascriptInterface mJsCallJava;
    private boolean mIsInjectedJS;

    public ExtendJavascriptInterfaceClient(ExtendJavascriptInterface jsCallJava) {
        mJsCallJava = jsCallJava;
    }

    // 处理Alert事件
    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        result.confirm();
        return true;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress <= 25) {
            mIsInjectedJS = false;
        } else if (!mIsInjectedJS) {
            view.loadUrl(mJsCallJava.getPreloadInterfaceJS());
            mIsInjectedJS = true;
            Log.d(TAG, " inject js interface completely on progress " + newProgress);

        }
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        result.confirm(mJsCallJava.call(view,message));
        return true;
    }
}
