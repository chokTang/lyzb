package com.szy.yishopcustomer.View;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.szy.yishopcustomer.Activity.ViewOriginalImageActivity;
import com.szy.yishopcustomer.Constant.Key;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Smart on 2017/6/3.
 */
public class JavascriptInterface {

    private Context context;

    public JavascriptInterface(Context context) {
        this.context = context;
    }

    /**
     * js调用打开查看大图的方法，在编译版本API17之后必须加上注解才能被调用
     * @param img
     * @param position
     */
    @android.webkit.JavascriptInterface
    public void openImage(String img, int position) {
        if (!TextUtils.isEmpty(img)) {
            String[] imgs = null;

            imgs = img.split(",");
            if (imgs != null) {
                ArrayList imgList = new ArrayList(Arrays.asList(imgs));
                Intent intent = new Intent();
                intent.setClass(context, ViewOriginalImageActivity.class);
                intent.putStringArrayListExtra(Key.KEY_GOODS_IMAGE_LIST.getValue(), imgList);
                intent.putExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.getValue(), position);
                context.startActivity(intent);
            }
        }
    }
}