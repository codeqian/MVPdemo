package net.codepig.mvp.net;

public class RxRequest extends BaseRxRequest {

    protected static final String TAG = RxRequest.class.getSimpleName();

    /**
     * 返回实体类http,增加版本号
     *
     * @return
     */
    public static <T>T create(Class<T> tClass, int version) {
        return createSSL(tClass, version, getToken());
    }

    /**
     * 发送网络请求方法
     *
     * @param version 版本
     * @return
     */
    public static <T>T create(Class<T> tClass, int version, String token) {
        return createSSL(tClass, version,token);
    }

}
