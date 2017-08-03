package yzs.commonlibrary.view.user

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_login.*
import yzs.commonlibrary.R
import yzs.commonlibrary.base.CommonBaseRxMvpActivity
import yzs.commonlibrary.base.constant.SType
import yzs.commonlibrary.presenter.user.LoginPresenter
import yzs.commonlibrary.util.SPUtils
import java.util.Map

/**
 * Des：登陆界面
 * create by Zishu.Ye on 2017/8/3  9:02
 */
@Route(path = "/commonlibrary/loginactivity")
class LoginActivity : CommonBaseRxMvpActivity<LoginPresenter>() ,ILoginView, View.OnClickListener{

    var isOpen=false

    var userNameAdapter :AutoCompleteAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initCreate()
    }

    override fun initView() {
        var userNameMap=SPUtils.getAll(this,SType.LOGIN_USERNAME)
        userNameAdapter= AutoCompleteAdapter(this,userNameMap)
        aet_phone.setAdapter(userNameAdapter)
        aet_phone!!.threshold=0
    }

    override fun initData() {
    }

    override fun initListener() {
        btn_login.setOnClickListener (this)
        btn_dropdown.setOnClickListener(this)
        aet_phone.setOnItemClickListener { parent, view, position, id ->
            var entry=userNameAdapter!!.getItem(position) as Map.Entry<*, *>
            var key=entry.key as String
            aet_phone.setText(key)
            aet_phone.setSelection(key.length)
        }
    }

    override fun initPresenter() {
        mPresenter=LoginPresenter(this)
    }

    override fun showFailInfo(errorInfo: String) {

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_dropdown->lookHistoryUserName()
            R.id.btn_login-> mPresenter.login(this,aet_phone.text.toString(),"")
            else ->{}
        }
    }

    fun lookHistoryUserName(){
        if (!isOpen) {
            aet_phone.showDropDown()
            isOpen = true
        } else {
            isOpen = false
        }
    }

    override fun loginSuccess() {
        userNameAdapter!!.setShowData(SPUtils.getAll(this,SType.LOGIN_USERNAME))
        userNameAdapter!!.notifyDataSetChanged()
    }

}
