package yzs.commonlibrary.presenter.user;

import android.content.Context;

import yzs.commonlibrary.base.BaseRxPresenter;
import yzs.commonlibrary.base.constant.Net;
import yzs.commonlibrary.base.constant.SType;
import yzs.commonlibrary.data.model.RegisterModel;
import yzs.commonlibrary.data.net.HttpResultFunc;
import yzs.commonlibrary.data.net.NetWorkSubscriber;
import yzs.commonlibrary.data.net.RetrofitUtils;
import yzs.commonlibrary.data.service.IUserService;
import yzs.commonlibrary.util.SPUtils;
import yzs.commonlibrary.view.user.IUserView;

/**
 * Des：用户模块 presenter
 * create by Zishu.Ye on 2017/8/3  9:22
 */
public class UserPresenter<V extends IUserView> extends BaseRxPresenter<V> {

    private IUserService iUserService;

    public UserPresenter(V view) {
        super(view);
        iUserService = RetrofitUtils.getInstance()
                .getRetrofit(Net.URL_HOST)
                .create(IUserService.class);
    }

    public void dataLogon(Context mContext, String userName) {
        SPUtils.put(mContext, SType.LOGIN_USERNAME, userName, userName);
    }

    public void register(String phone, String pw, String tjNo) {
        addDisposable(
                iUserService.register(phone, tjNo, pw)
                        .map(new HttpResultFunc<RegisterModel>()),
                new NetWorkSubscriber<RegisterModel>() {
                    @Override
                    public void showErrorInfo(String errorInfo) {
                        mView.showFailInfo(errorInfo);
                    }

                    @Override
                    public void showNetResult(RegisterModel registerModel) {

                    }
                });
    }

}
