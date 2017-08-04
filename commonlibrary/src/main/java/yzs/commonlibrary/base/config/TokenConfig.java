package yzs.commonlibrary.base.config;

import yzs.commonlibrary.util.SPUtils;
import yzs.commonlibrary.util.StringUtils;

/**
 * Des：token 配置类
 * create by Zishu.Ye on 2017/8/4  14:53
 */
public class TokenConfig {
    private static String token;

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
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        SPUtils.saveEncode(App.INSTANCE, AppConfig.SP_FILE_NAME, AppConfig.SP_KEY_TOKEN, token);
        TokenConfig.token = token;
        return true;
    }
}
