package yzs.itaxi.view.icall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import yzs.commonlibrary.base.CommonBaseRxMvpActivity;
import yzs.itaxi.R;
import yzs.itaxi.util.PhoneUtil;

/**
 * Des：拦截来电界面
 * create by Zishu.Ye
 */
public class ICallActivity extends CommonBaseRxMvpActivity<ICallPresenter> implements IICallView {
    /**
     * 来电号码extra
     */
    public static final String EXTRA_PHONE_NUM = "phoneNum";
    /**
     * 挂断电话action
     */
    public static final String ACTION_END_CALL = "com.likebamboo.phoneshow.ACTION_END_CALL";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icall);
    }

    @Override
    public void initView() {
        ((TextView)findViewById(R.id.tv_phone)).setText(getIntent().getStringExtra(EXTRA_PHONE_NUM));
        findViewById(R.id.btn_end_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneUtil.endCall(ICallActivity.this);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initPresenter() {
        new ICallPresenter(this);
    }

    @Override
    public void showFailInfo(String errorInfo) {

    }
}
