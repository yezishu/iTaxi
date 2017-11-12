package yzs.commonlibrary.base.constant;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import yzs.commonlibrary.base.config.TokenConfig;

/**
 * Des：网络请求 常量类
 * create by Zishu.Ye on 2017/11/7  20:09
 */
public class Net {

    //    public static final String URL_HOST = "http://localhost:8080/ECar/";
//    public static final String URL_HOST = "http://172.16.33.195:8082";
    public static final String URL_HOST = "http://192.168.1.102:8080";

    public static final String PARAMETER = "parameter";
    public static final String mToken = "token";
    public static final String USER_ID = "driverid";
    /**
     * http表单请求的form头
     */
    public static final String HEAD_FORM = "Content-Type: application/x-www-form-urlencoded; charset=UTF-8";

    /**
     * 状态码
     */
    public static final String RESPONSE_SUCCESS_CODE = "00000";//成功
    public static final String RESPONSE_FAIL_CODE = "20000";//失败
    public static final String RESPONSE_ERROR_CODE = "10000";//异常
    public static final String RESPONSE_TOKEN_INVALID_OTHER = "to";//token 失效

    /**
     * 提示信息
     */
    public static final String CONNECT_FAIL_MSG = "连接请求出现问题，请您检查网络是否连接。";
    public static final String RESPONSE_FAIL_MSG = "请求服务器出现问题，请稍后再试。";
    public static final String RESPONSE_TOKEN_INVALID_MSG = "用户信息过期";

    /**
     * @return  获取统一参数请求体
     */
    public static final Map<String, Object> getRequestMap() {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put(Net.USER_ID, TokenConfig.getToken());
        return parameterMap;
    }

    /**
     * @param parameterMap  请求键值对
     * @return  json
     */
    public static String getRequestJson(Map<String, Object> parameterMap){
        Gson g=new Gson();
        return g.toJson(parameterMap);
    }

}
