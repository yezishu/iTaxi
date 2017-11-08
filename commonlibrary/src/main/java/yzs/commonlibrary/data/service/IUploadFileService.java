package yzs.commonlibrary.data.service;

import io.reactivex.Flowable;
import retrofit2.http.POST;
import retrofit2.http.Query;
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
    @POST("loadImage")
    Flowable<HttpResult<UploadImgModel>> uploadImgByBase64(@Query("filedata") String filedata,
                                                           @Query("driverid") String driverid );
}
