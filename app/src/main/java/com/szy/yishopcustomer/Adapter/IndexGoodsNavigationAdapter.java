package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.NavigationItemModel;
import com.szy.yishopcustomer.ViewHolder.Index.GoodsNavigationWrapperViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smart on 18/1/3.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IndexGoodsNavigationAdapter extends PagerAdapter {

    private int spanCount = 4;

    public View.OnClickListener onClickListener;
    public int itemPosition;
    private List<NavigationItemModel> mData;
//    private GridLayoutManager gridLayoutManager;


    public IndexGoodsNavigationAdapter(Context mContext, List<NavigationItemModel> data, int spanCount, int itemPosition) {
        super();
        this.mData = data;
        this.spanCount = spanCount;
        this.itemPosition = itemPosition;

//        gridLayoutManager = new GridLayoutManager(mContext, spanCount);
    }

    @Override
    public int getCount() {
        return (int) Math.ceil((float) mData.size() / (2*spanCount));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View wrapper = LayoutInflater.from(container.getContext()).inflate(R.layout
                .fragment_index_goods_navigation_wrapper, container, false);
        GoodsNavigationWrapperViewHolder viewHolder = new GoodsNavigationWrapperViewHolder(wrapper);

        List<NavigationItemModel> list = new ArrayList<>();

        if(position < getCount() - 1) {
            for(int i = position * 2 * spanCount,len = (position+1) * 2 * spanCount ; i < len ; i ++) {
                list.add(mData.get(i));
            }
        } else {
            for(int i = position * 2 * spanCount,len = mData.size() ; i < len ; i ++) {
                list.add(mData.get(i));
            }
        }

        IndexNavigationAdapter adapter = new IndexNavigationAdapter(list);
        adapter.baseNum = position * 2 * spanCount;
        adapter.onClickListener = onClickListener;
        adapter.itemPosition = itemPosition;

        viewHolder.recyclerView.setNestedScrollingEnabled(false);
        viewHolder.recyclerView.setAdapter(adapter);
        viewHolder.recyclerView.setLayoutManager( new GridLayoutManager(viewHolder.itemView.getContext(), spanCount));


        container.addView(wrapper);
        return wrapper;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}