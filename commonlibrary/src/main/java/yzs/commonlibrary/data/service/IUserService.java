package yzs.commonlibrary.data.service;

import io.reactivex.Flowable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import yzs.commonlibrary.base.constant.Net;
import yzs.commonlibrary.data.model.user.UserModel;
import yzs.commonlibrary.data.model.user.RegisterModel;
import yzs.commonlibrary.data.net.HttpResult;

/**
 * Des：用户 模块的接口请求
 * create by Zishu.Ye on 2017/11/7  19:37
 */
public interface IUserService {

    String COMMOM_URL = "/ECar/service/driver/";

    /**
     * 注册登录
     */
    @POST(COMMOM_URL + "register.json")
    Flowable<HttpResult<RegisterModel>> register(@Query(Net.PARAMETER) String parameter);


    /**
     * 登录
     */
    @POST(COMMOM_URL + "logon.json")
    Flowable<HttpResult<RegisterModel>> login(@Query(Net.PARAMETER) String parameter);

    /**
     * 资质认证
     */
    @POST(COMMOM_URL + "identification")
    Flowable<HttpResult<RegisterModel>> identification(@Query(Net.PARAMETER) String parameter);

    /**
     * 用户个人信息
     */
    @POST(COMMOM_URL + "loadDetail")
    Flowable<HttpResult<UserModel>> info(@Query(Net.PARAMETER) String parameter);


}
