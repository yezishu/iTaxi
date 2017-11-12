package yzs.itaxi.view.main

import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*
import yzs.commonlibrary.base.constant.SConstant
import yzs.commonlibrary.util.StringUtils
import yzs.commonlibrary.util.ToastUtil
import yzs.commonlibrary.widget.CustomDialog
import yzs.itaxi.R
import yzs.itaxi.base.BaseActivity
import yzs.itaxi.data.module.NewsResponse
import yzs.itaxi.presenter.MainPresenter

@Route(path = "/app/main/MainActivity")
class MainActivity : BaseActivity<MainPresenter>(), MainContract.View, View.OnClickListener {

    private var customDialog: CustomDialog? = null

    override fun onClick(v: View) {
        when (v.id) {
//            R.id.btn_latest_list -> mPresenter.latestList("", ""/)
            R.id.btn_user -> userClick()
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
        tv_name!!.text = StringUtils.setTextWithCheckNull(SConstant.getUser().xm)
        btn_user.setOnClickListener(this)
    }

    override fun initPresenter() {
        mPresenter = MainPresenter(this)
    }

    override fun initData() {}

    override fun initListener() {

    }

    fun userClick(){
        if(StringUtils.isEmpty(SConstant.getUser().xm)){
            if (customDialog == null) {
                customDialog = CustomDialog(this, "", "您未认证信息，需要去进行认证", "确定", "取消")
                customDialog!!.setClicklistener(object : CustomDialog.ClickListenerInterface {
                    override fun doConfirm() {
                        ARouter.getInstance().build("/commonlibrary/user/AuthenticationActivity").navigation()
                        customDialog!!.dismiss()
                    }
                    override fun doCancel() {
                        customDialog!!.dismiss()
                    }
                })
            }
            customDialog!!.show()

        }else{
            ARouter.getInstance().build("/commonlibrary/user/InfoActivity").navigation()
        }
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
