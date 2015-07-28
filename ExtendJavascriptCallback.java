package com.lrdcq.ExtendJavascriptInterface;

import android.util.Log;
import android.webkit.WebView;
import java.lang.ref.WeakReference;

public class ExtendJavascriptCallback {
    private static final String CALLBACK_JS_FORMAT = "javascript:%s.callback(%d, %d %s);";
    private int mIndex;
    private boolean mCouldGoOn;
    private WeakReference<WebView> mWebViewRef;
    private int mIsPermanent;
    private String mInjectedName;

    public ExtendJavascriptCallback(WebView view, String injectedName, int index) {
        mCouldGoOn = true;
        mWebViewRef = new WeakReference<WebView>(view);
        mInjectedName = injectedName;
        mIndex = index;
    }

    public void apply (Object... args) throws ExtendJavascriptCallbackException {
        if (mWebViewRef.get() == null) {
            throw new ExtendJavascriptCallbackException("the WebView related to the Callback has been recycled");
        }
        if (!mCouldGoOn) {
            throw new ExtendJavascriptCallbackException("the Callback isn't permanent,cannot be called more than once");
        }
        StringBuilder sb = new StringBuilder();
        for (Object arg : args){
            sb.append(",");
            boolean isStrArg = arg instanceof String;
            if (isStrArg) {
                sb.append("\"");
            }
            sb.append(String.valueOf(arg));
            if (isStrArg) {
                sb.append("\"");
            }
        }
        String execJs = String.format(CALLBACK_JS_FORMAT, mInjectedName, mIndex, mIsPermanent, sb.toString());
        Log.d("JsCallBack", execJs);
        mWebViewRef.get().loadUrl(execJs);
        mCouldGoOn = mIsPermanent > 0;
    }

    public void setPermanent (boolean value) {
        mIsPermanent = value ? 1 : 0;
    }

    public static class ExtendJavascriptCallbackException extends Exception {
        public ExtendJavascriptCallbackException(String msg) {
            super(msg);
        }
    }
}
