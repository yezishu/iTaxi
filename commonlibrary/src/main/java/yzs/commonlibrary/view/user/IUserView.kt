package yzs.commonlibrary.view.user

import yzs.commonlibrary.base.BaseView
import yzs.commonlibrary.data.model.RegisterModel

/**
 * Des：user 模块视图view
 * create by Zishu.Ye on 2017/8/3  9:22
 */
interface IUserView : BaseView{
    fun showRegister(model: RegisterModel)
}


