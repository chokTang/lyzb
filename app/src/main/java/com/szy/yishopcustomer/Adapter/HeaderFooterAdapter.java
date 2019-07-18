package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Smart on 2017/7/5.
 */

public class HeaderFooterAdapter<T> extends RecyclerView.Adapter {

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    //HeaderView, FooterView
    View mHeaderView;
    View mFooterView;

    public List<T> data;

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getFooterView() {
        return mFooterView;
    }

    boolean isInsertFooter = false;
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        if(!isInsertFooter) {
            isInsertFooter = !isInsertFooter;
            notifyItemInserted(getItemCount()-1);
        } else {
            notifyItemRangeChanged(getItemCount()-1,1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    /** 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    * */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }
        if (mHeaderView != null && position == 0){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (mFooterView != null && position == getItemCount()-1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if(mHeaderView == null && mFooterView == null){
            return data.size();
        }else if(mHeaderView == null && mFooterView != null){
            return data.size() + 1;
        }else if (mHeaderView != null && mFooterView == null){
            return data.size() + 1;
        }else {
            return data.size() + 2;
        }
    }


    //在这里面加载ListView中的每个item的布局
    class ListHolder extends RecyclerView.ViewHolder{
        public ListHolder(View itemView) {
            super(itemView);
            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView){
                return;
            }
            if (itemView == mFooterView){
                return;
            }
        }
    }
}
