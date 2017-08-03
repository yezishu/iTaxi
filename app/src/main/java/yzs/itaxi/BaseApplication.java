package yzs.itaxi;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zhy.autolayout.config.AutoLayoutConifg;

import yzs.commonlibrary.util.AllUtilConfig;

/**
 * Des：底层application
 * create by Zishu.Ye on 2017/5/31  9:35
 */
public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
    }

    private void initConfig() {
        AllUtilConfig.applicationContext = this;
        AllUtilConfig.DES_Key = "sfasfsdf";
        AllUtilConfig.LogSwitch = BuildConfig.DEBUG;

        if ( AllUtilConfig.LogSwitch) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        //路由框架初始化
        ARouter.init(this);

        initAutoLayout();
    }

    /**
     * 自适应布局框架初始化
     */
    private void initAutoLayout() {
        AutoLayoutConifg.getInstance().useDeviceSize();
    }
}
