package yzs.itaxi.view.calllog

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route

import yzs.commonlibrary.base.CommonBaseRxMvpActivity
import yzs.itaxi.R
import yzs.itaxi.data.module.CallLogItem
import yzs.itaxi.presenter.calllog.CallLogPresenter

/**
 * Des：通话记录界面
 * create by Zishu.Ye
 */
@Route(path = "/app_/CallLogActivity")
class CallLogActivity : CommonBaseRxMvpActivity<CallLogPresenter>(), ICallLogView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_log)
        initCreate()
    }

    override fun initView() {

    }

    override fun initData() {
        mPresenter.getCallLog()
    }

    override fun initListener() {

    }

    override fun initPresenter() {
        mPresenter=CallLogPresenter(this, this)
    }

    override fun showFailInfo(errorInfo: String) {

    }

    override fun showCallLog(items: List<CallLogItem>) {

    }
}
