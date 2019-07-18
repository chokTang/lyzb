package com.lyzb.jbx.adapter.me.customerManage;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.date.DateUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.customerManage.CustomerTrakRecordModel;
import com.lyzb.jbx.model.me.customerManage.CustomerVisitRecordModel;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/9 13:43
 */

public class CustomerVisitRecordAdapter extends BaseRecyleAdapter<CustomerVisitRecordModel.ListBean> {
    public CustomerVisitRecordAdapter(Context context, List<CustomerVisitRecordModel.ListBean> list) {
        super(context, R.layout.item_customer_track_record, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CustomerVisitRecordModel.ListBean item) {
        //当前item的年或月与上个item不一致时要展示相应的年月、日期上半部分的分割线。

        int year = DateUtil.getYear(item.getCreateTime());
        int monute = DateUtil.getMonth(item.getCreateTime());
        int lastYear = 0;
        int lastMonute = 0;
        if (holder.getAdapterPosition() > 0) {
            CustomerVisitRecordModel.ListBean lastItem = getList().get(holder.getAdapterPosition() - 1);
            lastYear = DateUtil.getYear(lastItem.getCreateTime());
            lastMonute = DateUtil.getMonth(lastItem.getCreateTime());

        }
        if (year != lastYear) {
            holder.setVisible(R.id.customer_track_record_year_tv, true);
        } else {
            holder.setVisible(R.id.customer_track_record_year_tv, false);
        }
        //时间上半部分的分割线
        View topLine = holder.cdFindViewById(R.id.customer_track_record_top_line_view);
        if (monute != lastMonute || year != lastYear) {
            topLine.setVisibility(View.INVISIBLE);
            holder.setVisible(R.id.customer_track_record_month_tv, true);
        } else {
            topLine.setVisibility(View.VISIBLE);
            holder.setVisible(R.id.customer_track_record_month_tv, false);
        }

        //这里又要跟下一条的年或月比较，判断是否展示内容左边、下面的分割线
        int nextYear = 0;
        int nextMonute = 0;
        if (holder.getAdapterPosition() < getList().size() - 1) {
            CustomerVisitRecordModel.ListBean lastItem = getList().get(holder.getAdapterPosition() + 1);
            nextYear = DateUtil.getYear(lastItem.getCreateTime());
            nextMonute = DateUtil.getMonth(lastItem.getCreateTime());
        }
        //内容底部的分割线
        LinearLayout bottomLine = holder.cdFindViewById(R.id.customer_track_record_bottom_line);
        if (monute != nextMonute || year != nextYear) {
            bottomLine.setVisibility(View.INVISIBLE);
            holder.setVisible(R.id.customer_track_record_content_line_ll, false);
        } else {
            bottomLine.setVisibility(View.VISIBLE);
            holder.setVisible(R.id.customer_track_record_content_line_ll, true);
        }
        //年
        holder.setText(R.id.customer_track_record_year_tv, String.format("%d年", year));
        //月
        holder.setText(R.id.customer_track_record_month_tv, String.format("%d月", monute));
        //时间
        holder.setText(R.id.customer_track_record_time_tv, item.getCreateTime());
        //来源
        holder.setText(R.id.customer_track_record_source_tv, String.format("来自：%s", item.getSourceZh()));
        holder.setVisible(R.id.customer_track_record_source_tv, true);
        //内容
        if (item.getType() == 1) {
            holder.setText(R.id.customer_track_record_content_tv, String.format("查看了我的名片，停留了%d秒", item.getStayTime()));
        } else if (item.getType() == 2) {
            String dynamicValue = "，";
            if (!TextUtils.isEmpty(item.getContent())) {
                if (item.getContent().length() > 10) {
                    dynamicValue = "：" + item.getContent().substring(0, 10) + "...，";
                } else {
                    dynamicValue = "：" + item.getContent() + "，";
                }
            }
            holder.setText(R.id.customer_track_record_content_tv, String.format("查看了我的动态%s停留了%d秒", dynamicValue, item.getStayTime()));
        } else if (item.getType() == 3) {
            holder.setText(R.id.customer_track_record_content_tv, String.format("查看了我的商品《%s》，停留了%d秒", item.getContent(), item.getStayTime()));
        }
    }
}
