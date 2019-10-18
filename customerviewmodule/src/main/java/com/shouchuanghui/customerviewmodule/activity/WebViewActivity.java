package com.shouchuanghui.customerviewmodule.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.shouchuanghui.commonmodule.base.BaseActivity;
import com.shouchuanghui.commonmodule.bean.WebViewJsBridge;
import com.shouchuanghui.commonmodule.config.IntentConfig;
import com.shouchuanghui.commonmodule.config.StringConfig;
import com.shouchuanghui.customerviewmodule.R;

public class WebViewActivity extends BaseActivity {
    private boolean isTitleShow = true;
    private TextView pageTitle;
    private WebView wv;
    private String link;
    private final String TAG = WebViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        link = bundle.getString(IntentConfig.LINK);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        pageTitle=findViewById(R.id.pageTitle);
        wv=findViewById(R.id.wv);
        wv.getSettings().setJavaScriptEnabled(true);
        WebViewJsBridge jsbridge = new WebViewJsBridge(this);
        jsbridge.setCallback(new JsCallbackImpl());
        wv.addJavascriptInterface(jsbridge, StringConfig.WEB_JS);

        wv.loadUrl(link);
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                //设置加载进度条
                view.setWebChromeClient(new WebChromeClient());
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setTitle(view.getTitle());
            }
        });
    }

    private class JsCallbackImpl implements WebViewJsBridge.WebViewJsCallback {
        @Override
        public void setTitle(final String title) {
            pageTitle.post(new Runnable() {
                @Override
                public void run() {
                    WebViewActivity.this.setTitle(title);
//                    include_title.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        public void setMenu(String param) {

        }

        @Override
        public void modifyMenu(String param) {

        }

        @Override
        public void setTitleGone() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pageTitle.setVisibility(View.GONE);
                    isTitleShow = false;
                }
            });
        }
    }
}
