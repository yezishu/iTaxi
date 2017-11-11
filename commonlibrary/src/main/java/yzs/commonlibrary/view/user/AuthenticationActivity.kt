package yzs.commonlibrary.view.user

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_authentication.*
import yzs.commonlibrary.R
import yzs.commonlibrary.base.CommonBaseRxMvpActivity
import yzs.commonlibrary.base.constant.SState
import yzs.commonlibrary.presenter.user.AuthenticationPresenter
import yzs.commonlibrary.thridparty.GlideWrapper
import yzs.commonlibrary.util.StringUtils
import yzs.commonlibrary.util.ToastUtil
import yzs.commonlibrary.widget.multiImageselector.ImageResultAdapter
import yzs.commonlibrary.widget.multiImageselector.MultiImageSelector
import java.io.File

/**
 * Des：认证界面
 * create by Zishu.Ye on 2017/11/8  19:04
 */
@Route(path = "/commonlibrary/user/AuthenticationActivity")
class AuthenticationActivity : CommonBaseRxMvpActivity<AuthenticationPresenter>(), IAuthenticationView, View.OnClickListener {

    var file: File? = null
    var file1: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        initCreate()
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {
        iv_idcard_negative.setOnClickListener(this)
        iv_idcard_positive.setOnClickListener(this)
        submit_tv.setOnClickListener(this)
    }

    override fun initPresenter() {
        mPresenter = AuthenticationPresenter(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_idcard_negative -> choosePhoto(SState.opInt0)
            R.id.iv_idcard_positive -> choosePhoto(SState.opInt1)
            R.id.submit_tv -> commit()

            else -> {
            }
        }
    }

    private fun commit() {
        var nameStr = tv_name.text.toString().trim()
        var jszStr = tv_jsz.text.toString().trim()
        var cphStr = tv_cph.text.toString().trim()
        var xszStr = tv_xsz.text.toString().trim()

        if (isUnLegal(nameStr, jszStr, cphStr, xszStr)) {
            ToastUtil.showMessage("请正确填写信息")
            return
        }

        if (file == null
                || file1 == null) {
            ToastUtil.showMessage("请选择上传的图片")
            return
        }
        var imgs = ArrayList<String>()
        imgs.add(file!!.path)
        imgs.add(file1!!.path)
        var par = HashMap<String, String>()
        par.put("xm",nameStr)
        par.put("cph",cphStr)
        par.put("xszhm",xszStr)
        par.put("jszhm",jszStr)
        mPresenter.upLoad(imgs,par)

    }

    private fun isUnLegal(nameStr: String, jszStr: String, cphStr: String, xszStr: String): Boolean {
        if (StringUtils.isEmpty(nameStr, jszStr, cphStr, xszStr)) {
            return true
        }
        return false
    }

    private fun choosePhoto(type: Int) {
        RxPermissions(this)
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
                        choicePhoto(type)
                    }
                })
    }

    private fun choicePhoto(type: Int) {
        MultiImageSelector.create()
                .showCamera(true) // 是否显示相机. 默认为显示
                .single() // 单选模式
                .crop(false)
                .start(this, type)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == MultiImageSelector.RESULT_CODE) {
            val mSelectPath = ImageResultAdapter.map(data, resultCode)

            when (requestCode) {
                SState.opInt0 -> {
                    file = File(mSelectPath.get(0))
                    if (!StringUtils.isEmpty(mSelectPath.get(0))) {
                        GlideWrapper.with(this).load(mSelectPath.get(0)).centerCrop().error(R.drawable.take_picture_img).into(iv_idcard_negative)//反
                    }
                }
                SState.opInt1 -> {
                    file1 = File(mSelectPath.get(0))
                    if (!StringUtils.isEmpty(mSelectPath.get(0))) {
                        GlideWrapper.with(this).load(mSelectPath.get(0)).centerCrop().error(R.drawable.take_picture_img).into(iv_idcard_positive)//正
                    }
                }
            }
        }
    }
}
