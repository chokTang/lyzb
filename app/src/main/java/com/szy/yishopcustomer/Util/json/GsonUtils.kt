package com.szy.yishopcustomer.Util.json

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.Util.Utils
import java.util.ArrayList


class GsonUtils {

    companion object {
        fun <T> toObj(json: String, clazz: Class<T>): T? = Gson().fromJson(json, clazz)
//        fun <T> toObj(json: String, clazz: Class<T>):T? {
//            var a: T? = null
//            try {
//                a = Gson().fromJson(json, clazz)
//            } catch (e: JsonSyntaxException) {
//                LogUtils.e("数据解析错误")
//            }
//            return a
//        }

        fun toJson(var1: Any): String = Gson().toJson(var1)
        fun <T> toList(jsonString: String, entity: Class<T>): List<T>? = Gson().fromJson(jsonString, GsonType(entity))
    }


    /**
     * json数据转换为实体集合
     *
     * @param jsonString
     * @return
     */
    fun <T> getEntityList(jsonString: String, entity: Class<T>): List<T>? {
        val gson = Gson()
        var list: List<T> = ArrayList()
        try {
            list = gson.fromJson(jsonString, GsonType(entity))
        } catch (e: Exception) {
            return ArrayList()
        }

        return list
    }
}