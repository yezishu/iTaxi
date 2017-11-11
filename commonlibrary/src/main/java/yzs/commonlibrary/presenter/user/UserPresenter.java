package yzs.commonlibrary.presenter.user;

import yzs.commonlibrary.presenter.base.BaseRxPresenter;
import yzs.commonlibrary.base.config.App;
import yzs.commonlibrary.base.config.AppConfig;
import yzs.commonlibrary.base.config.TokenConfig;
import yzs.commonlibrary.base.constant.Net;
import yzs.commonlibrary.base.constant.SType;
import yzs.commonlibrary.data.model.user.RegisterModel;
import yzs.commonlibrary.data.net.HttpResultFunc;
import yzs.commonlibrary.data.net.NetWorkSubscriber;
import yzs.commonlibrary.data.net.RetrofitUtils;
import yzs.commonlibrary.data.service.IUserService;
import yzs.commonlibrary.util.AppSPUtils;
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

    /**
     * @param userName 缓存登录记录
     */
    public void dataLogon( String userName) {
        SPUtils.put(App.INSTANCE, SType.LOGIN_USERNAME, userName, userName);
    }

    /**
     * 登录注册接口
     */
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
                        TokenConfig.saveToken(registerModel.getToken());
                        AppSPUtils.putSpConfigValue(AppConfig.SP_USER_ID, registerModel.getTarget().getDriverid());
                        AppSPUtils.putSpConfigValue(AppConfig.SP_KEY_USER_TELEPHONE, registerModel.getTarget().getTelno());
                        dataLogon(registerModel.getTarget().getTelno());
                    }
                });
    }

}
