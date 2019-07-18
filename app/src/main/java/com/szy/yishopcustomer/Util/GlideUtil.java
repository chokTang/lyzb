package com.szy.yishopcustomer.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.image.CornerTransform;


/**
 * Created by TYK on 2018/5/21.
 */
public class GlideUtil {



    /**
     * glide   加载条件占位图和错误图适配
     *
     * @return
     */
    public static RequestOptions Options() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.pl_image)
                .error(R.mipmap.pl_image)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        return options;
    }

    /**
     * glide   加载条件占位图和错误图适配
     *
     * @return
     */
    public static RequestOptions EmptyOptions() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.img_empty)
                .error(R.mipmap.img_empty)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        return options;
    }


    /**
     * glide   加载条件占位图和错误图适配4个圆角
     *
     * @return
     */
    public static RequestOptions RadioOptions(int radius) {
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(radius);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.bg_zfx_default)
                .error(R.mipmap.bg_zfx_default)
                .priority(Priority.HIGH)
                .bitmapTransform(roundedCorners)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        return options;
    }

    /**
     * glide   加载条件占位图和错误图适配4个圆角
     *
     * @return
     */
    public static RequestOptions RadioOptionsCanpany(int radius) {
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(radius);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_company_defult)
                .error(R.mipmap.icon_company_defult)
                .priority(Priority.HIGH)
                .bitmapTransform(roundedCorners)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        return options;
    }

    /**
     * glide  单圆角或则多圆角(上下左右)
     *除去哪几个不要圆角
     * @return
     */
    public static RequestOptions FxRadioOptions(Context context,boolean lefttop,boolean righttop
            ,boolean leftbottom,boolean rightbottom , int radius) {
        //设置图片圆角角度
        CornerTransform roundedCorners= new CornerTransform(context,radius);
        roundedCorners.setExceptCorner(lefttop,righttop,leftbottom,rightbottom);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.img_empty)
                .error(R.mipmap.img_empty)
                .priority(Priority.HIGH)
                .bitmapTransform(roundedCorners)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        return options;
    }


    /**
     * glide   加载条件占位图user和错误图适配
     *
     * @return
     */
    public static RequestOptions OptionsDefaultUser() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.pl_image)
                .error(R.drawable.pl_image)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        return options;
    }

    /**
     * glide   加载条件占位图doc和错误图适配
     *
     * @return
     */
    public static RequestOptions OptionsDefaultDoc() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.pl_image)
                .error(R.drawable.pl_image)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        return options;
    }

    /**
     * glide   加载条件占位图logo和错误图适配
     *
     * @return
     */
    public static RequestOptions OptionsDefaultLogo() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.pl_image)
                .error(R.drawable.pl_image)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        return options;
    }

    /**
     * glide   加载条件占位图logo和错误图适配
     *
     * @return
     */
    public static RequestOptions OptionsNoCropDefaultLogo() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.pl_image)
                .error(R.drawable.pl_image)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        return options;
    }

    /**
     * glide   加载条件占位图avata和错误图适配
     *
     * @return
     */
    public static RequestOptions OptionsDefaultAvata() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ease_default_avatar)
                .error(R.drawable.ease_default_avatar)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        return options;
    }


}
