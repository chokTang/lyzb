package com.szy.yishopcustomer.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lyzb.jbx.R

/**
 * Created by :TYK
 * Date: 2019/2/26  13:45
 * Desc:店铺详情中的商品碎片
 */

class ProductFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_shop_product,container,false)
    }
}