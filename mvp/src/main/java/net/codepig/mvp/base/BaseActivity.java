package net.codepig.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 基础页面，包含信息展示的方法
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
    }

    protected abstract int getLayoutId();
    protected abstract void initView();

    @Override
    public void showInfo() {
    }

    @Override
    public void showError() {
    }
}
