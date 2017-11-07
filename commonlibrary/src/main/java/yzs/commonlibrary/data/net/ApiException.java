package yzs.commonlibrary.data.net;

/**
 * 自定义API异常抛出
 * by Andy.Wu on 2016/7/4.
 */
public class ApiException extends RuntimeException{
    private String errorCode;

    public ApiException() {
    }

    public ApiException(String detailMessage, String errorCode) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    public ApiException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ApiException(Throwable throwable) {
        super(throwable);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
