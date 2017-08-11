package yzs.map.view.poisearch

import android.os.Bundle

import yzs.commonlibrary.base.CommonBaseRxMvpActivity
import yzs.map.R
import yzs.map.presenter.POISearchPresenter

/**
 * Des：请输入标题
 * create by zishu.ye
 */
class POISearchActivity : CommonBaseRxMvpActivity<POISearchPresenter>(), IPOISearchView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poisearch)
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun initPresenter() {
        POISearchPresenter(this)
    }

    override fun showFailInfo(errorInfo: String) {

    }
}
