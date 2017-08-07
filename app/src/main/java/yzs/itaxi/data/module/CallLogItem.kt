package yzs.itaxi.data.module

/**
 * Des：通话记录 item
 * create by Zishu.Ye on 2017/8/7  9:43
 */
class CallLogItem {
    companion object {
        val TYPE_INCOMING="0" //呼入
        val TYPE_OUTGOING="1" //呼入
        val TYPE_MISSED="2"   //未接
        val TYPE_UNDEFINE="-1"   //未知

    }
    var name: String=""
    var num: String=""
    var type: String=""
    var date: String=""
    var duration: Long=0
}
