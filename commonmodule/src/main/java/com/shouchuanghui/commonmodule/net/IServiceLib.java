package com.shouchuanghui.commonmodule.net;

import com.shouchuanghui.commonmodule.bean.BaseData;
import com.shouchuanghui.commonmodule.bean.UpdateInfo;
import com.shouchuanghui.commonmodule.bean.Upload;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author huscarter@163.com
 * @title
 * @description
 * @date 7/10/17
 */

public interface IServiceLib {
    /**
     * 上传图片
     *
     * @param body
     * @return
     */
    @Multipart
    @POST("/image/upload")
    Observable<Upload> uploadImage(@Part MultipartBody.Part body);

    /**
     * 上传文件
     * @param body
     * @return
     */
    @Multipart
    @POST("/file/upload")
    Observable<Upload> uploadFile(@Part MultipartBody.Part body);

    /**
     * 获取APP版本更新信息
     * @return
     */
    @GET("/api/app/update_info")
    Observable<BaseData<UpdateInfo>> getUpdateInfo(@Query("system") String system);
}
