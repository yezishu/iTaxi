package yzs.commonlibrary.presenter.user

import android.app.Activity
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter

import yzs.commonlibrary.base.config.AppConfig
import yzs.commonlibrary.base.config.TokenConfig
import yzs.commonlibrary.base.constant.SConstant
import yzs.commonlibrary.data.model.user.UserModel
import yzs.commonlibrary.view.user.ILoginView

/**
 * Des：登录presenter
 * create by Zishu.Ye on 2017/8/3  16:04
 */
class LoginPresenter(view: ILoginView) : UserPresenter<ILoginView>(view) {

    fun login(context: Context, userName: String, passWord: String) {
        if (AppConfig.IS_NO_SERVER) {
            testData()
            ARouter.getInstance().build("/app/main/MainActivity").navigation()
            (context as Activity).finish()
        }
        register(userName, passWord, "")
    }

    /**
     * 没有服务器情况测试数据
     */
    private fun testData() {
        var user = UserModel()
        user.telno = "13799723567"
        user.xm = "测试账户"
        user.cph = "testCPH"
        user.jszhm = "testJSZH"
        user.xszhm = "testXSZHM"
        user.ptmc = "测试平台"
        user.driverid = "test_driver_id"

        TokenConfig.saveToken("test_token")
        SConstant.setUser(user)
    }
}
