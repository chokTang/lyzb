package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.View.SiteHeaderListView;
import com.szy.yishopcustomer.ViewModel.SiteEntity;

import java.util.List;

/**
 * Created by liuzhifeng on 2016/8/25.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SiteAdapter extends BaseAdapter implements OnScrollListener, SiteHeaderListView
        .SiteHeaderAdapter {

    private static final String TAG = SiteAdapter.class.getSimpleName();

    private Context mContext;
    private List<SiteEntity> mData;
    private LayoutInflater mLayoutInflater;

    public SiteAdapter(Context pContext, List<SiteEntity> pData) {
        mContext = pContext;
        mData = pData;

        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void configurePinnedHeader(View headerView, int position, int alpaha) {
        // 设置标题的内容
        SiteEntity siteEntity = (SiteEntity) getItem(position);
        String headerValue = siteEntity.getTitle();

        if (!TextUtils.isEmpty(headerValue)) {
            TextView headerTextView = (TextView) headerView.findViewById(R.id.header);
            headerTextView.setText(headerValue);
        }

    }

    @Override
    public int getPinnedHeaderState(int position) {
        if (getCount() == 0 || position < 0) {
            return SiteHeaderListView.SiteHeaderAdapter.PINNED_HEADER_GONE;
        }

        if (isMove(position) == true) {
            return SiteHeaderListView.SiteHeaderAdapter.PINNED_HEADER_PUSHED_UP;
        }

        return SiteHeaderListView.SiteHeaderAdapter.PINNED_HEADER_VISIBLE;
    }

    @Override
    public int getCount() {
        if (null != mData) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != mData && position < getCount()) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.item_site, null);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取数据
        SiteEntity siteEntity = (SiteEntity) getItem(position);
        viewHolder.content.setText(siteEntity.getContent());

        if (needTitle(position)) {
            // 显示标题并设置内容
            viewHolder.title.setText(siteEntity.getTitle());
            viewHolder.title.setVisibility(View.VISIBLE);
        } else {
            // 内容项隐藏标题
            viewHolder.title.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int
            totalItemCount) {

        if (view instanceof SiteHeaderListView) {
            ((SiteHeaderListView) view).controlPinnedHeader(firstVisibleItem);
        }
    }

    private boolean isMove(int position) {
        // 获取当前与下一项
        SiteEntity currentEntity = (SiteEntity) getItem(position);
        SiteEntity nextEntity = (SiteEntity) getItem(position + 1);
        if (null == currentEntity || null == nextEntity) {
            return false;
        }

        // 获取两项header内容
        String currentTitle = currentEntity.getTitle();
        String nextTitle = nextEntity.getTitle();
        if (null == currentTitle || null == nextTitle) {
            return false;
        }

        // 当前不等于下一项header，当前项需要移动
        return !currentTitle.equals(nextTitle);

    }

    /**
     * 判断是否需要显示标题
     *
     * @param position
     * @return
     */
    private boolean needTitle(int position) {
        // 第一个肯定是分类
        if (position == 0) {
            return true;
        }

        // 异常处理
        if (position < 0) {
            return false;
        }

        // 当前  // 上一个
        SiteEntity currentEntity = (SiteEntity) getItem(position);
        SiteEntity previousEntity = (SiteEntity) getItem(position - 1);
        if (null == currentEntity || null == previousEntity) {
            return false;
        }

        String currentTitle = currentEntity.getTitle();
        String previousTitle = previousEntity.getTitle();
        if (null == previousTitle || null == currentTitle) {
            return false;
        }

        // 当前item分类名和上一个item分类名不同，则表示两item属于不同分类
        return !currentTitle.equals(previousTitle);

    }

    private class ViewHolder {
        TextView title;
        TextView content;
    }

}
