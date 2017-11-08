package yzs.commonlibrary.widget.multiImageselector;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

/**
 * 图片选择器
 * Created by nereo on 16/3/17.
 */
public class MultiImageSelector {

    /**
     * 选取结果key
     */
    public static String EXTRA_RESULT = ImagePicker.EXTRA_RESULT_ITEMS;
    /**
     * activity result code
     */
    public static int RESULT_CODE = ImagePicker.RESULT_CODE_ITEMS;


    /**
     * 是否直接打开拍照
     */
    private boolean onlyOpenCamera = false;
    /**
     * 是否显示拍照按钮
     */
    private boolean mShowCamera = true;
    /**
     * 选择数量限制
     */
    private int mMaxCount = 9;
    /**
     * 选择模式
     */
    private boolean mIsMultiMode = true;
    /**
     * 是否裁剪
     */
    private boolean mIsCrop = true;

    private ImagePicker mImagePicker;

    static MultiImageSelector sSelector;

    private MultiImageSelector() {
        if (mImagePicker == null) {
            mImagePicker = ImagePicker.getInstance();
            mImagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        }
    }


    public static MultiImageSelector create() {
        sSelector = new MultiImageSelector();
        return sSelector;
    }


    public MultiImageSelector showCamera(boolean show) {
        mShowCamera = show;
        return sSelector;
    }

    public MultiImageSelector onlyOpenCamera(boolean onlyOpenCamera) {
        this.onlyOpenCamera = onlyOpenCamera;
        return sSelector;
    }

    public MultiImageSelector count(int count) {
        mMaxCount = count;
        return sSelector;
    }

    public MultiImageSelector crop(boolean isCrop) {
        this.mIsCrop = isCrop;
        return sSelector;
    }

    public MultiImageSelector single() {
        mIsMultiMode = false;
        return sSelector;
    }

    public MultiImageSelector multi() {
        mIsMultiMode = true;
        return sSelector;
    }

    public MultiImageSelector origin(ArrayList<String> images) {
//        mOriginData = images;
        return sSelector;
    }

    public void start(Activity activity, int requestCode) {
        final Context context = activity;
        if (hasPermission(context)) {
            setPicker();
            activity.startActivityForResult(createIntent(context), requestCode);
        } else {
            Toast.makeText(context, "没有权限", Toast.LENGTH_SHORT).show();
        }
    }

    public void start(Fragment fragment, int requestCode) {
        final Context context = fragment.getContext();
        if (hasPermission(context)) {
            setPicker();
            fragment.startActivityForResult(createIntent(context), requestCode);
        } else {
            Toast.makeText(context, "没有权限", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // Permission was added in API Level 16
            return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }


    private void setPicker() {
        mImagePicker.setShowCamera(mShowCamera);  //显示拍照按钮
        mImagePicker.setCrop(mIsCrop);        //允许裁剪（单选才有效）
        mImagePicker.setSaveRectangle(true); //是否按矩形区域保存
        mImagePicker.setSelectLimit(mMaxCount);    //选中数量限制
        mImagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        mImagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        mImagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        mImagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        mImagePicker.setOutPutY(1000);//保存文件的高度。单位像素
        mImagePicker.setMultiMode(mIsMultiMode);
    }

    private Intent createIntent(Context context) {
        Intent intent = new Intent(context, ImageGridActivity.class);
        if (onlyOpenCamera) {//直接打开相机
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        } else {
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, false); // 是否是直接打开相机
        }
        return intent;
    }

    /**
     * 打开预览
     *
     * @param activity   context
     * @param imageItems ArrayList<ImageItem>
     * @param position   定位
     */
    public static void startImagePreview(@NonNull Activity activity, ArrayList<ImageItem> imageItems, int position) {
        // 打开预览
        Intent intentPreview = new Intent(activity, ImagePreviewDelActivity.class);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItems);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        activity.startActivityForResult(intentPreview, ImagePicker.REQUEST_CODE_PREVIEW);
    }
}