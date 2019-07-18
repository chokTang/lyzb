package com.szy.yishopcustomer.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.*;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.AppIndex.ArticleItemModel;
import com.szy.yishopcustomer.ViewHolder.Index.ArticleWrapperViewHolder;

import java.util.List;

/**
 * Created by 宗仁 on 16/5/20.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class IndexArticleAdapter extends PagerAdapter {
    public List<ArticleItemModel> data;

    public IndexArticleAdapter(List<ArticleItemModel> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return (int) Math.ceil((double) data.size() / 2.0);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout
                .fragment_index_article_wrapper, container, false);
        ArticleWrapperViewHolder viewHolder = new ArticleWrapperViewHolder(view);
        if (position * 2 + 1 < data.size()) {
            viewHolder.secondView.setVisibility(View.VISIBLE);
            viewHolder.secondView.setVisibility(View.VISIBLE);
            ArticleItemModel secondItemModel = data.get(position * 2 + 1);
            viewHolder.secondItemViewHolder.textView.setText(secondItemModel.title);
        } else {
            viewHolder.secondView.setVisibility(View.INVISIBLE);
            viewHolder.secondView.setVisibility(View.INVISIBLE);
        }
        ArticleItemModel firstItemModel = data.get(position * 2);
        viewHolder.firstItemViewHolder.textView.setText(firstItemModel.title);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
