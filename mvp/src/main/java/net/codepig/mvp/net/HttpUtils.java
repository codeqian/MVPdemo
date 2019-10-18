package net.codepig.mvp.net;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
* @description 网络请求封装
* @author nmssdmf
* @date 2018/6/15 0015 13:46
* @version v3.2.0
*/
public class HttpUtils {
    /**
    * @description 请求网络接口
    * @author nmssdmf
    * @date 2018/6/15 0015 14:27
    * @version v3.2.0
    */
    public static <T extends Base> void doHttp(CompositeDisposable subscription, Observable<T> observable, final ServiceCallback<T> callback){
        subscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new HttpObserver<T>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LoadingDialog.dismiss();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(T t) {
                        LoadingDialog.dismiss();
                        if (isReturnOk(t.getStatus_code())) {
                            callback.onSuccess(t);
                        } else {
                            if ("401".equals(t.getStatus_code())) {

                            } else {
                                ToastUtil.showMsg(t.getMessage());
                            }
                            callback.onDefeated(t);
                        }
                    }
                }));
    }

    /**
    * @description 接口返回status_code判断
    * @author nmssdmf
    * @date 2018/6/15 0015 14:27
    * @version v3.2.0
    */
    public static boolean isReturnOk(String statusCode){
        if (StringConfig.OK.equals(statusCode)) {
            return true;
        }else {
            return false;
        }
    }
}
