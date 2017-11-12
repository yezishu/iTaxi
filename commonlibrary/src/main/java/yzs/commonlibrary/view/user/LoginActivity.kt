package yzs.commonlibrary.view.user

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import yzs.commonlibrary.R
import yzs.commonlibrary.base.CommonBaseRxMvpActivity
import yzs.commonlibrary.base.constant.SType
import yzs.commonlibrary.presenter.user.LoginPresenter
import yzs.commonlibrary.util.SPUtils
import yzs.commonlibrary.util.StringUtils
import yzs.commonlibrary.util.ToastUtil
import java.util.Map

/**
 * Des：登陆界面
 * create by Zishu.Ye on 2017/8/3  9:02
 */
@Route(path = "/commonlibrary/user/LoginActivity")
class LoginActivity : CommonBaseRxMvpActivity<LoginPresenter>(), ILoginView, View.OnClickListener {

    var isOpen = false

    var userNameAdapter: AutoCompleteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initCreate()
    }

    override fun initView() {
        val userNameMap = SPUtils.getAll(this, SType.LOGIN_USERNAME)
        userNameAdapter = AutoCompleteAdapter(this, userNameMap)
        aet_phone.setAdapter(userNameAdapter)
        aet_phone!!.threshold = 0
        btn_pw_isvisible!!.tag = R.drawable.c_icon_pw_hide
    }

    override fun initData() {}

    override fun initListener() {
        btn_login.setOnClickListener(this)
        btn_dropdown.setOnClickListener(this)
        btn_pw_isvisible.setOnClickListener(this)
        newuser_tv.setOnClickListener(this)
        aet_phone.setOnItemClickListener { parent, view, position, id ->
            var entry = userNameAdapter!!.getItem(position) as Map.Entry<*, *>
            var key = entry.key as String
            aet_phone.setText(key)
            aet_phone.setSelection(key.length)
        }
    }

    override fun initPresenter() {
        mPresenter = LoginPresenter(this)
    }

    override fun showFailInfo(errorInfo: String) {
        ToastUtil.showMessage(errorInfo)
        ARouter.getInstance().build("/commonlibrary/user/AuthenticationActivity").navigation()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_dropdown -> lookHistoryUserName()
            R.id.btn_login -> login()
            R.id.btn_pw_isvisible -> switchPwVisible()
            R.id.newuser_tv -> ARouter.getInstance().build("/commonlibrary/user/RegisterActivity").navigation()
            else -> {
            }
        }
    }

    fun login() {
        var phone = aet_phone.text.toString().trim()
        var pw = ev_password!!.text.toString().trim()
        if (isUnLegal(phone, pw)) {
            ToastUtil.showMessage("输入参数不合法请检查")
            return
        }
        mPresenter.login(this, phone, pw)
    }

    private fun isUnLegal(phone: String, pw: String): Boolean {
        if (StringUtils.isEmpty(phone, pw))
            return true

        if (!StringUtils.isLegalPhoneNum(phone))
            return true
        return false
    }

    fun switchPwVisible() {
        if (btn_pw_isvisible!!.tag == R.drawable.c_icon_pw_hide) {
            ev_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            setPwStatus(R.drawable.c_icon_pw_show)
        } else {
            ev_password.transformationMethod = PasswordTransformationMethod.getInstance()
            setPwStatus(R.drawable.c_icon_pw_hide)
        }
    }

    fun setPwStatus(id: Int) {
        btn_pw_isvisible.setImageResource(id)
        btn_pw_isvisible!!.tag = id
        ev_password.setSelection(ev_password.length())
    }

    fun lookHistoryUserName() {
        if (!isOpen) {
            aet_phone.showDropDown()
            isOpen = true
        } else {
            isOpen = false
        }
    }

    override fun showRegister(info: String) {
        userNameAdapter!!.setShowData(SPUtils.getAll(this, SType.LOGIN_USERNAME))
        userNameAdapter!!.notifyDataSetChanged()
        ToastUtil.showMessage("登录成功")
        ARouter.getInstance().build("/app/main/MainActivity").navigation()
        finish()
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEventMainThread(event: RegisterEvent) {
        if (event.isSuccess)
            finish()
    }

}

