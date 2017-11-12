package yzs.commonlibrary.base;

/**
 * Des：base event
 * creat by Zishu.Ye on 2017/11/12  16:31
 */
public class BaseEvent {

    private boolean isSuccess;//成功否
    private String msg;//提示消息（如果失败的话）

    public BaseEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public BaseEvent(boolean isSuccess, String msg) {
        this.isSuccess = isSuccess;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMsg() {
        return msg;
    }
}
