package com.shouchuanghui.commonmodule.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.shouchuanghui.commonmodule.R;
import com.shouchuanghui.commonmodule.net.retrofit.BaseRxRequest;
import com.shouchuanghui.commonmodule.util.KeyBoardUtil;
import com.shouchuanghui.commonmodule.util.WindowUtil;
import com.shouchuanghui.commonmodule.view.LoadingDialog.LoadingDialog;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 基础Activity,提供基本的缓冲图标及页面跳转逻辑
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseCB{
    /**
     * 网络请求disposable
     */
    protected CompositeDisposable subscription = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initAll(savedInstanceState);
        registerRxBus();

        //设置状态栏颜色
        try {
            WindowUtil.setStatusbarColor(this, R.color.top_background_color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract int getLayoutId();
    protected abstract void initAll(Bundle savedInstanceState);

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 点击空白处，隐藏软键盘
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();

            if (view instanceof EditText) {
            } else {
                KeyBoardUtil.closeKeyWords(BaseActivity.this);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void showLoaddingDialog() {
        LoadingDialog.show(this, R.string.wait);
    }

    public void dismissLoaddingDialog() {
        LoadingDialog.dismiss();
    }

    public void doIntent(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(BaseActivity.this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void doIntentClassName(String clsname, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClassName(BaseActivity.this, clsname);
        if ( bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void doIntentForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void doInentClassNameForResult(String clsname, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClassName(this, clsname);
        if ( bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void setResultCode(int resultCode, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        setResult(resultCode, intent);
    }

    public Bundle getIntentData() {
        Bundle bundle = null;
        if (getIntent() != null) {
            bundle = getIntent().getExtras();
        }
        return bundle;
    }

    public void closeKeyBoard() {
        KeyBoardUtil.closeKeyWords(this);
    }
    /**
     * 设置log打印,方便调试
     * @return
     */
    public abstract String getTAG();

    /**
     * 注册rxbus
     */
    public void registerRxBus(){}

    /**
     * 注销rxbus
     */
    public void unRegisterRxBus(){}

    /**
     * On destroy.
     */
    @CallSuper
    public void unregisterRx() {
        LoadingDialog.dismiss();
        BaseRxRequest.unsubscribeIfNotNull(subscription);
        subscription.dispose();
    }
}
