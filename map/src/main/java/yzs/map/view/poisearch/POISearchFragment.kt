package yzs.map.view.poisearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import yzs.commonlibrary.base.CommonBaseRxMvpFragment
import yzs.map.R
import yzs.map.presenter.POISearchPresenter

/**
 * Des：请输入标题
 * create by zishu.ye
 */
class POISearchFragment : CommonBaseRxMvpFragment<POISearchPresenter>(), IPOISearchView {


    companion object {
        fun getInstance(): POISearchFragment {
            return POISearchFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_poisearch, container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initCreate()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        var a = POISearchFragment.getInstance()
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
