package yzs.commonlibrary.view.user;

import yzs.commonlibrary.base.BaseEvent;

/**
 * Des：注册事件
 * creat by Zishu.Ye on 2017/11/12  16:28
 */
public class RegisterEvent extends BaseEvent{

    public RegisterEvent(boolean isSuccess) {
        super(isSuccess);
    }

}
