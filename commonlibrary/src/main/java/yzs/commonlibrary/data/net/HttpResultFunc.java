package yzs.commonlibrary.data.net;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import yzs.commonlibrary.util.ToastUtil;

import static yzs.commonlibrary.base.constant.Net.RESPONSE_FAIL_CODE;
import static yzs.commonlibrary.base.constant.Net.RESPONSE_SUCCESS_CODE;
import static yzs.commonlibrary.base.constant.Net.RESPONSE_TOKEN_INVALID_MSG;
import static yzs.commonlibrary.base.constant.Net.RESPONSE_TOKEN_INVALID_OTHER;

/**
 * 接口返回结果处理
 * 有问题抛异常，没问题就返回数据
 * by Andy.Wu on 2016/7/5.
 */
public class HttpResultFunc<T> implements Function<HttpResult<T>, T> {


    @Override
    public T apply(@NonNull HttpResult<T> httpResult) throws Exception {
        if (!httpResult.getCode().equals(RESPONSE_SUCCESS_CODE)) {
            throw new ApiException(getApiExceptionMessage(httpResult.getCode(), httpResult.getMessage()),httpResult.getCode());
        }
        return httpResult.getData();

    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private String getApiExceptionMessage(String code,String message){
        switch (code) {
            case RESPONSE_FAIL_CODE://处理失败
                break;
            case RESPONSE_TOKEN_INVALID_OTHER: //Token失效，异地登录
                ToastUtil.showMessage(RESPONSE_TOKEN_INVALID_MSG);
                tokenInvalid();
                break;
            default://其他类型的错误消息，按照服务端返回
                break;
        }
        return message;
    }

    /**
     * token失效
     */
    private void tokenInvalid() {
//        EventBus.getDefault().postSticky(new BusEvent.AbnormalLoginEvent(true, SState.RESPONSE_TOKEN_INVALID_MSG, false));
    }
}