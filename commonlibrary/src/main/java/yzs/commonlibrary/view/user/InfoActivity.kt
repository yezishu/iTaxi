package yzs.commonlibrary.view.user

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user_info.*
import yzs.commonlibrary.R
import yzs.commonlibrary.base.CommonBaseRxMvpActivity
import yzs.commonlibrary.data.model.user.InfoModel
import yzs.commonlibrary.presenter.user.InfoPresenter

/**
 * Des：用户信息界面
 * creat by Zishu.Ye on 2017/11/11  11:23
 */
class InfoActivity : CommonBaseRxMvpActivity<InfoPresenter>(), IInfoView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        initCreate()
    }

    override fun initPresenter() {
        mPresenter = InfoPresenter(this)
    }

    override fun initView() {

    }

    override fun initData() {
        mPresenter.getInfo()
    }

    override fun initListener() {

    }

    override fun showInfo(infoModel: InfoModel) {
        tv_name.text = infoModel!!.xm
        tv_tel.text = infoModel!!.telno
        tv_cph.text = infoModel!!.cph
        tv_jsz.text = infoModel!!.jszhm
        tv_xsz.text = infoModel!!.xszhm
        tv_ptmc.text = infoModel!!.ptmc
    }
}
