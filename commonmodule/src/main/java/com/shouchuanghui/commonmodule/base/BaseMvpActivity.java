package com.shouchuanghui.commonmodule.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shouchuanghui.commonmodule.util.ReflectUtil;

/**
 * 继承自BaseActivity，用于有信息处理的页面
 * @param <T>
 * @param <M>
 */
public abstract class BaseMvpActivity <T extends BasePresenter, M extends BaseModel> extends BaseActivity {
    protected T mPresenter;
    protected M mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ReflectUtil.getT(this, 0);
        mModel = ReflectUtil.getT(this, 1);
        mPresenter.onAttach(mModel, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    /**
     * 信息处理方法
     */
    protected abstract void loadData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }
}
