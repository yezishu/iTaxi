package yzs.commonlibrary.view.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.jetbrains.annotations.NotNull;

import yzs.commonlibrary.R;
import yzs.commonlibrary.base.CommonBaseRxMvpActivity;
import yzs.commonlibrary.data.model.RegisterModel;
import yzs.commonlibrary.presenter.user.UserPresenter;
import yzs.commonlibrary.util.StringUtils;
import yzs.commonlibrary.util.ToastUtil;

/**
 * Des：注册界面
 * create by Zishu.Ye on 2017/8/3  9:59
 */
@Route(path = "/commonlibrary/user/RegisterActivity")
public class RegisterActivity extends CommonBaseRxMvpActivity<UserPresenter> implements IUserView, View.OnClickListener {

    EditText et_telephone;
    EditText et_pw;
    EditText et_tjNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initCreate();
    }

    @Override
    public void initView() {
        et_telephone = (EditText) findViewById(R.id.et_telephone);
        et_pw = (EditText) findViewById(R.id.et_pw);
        et_tjNo = (EditText) findViewById(R.id.et_tjNo);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = et_telephone.getText().toString().trim();
                String pw = et_pw.getText().toString().trim();
                String tjNo = et_tjNo.getText().toString().trim();
                if (isUnLegal(phone, pw, tjNo)) {
                    ToastUtil.showMessage("输入参数不合法请检查");
                }
            }
        });
    }

    private boolean isUnLegal(String phone, String pw, String tjNo) {
        if (StringUtils.isEmpty(phone, pw, tjNo))
            return true;

        if (StringUtils.isLegalPhoneNum(phone))
            return true;
        return false;
    }

    @Override
    public void initPresenter() {
        mPresenter = new UserPresenter(this);
    }

    @Override
    public void showFailInfo(String errorInfo) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void showRegister(@NotNull RegisterModel model) {

    }
}
