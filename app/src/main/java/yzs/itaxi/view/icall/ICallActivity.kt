package yzs.itaxi.view.icall

import android.os.Bundle
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import yzs.commonlibrary.base.CommonBaseRxMvpActivity
import yzs.itaxi.R
import yzs.itaxi.util.PhoneUtil

/**
 * Des：拦截来电界面
 * create by Zishu.Ye
 */
@Route(path = "/app_/ICallActivity")
class ICallActivity : CommonBaseRxMvpActivity<ICallPresenter>(), IICallView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icall)
    }

    override fun initView() {
        (findViewById(R.id.tv_phone) as TextView).text = intent.getStringExtra(EXTRA_PHONE_NUM)
        findViewById(R.id.btn_end_call).setOnClickListener { PhoneUtil.endCall(this@ICallActivity) }
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun initPresenter() {
        ICallPresenter(this)
    }

    override fun showFailInfo(errorInfo: String) {

    }

    companion object {
        /**
         * 来电号码extra
         */
        val EXTRA_PHONE_NUM = "phoneNum"
        /**
         * 挂断电话action
         */
        val ACTION_END_CALL = "com.likebamboo.phoneshow.ACTION_END_CALL"
    }
}