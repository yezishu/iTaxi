package yzs.commonlibrary.widget.multiImageselector;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

import yzs.commonlibrary.R;
import yzs.commonlibrary.thridparty.GlideWrapper;

/**
 * Des：本地图片选择器的 图片加载器
 * creat by Zishu.Ye on 2017/4/10  10:30
 */
public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        GlideWrapper.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.default_image)           //设置错误图片
                .placeholder(R.mipmap.default_image)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做本地磁盘缓存
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
