package com.szy.yishopcustomer.Adapter.samecity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.utilslib.image.config.GlideApp;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.samecity.SortStoreActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.MoreSort.MoreModel;
import com.szy.yishopcustomer.Util.DividerGridItemDecoration;
import com.szy.yishopcustomer.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 分类更多 适配器
 * @time
 */

public class MoreSortAdapter extends RecyclerView.Adapter {

    public List<Object> data;
    public Context mContext;

    public MoreSortAdapter(Context context) {
        this.data = new ArrayList<>();
        this.mContext = context;
    }

    public RecyclerView.ViewHolder createItemView(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_sort, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }


    /***
     * ITEM布局切换
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createItemView(parent);
    }

    /***
     * ITEM 赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MoreModel moreModel = (MoreModel) data.get(position);
        bindItemMore((ItemViewHolder) holder, moreModel);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_more_icon)
        ImageView mImageView_Icon;
        @BindView(R.id.tv_more_title)
        TextView mTextView_Title;
        @BindView(R.id.recy_more_content)
        CommonRecyclerView mRecyclerView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            mRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, R.drawable.recy_grid_line));
        }
    }

    public void bindItemMore(ItemViewHolder holder, final MoreModel model) {

        GlideApp.with(mContext)
                .load(Utils.setImgNetUrl(Api.API_CITY_HOME_MER_IMG_URL, model.catgIcon))
                .error(R.mipmap.bg_empty_index)
                .into(holder.mImageView_Icon);

        holder.mTextView_Title.setText(model.catgName);



        MoreContentAdapter moreContentAdapter = new MoreContentAdapter(mContext);
        holder.mRecyclerView.setAdapter(moreContentAdapter);
        moreContentAdapter.data.addAll(model.children);
        moreContentAdapter.notifyDataSetChanged();

        moreContentAdapter.setClick(new MoreContentAdapter.onConItemClick() {
            @Override
            public void onItemClick(MoreModel.ChildrenBean bean) {

                //传递 parentId(父级id) catgId(ITEMid)
                Intent intent = new Intent(mContext, SortStoreActivity.class);
                intent.putExtra(SortStoreActivity.SORT_NAME, model.catgName);
                intent.putExtra(SortStoreActivity.TITLE_ID, bean.parentId);
                intent.putExtra(SortStoreActivity.TITLE_NAME, bean.catgName);
                mContext.startActivity(intent);
            }
        });
    }
}
