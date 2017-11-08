package yzs.commonlibrary.thridparty;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import yzs.commonlibrary.util.LogUtil;


/**
 * Des：三个项目使用Glide封装器
 * <p>
 * create by Zishu.Ye on 2017/8/14  10:38
 */
public class GlideWrapper {


    public static Builder with(Context context) {
        // 添加由于旧版本HTTP网络请求中 存在内存泄露问题 增加对持有content的Activity判断是否已经被销毁或移除
        if (context instanceof Activity
                && ((Activity) context).isFinishing()) {
            return new Builder(null);
        }
        return new Builder(Glide.with(context));
    }

    public static Builder with(Activity activity) {
        if (activity.isFinishing())
            return new Builder(null);
        return new Builder(Glide.with(activity));
    }

    public static Builder with(FragmentActivity activity) {
        if (activity.isFinishing())
            return new Builder(null);
        return new Builder(Glide.with(activity));
    }

    public static Builder with(Fragment fragment) {
        if (fragment.isDetached())
            return new Builder(null);
        return new Builder(Glide.with(fragment));
    }

    public static Builder with(android.app.Fragment fragment) {
        if (fragment.isDetached())
            return new Builder(null);
        return new Builder(Glide.with(fragment));
    }

    public static class Builder {

        private static String ERROR_MSG = "GlideWrapper 配置错误";
        private RequestManager requestManager;

        private Object url;
        private int placeId;
        private int errorRsId;
        private boolean notAni;
        private boolean dontTransform;
        private boolean circleCrop;
        private DiskCacheStrategy diskCacheStrategy;
        private boolean fitCenter;
        private boolean centerCrop;
        private boolean skipMemoryCache;

        //builder
        private boolean asBitmap;
        private float sizeMultiplier;


        public Builder(RequestManager requestManager) {
            this.requestManager = requestManager;
        }

        public Builder load(Object url) {
            this.url = url;
            return this;
        }


        public Builder placeholder(int id) {
            this.placeId = id;
            return this;
        }

        /**
         * 设置没有没动画
         */
        public Builder dontAnimate() {
            this.notAni = true;
            return this;
        }

        public Builder dontTransform() {
            this.dontTransform = true;
            return this;
        }

        /**
         * 圆形剪裁
         */
        public Builder circleCrop() {
            this.circleCrop = true;
            return this;
        }

        /**
         * @param errorRsId 加载错误是默认图片
         */
        public Builder error(int errorRsId) {
            this.errorRsId = errorRsId;
            return this;
        }

        /**
         * @param diskCacheStrategy 磁盘存储策略
         */
        public Builder diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
            this.diskCacheStrategy = diskCacheStrategy;
            return this;
        }

        /**
         * @return fitCenter
         */
        public Builder fitCenter() {
            this.fitCenter = true;
            return this;
        }

        /**
         * @return centerCrop
         */
        public Builder centerCrop() {
            this.centerCrop = true;
            return this;
        }

        public Builder skipMemoryCache(boolean skipMemoryCache) {
            this.skipMemoryCache = skipMemoryCache;
            return this;
        }

        public Builder asBitmap() {
            this.asBitmap = true;
            return this;
        }

        public Builder thumbnail(float sizeMultiplier) {
            this.sizeMultiplier = sizeMultiplier;
            return this;
        }

        public void into(ImageView iv) {
            build(iv);
        }

        public void into(Target target) {
            buildT(target);
        }

        public FutureTarget into(int width, int height) {
            return buildR(width, height);
        }

        public void clear(ImageView iv) {
            requestManager.clear(iv);
        }

        private void build(ImageView iv) {
            if (url == null) {
                LogUtil.e(ERROR_MSG + " url不能为空");
                return;
            }
            if (iv == null) {
                LogUtil.e(ERROR_MSG + " ImageView不能为空");
                return;
            }
            RequestBuilder requestBuilder = getBuilder();
            if (requestBuilder != null) {
                requestBuilder.apply(getOptions()).into(iv);
            }
        }

        private void buildT(Target target) {
            if (url == null) {
                LogUtil.e(ERROR_MSG + " url不能为空");
                return;
            }
            if (target == null) {
                LogUtil.e(ERROR_MSG + " target 不能为空");
                return;
            }
            RequestBuilder requestBuilder = getBuilder();
            if (requestBuilder != null) {
                requestBuilder.apply(getOptions()).into(target);
            }
        }

        private FutureTarget buildR(int targetW, int targetH) {
            if (targetW == 0 || targetH == 0) {
                LogUtil.e(ERROR_MSG + " targetW | targetH 无设置");
                return null;
            }
            RequestBuilder requestBuilder = getBuilder();
            if (requestBuilder != null) {
                return requestBuilder.apply(getOptions()).into(targetW, targetH);
            } else {
                return null;
            }
        }

        /**
         * 初始化 builder
         */
        private RequestBuilder getBuilder() {
            if (requestManager == null)
                return null;
            RequestBuilder requestBuilder;
            if (asBitmap) {
                requestBuilder = requestManager.asBitmap().load(url);
            } else {
                requestBuilder = requestManager.load(url);
            }

            if (sizeMultiplier != 0f) {
                requestBuilder = requestBuilder.thumbnail(sizeMultiplier);
            }
            return requestBuilder;
        }

        /**
         * @return 配置
         */
        private RequestOptions getOptions() {
            //设置option
            RequestOptions requestOptions = new RequestOptions();
            if (placeId != 0) {
                requestOptions.placeholder(placeId);
            }
            //设置没有没动画
            if (notAni) {
                requestOptions.dontAnimate();
            }
            if (circleCrop) {
                requestOptions.circleCrop();
            }
            if (errorRsId != 0) {
                requestOptions.error(errorRsId);
            }
            if (diskCacheStrategy != null) {
                requestOptions.diskCacheStrategy(diskCacheStrategy);
            }
            if (fitCenter) {
                requestOptions.fitCenter();
            }
            if (centerCrop) {
                requestOptions.centerCrop();
            }
            if (dontTransform) {
                requestOptions.dontTransform();
            }
            if (skipMemoryCache) {
                requestOptions.skipMemoryCache(skipMemoryCache);
            }
            return requestOptions;
        }

    }
}
