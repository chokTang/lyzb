package com.lyzb.jbx.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lyzb.jbx.widget.PhotoViewActivity

/**
 * Created by :TYK
 * Date: 2019/4/4  9:14
 * Desc:
 */

class ImageUtil {
    companion object {
        val BIG_IMAGE_URL = "bigimgurls"
        val BIG_IMAGE_POSITION = "bigimgurls"
        val BUNDLE = "bundle"

        /**  查看大图
         * list  是String类型的 Url
         * position  是点击的position
         */
        fun statPhotoViewActivity(context: Context, position: Int, list: MutableList<String>) {
            val intent = Intent(context, PhotoViewActivity::class.java)
            val bundle = Bundle()
            bundle.putStringArrayList(BIG_IMAGE_URL, list as ArrayList<String>?)
            intent.putExtra(BUNDLE, bundle)
            intent.putExtra(BIG_IMAGE_POSITION, position)
            context.startActivity(intent)
        }
    }

}