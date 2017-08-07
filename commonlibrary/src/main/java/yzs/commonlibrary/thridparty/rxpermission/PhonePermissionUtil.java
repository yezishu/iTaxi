package yzs.commonlibrary.thridparty.rxpermission;

import android.Manifest;
import android.app.Activity;
import android.os.Build;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import yzs.commonlibrary.R;
import yzs.commonlibrary.util.ToastUtil;


/**
 * Des：联系人 通话记录 权限
 * create by Zishu.Ye on 2017/8/4  17:44
 */
public class PhonePermissionUtil {

    public static class PhoneObserver implements Observer<Permission> {

        boolean readCon, writeCon, reaCallLog;

        @Override
        public void onSubscribe(@NonNull Disposable d) {
        }

        public void granted() {
        }

        @Override
        public void onNext(@NonNull Permission permission) {
            if (Build.VERSION.SDK_INT < 16) {
                reaCallLog = true;
            }
            switch (permission.name) {
                case Manifest.permission.READ_CONTACTS:
                    readCon = permission.granted;
                    break;
                case Manifest.permission.WRITE_CONTACTS:
                    writeCon = permission.granted;
                    break;
                case Manifest.permission.READ_CALL_LOG:
                    reaCallLog = permission.granted;
                    break;
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {
            if (!reaCallLog || !writeCon || !readCon) {
                ToastUtil.showMessage(R.string.c_permission_error);
            }
        }

        @Override
        public void onComplete() {
            if (!reaCallLog || !writeCon || !readCon) {
                ToastUtil.showMessage(R.string.c_permission_error);
            } else {
                granted();
            }
        }
    }

    public static <S extends PhoneObserver> void requestPermission(Activity activity, S s) {
        String[] permissions;
        if (Build.VERSION.SDK_INT >= 16) {
            permissions = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CALL_LOG};
        } else {
            permissions = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
        }
        new RxPermissions(activity)
                .requestEach(permissions)
                .subscribe(s);
    }
}
