package yzs.itaxi.view.icall

import android.support.v4.content.ContextCompat
import android.widget.TextView
import yzs.commonlibrary.thridparty.autolayout.AutoQuickAdapter
import yzs.commonlibrary.thridparty.autolayout.AutoViewHolder
import yzs.commonlibrary.util.StringUtils
import yzs.itaxi.R
import yzs.itaxi.data.module.CallLogItem

/**
 * Des：通话记录
 * create by Zishu.Ye on 2017/8/9  16:09
 */
class CallLogAdapter(data: List<CallLogItem>) : AutoQuickAdapter<CallLogItem, AutoViewHolder>(R.layout.item_call_log, data) {

    override fun convert(helper: AutoViewHolder, item: CallLogItem?) {
        val tvNum = helper.getView<TextView>(R.id.tv_num)
        when (item!!.type) {
            CallLogItem.TYPE_INCOMING -> tvNum.setTextColor(getColor(R.color.colorPrimary))
            CallLogItem.TYPE_OUTGOING -> tvNum.setTextColor(getColor(R.color.colorAccent))
            CallLogItem.TYPE_MISSED -> tvNum.setTextColor(getColor(R.color.c_text_99gray))
            CallLogItem.TYPE_UNDEFINE -> tvNum.setTextColor(getColor(R.color.c_text_22black))
        }
        if (StringUtils.isEmpty(item!!.name)) {
            tvNum.text = item!!.num
        } else {
            tvNum.text = "${item!!.num} (${item!!.name})"
        }

        helper.setText(R.id.tv_time, item!!.date)
    }

    fun getColor(id: Int): Int {
        return ContextCompat.getColor(mContext, id)
    }
}

