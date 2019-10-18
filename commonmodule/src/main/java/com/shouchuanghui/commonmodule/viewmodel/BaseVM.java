package com.shouchuanghui.commonmodule.viewmodel;

import android.support.annotation.CallSuper;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.net.retrofit.BaseRxRequest;
import com.nmssdmf.commonlib.view.LoadingDialog.LoadingDialog;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by ${nmssdmf} on 2018/7/4 0004.
 * 综合BaseActivityVM和BaseFragmentVM
 */

public abstract class BaseVM {
    /**
     * 网络请求disposable
     */
    protected CompositeDisposable subscription = new CompositeDisposable();
    /**
     * 基础view的回调
     */
    protected BaseCB baseCallBck;

    /**
     * 不需要callback可以传null
     * @param callBack
     */
    public BaseVM(BaseCB callBack) {
        this.baseCallBck = callBack;
        registerRxBus();
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
