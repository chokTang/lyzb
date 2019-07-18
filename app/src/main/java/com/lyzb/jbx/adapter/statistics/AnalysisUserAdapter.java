package com.lyzb.jbx.adapter.statistics;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.statistics.AnalysisNewUserModel;
import com.szy.common.Util.ImageLoader;

import java.util.List;

/**
 * @author wyx
 * @role 数据分析-新用户
 * @time 2019 2019/4/17 9:57
 */

public class AnalysisUserAdapter extends BaseRecyleAdapter<AnalysisNewUserModel.DataListBean> {
    private boolean isCompany;

    /**
     * @param context
     * @param list
     * @param isCompany 个人用户与企业引流区别展示
     */
    public AnalysisUserAdapter(Context context, List<AnalysisNewUserModel.DataListBean> list, boolean isCompany) {
        super(context, R.layout.item_analysis_user, list);
        this.isCompany = isCompany;
    }

    @Override
    protected void convert(BaseViewHolder holder, AnalysisNewUserModel.DataListBean item) {
        holder.setText(R.id.analysis_user_name_tv, item.getUserName());
        holder.setText(R.id.analysis_user_time_tv, String.format("注册时间：%s", item.getRegTime()));
        holder.setRadiusImageUrl(R.id.analysis_user_head_iv, item.getHeadimg(), 4);
        //分享者的企业及用户名
        String name = "";
        if (TextUtils.isEmpty(item.getUserGsName())) {
            name = item.getAccountName();
        } else {
            name = String.format("%1$s(%2$s)", item.getAccountName(), item.getUserGsName());
        }
        String shareName = "";
        if (!TextUtils.isEmpty(item.getShareName())) {
            shareName = item.getShareName();
        }
        //企业与个人引流展示不同
        if (isCompany) {
            holder.setText(R.id.analysis_user_content_tv, "通过" + name + shareName + "分享注册");
        } else {
            holder.setText(R.id.analysis_user_content_tv, "通过" + shareName + "分享注册");
        }

        ImageView isVip = holder.cdFindViewById(R.id.analysis_user_isvip_iv);
        if (item.getUserActionVos() == null || item.getUserActionVos().size() < 1) {
            isVip.setVisibility(View.INVISIBLE);
        } else {
            isVip.setVisibility(View.VISIBLE);
        }

        holder.addItemClickListener(R.id.analysis_user_head_iv);
    }
}
