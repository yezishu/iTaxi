package yzs.itaxi.view.icall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_call_log.*
import yzs.itaxi.R
import yzs.itaxi.base.BaseFragment
import yzs.itaxi.data.module.CallLogItem
import yzs.itaxi.presenter.calllog.CallLogPresenter

/**
 * Des：
 * create by Zishu.Ye on 2017/8/9  16:06
 */
class CallLogListFragment : BaseFragment<CallLogPresenter>(), ICallLogView {

    internal var callLogAdapter = CallLogAdapter(ArrayList<CallLogItem>())

    companion object {
        fun getInstance(): CallLogListFragment {
            return CallLogListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_call_log, container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initCreate()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        rv_call_log.adapter = callLogAdapter;

    }

    override fun initData() {
        mPresenter.getCallLog()
    }

    override fun initListener() {

    }

    override fun initPresenter() {
        mPresenter = CallLogPresenter(context, this)
    }

    override fun showCallLog(items: MutableList<CallLogItem>) {
        callLogAdapter.setNewData(items)
    }

    override fun showFailInfo(errorInfo: String?) {
    }
}
