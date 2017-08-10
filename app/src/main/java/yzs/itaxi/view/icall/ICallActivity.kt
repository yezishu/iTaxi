package yzs.itaxi.view.icall

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_icall.*
import yzs.commonlibrary.base.CommonBaseRxMvpActivity
import yzs.commonlibrary.widget.actionsheetdialog.ActionSheetDialog
import yzs.itaxi.R
import yzs.itaxi.util.PhoneUtil

/**
 * Des：拦截来电界面
 * create by Zishu.Ye
 */
@Route(path = "/app_/ICallActivity")
class ICallActivity : CommonBaseRxMvpActivity<ICallPresenter>(), IICallView, View.OnClickListener {

    var numberPicker: ActionSheetDialog? = null
    internal var number = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icall)
        initCreate()
    }

    override fun initView() {
        setNumber(this.number)
        (findViewById(R.id.tv_phone) as TextView).text = intent.getStringExtra(EXTRA_PHONE_NUM)
    }

    override fun initData() {
    }

    override fun initListener() {
        tv_number.setOnClickListener(this)
        btn_end_call.setOnClickListener(this)
        btn_answer_call.setOnClickListener(this)
        btn_hf.setOnClickListener {
            if(PhoneUtil.switchAudio(this)){
                btn_hf.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimaryDark))
            }else{
                btn_hf.setBackgroundColor(ContextCompat.getColor(this,R.color.c_bg_99gray))
            }
        }
    }

    override fun initPresenter() {
        mPresenter = ICallPresenter(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_number -> showNumberPicker()
            R.id.btn_end_call -> PhoneUtil.endCall(this)
            R.id.btn_answer_call -> PhoneUtil.answerCall(this)
            else -> {
            }
        }
    }

    override fun showFailInfo(errorInfo: String) {

    }

    fun showNumberPicker() {
        if (numberPicker == null) {
            numberPicker = ActionSheetDialog(this)
                    .builder()
                    .setTitle(R.string.a_number_title)
                    .setCancelable(true)
                    .setCanceledOnTouchOutside(true)
                    .addSheetItem("1", ActionSheetDialog.SheetItemColor.Blue,
                            { which: Int -> setNumber(which) })
                    .addSheetItem("2", ActionSheetDialog.SheetItemColor.Blue,
                            { which: Int -> setNumber(which) })
                    .addSheetItem("3", ActionSheetDialog.SheetItemColor.Blue,
                            { which: Int -> setNumber(which) })
                    .addSheetItem("4", ActionSheetDialog.SheetItemColor.Blue,
                            { which: Int -> setNumber(which) })

        }
        numberPicker!!.show()
    }

    fun setNumber(num: Int) {
        tv_number.setText("$num 人")
        this.number = num
    }

    companion object {
        /**
         * 来电号码extra
         */
        val EXTRA_PHONE_NUM = "phoneNum"
        /**
         * 挂断电话action
         */
        val ACTION_END_CALL = "com.likebamboo.phoneshow.ACTION_END_CALL"
    }


}