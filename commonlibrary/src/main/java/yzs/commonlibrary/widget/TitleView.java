package yzs.commonlibrary.widget;

/**
 * Des：
 * create by Zishu.Ye on 2017/11/7  19:50
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import yzs.commonlibrary.R;
import yzs.commonlibrary.util.StringUtils;

/**
 * Des：app 使用通用 标题栏
 * creat by Zishu.Ye on 2016/11/4  8:52
 */
public class TitleView extends AutoRelativeLayout {

    public static final int DEFAULT_CODE = -1;
    /**
     * 右边按钮 类型
     */
    public static final int TEXT_RIGHT_BTN = 0;       //文本类型
    public static final int IMG_RIGHT_BTN = 1;        //图片类型

    /**
     * xml 资源属性
     */
    private int right_btn_model;            //右边按钮类型
    private String right_btn_text;             //文本右边按钮  内容
    private int right_btn_img_reference;    //图片右边阿牛  资源文件

    private String titleStr;    //标题
    private boolean isShowBack; //是否显示返回按钮

    /**
     * 布局控件
     */
    private TextView tvTitle;   //标题
    View backView;      //返回按钮
    private AutoRelativeLayout rightViewGroup;  //右边按钮 布局
    TextView right_commom_tv;           //右边通用 文本按钮
    ImageView right_commom_iv;          //右边通用 图片按钮

    private TitleViewRbtnClickListener rbtnClickListener;

    private Context mContext;

    /**
     * 右边通用按钮 控件监听
     */
    public interface TitleViewRbtnClickListener {
        void onRbtnClick();
    }

    public TitleView(Context context) {
        super(context);
        this.mContext = context;
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);

    }

    /**
     * @param attrs attrs
     */
    private void init(AttributeSet attrs) {
        if (!initAttrs(attrs))
            return;
        View v = LayoutInflater.from(mContext).inflate(R.layout.common_title, this, false);
        AutoUtils.auto(v);
        initTitleView(v);
        initBackView(v);
        initRightView(v);
        addView(v, 0);
    }

    /**
     * @param attrs xml 资源属性
     * @return 是否加载成功
     */
    private boolean initAttrs(AttributeSet attrs) {
        if (attrs == null)
            return false;
        TypedArray styled = mContext.obtainStyledAttributes(attrs, R.styleable.TitleView);
        this.titleStr = styled.getString(R.styleable.TitleView_tv_title);
        this.isShowBack = styled.getBoolean(R.styleable.TitleView_tv_back_view, false);
        this.right_btn_model = styled.getInt(R.styleable.TitleView_tv_right_btn, DEFAULT_CODE);
        this.right_btn_text = styled.getString(R.styleable.TitleView_tv_text_btn_str);
        this.right_btn_img_reference = styled.getResourceId(R.styleable.TitleView_tv_img_btn_res, DEFAULT_CODE);
        return true;
    }

    /**
     * 加载退出按钮
     *
     * @param v v
     */
    private void initBackView(View v) {
        backView = v.findViewById(R.id.common_back);
        if (backView == null) {
            return;
        }
        if (!isShowBack) {
            backView.setVisibility(GONE);
            return;
        }
        backView.setVisibility(VISIBLE);
        backView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });
    }

    /**
     * 加载标题
     *
     * @param v v
     */
    private void initTitleView(View v) {
        tvTitle = (TextView) v.findViewById(R.id.common_title);
        if (StringUtils.isEmptyWithNullStr(titleStr))
            return;
        if (tvTitle != null) {
            tvTitle.setText(titleStr);
            tvTitle.setVisibility(VISIBLE);
        }
    }

    /**
     * 加载右边 按钮
     *
     * @param v v
     */
    private void initRightView(View v) {
        rightViewGroup = (AutoRelativeLayout) v.findViewById(R.id.common_title_right_rl_2);
        if (right_btn_model == DEFAULT_CODE || rightViewGroup == null) {
            return;
        }
        rightViewGroup.setVisibility(VISIBLE);

        switch (right_btn_model) {
            case TEXT_RIGHT_BTN:
                initRightTextView(v);
                break;
            case IMG_RIGHT_BTN:
                initRightImgView(v);
                break;
            default:
                break;
        }

    }

    /**
     * 加载 右边通用文本按钮
     *
     * @param v v
     */
    private void initRightTextView(View v) {
        if (StringUtils.isEmptyWithNullStr(right_btn_text)) {
            return;
        }
        right_commom_tv = (TextView) v.findViewById(R.id.common_tv_right);
        if (right_commom_tv == null)
            return;
        right_commom_tv.setVisibility(VISIBLE);
        right_commom_tv.setText(right_btn_text);

        right_commom_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbtnClickListener == null) {
                    return;
                }
                rbtnClickListener.onRbtnClick();
            }
        });
    }

    /**
     * 加载 右边通用图片按钮
     *
     * @param v v
     */
    private void initRightImgView(View v) {
        if (right_btn_img_reference == DEFAULT_CODE) {
            return;
        }
        right_commom_iv = (ImageView) v.findViewById(R.id.common_iv);
        if (right_commom_iv == null) {
            return;
        }
        right_commom_iv.setVisibility(VISIBLE);
        right_commom_iv.setImageResource(right_btn_img_reference);
        right_commom_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbtnClickListener == null) {
                    return;
                }
                rbtnClickListener.onRbtnClick();
            }
        });
    }

    /**
     * @return 获取返回键
     */
    public View getBackView() {
        if (backView == null)
            throw new NullPointerException();
        return backView;
    }

    /**
     * @return 获取标题控件
     */
    public TextView getTvTitle() {
        if (tvTitle == null)
            throw new NullPointerException();
        return tvTitle;
    }

    /**
     * @return 在右边是文本框模式下  获取右边文本按钮
     */
    public TextView getRightTv() {
        if (right_commom_tv == null)
            throw new NullPointerException();
        return right_commom_tv;
    }

    /**
     * @return 在右边是图片模式下  获取右边图片按钮
     */
    public ImageView getRightIv() {
        if (right_commom_iv == null) {
            throw new NullPointerException();
        }
        return right_commom_iv;
    }

    /**
     * 纯文字、图片 模式下设置右边按钮点击回调事件
     *
     * @param rbtnClickListener 事件
     */
    public void setRbtnClickListener(TitleViewRbtnClickListener rbtnClickListener) {
        this.rbtnClickListener = rbtnClickListener;
    }
}
