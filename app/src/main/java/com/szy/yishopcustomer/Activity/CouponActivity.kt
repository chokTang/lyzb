package com.szy.yishopcustomer.Activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.lyzb.jbx.R
import com.szy.yishopcustomer.Adapter.samecity.MyViewPageAdapter
import com.szy.yishopcustomer.Fragment.CouponOfflineFragment
import com.szy.yishopcustomer.Fragment.CouponOnlineFragment
import kotlinx.android.synthetic.main.activity_coupon.*

/**
 * Created by :TYK
 * Date: 2019/2/27  10:11
 * Desc:优惠券页面
 */

class CouponActivity : AppCompatActivity() ,View.OnClickListener{


    var viewPageAdapter: MyViewPageAdapter? = null
    var list: MutableList<String> = arrayListOf()
    var fragmentsList: MutableList<Fragment> = arrayListOf()
    var couponOnlineFragment: CouponOnlineFragment? = null
    var couponOfflineFragment: CouponOfflineFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)
        initView()
    }


    /**
     * 初始化
     */
    private fun initView() {
        list.add("线上使用")
        list.add("到店使用")
        couponOnlineFragment = CouponOnlineFragment()
        couponOfflineFragment = CouponOfflineFragment()
        fragmentsList.add(couponOnlineFragment!!)
        fragmentsList.add(couponOfflineFragment!!)
        viewPageAdapter = MyViewPageAdapter(supportFragmentManager, list, fragmentsList)
        viewPager.adapter = viewPageAdapter
        tablayout.setupWithViewPager(viewPager)//此方法就是让tablayout和ViewPager联动

    }



    override fun onClick(v: View?) {
    }
}