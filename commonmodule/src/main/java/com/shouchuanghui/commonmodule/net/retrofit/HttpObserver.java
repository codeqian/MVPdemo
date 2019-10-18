package com.shouchuanghui.commonmodule.net.retrofit;

import android.support.annotation.CallSuper;

import com.shouchuanghui.commonmodule.rxbus.RxBus;
import com.shouchuanghui.commonmodule.rxbus.RxEvent;
import com.shouchuanghui.commonmodule.util.ToastUtil;

import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @author huscarter.
 * @title
 * @description <ul>
 * <li>
 * 1、如果想在出错的情况下finish当前页面，实例化时请将force_close赋值为true，
 * 建议onError给出提示信息，即需要在重写onError的时候传入nulll值。
 * </li>
 * <li>
 * 2、如果不想提示任何信息，有两种方法：1)、实例化时Context/Activity传入null；2)、请重写onError传入null。
 * </li>
 * </ul>
 * @date 11/25/2015.
 */
public abstract class HttpObserver<T> extends DisposableObserver<T> {
    private final static String TAG = HttpObserver.class.getSimpleName();

    private static final int DELAY_TIME = 200;

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int NOT_IMPLEMENTED = 501;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;


    public HttpObserver() {
    }

    @CallSuper
    @Override
    public void onComplete() {

    }

    /**
     * 如果需要在出错时对页面组建做处理，请重载此方法
     *
     * @param e
     */
    @CallSuper
    @Override
    public void onError(Throwable e) {
        handleError(e);
    }

    public abstract void onNext(T t);

    public void handleError(Throwable e) {
        if (e != null) {
            e.printStackTrace();
        }
        if (e instanceof HttpException) { // is http exception
            HttpException error = (HttpException) e;
            switch (error.code()) {
                case UNAUTHORIZED:
                    ToastUtil.showToast( "当前身份已失效,请重新登录");
                    RxBus.getInstance().send(RxEvent.LoginEvent.RE_LOGIN, null);//发送消息,跳转到登陆页面
                    break;
                case FORBIDDEN: // 网络禁止访问
                case NOT_FOUND:
                    ToastUtil.showToast( "请求失败,请稍后重试:" + error.code());
                    break;
                case REQUEST_TIMEOUT:
                    ToastUtil.showToast( "请求超时,请稍后重试:" + error.code());
                    break;
                case INTERNAL_SERVER_ERROR:
                case NOT_IMPLEMENTED: // 服务器不支持访问的方法
                case BAD_GATEWAY:
                    ToastUtil.showToast( "服务器异常,请稍后重试:" + error.code());
                    break;
                case SERVICE_UNAVAILABLE:
                    ToastUtil.showToast("服务器正在维护,请稍后重试:" + error.code());
                    break;
                default:
                    ToastUtil.showToast( "请求失败,请稍后重试:" + error.code());
                    break;
            }
        } else if (e instanceof UnknownHostException) { // is not http exception
            ToastUtil.showToast("网络连接异常，请检查网络");
        } else {
            ToastUtil.showToast("请求失败,请稍后重试");
        }
    }
}
