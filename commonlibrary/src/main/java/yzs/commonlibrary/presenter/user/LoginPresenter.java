package yzs.commonlibrary.presenter.user;

import android.content.Context;

import yzs.commonlibrary.view.user.ILoginView;

/**
 * Des：
 * create by Zishu.Ye on 2017/8/3  16:04
 */
public class LoginPresenter extends UserPresenter<ILoginView> {

    public LoginPresenter(ILoginView view) {
        super(view);
    }

    public void login(Context context, String userName, String passWord){
        register(userName,passWord,"");
    }
}
