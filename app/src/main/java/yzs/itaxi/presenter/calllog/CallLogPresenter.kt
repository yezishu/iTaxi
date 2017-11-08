package yzs.itaxi.presenter.calllog


import android.app.Activity
import android.content.Context
import android.provider.CallLog
import yzs.commonlibrary.presenter.base.BaseRxPresenter
import yzs.commonlibrary.thridparty.rxpermission.PhonePermissionUtil
import yzs.commonlibrary.util.dateutil.DateUtil
import yzs.commonlibrary.util.StringUtils
import yzs.itaxi.data.module.CallLogItem
import yzs.itaxi.view.icall.ICallLogView
import java.util.*

/**
 * Des：通话记录界面
 * Created by Zishu.Ye
 */
class CallLogPresenter(private val mContent: Context, view: ICallLogView) : BaseRxPresenter<ICallLogView>(view) {

    fun getCallLog() {
        PhonePermissionUtil.requestPermission(mContent as Activity, object : PhonePermissionUtil.PhoneObserver() {
            override fun granted() {
                super.granted()
                mView.showCallLog(getCallLogData(mContent))
            }
        })
    }

    fun getCallLogData(activity: Activity): List<CallLogItem> {
        val cs=activity.contentResolver.query(CallLog.Calls.CONTENT_URI,
                arrayOf(CallLog.Calls.CACHED_NAME,  //姓名
                    CallLog.Calls.NUMBER,    //号码
                    CallLog.Calls.TYPE,  //呼入/呼出(2)/未接
                    CallLog.Calls.DATE,  //拨打时间
                    CallLog.Calls.DURATION ), //通话时长
                null,
                null,
                CallLog.Calls.DEFAULT_SORT_ORDER)
        var callLogItems=ArrayList<CallLogItem>()
        var i=0
        if(cs==null || cs.count<=0){
            return callLogItems
        }
        cs.moveToFirst()
        while(!cs.isAfterLast&&i<30){
            var item=CallLogItem()
            item.name=StringUtils.setTextWithCheckNull(cs.getString(0))
            item.num=cs.getString(1)
            when(cs.getInt(2)){
                CallLog.Calls.INCOMING_TYPE-> item.type=CallLogItem.TYPE_INCOMING
                CallLog.Calls.OUTGOING_TYPE-> item.type=CallLogItem.TYPE_OUTGOING
                CallLog.Calls.MISSED_TYPE-> item.type=CallLogItem.TYPE_MISSED
                else->item.type=CallLogItem.TYPE_UNDEFINE
            }
            item.date= DateUtil.SIMPLE_DATE_FORMAT.format(Date(cs.getLong(3)))
            item.duration=cs.getLong(4)
            callLogItems.add(item)
            i++
            cs.moveToNext()
        }

        return callLogItems
    }
}
