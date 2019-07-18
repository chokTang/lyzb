package com.szy.yishopcustomer.Adapter

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.szy.yishopcustomer.Activity.ProjectH5Activity
import com.szy.yishopcustomer.Activity.ShopActivity
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity
import com.szy.yishopcustomer.Adapter.CityHomeAdapter.KEY_SHOP_ID
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.Key
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.App
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.Util.Utils
import com.szy.yishopcustomer.Util.json.GsonUtils
import com.szy.yishopcustomer.View.BannerScroller
import com.szy.yishopcustomer.View.CirclePageIndicator
import com.szy.yishopcustomer.View.HeadViewPager
import com.szy.yishopcustomer.ViewHolder.Index.AdBannerViewHolder
import com.szy.yishopcustomer.ViewModel.HomeShopAndProductBean
import com.szy.yishopcustomer.ViewModel.NearShopMoreModel

/**
 * Created by :TYK
 * Date: 2018/12/18  9:12
 * Desc:附近商家-更多  适配器
 */

class NearShopMoreAdapter(layoutResId: Int) : BaseQuickAdapter<NearShopMoreModel, BaseViewHolder>(layoutResId) {
    var nearShopAdapter: HomeNearShopAdapter? = null//附近商家适配器
    var onClickListener: View.OnClickListener? = null

    companion object {
        const val TYPE_BANNER = 33321  // 顶部banner
        const val TYPE_SHOP = 33322  //店铺列表
    }


    init {
        multiTypeDelegate = object : MultiTypeDelegate<NearShopMoreModel>() {
            override fun getItemType(t: NearShopMoreModel?): Int {
                var type = 0
                when (t!!.type) {
                    1 -> {//广告
                        type = TYPE_BANNER
                    }
                    2 -> {//附近商店
                        type = TYPE_SHOP
                    }
                }
                return type
            }
        }


        multiTypeDelegate.registerItemType(TYPE_BANNER, R.layout.item_fragment_city_banner)
                .registerItemType(TYPE_SHOP, R.layout.item_home_near_shop)
    }

    override fun convert(helper: BaseViewHolder?, item: NearShopMoreModel?) {

        when (helper!!.itemViewType) {
            TYPE_BANNER -> {//banner时候设置
                bindItemBanner(helper, item)

            }
            TYPE_SHOP -> {//附近商家时候的设置
                val recyclerView = helper.getView<RecyclerView>(R.id.rv_home_near_shop)
                helper.getView<RelativeLayout>(R.id.title).visibility = View.GONE
                val manager = LinearLayoutManager(helper.itemView.context, LinearLayoutManager.VERTICAL, false)
                recyclerView.layoutManager = manager
                if (null == nearShopAdapter) {
                    nearShopAdapter = HomeNearShopAdapter(R.layout.item_item_home_near_shop)
                    recyclerView.adapter = nearShopAdapter
                }

                nearShopAdapter!!.setOnItemClickListener(OnItemClickListener { adapter, view, position ->
                    val bean = adapter.getItem(position) as HomeShopAndProductBean.NearbyBean?
                            ?: return@OnItemClickListener
                    var intent: Intent? = null
                    when (bean.local_service) {
                        //跳转店铺详情
                        "0" -> {//非同城商家 店铺
                            intent = Intent()
                            intent.putExtra(Key.KEY_SHOP_ID.value, bean.shop_id)
                            intent.setClass(helper.itemView.context, ShopActivity::class.java)
                            helper.itemView.context.startActivity(intent)
                        }
                        "1" -> {//同城商家  店铺
                            if (bean.take_out_status == "0") {//非外卖
                                intent = Intent(helper.itemView.context, ShopDetailActivity::class.java)
                                intent.putExtra(KEY_SHOP_ID, bean.shop_id)
                            } else {//外卖1
                                intent = Intent(helper.itemView.context, ProjectH5Activity::class.java)
                                intent.putExtra(Key.KEY_URL.value, Api.H5_CITYLIFE_URL + "/homepage?storeId=" + bean.shop_id)
                            }
                            helper.itemView.context.startActivity(intent)
                        }
                    }
                })

                recyclerView.setHasFixedSize(true)
                recyclerView.isNestedScrollingEnabled = false
                nearShopAdapter!!.setNewData(item!!.nearby)
            }
        }

    }


    /***
     * Banner 数据绑定
     * @param holder
     * @param position
     */
    fun bindItemBanner(holder: BaseViewHolder, item: NearShopMoreModel?) {

        val mHeadViewPager = holder.getView<com.szy.yishopcustomer.View.HeadViewPager>(R.id.fragment_city_banner_viewPager)
        if (null != item!!.adver) {
            mHeadViewPager.visibility = View.VISIBLE
            val layoutParams = mHeadViewPager.layoutParams
            mHeadViewPager.layoutParams = layoutParams
            val adapter = NearShopAdBannerAdapter(holder.itemView.context, item!!.adver)
            mHeadViewPager.adapter = adapter
        } else {
            mHeadViewPager.visibility = View.GONE
        }
    }


}