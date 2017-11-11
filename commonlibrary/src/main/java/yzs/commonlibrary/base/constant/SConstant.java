package yzs.commonlibrary.base.constant;

import yzs.commonlibrary.base.config.App;
import yzs.commonlibrary.base.config.AppConfig;
import yzs.commonlibrary.data.model.user.UserModel;
import yzs.commonlibrary.util.ACache;

/**
 * Des：存放基本全局变量
 * creat by Zishu.Ye on 2017/11/11  14:24
 */
public class SConstant {

    private static UserModel user = null;

    public static UserModel getUser() {
        if (user == null) {
            user = ACache.getCache(App.INSTANCE, AppConfig.LOGIN_USER_MODEL, UserModel.class);
        }
        return user;
    }

    public static void setUser(UserModel user) {
        SConstant.user = user;
    }


}
