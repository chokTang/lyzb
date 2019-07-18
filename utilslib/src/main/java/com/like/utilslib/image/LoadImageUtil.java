package com.like.utilslib.image;

import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.like.utilslib.R;
import com.like.utilslib.UtilApp;
import com.like.utilslib.image.config.GlideApp;
import com.like.utilslib.image.helper.GlideCircleTransform;
import com.like.utilslib.image.helper.GlideRoundTransform;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;

/**
 * 加载图片工具类
 * Created by longshao on 2017/5/10.
 */

public class LoadImageUtil {

    /**
     * 加载图片
     * 默认0.2  倍缩略图
     *
     * @param img
     * @param imageRes
     */
    public static void loadImage(final ImageView img, Object imageRes) {
        final ImageView.ScaleType scaleType = img.getScaleType();
        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(UtilApp.getIntance().getApplicationContext())
                .load(imageRes)
                .thumbnail(0.2f)
                .placeholder(R.drawable.glide_rotateimage)
                .error(R.drawable.image_load_error)
                .dontAnimate()
                .fitCenter()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        img.setScaleType(scaleType);
                        anim.cancel();
                        return false;
                    }
                })
                .into(img);
    }

    /**
     * 加载图片
     * 缩略thumbnail  倍数 浮点数
     *
     * @param img
     * @param imageRes
     */
    public static void loadImageNo(final ImageView img, Object imageRes) {
        final ImageView.ScaleType scaleType = img.getScaleType();
        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(UtilApp.getIntance().getApplicationContext())
                .load(imageRes)
                .placeholder(R.drawable.glide_rotateimage)
                .error(R.drawable.image_load_error)
                .dontAnimate()
                .fitCenter()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        img.setScaleType(scaleType);
                        anim.cancel();
                        return false;
                    }
                })
                .into(img);
    }

    /**
     * 加载图片 给图片放大看详情使用
     *
     * @param img
     * @param imageRes
     */
    public static void loadImageRetail(ImageView img, Object imageRes) {
        GlideApp.with(UtilApp.getIntance().getApplicationContext())
                .load(imageRes)
                .error(R.drawable.image_load_error)
                .fitCenter()
                .skipMemoryCache(true)
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
    public static void loadSizeImage(final ImageView img, Object imageUrl, int width, int height) {
        width = DensityUtil.dpTopx(width);
        height = DensityUtil.dpTopx(height);

        final ImageView.ScaleType scaleType = img.getScaleType();
        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(UtilApp.getIntance().getApplicationContext())
                .load(imageUrl)
                .placeholder(R.drawable.glide_rotateimage)
                .error(R.drawable.image_load_error)
                .override(width, height)
                .dontAnimate()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        img.setScaleType(scaleType);
                        anim.cancel();
                        return false;
                    }
                })
                .into(img);
    }

    /**
     * 默认的加载椭圆角图片
     *
     * @param img
     * @param imageUrl
     * @param radius   圆角度数 dp
     */
    public static void loadRoundImage(final ImageView img, Object imageUrl, int radius) {
        final ImageView.ScaleType scaleType = img.getScaleType();
        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        radius = DensityUtil.dpTopx(radius);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(UtilApp.getIntance().getApplicationContext())
                .load(imageUrl)
                .transform(new GlideRoundTransform(radius))
                .placeholder(R.drawable.glide_rotateimage)
                .error(R.drawable.image_load_error)
                .dontAnimate()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        img.setScaleType(scaleType);
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
    public static void loadSizeRoundImage(final ImageView img, Object imageUrl, int width, int height, int radius) {
        width = DensityUtil.dpTopx(width);
        height = DensityUtil.dpTopx(height);
        radius = DensityUtil.dpTopx(radius);

        final ImageView.ScaleType scaleType = img.getScaleType();
        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(UtilApp.getIntance().getApplicationContext())
                .load(imageUrl)
                .transform(new GlideRoundTransform(radius))
                .override(width, height)
                .placeholder(R.drawable.glide_rotateimage)
                .error(R.drawable.image_load_error)
                .dontAnimate()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        img.setScaleType(scaleType);
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
    public static void loadRoundSizeImage(final ImageView img, Object imageUrl, int widthorHeight) {
        final ImageView.ScaleType scaleType = img.getScaleType();
        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        int widthorHeightother = DensityUtil.dpTopx(widthorHeight);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(UtilApp.getIntance().getApplicationContext())
                .load(imageUrl)
                .transform(new GlideCircleTransform())
                .override(widthorHeightother, widthorHeightother)
                .placeholder(R.drawable.glide_rotateimage)
                .error(R.drawable.image_load_error_round)
                .dontAnimate()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        img.setScaleType(scaleType);
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
    public static void loadImage(final ImageView img, Object imageRes, Integer defaltImg) {
        final ImageView.ScaleType scaleType = img.getScaleType();
        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(UtilApp.getIntance().getApplicationContext())
                .load(imageRes)
                .placeholder(R.drawable.glide_rotateimage)
                .error(defaltImg)
                .dontAnimate()
                .fitCenter()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        img.setScaleType(scaleType);
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
    public static void loadSizeImage(final ImageView img, Object imageUrl, int width, int height, Integer defaltImg) {
        width = DensityUtil.dpTopx(width);
        height = DensityUtil.dpTopx(height);

        final ImageView.ScaleType scaleType = img.getScaleType();
        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(UtilApp.getIntance().getApplicationContext())
                .load(imageUrl)
                .override(width, height)
                .placeholder(R.drawable.glide_rotateimage)
                .error(defaltImg)
                .dontAnimate()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        img.setScaleType(scaleType);
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
    public static void loadRoundImage(final ImageView img, Object imageUrl, int radius, Integer defaltImg) {
        radius = DensityUtil.dpTopx(radius);

        final ImageView.ScaleType scaleType = img.getScaleType();
        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(UtilApp.getIntance().getApplicationContext())
                .load(imageUrl)
                .transform(new GlideRoundTransform(radius))
                .placeholder(R.drawable.glide_rotateimage)
                .error(defaltImg)
                .dontAnimate()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        img.setScaleType(scaleType);
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
    public static void loadRoundSizeImage(final ImageView img, Object imageUrl, int widthorHeight, Integer defaltImg) {
        int widthorHeightother = DensityUtil.dpTopx(widthorHeight);

        final ImageView.ScaleType scaleType = img.getScaleType();
        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        final ObjectAnimator anim = ObjectAnimator.ofInt(img, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        GlideApp.with(UtilApp.getIntance().getApplicationContext())
                .load(imageUrl)
                .transform(new GlideCircleTransform())
                .override(widthorHeightother, widthorHeightother)
                .placeholder(R.drawable.glide_rotateimage)
                .error(defaltImg)
                .dontAnimate()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        img.setScaleType(scaleType);
                        return false;
                    }
                })
                .into(img);
    }

    /**
     * 清理内存缓存
     */
    public static void clearGlideMemory() {
        Glide.get(UtilApp.getIntance().getApplicationContext()).clearMemory();//  可以在UI主线程中进行
    }

    /**
     * 清理磁盘缓存
     */
    public static void clearGlideDiskCache() {
        Glide.get(UtilApp.getIntance().getApplicationContext()).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
    }


    /**
     * 根据屏幕宽度等比例缩放高度  加载图片
     *
     * @param img
     * @param imageUrl
     */
    public static void MachWitdhHightWrop(final ImageView img, Object imageUrl, final int margin) {
        Glide.with(UtilApp.getIntance().getApplicationContext()).load(imageUrl)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        int imageWidth = resource.getIntrinsicWidth();
                        int imageHeight = resource.getIntrinsicHeight();
                        int width = ScreenUtil.getScreenWidth() - DensityUtil.dpTopx(margin);//固定宽度
                        //宽度固定,然后根据原始宽高比得到此固定宽度需要的高度
                        int height = width * imageHeight / imageWidth;
                        ViewGroup.LayoutParams para = img.getLayoutParams();
                        para.height = height;
                        para.width = width;
                        img.setImageDrawable(resource);
                    }
                });


    }


}