package com.szy.yishopcustomer.Adapter

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.szy.yishopcustomer.Activity.GoodsActivity
import com.szy.yishopcustomer.Activity.ProjectH5Activity
import com.szy.yishopcustomer.Activity.samecity.GroupBuyActivity
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_BUNDLE
import com.szy.yishopcustomer.Activity.samecity.ShopDetailActivity.Companion.KEY_PRODUCT_ID
import com.szy.yishopcustomer.Adapter.CityHomeAdapter.KEY_SHOP_ID
import com.szy.yishopcustomer.Constant.Api
import com.szy.yishopcustomer.Constant.Key
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.GlideUtil
import com.szy.yishopcustomer.Util.LogUtils
import com.szy.yishopcustomer.Util.Utils
import com.szy.yishopcustomer.View.RatingBar
import com.szy.yishopcustomer.ViewModel.HomeShopAndProductBean

/**
 * Created by :TYK
 * Date: 2018/12/1411:37
 * Desc:主页附近商家模块适配器
 */
class HomeNearShopAdapter(layoutResId: Int) : BaseQuickAdapter<HomeShopAndProductBean.NearbyBean, BaseViewHolder>(layoutResId) {

    var adapter: NearShopProductAdapter? = null


    override fun convert(helper: BaseViewHolder?, item: HomeShopAndProductBean.NearbyBean?) {

        helper!!.setText(R.id.tv_near_shop_name, item!!.shop_name)
        if (!TextUtils.isEmpty(item.distance_msg)) {
            helper.setText(R.id.tv_near_time_distance, item.distance_msg)
        }

        if (!TextUtils.isEmpty(item.shop_config_msg)) {
            helper.setText(R.id.tv_near_shop_condition, item.shop_config_msg)
        }
        LogUtils.e("图片链接为"+Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL , item.shop_logo))
        Glide.with(helper.itemView.context).load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL , item.shop_logo)).
                apply(GlideUtil.EmptyOptions()).into(helper.getView<ImageView>(R.id.img_near_shop_header))
        val manager = LinearLayoutManager(helper.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        helper.getView<RecyclerView>(R.id.rv_home_near_shop_product).layoutManager = manager
        helper.getView<RatingBar>(R.id.ratingbar_near_shop).star = Math.ceil(item.desc_score.toDouble()).toInt()
        adapter = NearShopProductAdapter(R.layout.item_home_near_shop_product)
        helper.getView<RecyclerView>(R.id.rv_home_near_shop_product).adapter = adapter
        adapter!!.setNewData(item.goods_list)
        Glide.with(helper.itemView.context)
                .load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL,item.shop_poster))
                .thumbnail(0.2f)
                .apply(GlideUtil.FxRadioOptions(helper.itemView.context, false, false, true,
                        true, Utils.dpToPx(helper.itemView.context,5f)))
                .into(object : ViewTarget<View, Drawable>(helper.getView<RelativeLayout>(R.id.ll_near_shop_bg)) {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        this.view.background = resource!!.current
                    }
                })

        adapter!!.setOnItemClickListener { adapter, _, position ->
            var intent: Intent? = null
            val bean = adapter.data[position] as HomeShopAndProductBean.NearbyBean.GoodsListBean
            when (item.local_service) { //跳转商品页面
                "0" -> {//非同城商家
                    intent = Intent()
                    intent.putExtra(Key.KEY_GOODS_ID.value, bean.goods_id)
                    intent.setClass(helper.itemView.context, GoodsActivity::class.java)
                    helper.itemView.context.startActivity(intent)
                }
                "1" -> {//同城商家
                    if (bean.is_sale == "10001") {//团购10001
                        intent = Intent(helper.itemView.context, GroupBuyActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString(KEY_SHOP_ID, bean.shop_id)
                        intent.putExtra(KEY_BUNDLE, bundle)
                        intent.putExtra(KEY_PRODUCT_ID, bean.goods_id)
                    } else {//外卖10002
                        intent = Intent(helper.itemView.context, ProjectH5Activity::class.java)
                        intent.putExtra(Key.KEY_URL.value, Api.H5_CITYLIFE_URL + "/homepage?storeId=" + bean.shop_id + "&catId=" + bean.cat_id)
                    }
                    helper.itemView.context.startActivity(intent)
                }
            }
        }
    }
}

