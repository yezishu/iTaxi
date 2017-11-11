package yzs.commonlibrary.presenter.user;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import yzs.commonlibrary.base.constant.Net;
import yzs.commonlibrary.data.model.RegisterModel;
import yzs.commonlibrary.data.model.UploadImgModel;
import yzs.commonlibrary.data.net.HttpResultFunc;
import yzs.commonlibrary.data.net.NetWorkSubscriber;
import yzs.commonlibrary.data.net.RetrofitUtils;
import yzs.commonlibrary.data.service.IUserService;
import yzs.commonlibrary.presenter.base.UploadFilePresenter;
import yzs.commonlibrary.view.user.IAuthenticationView;

/**
 * Des：认证界面
 * create by Zishu.Ye on 2017/11/8  19:05
 */
public class AuthenticationPresenter extends UploadFilePresenter<IAuthenticationView> {

    IUserService iUserService;

    public AuthenticationPresenter(IAuthenticationView view) {
        super(view);
        iUserService = RetrofitUtils.getInstance()
                .getRetrofit(Net.URL_HOST)
                .create(IUserService.class);
    }

    public void upLoad(List<String> url, Map<String, String> par) {
        final List<String> imgUrl = new ArrayList<>();
        String[] strings = new String[10];
        url.toArray(strings);
        addDisposable(Flowable.fromArray(strings)
                .map(new Function<String, Flowable<UploadImgModel>>() {
                    @Override
                    public Flowable<UploadImgModel> apply(@io.reactivex.annotations.NonNull String s) throws Exception {
                        return upLoadImgByBase64(s)
                                .map(new HttpResultFunc<UploadImgModel>());
                    }
                })
                .flatMap(new Function<Flowable<UploadImgModel>, Publisher<UploadImgModel>>() {
                    @Override
                    public Publisher<UploadImgModel> apply(@io.reactivex.annotations.NonNull Flowable<UploadImgModel> uploadImgModelFlowable) throws Exception {
                        return uploadImgModelFlowable;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<UploadImgModel>() {
                    @Override
                    public void onNext(UploadImgModel uploadImgModel) {
//                                ToastUtil.showMessage();
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));


    }

    private void identification(Map<String, String> par) {
        addDisposable(
                iUserService.identification("", "")
                        .map(new HttpResultFunc<RegisterModel>()),
                new NetWorkSubscriber<RegisterModel>() {
                    @Override
                    public void showErrorInfo(String errorInfo) {
                        mView.showFailInfo(errorInfo);
                    }

                    @Override
                    public void showNetResult(RegisterModel registerModel) {

                    }
                });
    }

}
