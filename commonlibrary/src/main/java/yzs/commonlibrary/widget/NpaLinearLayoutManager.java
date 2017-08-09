package yzs.commonlibrary.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Des： No Predictive Animations NpaLinearLayoutManager
 * 参考资料 http://stackoverflow.com/questions/30220771/recyclerview-inconsistency-detected-invalid-item-position
 * creat by Zishu.Ye on 2016/12/20  16:46
 */
public class NpaLinearLayoutManager extends LinearLayoutManagerWrapper {

    /**
     * Disable predictive animations. There is a bug in RecyclerView which causes views that
     * are being reloaded to pull invalid ViewHolders from the internal recycler stack if the
     * adapter size has decreased since the ViewHolder was recycled.
     */
    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    public NpaLinearLayoutManager(Context context) {
        super(context);
    }

    public NpaLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public NpaLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
