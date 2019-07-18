package com.szy.yishopcustomer.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.Search.KeyWordModel;
import com.szy.yishopcustomer.ResponseModel.Search.SearchHintModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.SearchDeleteViewHolder;
import com.szy.yishopcustomer.ViewHolder.SearchEmptyViewHolder;
import com.szy.yishopcustomer.ViewHolder.SearchHotListViewHolder;
import com.szy.yishopcustomer.ViewHolder.SearchTitleViewHolder;
import com.szy.yishopcustomer.ViewHolder.SearchViewHolder;
import com.szy.yishopcustomer.ViewModel.SearchTitleModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuzhfieng on 17/2/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class SearchAdapter extends RecyclerView.Adapter {

    private static final int ITEM_TYPE_TITLE = 0;
    private static final int ITEM_TYPE_BLANK = 1;
    private static final int ITEM_TYPE_HOT_BUTTON = 2;
    private static final int ITEM_TYPE_HISTORY_BUTTON = 3;
    private static final int ITEM_TYPE_SEARCH_DELETE = 4;

    private static final int ITEM_TYPE_SEARCH_HINT = 6;

    private static final int ITEM_TYPE_SEARCH_SHOP_NAME = 9;

    public View.OnClickListener onCLickListener;
    public List<Object> data;

    public boolean isHaveHistory = false;

    /**
     * 是否为输入文本后的搜索结果提示 item
     **/
    public boolean isHintList = false;

    public List<SearchHintModel> mHintModels;

    public SearchAdapter() {
        this.data = new ArrayList<>();
        this.mHintModels = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {

            case ITEM_TYPE_TITLE:
                return createTitleViewHolder(parent, inflater);
            case ITEM_TYPE_HOT_BUTTON:
                return createSearchHotViewHolder(parent, inflater);
            case ITEM_TYPE_HISTORY_BUTTON:
                return createSearchViewHolder(parent, inflater);
            case ITEM_TYPE_BLANK:
                return createBlankViewHolder(parent, inflater);
            case ITEM_TYPE_SEARCH_DELETE:
                return createSearchDeleteViewHolder(parent, inflater);
            case ITEM_TYPE_SEARCH_HINT:
                return createSearchHintHolder(parent, inflater);
            case ITEM_TYPE_SEARCH_SHOP_NAME:
                return createSearchShopHintHolder(parent,inflater);
            default:
                return null;
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_HOT_BUTTON:
                setSearchHotData((SearchHotListViewHolder) viewHolder, position);
                break;
            case ITEM_TYPE_TITLE:
                setSearchTitleData((SearchTitleViewHolder) viewHolder, position);
                break;
            case ITEM_TYPE_HISTORY_BUTTON:
                setSearchHistoryData((SearchViewHolder) viewHolder, position);
                break;
            case ITEM_TYPE_BLANK:
                SearchEmptyViewHolder searchEmptyViewHolder = (SearchEmptyViewHolder) viewHolder;
                CheckoutDividerModel item = (CheckoutDividerModel) data.get(position);
                searchEmptyViewHolder.mTextView.setText(item.textString);
                break;
            case ITEM_TYPE_SEARCH_DELETE:
                setSearchDeleteData((SearchDeleteViewHolder) viewHolder, position);
                break;
            case ITEM_TYPE_SEARCH_HINT:
                SearchHintModel model = mHintModels.get(position);
                setSearchHinteData((SearchHintHolder) viewHolder, model, position);
                break;
            case ITEM_TYPE_SEARCH_SHOP_NAME:
                setSearchHintShopName((SearchShopNameHolder) viewHolder,position);
                break;
        }

    }

    private void setSearchHintShopName(SearchShopNameHolder viewHolder, int position) {
        SearchShopNameHolder itemViewHolder = viewHolder;
        if (TextUtils.isEmpty(mHintModels.get(position).row)){
            itemViewHolder.tvFirstShopName.setVisibility(View.GONE);
        }else {
            itemViewHolder.tvFirstShopName.setVisibility(View.VISIBLE);
            String model =  mHintModels.get(position).row;
            itemViewHolder.tvFirstShopName.setText("搜索\""+model+"\"店铺");
        }
        Utils.setViewTypeForTag(itemViewHolder.tvFirstShopName, ViewType.VIEW_TYPE_SEARCH_HINT_SHOP_NAME);
        Utils.setPositionForTag(itemViewHolder.tvFirstShopName, position);
        itemViewHolder.tvFirstShopName.setOnClickListener(onCLickListener);
    }

    private void setSearchHistoryData(SearchViewHolder viewHolder, int position) {
        SearchViewHolder itemViewHolder = viewHolder;
        String model = (String) data.get(position);
        itemViewHolder.mTextView.setText(model);
        Utils.setViewTypeForTag(itemViewHolder.mTextView, ViewType.VIEW_TYPE_SEARCH_BUTTON_HISTORY);
        Utils.setPositionForTag(itemViewHolder.mTextView, position);
        itemViewHolder.mTextView.setOnClickListener(onCLickListener);
    }

    private void setSearchDeleteData(SearchDeleteViewHolder viewHolder, int position) {
        SearchDeleteViewHolder itemViewHolder = viewHolder;

        Utils.setViewTypeForTag(itemViewHolder.linearlayout_delete_search, ViewType.VIEW_TYPE_SEARCH_DELETE);
        Utils.setPositionForTag(itemViewHolder.linearlayout_delete_search, position);
        itemViewHolder.linearlayout_delete_search.setOnClickListener(onCLickListener);
    }

    private void setSearchTitleData(SearchTitleViewHolder viewHolder, int position) {
        Context mContext = viewHolder.itemView.getContext();

        SearchTitleViewHolder itemViewHolder = viewHolder;
        SearchTitleModel item = (SearchTitleModel) data.get(position);
        itemViewHolder.mTextView.setText(item.titleName);
        if (item.titleType.equals("hotSearch")) {
            itemViewHolder.view_line.setVisibility(View.GONE);
            itemViewHolder.line.setVisibility(View.GONE);
            itemViewHolder.mTextView.setPadding(itemViewHolder.mTextView.getPaddingLeft(), Utils.dpToPx(mContext, 15), itemViewHolder.mTextView.getPaddingRight(), itemViewHolder.mTextView.getPaddingBottom());
        } else {
            itemViewHolder.view_line.setVisibility(View.VISIBLE);
            itemViewHolder.line.setVisibility(View.VISIBLE);
            itemViewHolder.mTextView.setPadding(itemViewHolder.mTextView.getPaddingLeft(), Utils.dpToPx(mContext, 10), itemViewHolder.mTextView.getPaddingRight(), itemViewHolder.mTextView.getPaddingBottom());
        }

    }

    @SuppressLint("ResourceType")
    private void setSearchHotData(SearchHotListViewHolder viewHolder, int position) {
        SearchHotListViewHolder itemViewHolder = viewHolder;
        List<KeyWordModel> keyWordModel = (List<KeyWordModel>) data.get(position);

        Context mContext = viewHolder.itemView.getContext();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, Utils.dpToPx(mContext, 15), 0);

        viewHolder.flowLayout_hot.removeAllViews();
        viewHolder.linearLayout_hot.removeAllViews();

        if (isHaveHistory) {
            viewHolder.linearLayout_hot.setVisibility(View.VISIBLE);
            viewHolder.flowLayout_hot.setVisibility(View.GONE);
        } else {
            viewHolder.linearLayout_hot.setVisibility(View.GONE);
            viewHolder.flowLayout_hot.setVisibility(View.VISIBLE);
        }

        for (int i = 0, len = keyWordModel.size(); i < len; i++) {
            TextView tv = new TextView(mContext);
            tv.setBackgroundResource(R.drawable.item_background_selector_search);
            tv.setTextColor(mContext.getResources().getColor(R.color.colorTwo));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tv.setPadding(Utils.dpToPx(mContext, 10), Utils.dpToPx(mContext, 4), Utils.dpToPx(mContext, 10), Utils.dpToPx(mContext, 4));
            tv.setMaxLines(1);
            tv.setGravity(Gravity.CENTER);
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setText(keyWordModel.get(i).keyword);

            if (isHaveHistory) {
                if (i < len - 1) {
                    tv.setLayoutParams(layoutParams);
                }
                viewHolder.linearLayout_hot.addView(tv);
            } else {
                viewHolder.flowLayout_hot.addView(tv);
            }

            Utils.setViewTypeForTag(tv, ViewType.VIEW_TYPE_SEARCH_BUTTON);
            Utils.setPositionForTag(tv, position);
            Utils.setExtraInfoForTag(tv, i);
            tv.setOnClickListener(onCLickListener);
        }
    }

    private void setSearchHinteData(final SearchHintHolder viewHolder, SearchHintModel model, int position) {

        viewHolder.mTextView.setText(model.row);

        if (model.tags.size() > 0) {

            viewHolder.mTextOne.setVisibility(View.GONE);
            viewHolder.mTextTwo.setVisibility(View.GONE);
            viewHolder.mTextThr.setVisibility(View.GONE);
            if (model.tags.size() == 1) {

                viewHolder.mTextOne.setText(model.tags.get(0).toString());
                viewHolder.mTextOne.setVisibility(View.VISIBLE);
            } else if (model.tags.size() == 2) {

                viewHolder.mTextOne.setText(model.tags.get(0).toString());
                viewHolder.mTextTwo.setText(model.tags.get(1).toString());

                viewHolder.mTextOne.setVisibility(View.VISIBLE);
                viewHolder.mTextTwo.setVisibility(View.VISIBLE);
            } else if (model.tags.size() >= 3) {

                viewHolder.mTextOne.setText(model.tags.get(0).toString());
                viewHolder.mTextTwo.setText(model.tags.get(1).toString());
                viewHolder.mTextThr.setText(model.tags.get(2).toString());

                viewHolder.mTextOne.setVisibility(View.VISIBLE);
                viewHolder.mTextTwo.setVisibility(View.VISIBLE);
                viewHolder.mTextThr.setVisibility(View.VISIBLE);
            }

        }

        //搜索提示 标签点击
        Utils.setViewTypeForTag(viewHolder.mTextOne, ViewType.VIEW_TYPE_SEARCH_HINT_ONE_TAG);
        Utils.setPositionForTag(viewHolder.mTextOne, position);
        viewHolder.mTextOne.setOnClickListener(onCLickListener);

        Utils.setViewTypeForTag(viewHolder.mTextTwo, ViewType.VIEW_TYPE_SEARCH_HINT_TWO_TAG);
        Utils.setPositionForTag(viewHolder.mTextTwo, position);
        viewHolder.mTextTwo.setOnClickListener(onCLickListener);

        Utils.setViewTypeForTag(viewHolder.mTextThr, ViewType.VIEW_TYPE_SEARCH_HINT_THR_TAG);
        Utils.setPositionForTag(viewHolder.mTextThr, position);
        viewHolder.mTextThr.setOnClickListener(onCLickListener);

        Utils.setViewTypeForTag(viewHolder.mTextView, ViewType.VIEW_TYPE_SEARCH_HINT_LIST);
        Utils.setPositionForTag(viewHolder.mTextView, position);
        viewHolder.mTextView.setOnClickListener(onCLickListener);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHintList) {
            if (position==0){//第一条显示的事店铺
                return ITEM_TYPE_SEARCH_SHOP_NAME;
            }else {
                return ITEM_TYPE_SEARCH_HINT;
            }
        } else {
            Object object = data.get(position);

            if (object instanceof SearchTitleModel) {
                SearchTitleModel searchTitleModel = (SearchTitleModel) object;
                if (!("hotSearch").equals(searchTitleModel.titleType)) {
                    isHaveHistory = true;
                }
                return ITEM_TYPE_TITLE;
            } else if (object instanceof CheckoutDividerModel) {
                return ITEM_TYPE_BLANK;
            } else if (object instanceof List) {
                return ITEM_TYPE_HOT_BUTTON;
            } else if (object instanceof String) {
                return ITEM_TYPE_HISTORY_BUTTON;
            } else if (object instanceof Integer) {
                return ITEM_TYPE_SEARCH_DELETE;
            } else {
                return -1;
            }
        }
    }

    @Override
    public int getItemCount() {

        if (isHintList) {
            return mHintModels.size();
        } else {
            return data.size();
        }
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }

    @NonNull
    private RecyclerView.ViewHolder createSearchDeleteViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_search_delete, parent, false);
        return new SearchDeleteViewHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createBlankViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_search_empty, parent, false);
        return new SearchEmptyViewHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createTitleViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_search_title, parent, false);
        return new SearchTitleViewHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createSearchViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.search_activity_parameter_item, parent, false);
        return new SearchViewHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createSearchHotViewHolder(ViewGroup parent, LayoutInflater
            inflater) {
        View view = inflater.inflate(R.layout.item_search_hot, parent, false);
        return new SearchHotListViewHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createSearchHintHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.search_hint_item, parent, false);
        return new SearchHintHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder createSearchShopHintHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_search_hint_shop_first, parent, false);
        return new SearchShopNameHolder(view);
    }

    public class SearchHintHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_hint_tag_one)
        public TextView mTextOne;
        @BindView(R.id.tv_hint_tag_two)
        public TextView mTextTwo;
        @BindView(R.id.tv_hint_tag_thr)
        public TextView mTextThr;


        @BindView(R.id.tv_search_hint_text)
        public TextView mTextView;

        public SearchHintHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public class SearchShopNameHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_first_shop_name)
        public TextView tvFirstShopName;

        public SearchShopNameHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
