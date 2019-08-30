package net.codepig.mvp.net;

import net.codepig.mvp.base.BaseConfig;
import net.codepig.mvp.bean.BaseBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRequest {
    public static void request() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseConfig.ROOTURL) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 创建 网络请求接口 的实例
        Api request = retrofit.create(Api.class);

        //对 发送请求 进行封装
        Call<BaseBean> call = request.getCall();

        //发送网络请求(异步)
        call.enqueue(new Callback<BaseBean>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                //处理返回的数据结果
                System.out.println(response.body().toString());
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<BaseBean> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }
}
