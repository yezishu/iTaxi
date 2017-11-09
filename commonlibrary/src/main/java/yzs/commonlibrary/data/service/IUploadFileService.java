package yzs.commonlibrary.data.service;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import yzs.commonlibrary.base.constant.Net;
import yzs.commonlibrary.data.model.UploadImgModel;
import yzs.commonlibrary.data.net.HttpResult;

/**
 * Des：上传service
 * create by Zishu.Ye on 2017/11/8  20:11
 */
public interface IUploadFileService {


    String COMMOM_URL = "/service/driver";

    /**
     * 获取问题状态
     */
    @Headers(Net.HEAD_FORM)
    @FormUrlEncoded
    @POST("/team/up")
    Flowable<HttpResult<UploadImgModel>> uploadImgByBase64(@Field("filedata") String filedata,
                                                           @Query("driverid") String driverid );
}
