package yzs.commonlibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.alibaba.android.arouter.launcher.ARouter;

import yzs.commonlibrary.BuildConfig;
import yzs.commonlibrary.base.config.TokenConfig;
import yzs.commonlibrary.base.constant.SConstant;
import yzs.commonlibrary.data.model.user.UserModel;
import yzs.commonlibrary.util.ACache;
import yzs.commonlibrary.util.ToastUtil;
import yzs.commonlibrary.view.user.LoginActivity;

/**
 * Created by Vamoose on 2015/9/22.
 */
public class CommonMethod {
    private static AlertDialog alertDialog;

    /**
     * 菜单点击事件
     *
     * @param context con
     */
    public static void MenuCLick(final Context context) {
        try {

        } catch (Exception e) {
            ToastUtil.showMessage("菜单点击异常。" + e.getMessage());
        }
    }

    /**
     * 显示推出登录对话框
     *
     * @param mContext con
     */
    private static void showLogoutDialog(final Context mContext) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("确认注销")
                // .setTitle("提示")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mCustomDialogInterface, int i) {
                        if (alertDialog != null) {
                            Logout(mContext);
                            ARouter.getInstance().build("/commonlibrary/user/LoginActivity").navigation();
                            ((Activity) mContext).finish();
                            alertDialog.dismiss();
                        }
                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mCustomDialogInterface, int i) {
                        if (alertDialog != null)
                            alertDialog.dismiss();
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private static void Logout(Context mContext) {

    }


    /**
     * 清除堆栈，进入登录界面
     */
    public static void ToLoginWithClear(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
//        MyApplication.getInstance().finishAllActivity();
    }

    /**
     * @param userModel user
     *                  登录时保存用户信息
     */
    public static void loginSaveData(UserModel userModel) {
        TokenConfig.saveTelephone(userModel.getTelno());
        SConstant.setUser(userModel);
    }

    /**
     * 退出时 数据销毁。
     *
     * @param context
     */
    public static void dataLogout(Context context) {
        ACache.clearALLCache(context);
        TokenConfig.logoutClean();
    }

    /**
     * @return
     */
    public static String isDebug() {
        return BuildConfig.DEBUG ? "debug" : "rc";
    }
}
