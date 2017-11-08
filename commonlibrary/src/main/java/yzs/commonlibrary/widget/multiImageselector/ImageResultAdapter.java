package yzs.commonlibrary.widget.multiImageselector;

import android.content.Intent;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;

/**
 * Des：图片选择结果转换
 * creat by Zishu.Ye on 2017/4/10  11:15
 */
public class ImageResultAdapter {
    public static ArrayList<String> map(Intent data, int resultCode) {
        ArrayList<String> result = new ArrayList<>();
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() != 0)
                    for (ImageItem item : images)
                        result.add(item.path);
            }
        }
        return result;
    }
}
