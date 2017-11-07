package yzs.commonlibrary.data.service;

import io.reactivex.Flowable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import yzs.commonlibrary.data.model.RegisterModel;
import yzs.commonlibrary.data.net.HttpResult;

/**
 * Des：用户 模块的接口请求
 * create by Zishu.Ye on 2017/11/7  19:37
 */
public interface IUserService {

    String COMMOM_URL = "/service/driver";

    /**
     * 获取问题状态
     */
    @POST("register")
    Flowable<HttpResult<RegisterModel>> register(@Query("telno") String token,
                                                 @Query("tjno") String tjno,
                                                 @Query("pwd") String pwd);

}
