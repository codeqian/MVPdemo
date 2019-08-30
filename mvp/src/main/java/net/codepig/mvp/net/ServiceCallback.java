package net.codepig.mvp.net;

/**
 * @title service基础回调类
 */

public interface ServiceCallback<T> {

    /**
     * 当网络请求发生了错误时由service回调
     * <p>
     * 注：
     * <p>
     * 1. 如果需要实现次方法，因为统一的网络请求已经有error.printStackTrace()语句
     * <p>
     * 2. 如果一个页面只有一个请求可调用次方法，否则推荐自定义方法如：onLoginError.
     *
     * @param error
     */
    void onError(Throwable error);

    /**
     * 网络请求成功之后的回调
     * <p>
     * 注：如果一个页面只有一个请求可调用次方法，否则推荐自定义方法如：onLoginNext
     *
     * @param t
     */
    void onSuccess(T t);

    void onDefeated(T t);
}
