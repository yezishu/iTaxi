package yzs.commonlibrary.presenter.user;

import yzs.commonlibrary.base.config.AppConfig;
import yzs.commonlibrary.base.constant.Net;
import yzs.commonlibrary.base.constant.SConstant;
import yzs.commonlibrary.data.model.user.UserModel;
import yzs.commonlibrary.data.net.HttpResultFunc;
import yzs.commonlibrary.data.net.NetWorkSubscriber;
import yzs.commonlibrary.data.net.RetrofitUtils;
import yzs.commonlibrary.data.service.IUserService;
import yzs.commonlibrary.presenter.base.BaseRxPresenter;
import yzs.commonlibrary.view.user.IInfoView;

/**
 * Des：用户个人信息
 * creat by Zishu.Ye on 2017/11/11  11:24
 */
public class InfoPresenter extends BaseRxPresenter<IInfoView> {


    IUserService iUserService;

    public InfoPresenter(IInfoView view) {
        super(view);
        iUserService = RetrofitUtils.getInstance()
                .getRetrofit(Net.URL_HOST)
                .create(IUserService.class);
    }

    public void getInfo() {
        if(AppConfig.IS_NO_SERVER){
            mView.showInfo(SConstant.getUser());
            return;
        }
        addDisposable(
                iUserService.info("", "")
                        .map(new HttpResultFunc<UserModel>()),
                new NetWorkSubscriber<UserModel>() {
                    @Override
                    public void showErrorInfo(String errorInfo) {
                        mView.showFailInfo(errorInfo);
                    }

                    @Override
                    public void showNetResult(UserModel infoModel) {
                        mView.showInfo(infoModel);
                    }
                });
    }
}
