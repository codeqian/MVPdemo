package net.codepig.mvp.net;

public interface MyListener<T> {
    void onSuccess(T result);
    void onError(String errorMsg);
}
