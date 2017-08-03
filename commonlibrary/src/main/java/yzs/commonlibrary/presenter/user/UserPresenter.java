package yzs.commonlibrary.presenter.user;

import android.content.Context;

import yzs.commonlibrary.base.BaseRxPresenter;
import yzs.commonlibrary.base.constant.SType;
import yzs.commonlibrary.util.SPUtils;
import yzs.commonlibrary.view.user.IUserView;

/**
 * Des：用户模块 presenter
 * create by Zishu.Ye on 2017/8/3  9:22
 */
public class UserPresenter<V extends IUserView> extends BaseRxPresenter<V> {

    public UserPresenter(V view) {
        super(view);
    }

    public void dataLogon(Context mContext, String userName) {
        SPUtils.put(mContext, SType.LOGIN_USERNAME, userName, userName);
    }

}
