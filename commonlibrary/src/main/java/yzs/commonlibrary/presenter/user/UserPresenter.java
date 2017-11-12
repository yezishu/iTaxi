package yzs.commonlibrary.presenter.user;

import java.util.HashMap;
import java.util.Map;

import yzs.commonlibrary.base.config.TokenConfig;
import yzs.commonlibrary.base.constant.Net;
import yzs.commonlibrary.base.constant.SConstant;
import yzs.commonlibrary.data.model.user.RegisterModel;
import yzs.commonlibrary.data.net.HttpResultFunc;
import yzs.commonlibrary.data.net.NetWorkSubscriber;
import yzs.commonlibrary.data.net.RetrofitUtils;
import yzs.commonlibrary.data.service.IUserService;
import yzs.commonlibrary.presenter.base.BaseRxPresenter;
import yzs.commonlibrary.view.user.IUserView;

/**
 * Des：用户模块 presenter
 * create by Zishu.Ye on 2017/8/3  9:22
 */
public class UserPresenter<V extends IUserView> extends BaseRxPresenter<V> {

    public IUserService iUserService;

    public UserPresenter(V view) {
        super(view);
        iUserService = RetrofitUtils.getInstance()
                .getRetrofit(Net.URL_HOST)
                .create(IUserService.class);
    }


    /**
     * 登录注册接口
     */
    public void register(String phone, String pw, String tjNo) {
        Map<String, Object> par = new HashMap<>();
        par.put("telno", phone);
        par.put("pwd", pw);
        par.put("tjno", tjNo);
        addDisposable(
                iUserService.register(Net.getRequestJson(par))
                        .map(new HttpResultFunc<RegisterModel>()),
                new NetWorkSubscriber<RegisterModel>() {
                    @Override
                    public void showErrorInfo(String errorInfo) {
                        mView.showFailInfo(errorInfo);
                    }

                    @Override
                    public void showNetResult(RegisterModel registerModel) {
                        TokenConfig.saveToken(registerModel.getToken());
                        SConstant.setUser(registerModel);
                        mView.showRegister("注册成功");
                    }
                });
    }

}
