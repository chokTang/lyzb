package com.lyzb.jbx.adapter.home.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;
import com.szy.yishopcustomer.ResponseModel.Search.SearchHintModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

public class SearchValueAdapter extends BaseRecyleAdapter<SearchHintModel> {

    private final static int TYPE_FRIST = 0x635;
    private final static int TYPE_SECOND = 0x636;

    private String mSearchValue = "";
    private IRecycleAnyClickListener clickListener;

    public SearchValueAdapter(Context context, List<SearchHintModel> list) {
        super(context, -1, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, final SearchHintModel item) {
        switch (holder.getItemViewType()) {
            case TYPE_FRIST:
                switch (holder.getAdapterPosition()) {
                    case 0:
                        holder.setText(R.id.tv_firsh_value, String.format("搜索“%s”企业家、专家、达人", mSearchValue));
                        holder.setImageUrl(R.id.tv_firsh_img, R.drawable.union_sreach_account);
                        break;
                    case 1:
                        holder.setText(R.id.tv_firsh_value, String.format("搜索“%s”圈子、企业", mSearchValue));
                        holder.setImageUrl(R.id.tv_firsh_img, R.drawable.union_sreach_circle);
                        break;
                    case 2:
                        holder.setText(R.id.tv_firsh_value, String.format("搜索“%s”动态", mSearchValue));
                        holder.setImageUrl(R.id.tv_firsh_img, R.drawable.union_sreach_dynamic);
                        break;
                    case 3:
                        holder.setText(R.id.tv_firsh_value, String.format("搜索“%s”店铺", mSearchValue));
                        holder.setImageUrl(R.id.tv_firsh_img, R.drawable.union_sreach_shop);
                        break;
                }
                break;
            case TYPE_SECOND:
                holder.setText(R.id.tv_second_value, mSearchValue);
                holder.addItemClickListener(R.id.tv_second_value);

                final TagFlowLayout tagFlowLayout = holder.cdFindViewById(R.id.tag_layout);
                tagFlowLayout.setAdapter(new TagAdapter<String>(item.getTags()) {
                    @Override
                    public View getView(FlowLayout parent, int position, String o) {
                        TextView tv = (TextView) LayoutInflater.from(_context).inflate(R.layout.flowlayout_text,
                                tagFlowLayout, false);
                        tv.setText(o);
                        return tv;
                    }
                });
                tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        if (clickListener != null) {
                            clickListener.onItemClick(view, position, item.getTags().get(position));
                        }
                        return true;
                    }
                });
                break;
        }
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        switch (viewType) {
            case TYPE_FRIST:
                return new BaseViewHolder(_context, getItemView(R.layout.recycle_search_value_first, parent));
            case TYPE_SECOND:
                return new BaseViewHolder(_context, getItemView(R.layout.recycle_search_value_second, parent));
        }
        return super.onChildCreateViewHolder(viewType, parent);
    }

    @Override
    protected int getChildItemViewType(int position) {
        if (position < 4) {
            return TYPE_FRIST;
        } else {
            return TYPE_SECOND;
        }
    }

    public void update(String value, List<SearchHintModel> items) {
        mSearchValue = value;
        _list.clear();
        _list.add(new SearchHintModel());
        _list.add(new SearchHintModel());
        _list.add(new SearchHintModel());
        _list.add(new SearchHintModel());
        _list.addAll(items);
        notifyDataSetChanged();
    }

    public void setClickListener(IRecycleAnyClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
