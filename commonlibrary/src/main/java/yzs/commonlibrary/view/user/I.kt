package yzs.commonlibrary.view.user

import android.content.Context

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import yzs.commonlibrary.widget.CustomDialog

/**
 * Des：
 * creat by Zishu.Ye on 2017/11/11  11:01
 */
class I {
    internal var a = "d"
    internal var b = "d"

    private var customDialog: CustomDialog? = null

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEventMainThread(event: RegisterEvent, context: Context) {
        // 拨打电话
        if (customDialog == null) {
            customDialog = CustomDialog(context, "咨询客服", "是否拨打  客服电话?", "确定", "取消")
            customDialog!!.setClicklistener(object : CustomDialog.ClickListenerInterface {
                override fun doConfirm() {

                }

                override fun doCancel() {
                    customDialog!!.dismiss()
                }
            })
        }
        customDialog!!.show()
    }
}
