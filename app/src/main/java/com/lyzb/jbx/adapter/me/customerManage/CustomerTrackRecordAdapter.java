package com.lyzb.jbx.adapter.me.customerManage;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.date.DateUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.customerManage.CustomerTrakRecordModel;

import java.util.List;

/**
 * @author wyx
 * @role
 * @time 2019 2019/5/9 13:43
 */

public class CustomerTrackRecordAdapter extends BaseRecyleAdapter<CustomerTrakRecordModel.ListBean> {
    public CustomerTrackRecordAdapter(Context context, List<CustomerTrakRecordModel.ListBean> list) {
        super(context, R.layout.item_customer_track_record, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, CustomerTrakRecordModel.ListBean item) {
        //当前item的年或月与上个item不一致时要展示相应的年月、日期上半部分的分割线。
        int year = DateUtil.getYear(item.getCreatorTime());
        int monute = DateUtil.getMonth(item.getCreatorTime());
        int lastYear = 0;
        int lastMonute = 0;
        if (holder.getAdapterPosition() > 0) {
            CustomerTrakRecordModel.ListBean lastItem = getList().get(holder.getAdapterPosition() - 1);
            lastYear = DateUtil.getYear(lastItem.getCreatorTime());
            lastMonute = DateUtil.getMonth(lastItem.getCreatorTime());

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
            CustomerTrakRecordModel.ListBean lastItem = getList().get(holder.getAdapterPosition() + 1);
            nextYear = DateUtil.getYear(lastItem.getCreatorTime());
            nextMonute = DateUtil.getMonth(lastItem.getCreatorTime());
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

        //时间
        holder.setText(R.id.customer_track_record_time_tv, item.getCreatorTime());
        //内容
        holder.setText(R.id.customer_track_record_content_tv, item.getContent());
    }
}
