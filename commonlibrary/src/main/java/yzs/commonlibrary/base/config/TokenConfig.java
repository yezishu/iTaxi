package yzs.commonlibrary.base.config;

import yzs.commonlibrary.util.SPUtils;
import yzs.commonlibrary.util.StringUtils;

/**
 * Des：token 配置类
 * create by Zishu.Ye on 2017/8/4  14:53
 */
public class TokenConfig {

    private static String token;
    private static String telephone;

    /**
     * 如果缓存变量为null则去 sp文件获取
     *
     * @return 返回token
     */
    public synchronized static String getToken() {
        if (!StringUtils.isEmpty(token)) {
            return token;
        }
        token = SPUtils.get(App.INSTANCE, AppConfig.SP_FILE_NAME, AppConfig.SP_KEY_TOKEN, "").toString();
        return token;
    }

    /**
     * 保存token 到sp 并缓存
     *
     * @param token token
     * @return boolean
     */
    public synchronized static boolean saveToken(String token) {
        if (token == null) {
            return false;
        }
        SPUtils.saveEncode(App.INSTANCE, AppConfig.SP_FILE_NAME, AppConfig.SP_KEY_TOKEN, token);
        TokenConfig.token = token;
        return true;
    }

    public synchronized static String getTelephone() {
        if (!StringUtils.isEmpty(telephone)) {
            return telephone;
        }
        telephone = SPUtils.get(App.INSTANCE, AppConfig.SP_FILE_NAME, AppConfig.SP_KEY_USER_TELEPHONE, "").toString();
        return telephone;
    }

    public synchronized static boolean saveTelephone(String telephone) {
        if (telephone == null) {
            return false;
        }
        SPUtils.saveEncode(App.INSTANCE, AppConfig.SP_FILE_NAME, AppConfig.SP_KEY_USER_TELEPHONE, telephone);
        TokenConfig.telephone = telephone;
        return true;
    }

    /**
     * 退出登录时清除记录
     */
    public static void logoutClean(){
        TokenConfig.saveTelephone("");
        TokenConfig.saveToken("");
    }
}
