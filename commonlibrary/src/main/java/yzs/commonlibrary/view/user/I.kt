package yzs.commonlibrary.view.user

import android.Manifest
import android.app.Activity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import yzs.commonlibrary.data.model.RegisterModel
import yzs.commonlibrary.util.ToastUtil
import java.util.*

/**
 * Des：
 * create by Zishu.Ye on 2017/11/7  20:50
 */
class I {


    internal fun showRegister(model: RegisterModel, activity: Activity) {
        val img = ArrayList<String>()
        img.add("ddd")
        img.add("ddd")
        RxPermissions(activity)
                .requestEach(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(Consumer { permission ->
                    if (permission.name == Manifest.permission.CAMERA) {
                        if (!permission.granted) {
                            ToastUtil.showMessage("相机权限未开启，无法使用拍照上传")
                            return@Consumer
                        }
                        if (permission.name == Manifest.permission.READ_EXTERNAL_STORAGE) {
                            if (!permission.granted) {
                                ToastUtil.showMessage("访问手机存储权限未开启，无法使用拍照上传")
                                return@Consumer
                            }
                        }

                    }
                })
    }
}
