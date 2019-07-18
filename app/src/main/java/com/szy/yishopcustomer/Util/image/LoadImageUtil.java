package com.szy.yishopcustomer.Util.image;

import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hyphenate.util.DensityUtil;
import com.like.utilslib.image.config.GlideApp;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

/**
 * 加载图片工具类
 * Created by longshao on 2017/5/10.
 */

public class LoadImageUtil {

    /**
     * 加载图片
     *
     * @param img
     * @param imageRes
     */
    public static void loadImage(ImageView img, Object imageRes) {
        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(App.getInstance().mContext)
                .load(imageRes)
                .placeholder(R.mipmap.img_empty)
                .error(R.mipmap.img_empty)
                .dontAnimate()
                .fitCenter()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .into(img);
    }

    public static void loadImageGif(ImageView img, Object imageRes) {
        GlideApp.with(App.getInstance().mContext)
                .asGif()
                .load(imageRes)
                .format(PREFER_ARGB_8888)
                .placeholder(R.mipmap.img_empty)
                .error(R.mipmap.img_empty)
                .fitCenter()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img);
    }

    /**
     * 加载图片 给图片放大看详情使用
     *
     * @param img
     * @param imageRes
     */
    public static void loadImageRetail(ImageView img, Object imageRes) {
        GlideApp.with(App.getInstance().mContext)
                .load(imageRes)
                .error(R.mipmap.img_empty)
                .fitCenter()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img);
    }

    /**
     * 加载特定宽度网络图片
     *
     * @param img
     * @param imageUrl
     * @param width    dp
     * @param height   dp
     */
    public static void loadSizeImage(ImageView img, Object imageUrl, int width, int height) {
        width = DensityUtil.dip2px(App.getInstance().mContext, width);
        height = DensityUtil.dip2px(App.getInstance().mContext, height);
        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(App.getInstance().mContext)
                .load(imageUrl)
                .placeholder(R.mipmap.img_empty)
                .error(R.mipmap.img_empty)
                .override(width, height)
                .dontAnimate()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .into(img);
    }

    /**
     * 默认的加载圆角图片
     *
     * @param img
     * @param imageUrl
     * @param radius   圆角度数 dp
     */
    public static void loadRoundImage(ImageView img, Object imageUrl, int radius) {
        radius = DensityUtil.dip2px(App.getInstance().mContext, radius);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(App.getInstance().mContext)
                .load(imageUrl)
                .transform(new GlideRoundTransform(radius))
                .placeholder(R.mipmap.img_empty)
                .error(R.mipmap.img_empty)
                .dontAnimate()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .into(img);
    }

    /**
     * 默认的加载固定大小的固定圆角图片
     *
     * @param img
     * @param imageUrl
     * @param width
     * @param height
     * @param radius   圆角度数 dp
     */
    public static void loadSizeRoundImage(ImageView img, Object imageUrl, int width, int height, int radius) {
        width = DensityUtil.dip2px(App.getInstance().mContext, width);
        height = DensityUtil.dip2px(App.getInstance().mContext, height);
        radius = DensityUtil.dip2px(App.getInstance().mContext, radius);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(App.getInstance().mContext)
                .load(imageUrl)
                .transform(new GlideRoundTransform(radius))
                .override(width, height)
                .placeholder(R.mipmap.img_empty)
                .error(R.mipmap.img_empty)
                .dontAnimate()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .into(img);
    }

    /**
     * 默认的加载固定大小圆角图片
     *
     * @param img
     * @param imageUrl
     * @param widthorHeight 图片大小 dp
     */
    public static void loadRoundSizeImage(ImageView img, Object imageUrl, int widthorHeight) {
        int widthorHeightother = DensityUtil.dip2px(App.getInstance().mContext, widthorHeight);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(App.getInstance().mContext)
                .load(imageUrl)
                .transform(new GlideCircleTransform())
                .override(widthorHeightother, widthorHeightother)
                .placeholder(R.mipmap.img_empty)
                .error(R.mipmap.img_empty)
                .dontAnimate()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .into(img);
    }

    /**
     * 加载图片
     *
     * @param img
     * @param imageRes
     * @param defaltImg
     */
    public static void loadImage(ImageView img, Object imageRes, Integer defaltImg) {
        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(App.getInstance().mContext)
                .load(imageRes)
                .placeholder(R.mipmap.img_empty)
                .error(defaltImg)
                .dontAnimate()
                .fitCenter()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .into(img);
    }

    /**
     * 加载特定宽度网络图片
     *
     * @param img
     * @param imageUrl
     * @param width     dp
     * @param height    dp
     * @param defaltImg
     */
    public static void loadSizeImage(ImageView img, Object imageUrl, int width, int height, Integer defaltImg) {
        width = DensityUtil.dip2px(App.getInstance().mContext, width);
        height = DensityUtil.dip2px(App.getInstance().mContext, height);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(App.getInstance().mContext)
                .load(imageUrl)
                .override(width, height)
                .placeholder(R.mipmap.img_empty)
                .error(defaltImg)
                .dontAnimate()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .into(img);
    }

    /**
     * 默认的加载圆角图片
     *
     * @param img
     * @param imageUrl
     * @param radius    圆角度数 dp
     * @param defaltImg
     */
    public static void loadRoundImage(ImageView img, Object imageUrl, int radius, Integer defaltImg) {
        radius = DensityUtil.dip2px(App.getInstance().mContext, radius);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(App.getInstance().mContext)
                .load(imageUrl)
                .transform(new GlideRoundTransform(radius))
                .placeholder(R.mipmap.img_empty)
                .error(defaltImg)
                .dontAnimate()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .into(img);
    }

    /**
     * 默认的加载固定大小圆角图片
     *
     * @param img
     * @param imageUrl
     * @param widthorHeight 图片大小 dp
     * @param defaltImg
     */
    public static void loadRoundSizeImage(ImageView img, Object imageUrl, int widthorHeight, Integer defaltImg) {
        int widthorHeightother = DensityUtil.dip2px(App.getInstance().mContext, widthorHeight);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(App.getInstance().mContext)
                .load(imageUrl)
                .transform(new GlideCircleTransform())
                .override(widthorHeightother, widthorHeightother)
                .placeholder(R.mipmap.img_empty)
                .error(defaltImg)
                .dontAnimate()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .into(img);
    }

    /**
     * 清理内存缓存
     */
    public static void clearGlideMemory() {
        Glide.get(App.getInstance().mContext).clearMemory();//  可以在UI主线程中进行
    }

    /**
     * 清理磁盘缓存
     */
    public static void clearGlideDiskCache() {
        Glide.get(App.getInstance().mContext).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
    }
}