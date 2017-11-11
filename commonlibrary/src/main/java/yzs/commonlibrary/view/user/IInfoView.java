package yzs.commonlibrary.view.user;

import yzs.commonlibrary.base.BaseView;
import yzs.commonlibrary.data.model.user.UserModel;

/**
 * Des：用户信息界面
 * creat by Zishu.Ye on 2017/11/11  11:24
 */
public interface IInfoView extends BaseView{
    void showInfo(UserModel infoModel);
}
