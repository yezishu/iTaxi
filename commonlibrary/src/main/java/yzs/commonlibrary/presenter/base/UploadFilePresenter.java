package yzs.commonlibrary.presenter.base;

import io.reactivex.Flowable;
import yzs.commonlibrary.base.BaseView;
import yzs.commonlibrary.base.config.AppConfig;
import yzs.commonlibrary.base.constant.Net;
import yzs.commonlibrary.data.model.UploadImgModel;
import yzs.commonlibrary.data.net.HttpResult;
import yzs.commonlibrary.data.net.RetrofitUtils;
import yzs.commonlibrary.data.service.IUploadFileService;
import yzs.commonlibrary.util.AppSPUtils;
import yzs.commonlibrary.util.ImgBase64Util;

/**
 * Des：上传图片 presenter
 * create by Zishu.Ye on 2017/11/8  20:06
 */
public class UploadFilePresenter<V extends BaseView> extends BaseRxPresenter<V> {

    IUploadFileService iUploadFileService;

    public UploadFilePresenter(V view) {
        super(view);
        iUploadFileService = RetrofitUtils.getInstance()
                .getRetrofit(Net.URL_HOST)
                .create(IUploadFileService.class);
    }

    public Flowable<HttpResult<UploadImgModel>> upLoadImgByBase64(String path) {
        return iUploadFileService.uploadImgByBase64(ImgBase64Util.getBase64Str(path),
                AppSPUtils.getSpConfigValue(AppConfig.SP_USER_ID ,"").toString());
    }
}
