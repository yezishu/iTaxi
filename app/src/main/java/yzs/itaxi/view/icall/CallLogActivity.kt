package yzs.itaxi.view.icall

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_call_log.*
import yzs.commonlibrary.base.CommonBaseRxMvpActivity
import yzs.itaxi.R
import yzs.itaxi.presenter.calllog.CallLogPresenter

/**
 * Des：通话记录界面
 * create by Zishu.Ye
 */
@Route(path = "/app_/CallLogActivity")
class CallLogActivity : CommonBaseRxMvpActivity<CallLogPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_log)
        initView(savedInstanceState)
        initCreate()
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    fun initView(savedInstanceState: Bundle?){
        if (savedInstanceState == null) {
            attachCategoryGridFragment()
        } else {
//            setProgressBarVisibility(View.GONE)
        }
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun initPresenter() {

    }

    private fun attachCategoryGridFragment() {
        val supportFragmentManager = supportFragmentManager
        var fragment = supportFragmentManager.findFragmentById(R.id.call_log_container)
        if (fragment !is CallLogListFragment) {
            fragment = CallLogListFragment.getInstance()
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.call_log_container, fragment)
                .commit()
    }


}
