package com.lyzb.jbx.adapter.home.first;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.follow.InterestMemberModel;
import com.lyzb.jbx.model.me.CardModel;
import com.szy.yishopcustomer.Util.App;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeCardAdapter extends BaseRecyleAdapter<InterestMemberModel> {

    private final int ITEM_HEADER = 0x452;
    private final int ITEM_EMPTY = 0x453;
    private CardModel mCardModel = null;
    private Map<String, String> searchMap = new HashMap<>();//key值为 industryId industryName phoneId phoneName

    public HomeCardAdapter(Context context, List<InterestMemberModel> list) {
        super(context, -1, list);
        if (_list != null) {
            _list.add(0, new InterestMemberModel());
        }
        searchMap.put("industryId", "0");
        searchMap.put("industryName", "");
        searchMap.put("phoneId", "0");
        searchMap.put("phoneName", "");
    }

    @Override
    protected void convert(BaseViewHolder holder, InterestMemberModel item) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        BaseViewHolder holder = (BaseViewHolder) viewHolder;
        switch (holder.getItemViewType()) {
            case ITEM_HEADER:
                holder.setText(R.id.tv_industry, searchMap.get("industryName"));
                holder.setText(R.id.tv_phone, searchMap.get("phoneName"));
                holder.addItemClickListener(R.id.tv_industry);
                holder.addItemClickListener(R.id.tv_phone);
                holder.addItemClickListener(R.id.tv_throw_card);
                holder.addItemClickListener(R.id.tv_card_file);
                holder.setVisible(R.id.con_header, App.getInstance().isLogin());
                if (mCardModel == null) return;
                holder.setRoundImageUrl(R.id.img_me_header, mCardModel.getHeadimg(), 60);
                holder.setText(R.id.tv_me_name, mCardModel.getGsName());
                holder.setText(R.id.tv_me_info_progress, mCardModel.getPerfectStr());
                holder.setProgress(R.id.progress_card, mCardModel.getPerfectRatio());
                holder.setText(R.id.tv_me_info, mCardModel.getPerfectRatio() == 100 ? "进入名片" : "完善名片");
                holder.addItemClickListener(R.id.tv_me_info);
                holder.setText(R.id.tv_card_file, String.format("名片夹(%d)", mCardModel.getRelationGz()));
                break;
            case ITEM_EMPTY:
                break;
            default:
                final InterestMemberModel item = getPositionModel(position);
                holder.setRadiusImageUrl(R.id.img_recommon_header, item.getHeadimg(), 4);
                holder.setVisible(R.id.img_vip, item.getUserVipAction().size() > 0);
                holder.setText(R.id.tv_recommon_name, item.getGsName());
                holder.setVisible(R.id.tv_position_name, !TextUtils.isEmpty(item.getPosition()));
                holder.setText(R.id.tv_position_name, String.format("|  %s", item.getPosition()));
                holder.setText(R.id.tv_user_phone, TextUtils.isEmpty(item.getMobile())?"暂无电话":item.getMobile());
                holder.setText(R.id.tv_user_company, TextUtils.isEmpty(item.getShopName())?"暂无公司":item.getShopName());
                holder.setText(R.id.tv_user_need, String.format("我需要的:%s", item.getMyDemand()));
                holder.setText(R.id.tv_user_provide, String.format("我提供的:%s", item.getMyResources()));
                holder.setImageUrl(R.id.iv_recommon_bg, position % 2 == 0 ? R.drawable.union_recommend_bg2 : R.drawable.union_recommend_bg);

                dealFollowBtn(holder, item);

                holder.addItemClickListener(R.id.tv_follow);
                break;
        }
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        switch (viewType) {
            case ITEM_HEADER:
                return new BaseViewHolder(_context, getItemView(R.layout.recycle_home_card_first, parent));
            case ITEM_EMPTY:
                return new EmptyHolder(_context, getItemView(R.layout.empty_layout, parent));
            default:
                return new BaseViewHolder(_context, getItemView(R.layout.recycle_home_card, parent));
        }
    }

    @Override
    protected int getChildItemViewType(int position) {
        if (position == 0) {
            return ITEM_HEADER;
        } else if (position == 1 && _list.size() == 1) {
            return ITEM_EMPTY;
        }
        return super.getChildItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (_list.size() == 1) {//如果是空布局的时候
            return 2;
        }
        return super.getItemCount();
    }

    @Override
    public void update(List<InterestMemberModel> items) {
        if (items != null) {
            items.add(0, new InterestMemberModel());
        }
        super.update(items);
    }

    public void noticeFirstItem(CardModel model) {
        if (model == null) return;
        this.mCardModel = model;
        notifyItemChanged(0);
    }

    public CardModel getmCardModel() {
        return mCardModel;
    }

    public String getPhoneId() {
        return searchMap.get("phoneId");
    }

    public String getIndustryId() {
        return searchMap.get("industryId");
    }

    public Map<String, String> getSearchMap() {
        return searchMap;
    }

    private void dealFollowBtn(BaseViewHolder holder, InterestMemberModel model) {
        if (model.getUserId().equals(App.getInstance().userId)) {
            holder.setVisible(R.id.tv_follow, false);
            holder.setVisible(R.id.tv_follow, false);
        } else {
            holder.setVisible(R.id.tv_follow, true);
            TextView tv_no_follow = holder.cdFindViewById(R.id.tv_follow);
            if (model.getRelationNum() > 0) {//已关注--在线沟通
                holder.setText(R.id.tv_follow, "在线沟通");
                tv_no_follow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            } else {
                holder.setText(R.id.tv_follow, "关注");
                tv_no_follow.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(_context, R.drawable.union_add_gray),
                        null, null, null);
            }
        }
    }

    public class EmptyHolder extends BaseViewHolder {

        TextView empty_tv;

        public EmptyHolder(Context context, View itemView) {
            super(context, itemView);

            empty_tv = itemView.findViewById(R.id.empty_tv);
            empty_tv.setText("暂未匹配到该类用户哦，请重新筛选吧!");
        }
    }
}
