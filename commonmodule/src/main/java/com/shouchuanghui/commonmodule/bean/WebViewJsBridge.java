package com.shouchuanghui.commonmodule.bean;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;

/**
* @description WebView 和js交互
* @author chenbin
* @date 2018/12/18 13:40
* @version v3.2.0
*/
public class WebViewJsBridge {
    private final static String TAG = WebViewJsBridge.class.getSimpleName();
    private WebViewJsCallback callback;
    private Activity activity;

    public WebViewJsCallback getCallback() {
        return callback;
    }

    public void setCallback(WebViewJsCallback callback) {
        this.callback = callback;
    }

    public WebViewJsBridge(AppCompatActivity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void setNativeNavigationBarTitle(String title) {
        if (callback != null) {
            callback.setTitle(title);
        }
    }

    public interface WebViewJsCallback {
        void setTitle(String title);

        void setMenu(String param);

        void modifyMenu(String param);

        void setTitleGone();
    }
}
