package com.lyzb.jbx.adapter.statistics;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.fragment.statistics.StatisticsHomeFragment;
import com.lyzb.jbx.model.statistics.AnalysisRankingModel;

import java.util.List;

/**
 * @author wyx
 * @role 各种排行
 * @time 2019 2019/4/17 19:39
 */

public class AnalysisRankingAdapter extends BaseRecyleAdapter<AnalysisRankingModel.DataListBean> {
    private int type;
    private String visitSort;

    public AnalysisRankingAdapter(Context context, int type, List<AnalysisRankingModel.DataListBean> list) {
        super(context, R.layout.item_analysis_ranking, list);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder holder, AnalysisRankingModel.DataListBean item) {

        //排名
        ImageView rankingIv = holder.cdFindViewById(R.id.analysis_ranking_iv);
        TextView rankingTv = holder.cdFindViewById(R.id.analysis_ranking_tv);
        //头像
        ImageView headIv = holder.cdFindViewById(R.id.analysis_ranking_head_iv);
        int padding = DensityUtil.dpTopx(3);
        //前三名要金银铜牌,头像还要有个背景
        switch (holder.getAdapterPosition()) {
            case 0:
                headIv.setBackground(ContextCompat.getDrawable(_context, R.drawable.ic_ranking_no1));
                headIv.setPadding(padding, padding, padding, padding);
                rankingTv.setVisibility(View.GONE);
                rankingIv.setVisibility(View.VISIBLE);
                rankingIv.setImageResource(R.mipmap.ranking_no1);
                break;
            case 1:
                headIv.setBackground(ContextCompat.getDrawable(_context, R.drawable.ic_ranking_no2));
                headIv.setPadding(padding, padding, padding, padding);
                rankingTv.setVisibility(View.GONE);
                rankingIv.setVisibility(View.VISIBLE);
                rankingIv.setImageResource(R.mipmap.ranking_no2);
                break;
            case 2:
                headIv.setBackground(ContextCompat.getDrawable(_context, R.drawable.ic_ranking_no3));
                headIv.setPadding(padding, padding, padding, padding);
                rankingTv.setVisibility(View.GONE);
                rankingIv.setVisibility(View.VISIBLE);
                rankingIv.setImageResource(R.mipmap.ranking_no3);
                break;
            default:
                headIv.setBackground(null);
                headIv.setPadding(0, 0, 0, 0);
                rankingTv.setVisibility(View.VISIBLE);
                rankingIv.setVisibility(View.GONE);
                //后面的排名，用getAdapterPosition+2展示
                int mPosition = holder.getAdapterPosition() + 1;
                if (mPosition < 10) {
                    rankingTv.setText("0" + mPosition);
                } else {
                    rankingTv.setText(String.valueOf(mPosition));
                }
        }
        holder.setRadiusImageUrl(R.id.analysis_ranking_head_iv, item.getHeadImg(), 4);
        holder.addItemClickListener(R.id.analysis_ranking_head_iv);
        //数量
        TextView numberTv = holder.cdFindViewById(R.id.analysis_ranking_number_tv);
        //先要判断是哪种类型(访问、分享、新用户、交易)
        switch (type) {
            //访问
            case StatisticsHomeFragment.TYPE_VISIT:
                //分享
            case StatisticsHomeFragment.TYPE_SHAER:
                //访问、分享还要判断是 全部、名片、商品、动态
                switch (visitSort) {
                    case "all":
                        numberTv.setText(String.format("%d次", item.getTotalNum()));
                        break;
                    case "ext":
                        numberTv.setText(String.format("%d次", item.getExtNum()));
                        break;
                    case "goods":
                        numberTv.setText(String.format("%d次", item.getGoodsNum()));
                        break;
                    case "topic":
                        numberTv.setText(String.format("%d次", item.getTopicNum()));
                        break;
                    default:
                }

                break;
            //新用户
            case StatisticsHomeFragment.TYPE_NEWUSER:
                numberTv.setText(String.format("%d人", item.getNewUserNum()));
                break;
            //交易
            case StatisticsHomeFragment.TYPE_TRANSACTION:
                numberTv.setText(item.getOrderAmount() + "元/" + item.getOrderCount() + "笔");
            default:
        }
        //是否为vip
        ImageView isvip = holder.cdFindViewById(R.id.analysis_ranking_isvip_iv);
        isvip.setVisibility(View.INVISIBLE);
        if (TextUtils.isEmpty(item.getUserName())) {
            holder.setText(R.id.analysis_ranking_name_tv, item.getAccountName());
        } else {
            holder.setText(R.id.analysis_ranking_name_tv, item.getAccountName() + "(" + item.getUserName() + ")");
        }
    }

    public String getVisitSort() {
        return visitSort;
    }

    public void setVisitSort(String visitSort) {
        this.visitSort = visitSort;
    }
}
