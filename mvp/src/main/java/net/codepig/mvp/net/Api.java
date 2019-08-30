package net.codepig.mvp.net;

import net.codepig.mvp.bean.BaseBean;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 接口汇总
 */
public interface Api {
    /**
     * 数据请求
     * @return
     */
    @GET("page?param=0")
    Call<BaseBean> getCall();

    /**
     * 表单提交
     * @return
     */
    @FormUrlEncoded
    @POST("page")
    Call<BaseBean> commodityDelete(@Field("id") String id);

    /**
     * 多字段表单提交
     * @return
     */
    @FormUrlEncoded
    @POST("page")
    Call<BaseBean> commodityOperation(@FieldMap Map<String, Object> params);

    /**
     * 上传文件
     * @return
     */
    @Multipart
    @POST("page")
    Call<BaseBean> uploadFile(@Part MultipartBody.Part body);
}
