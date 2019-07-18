package com.lyzb.jbx.inter

import android.support.v7.widget.RecyclerView

/**
 * Created by :TYK
 * Date: 2019/5/8  17:11
 * Desc:
 */
interface IRecycleHolderAnyClilck{
    fun onItemLongClick(holder:RecyclerView.ViewHolder,position:Int,obj:Any):Boolean
}