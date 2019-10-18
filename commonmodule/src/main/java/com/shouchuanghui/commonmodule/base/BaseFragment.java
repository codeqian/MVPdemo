package com.shouchuanghui.commonmodule.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shouchuanghui.commonmodule.R;
import com.shouchuanghui.commonmodule.net.retrofit.BaseRxRequest;
import com.shouchuanghui.commonmodule.util.KeyBoardUtil;
import com.shouchuanghui.commonmodule.util.ToastUtil;
import com.shouchuanghui.commonmodule.view.LoadingDialog.LoadingDialog;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 基础fragment,提供基本的缓冲图标及页面跳转逻辑
 */
public abstract class BaseFragment extends Fragment implements BaseCB{
    public View thisView;
    /**
     * 网络请求disposable
     */
    protected CompositeDisposable subscription = new CompositeDisposable();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        thisView=inflater.inflate(setLayout(), container, false);
        initAll(container,savedInstanceState);
        registerRxBus();
        return thisView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unregisterRx();
        unRegisterRxBus();
    }

    /**
     * 设置activity对应的布局文件
     * @return 布局文件的id
     */
    public abstract int setLayout();

    /**
     * 初始化
     * @param savedInstanceState
     */
    public abstract void initAll(View view,Bundle savedInstanceState);

    public abstract String getTAG();

    public void showToast(String msg) {
        ToastUtil.showToast(msg);
    }

    public void showLoaddingDialog() {
        LoadingDialog.show(getActivity(), R.string.wait);
    }

    public void dismissLoaddingDialog() {
        LoadingDialog.dismiss();
    }

    public void doIntent(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivity(intent);
    }

    public void doIntentClassName(String clsname, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClassName(getActivity(), clsname);
        if ( bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivity(intent);
    }

    public void doIntentForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
       startActivityForResult(intent, requestCode);
    }

    public void doInentClassNameForResult(String clsname, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClassName(getActivity(), clsname);
        if ( bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivityForResult(intent, requestCode);
    }

    public void setResultCode(int resultCode, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().setResult(resultCode, intent);
    }

    public Bundle getIntentData() {
        Bundle bundle = null;
        if (getActivity().getIntent() != null) {
            bundle = getActivity().getIntent().getExtras();
        }
        return bundle;
    }

    public void closeKeyBoard() {
        KeyBoardUtil.closeKeyWords(getActivity());
    }

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
