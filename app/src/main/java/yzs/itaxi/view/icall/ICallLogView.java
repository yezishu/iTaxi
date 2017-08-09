package yzs.itaxi.view.icall;


import java.util.List;

import yzs.commonlibrary.base.BaseView;
import yzs.itaxi.data.module.CallLogItem;

/**
 * Des：通话记录界面
 * Created by Zishu.Ye
 */
public interface ICallLogView extends BaseView {
    void showCallLog(List<CallLogItem> items);
}
