package yzs.commonlibrary.presenter.user;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import yzs.commonlibrary.data.model.UploadImgModel;
import yzs.commonlibrary.data.net.HttpResultFunc;
import yzs.commonlibrary.presenter.base.UploadFilePresenter;
import yzs.commonlibrary.view.user.IAuthenticationView;

/**
 * Des：认证界面
 * create by Zishu.Ye on 2017/11/8  19:05
 */
public class AuthenticationPresenter extends UploadFilePresenter<IAuthenticationView> {

    public AuthenticationPresenter(IAuthenticationView view) {
        super(view);
    }

    public void upLoad(List<String> url) {
        final List<String> imgUrl = new ArrayList<>();
        addDisposable(Flowable.fromIterable(url)
                .flatMap(new Function<String, Flowable<UploadImgModel>>() {
                    @Override
                    public Flowable<UploadImgModel> apply(String s) throws Exception {
                        return upLoadImgByBase64(s)
                                .map(new HttpResultFunc<UploadImgModel>());
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<UploadImgModel>() {
                    @Override
                    public void onNext(UploadImgModel o) {
                        imgUrl.addAll(o.getFileids());
                    }

                    @Override
                    public void onError(Throwable t) {
                        imgUrl.add("1");
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

}
