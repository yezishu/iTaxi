package yzs.commonlibrary.util;

import android.support.annotation.NonNull;

import yzs.commonlibrary.base.config.App;
import yzs.commonlibrary.base.config.AppConfig;

/**
 * Des：app 使用的sp 工具类
 * creat by Zishu.Ye on 2017/11/7  22:59
 */
public class AppSPUtils {

    /**
     * 设置 SP文件里存储的变量值
     *
     * @param key
     * @param value
     */
    public static void putSpConfigValue(String key, Object value) {
        if (value == null) {//忽略空key或者空value
            return;
        }

        if ( SPUtils.contains(App.INSTANCE, AppConfig.SP_FILE_NAME, key)) {
             SPUtils.remove(App.INSTANCE , AppConfig.SP_FILE_NAME, key);
        }
         SPUtils.put(App.INSTANCE, AppConfig.SP_FILE_NAME, key, value);
    }

    /**
     * 取得 P文件里存储的变量值
     * @return
     */
    public static Object getSpConfigValue(@NonNull String key, @NonNull Object defaultValue) {
        if (StringUtils.isEmpty(key)) {//忽略空key
            return null;
        }
        return  SPUtils.get(App.INSTANCE, AppConfig.SP_FILE_NAME, key, defaultValue).toString();
    }
}
