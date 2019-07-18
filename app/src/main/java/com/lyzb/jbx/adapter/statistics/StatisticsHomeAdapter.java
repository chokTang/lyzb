package com.lyzb.jbx.adapter.statistics;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.like.utilslib.image.LoadImageUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.statistics.StatisticsHomeModel;

import java.util.List;

/**
 * @author wyx
 * @role 统计数据主页
 * @time 2019 2019/4/16 15:56
 */

public class StatisticsHomeAdapter extends BaseQuickAdapter<StatisticsHomeModel.DataListBean, BaseViewHolder> {


    public StatisticsHomeAdapter(@Nullable List<StatisticsHomeModel.DataListBean> data) {
        super(R.layout.item_statistics_home, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, StatisticsHomeModel.DataListBean item) {
        ImageView rankingIv = holder.getView(R.id.statistics_home_ranking_iv);
        TextView rankingTv = holder.getView(R.id.statistics_home_ranking_tv);
        //1、2、3名要展示图标，其余展示文字,这里因为加了header所以getAdapterPosition的下标是从1开始
        switch (holder.getAdapterPosition()) {
            case 1:
                rankingTv.setVisibility(View.GONE);
                rankingIv.setVisibility(View.VISIBLE);
                rankingIv.setImageResource(R.mipmap.ranking_no1);
                break;
            case 2:
                rankingTv.setVisibility(View.GONE);
                rankingIv.setVisibility(View.VISIBLE);
                rankingIv.setImageResource(R.mipmap.ranking_no2);
                break;
            case 3:
                rankingTv.setVisibility(View.GONE);
                rankingIv.setVisibility(View.VISIBLE);
                rankingIv.setImageResource(R.mipmap.ranking_no3);
                break;
            default:
                if (holder.getAdapterPosition() < 10) {
                    rankingTv.setText("0" + (holder.getAdapterPosition()));
                } else {
                    rankingTv.setText(String.valueOf(holder.getAdapterPosition()));
                }
                rankingTv.setVisibility(View.VISIBLE);
                rankingIv.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(item.getUserName())) {
            holder.setText(R.id.statistics_item_name_tv, item.getAccountName());
        } else {
            holder.setText(R.id.statistics_item_name_tv, item.getAccountName() + "(" + item.getUserName() + ")");
        }

        holder.setText(R.id.statistics_item_time_tv, String.format("最近登录：%s", item.getLastLoginTime()));
        holder.setText(R.id.statistics_item_content_tv, mContext.getString(R.string.statistics_home,
                item.getVisitNum(), item.getShareNum(), item.getNewUserNum(), item.getOrderNum()));
        ImageView imageView = holder.getView(R.id.statistics_item_head_iv);

        LoadImageUtil.loadRoundImage(imageView, item.getHeadImg(), 4);
        holder.addOnClickListener(R.id.statistics_item_head_iv);
    }
}
