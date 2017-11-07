package yzs.commonlibrary.base;

import yzs.commonlibrary.util.StringUtils;
import yzs.commonlibrary.util.ToastUtil;

/**
 * Des：mvp框架 使用的base activity
 *      处理了presenter 订阅以及取消订阅
 * create by Zishu.Ye on 2017/5/31  11:51
 */
public abstract class CommonBaseRxMvpActivity<P extends BasePresenter> extends CommonBaseAppCompatActivity{

    /**
     * Mvp对应P
     */
    public P mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.subscribe();//调用p层订阅
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null)
            mPresenter.unSubscribe();//调用p层取消订阅
    }

    /**
     * P层初始化
     */
    public abstract void initPresenter();

    @Override
    public void init() {
        initPresenter();
    }

    /**
     * @param msg 展示网络请求错误
     */
    public void showFailInfo(String msg) {
        if (!StringUtils.isEmpty(msg)) {
            ToastUtil.showMessage(msg);
        }
    }
}
