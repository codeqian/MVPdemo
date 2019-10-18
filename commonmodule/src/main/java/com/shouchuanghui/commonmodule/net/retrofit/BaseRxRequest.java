package com.shouchuanghui.commonmodule.net.retrofit;


import com.google.gson.Gson;
import com.shouchuanghui.commonmodule.config.BaseConfig;
import com.shouchuanghui.commonmodule.config.PreferenceConfig;
import com.shouchuanghui.commonmodule.net.IServiceLib;
import com.shouchuanghui.commonmodule.net.OkHttpClientProvider;
import com.shouchuanghui.commonmodule.util.PreferenceUtil;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author huscarter
 * @date 2017/06/15
 */
public class BaseRxRequest {

    protected static final String TAG = BaseRxRequest.class.getSimpleName();

    /**
     * 请求接口的版本号
     */
    public static final int DEFAULT_VERSION = 1;

    /**
     * 默认请求是不重新请求
     */
    public static final boolean RETRY_REQUEST = false;


    public BaseRxRequest() {
        //
    }

    /**
     * 返回实体类http,增加版本号
     * @return
     */
    public static IServiceLib createLib() {
        return createSSL(IServiceLib.class, 1, getToken());
    }

    public static IServiceLib createLib(int version) {
        return createSSL(IServiceLib.class, version, getToken());
    }

    public static <T> T createLib(Class<T> c, int version) {
        return createSSL(c, version, getToken());
    }

    /**
     * 返回实体类,http自定token
     *
     * @param c
     * @param <T>
     * @retur
     */
    public static <T> T createLib(Class<T> c, String token) {
        return createSSL(c, DEFAULT_VERSION, token);
    }

    public static <T> T createLib(Class<T> c, String token, int version) {
        return createSSL(c, version, token, RETRY_REQUEST);
    }

    /**
     * 最底层获取请求实体的方法,http
     * 返回实体类,http自定token
     *
     * @param clazz
     * @param <T>
     * @retur
     */
    public static <T> T createLib(Class<T> clazz, int version, String token, boolean retry) {
        Retrofit.Builder builder = getRetrofitBuilder(version, token, retry);
        return builder.build().create(clazz);
    }

    /**
     * 获取最底层的Retrofit，没有添加转换器，如果需要返回 Gson 格式请自己添加GsonConverterFactory
     *
     * @param version
     * @param token
     * @param retry
     * @return
     */
    public static Retrofit.Builder getRetrofitBuilder(int version, String token, boolean retry) {
        return new Retrofit.Builder()
                .baseUrl(BaseConfig.ROOTURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(OkHttpClientProvider.getInstance().getClient(version, token));
    }

    //---------------------------------使用SSL方法分界线--------------------------------------------------------------

    /**
     * 发送网络请求方法
     *
     * @param version 版本
     * @return
     */
    public static <T> T createSSL(Class<T> clazz, int version) {
        return createSSL(clazz, version, getToken());
    }

    /**
     * 发送网络请求方法
     *
     * @param version 版本
     * @return
     */
    public static <T> T createSSL(Class<T> clazz, int version, String token) {
        return createSSL(clazz, version, token, RETRY_REQUEST);
    }

    /**
     * 最底层获取请求实体的方法,https
     * 返回实体类,http自定token
     *
     * @param clazz
     * @param <T>
     * @retur
     */
    public static <T> T createSSL(Class<T> clazz, int version, String token, boolean retry) {
        Retrofit.Builder builder = getSSLRetrofitBuilder(version, token, retry);
        return builder.build().create(clazz);
    }

    /**
     * 获取最底层的Retrofit，没有添加转换器，如果需要返回 Gson 格式请自己添加GsonConverterFactory
     *
     * @param version
     * @param token
     * @param retry
     * @return
     */
    public static Retrofit.Builder getSSLRetrofitBuilder(int version, String token, boolean retry) {
        return new Retrofit.Builder()
                .baseUrl(BaseConfig.ROOTURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(OkHttpClientProvider.getInstance().getClientSSL(version, token));
    }


    //------------------------------------公共方法------------------------------------------------------------

    public static void unsubscribeIfNotNull(CompositeDisposable subscription) {
        if (subscription != null) {
            subscription.clear();
        }
    }

    public static CompositeDisposable getSubscription(CompositeDisposable subscription) {
        if (subscription == null || subscription.isDisposed()) {
            return new CompositeDisposable();
        }
        return subscription;
    }

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() {
        return PreferenceUtil.getString(PreferenceConfig.TOKEN, "");
    }

    /**
     * 获取HttpClient
     *
     * @return
     */
    public static OkHttpClient getClient() {
        OkHttpClient client = OkHttpClientProvider.getInstance().getClient(DEFAULT_VERSION, getToken());
        return client;
    }

}
