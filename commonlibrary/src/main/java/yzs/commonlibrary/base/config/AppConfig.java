package yzs.commonlibrary.base.config;

/**
 * Des：应用全局变量类
 * create by Zishu.Ye on 2017/8/4  14:47
 */
public class AppConfig {

    public final static boolean IS_NO_SERVER = true;//配置是否没有服务器调试模式

    /**
     * 以下是SP、Lrucache存储的Key名
     */
    public final static String SP_FILE_NAME = "config";//基础配置·SP存储文件名
    public final static String SP_KEY_TOKEN = "token"; // token

    public final static String SP_USER_ID = "userId"; // userId
    public final static String SP_KEY_USER_TELEPHONE = "userTelephone"; // 用户手机号
    public final static String LOGIN_USER_MODEL = "LOGIN_USER_MODEL"; // 登录的用户信息
}
