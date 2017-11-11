package yzs.commonlibrary.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import yzs.commonlibrary.R;
import yzs.commonlibrary.util.StringUtils;


/**
 * 确认,取消对话框
 */
public class CustomDialog extends Dialog {

    protected Context mContext;
    protected String title;
    protected String content;
    protected String confirmButtonText;
    protected String cacelButtonText;
    protected ClickListenerInterface clickListenerInterface;
    protected EditText inputEditText;
    protected TextView tvTitle;
    protected TextView tvContent;
    protected TextView tvCancel;
    protected boolean isShowEditTextView = false;
    protected int inputType = InputType.TYPE_CLASS_TEXT;
    protected View vMiddle;
    int contentGravity = Gravity.CENTER;


    public interface ClickListenerInterface {

        public void doConfirm();

        public void doCancel();
    }

    /**
     * @param context
     * @param title             标题
     * @param content           内容
     * @param confirmButtonText 确认
     * @param cacelButtonText   取消
     */
    public CustomDialog(Context context, String title, String content, String confirmButtonText, String cacelButtonText) {
        super(context, R.style.dlg_priority);
        this.mContext = context;
        this.title = title;
        this.content = StringUtils.isEmpty(content) ? "" : content;
        //认确认提示为:确认
        this.confirmButtonText = StringUtils.isEmpty(confirmButtonText) ? "确认" : confirmButtonText;
        // 默认取消提示为:取消
        this.cacelButtonText = StringUtils.isEmpty(cacelButtonText) ? "取消" : cacelButtonText;
        this.isShowEditTextView = false;
    }

    public CustomDialog(Context context, String title, int contentGravity, String content, String confirmButtonText, String cacelButtonText) {
        super(context, R.style.dlg_priority);
        this.mContext = context;
        this.title = title;
        this.content = StringUtils.isEmpty(content) ? "" : content;
        this.contentGravity = contentGravity;
        //认确认提示为:确认
        this.confirmButtonText = StringUtils.isEmpty(confirmButtonText) ? "确认" : confirmButtonText;
        // 默认取消提示为:取消
        this.cacelButtonText = StringUtils.isEmpty(cacelButtonText) ? "取消" : cacelButtonText;
        this.isShowEditTextView = false;
    }

    /**
     * 只有一个按钮的
     */
    public CustomDialog(Context context, String title, String content, String confirmButtonText) {
        super(context, R.style.dlg_priority);
        this.mContext = context;
        this.title = title;
        this.content = StringUtils.isEmpty(content) ? "" : content;
        // 默认确认提示为:确认
        this.confirmButtonText = StringUtils.isEmpty(confirmButtonText) ? "确认" : confirmButtonText;
        this.isShowEditTextView = false;
    }

    public CustomDialog(Context context, String title, int contentGravity, String content, String confirmButtonText) {
        super(context, R.style.dlg_priority);
        this.mContext = context;
        this.title = title;
        this.content = StringUtils.isEmpty(content) ? "" : content;
        this.contentGravity = contentGravity;
        // 默认确认提示为:确认
        this.confirmButtonText = StringUtils.isEmpty(confirmButtonText) ? "确认" : confirmButtonText;
        this.cacelButtonText = StringUtils.isEmpty(cacelButtonText) ? "取消" : cacelButtonText;
        this.isShowEditTextView = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * UI：初始化编辑框
     */
    protected void showEditTextView() {
        if (isShowEditTextView) {
            inputEditText.setVisibility(View.VISIBLE);
            inputEditText.setInputType(InputType.TYPE_CLASS_TEXT | inputType);
        } else {
            inputEditText.setVisibility(View.GONE);
        }
    }

    @SuppressLint("InflateParams")
    public void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.widget_simple_dialog, null);
        AutoUtils.auto(view);
        setContentView(view);

        tvTitle = (TextView) view.findViewById(R.id.title);
        tvContent = (TextView) view.findViewById(R.id.content);
        TextView tvConfirm = (TextView) view.findViewById(R.id.confirm);
        tvCancel = (TextView) view.findViewById(R.id.cancel);
        vMiddle = view.findViewById(R.id.v_middle);
        inputEditText = (EditText) view.findViewById(R.id.content_input);
        if (!StringUtils.isEmpty(title)) { // 根据title传参决定是否显示标题
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }

        if (StringUtils.isEmpty(content)) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setText(content);
        }
        tvConfirm.setText(confirmButtonText);
        tvCancel.setText(cacelButtonText);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListenerInterface.doConfirm();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListenerInterface.doCancel();
            }
        });

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9); // 宽度设置为屏幕的0.9
        dialogWindow.setAttributes(lp);

        this.getWindow().setBackgroundDrawableResource(R.color.transparent);
        // 初始化编辑输入框（如果展示的话）
        showEditTextView();
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    /**
     * 设置显示的内容
     *
     * @param contentStr
     */
    public void setContentStr(String contentStr) {
        tvContent.setText(contentStr);
    }

    /**
     * 设置输入框的输入样式
     *
     * @param inputType
     */
    public void setEditViewInputType(int inputType) {
        this.inputType = inputType;
    }

    /**
     * 获取输入框的值
     *
     * @return
     */
    public EditText getInputEditText() {
        return inputEditText;
    }

    /**
     * 设置是否显示输入框
     *
     * @param isShowEditTextView
     */
    public void setShowEditTextView(boolean isShowEditTextView) {
        this.isShowEditTextView = isShowEditTextView;
    }

    /**
     * 设置输入框值
     */
    public void setInputEditText(CharSequence param) {
        inputEditText.setText(param);
        if (!StringUtils.isEmpty(param)) {
            inputEditText.setSelection(param.length());
        }
    }

    /**
     * 设置输入框提示
     */
    public void setInputEditHint(CharSequence param) {
        inputEditText.setHint(param);
    }

    /**
     * 设置输入长度
     */
    public void setInputEditLength(int max) {
        inputEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max)});
    }

    /**
     * 设置是否显示取消,一定要在dialog show之后再调用,否则空指针
     *
     * @param isShowCancelView
     */
    public void setShowCancel(boolean isShowCancelView) {
        if (isShowCancelView) {//true
            tvCancel.setVisibility(View.VISIBLE);
            vMiddle.setVisibility(View.VISIBLE);
        } else {
            tvCancel.setVisibility(View.GONE);
            vMiddle.setVisibility(View.GONE);
        }
    }

    /**
     * 刷新标题文字
     *
     * @param titleStr 对应文字
     */
    public void refreshTitle(String titleStr) {
        if (StringUtils.isEmptyWithNullStr(titleStr))
            return;
        if (null == tvTitle)
            return;
        tvTitle.setText(titleStr);
    }

    /**
     * 刷新内容文字
     *
     * @param content 对应文字
     */
    public void refreshContent(String content) {
        if (StringUtils.isEmptyWithNullStr(content))
            return;
        if (null == tvContent)
            return;
        tvContent.setText(content);
    }

    public void setContentGravity(int contentGravity) {
        this.contentGravity = contentGravity;
    }

    public int getContentGravity() {
        return contentGravity;
    }
}
