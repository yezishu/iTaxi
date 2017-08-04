package yzs.commonlibrary.view.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AutoCompleteTextView;

import java.util.HashMap;
import java.util.Map;

import yzs.commonlibrary.R;
import yzs.commonlibrary.base.CommonBaseRxMvpActivity;
import yzs.commonlibrary.base.constant.SType;
import yzs.commonlibrary.presenter.user.UserPresenter;
import yzs.commonlibrary.util.SPUtils;

/**
 * Des：
 * create by Zishu.Ye on 2017/8/3  9:59
 */
public class RegisterActivity extends CommonBaseRxMvpActivity<UserPresenter> implements IUserView,View.OnClickListener{

    Map<String ,?> mao=new HashMap<>();
    AutoCompleteAdapter autoCompleteAdapter;
    AutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        mao= SPUtils.getAll(this, SType.LOGIN_USERNAME);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setTag(R.drawable.c_blue_white_selector);
        Map.Entry<?, ?> entry = (Map.Entry<?, ?>) autoCompleteAdapter.getItem(0);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initPresenter() {
        mPresenter=new UserPresenter(this);
    }

    @Override
    public void showFailInfo(String errorInfo) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
