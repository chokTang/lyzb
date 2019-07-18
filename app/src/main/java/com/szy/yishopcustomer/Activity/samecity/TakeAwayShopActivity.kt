package com.szy.yishopcustomer.Activity.samecity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.lyzb.jbx.R
import com.szy.yishopcustomer.Adapter.samecity.MyViewPageAdapter
import com.szy.yishopcustomer.Fragment.ProductFragment
import com.szy.yishopcustomer.Fragment.ShopDetailFragment
import kotlinx.android.synthetic.main.activity_take_away_shop.*

/**
 * Created by :TYK
 * Date: 2019/2/21  10:40
 * Desc:外卖店铺详情
 */

class TakeAwayShopActivity : AppCompatActivity() {

    var viewPageAdapter: MyViewPageAdapter? = null
    var list:MutableList<String> = arrayListOf()
    var fragmentsList:MutableList<Fragment> = arrayListOf()
    var productFragment: ProductFragment?=null
    var shopDetailFragment: ShopDetailFragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_away_shop)
        initView()
    }

    /**
     * 初始化
     */
    private fun initView(){
        list.add("商品")
        list.add("店铺详情")
        productFragment = ProductFragment()
        shopDetailFragment = ShopDetailFragment()
        fragmentsList.add(productFragment!!)
        fragmentsList.add(shopDetailFragment!!)
        viewPageAdapter = MyViewPageAdapter(supportFragmentManager,list,fragmentsList)
        viewPager.adapter = viewPageAdapter

    }

}