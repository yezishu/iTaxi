package yzs.itaxi.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;

import yzs.commonlibrary.base.CommonBaseRxMvpActivity;

/**
 * Des：程序欢迎界面
 * creat by Zishu.Ye on 2017/11/11  14:51
 */
public class WelcomeActivity extends CommonBaseRxMvpActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().build("/commonlibrary/user/LoginActivity").navigation();
        finish();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
