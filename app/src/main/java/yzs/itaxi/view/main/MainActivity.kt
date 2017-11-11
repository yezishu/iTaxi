package yzs.itaxi.view.main

import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*
import yzs.commonlibrary.base.constant.SConstant
import yzs.commonlibrary.util.ToastUtil
import yzs.itaxi.R
import yzs.itaxi.base.BaseActivity
import yzs.itaxi.data.module.NewsResponse
import yzs.itaxi.presenter.MainPresenter

@Route(path = "/app/main/MainActivity")
class MainActivity : BaseActivity<MainPresenter>(), MainContract.View, View.OnClickListener {


    override fun onClick(v: View) {
        when (v.id) {
//            R.id.btn_latest_list -> mPresenter.latestList("", ""/)
            R.id.btn_user -> ARouter.getInstance().build("/commonlibrary/user/InfoActivity").navigation()
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        initCreate()
        ARouter.getInstance().build("/commonlibrary/user/InfoActivity").navigation()
    }

    override fun init() {
        super.init()
    }

    override fun initView() {
        tv_name!!.text = SConstant.getUser().xm
        btn_user.setOnClickListener(this)
    }

    override fun initPresenter() {
        mPresenter = MainPresenter(this)
    }

    override fun initData() {}

    override fun initListener() {

    }


    override fun showData(data: String) {

    }

    override fun showLatestInfo(newsResponse: NewsResponse) {
        ToastUtil.showMessage("search success ：" + newsResponse.stories.size)
    }

    override fun showFailInfo(errorInfo: String) {
        ToastUtil.showMessage(errorInfo)
    }
}
