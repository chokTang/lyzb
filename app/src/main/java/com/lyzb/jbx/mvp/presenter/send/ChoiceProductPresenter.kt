package com.lyzb.jbx.mvp.presenter.send

import com.like.longshaolib.base.inter.IRequestListener
import com.like.utilslib.json.GSONUtil
import com.like.utilslib.other.LogUtil
import com.lyzb.jbx.model.send.DefaultProductModel
import com.lyzb.jbx.model.send.GoodsModel
import com.lyzb.jbx.mvp.APPresenter
import com.lyzb.jbx.mvp.view.send.IChoiceProductView
import com.lyzb.jbx.util.ModelToBeanlUtil
import com.szy.yishopcustomer.Util.App
import io.reactivex.Observable
import org.json.JSONObject

/**
 * Created by :TYK
 * Date: 2019/5/8  9:53
 * Desc:
 */
class ChoiceProductPresenter : APPresenter<IChoiceProductView>() {
    var goodslist: MutableList<GoodsModel> = arrayListOf()
    var startPage = 1

    /**
     * 默认展示爆品推广的商品
     */
    fun defaultProduct(isRefresh: Boolean) {
        if (isRefresh) {
            startPage = 1
        } else {
            startPage++
        }
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val params = HashMap<String, String>()
                params["region_code"] = "0"//配送地址
                params["cat_id"] = "0"//商品品类
                params["low_price"] = "0"// 最低价
                params["high_price"] = Integer.MAX_VALUE.toString()// 最高价
                params["cur_page"] = startPage.toString()// 页码
                params["page_size"] = App.PageSize.toString()// 每页条数
                //销量正序 =3 ，销量倒序 = 4，发布时间正序 =2，发布时间倒序 =1 ，商品价格正序 =5 ，商品价格降序 =6 ,
                // 最高抵扣元宝正序 =7 ，最高抵扣元宝倒序 =8
//                params["commodity_order"] = "8"
                return commonApi.getGuaGuaHotShop(getKeyValueByMd5(params))
            }

            override fun onSuccess(t: String?) {
                val jsonObject = JSONObject(t)
                val list = GSONUtil.getEntityList(jsonObject.getJSONObject("data").
                        getJSONArray("list").toString(), DefaultProductModel::class.java)

                goodslist.clear()
                for (i in 0 until list.size) {//将default model转成goodsmodel
                    goodslist.add(ModelToBeanlUtil.doDefaultProModelToGoodsMode(list[i]))
                }
                view.defaultSuccess(isRefresh, goodslist)
            }

            override fun onFail(message: String?) {
                view.defaultFail(isRefresh)
            }

        })
    }

    /**
     * 搜索search商品
     */
    fun searchProduct(isRefresh: Boolean, keyword: String) {
        if (isRefresh) {
            startPage = 1
        } else {
            startPage++
        }
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val params = java.util.HashMap<String, Any>()
                params["msg"] = keyword
                params["order"] = "DESC"
                params["page"] = startPage
                params["size"] = App.PageSize
                params["sort"] = 0
                params["user_id"] = if (App.getInstance().userId == null) "" else App.getInstance().userId
                return APPresenter.commonApi.getSearchGoodsList(getHeadersMap(APPresenter.GET, "/esapi/lbs/search"), params)
            }

            override fun onSuccess(t: String?) {
                val jsonObject = JSONObject(t)
                val list = GSONUtil.getEntityList(jsonObject.getJSONObject("data").getString("list"), GoodsModel::class.java)
                view.defaultSuccess(isRefresh, list)
            }

            override fun onFail(message: String?) {
                view.defaultFail(isRefresh)
            }

        })
    }


    /**
     * 获取历史选择商品列表
     */
    fun getHistoryProduct(isRefresh: Boolean) {
        if (isRefresh) {
            startPage = 1
        } else {
            startPage++
        }
        onRequestData(false, object : IRequestListener<String> {
            override fun onCreateObservable(): Observable<*> {
                val params = HashMap<String, String>()
                params["pageNum"] = startPage.toString()
                params["pageSize"] = App.PageSize.toString()
                params["haveChose"] = ""

                return sendApi.getHistoryProduct(getHeadersMap(GET, "/lbs/gs/topic/getHistoryGoods"), params)
            }

            override fun onSuccess(t: String?) {
                val jsonObject = JSONObject(t)
                val list = GSONUtil.getEntityList(jsonObject.getString("data"), DefaultProductModel::class.java)

                goodslist.clear()
                for (i in 0 until list.size) {//将default model转成goodsmodel
                    goodslist.add(ModelToBeanlUtil.doDefaultProModelToGoodsMode(list[i]))
                }
                view.defaultSuccess(isRefresh, goodslist)
            }

            override fun onFail(message: String?) {
                view.defaultFail(isRefresh)
            }

        })
    }
}