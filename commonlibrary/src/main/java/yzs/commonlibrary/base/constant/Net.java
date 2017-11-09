package yzs.commonlibrary.base.constant;

/**
 * Des：网络请求 常量类
 * create by Zishu.Ye on 2017/11/7  20:09
 */
public class Net {

//    public static final String URL_HOST = "http://localhost:8080/ECar/";
    public static final String URL_HOST = "http://172.16.33.195:8082";

    public static final String mParameter = "parameter";
    public static final String mToken = "token";
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

}
