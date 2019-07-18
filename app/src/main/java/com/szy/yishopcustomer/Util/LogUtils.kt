package com.szy.yishopcustomer.Util

import android.util.Log
import com.lyzb.jbx.BuildConfig

class LogUtils {

    companion object {
        private val TAG = "ShopCustomer"
        private val IS_DEBUG = BuildConfig.DEBUG

        fun e(xml: String) {
            if (IS_DEBUG) {
                if (xml.length > 4000) {
                    var i = 0
                    while (i < xml.length) {
                        if (i + 4000 < xml.length)
                            Log.e(TAG, xml.substring(i, i + 4000))
                        else
                            Log.e(TAG, xml.substring(i, xml.length))
                        i += 4000
                    }
                } else
                    Log.e(TAG, xml)
            }
        }
    }

    fun d(var1: String) {
        if (IS_DEBUG) {
            Log.d(TAG, var1)
        }
    }

    fun v(var1: String) {
        if (IS_DEBUG) {
            Log.v(TAG, var1)
        }
    }
}




