package com.lyzb.jbx.adapter.me;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.cenum.AccessType;
import com.lyzb.jbx.model.cenum.DataType;
import com.lyzb.jbx.model.me.AcsRecomdModel;
import com.szy.yishopcustomer.Util.DateUtil;

import java.util.List;

/****
 * 访问记录 列表
 *
 */
public class AcsListAdapter extends BaseRecyleAdapter<AcsRecomdModel> {

    private String mAccessType = AccessType.ACCESS.name();
    private String mUserName = "";
    private int mDataType = DataType.ALL.getValue();

    public AcsListAdapter(Context context, List<AcsRecomdModel> list) {
        super(context, R.layout.item_un_me_acs_list, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, AcsRecomdModel item) {
        holder.setText(R.id.item_acs_time, item.getCreateTime());
        if (item.getType() == 1) {
            holder.setText(R.id.item_acs_text, String.format("查看了%s的名片，停留了%d秒", TextUtils.isEmpty(mUserName) ? "我" : mUserName, item.getStayTime()));
        } else if (item.getType() == 2) {
            String dynamicValue = "，";
            if (!TextUtils.isEmpty(item.getContent())) {
                if (item.getContent().length() > 10) {
                    dynamicValue = "：" + item.getContent().substring(0, 10) + "...，";
                } else {
                    dynamicValue = "：" + item.getContent() + "，";
                }
            }
            if (TextUtils.isEmpty(mUserName)) {
                holder.setText(R.id.item_acs_text, String.format("查看了我的动态%s停留了%d秒", dynamicValue, item.getStayTime()));
            } else {
                holder.setText(R.id.item_acs_text, String.format("查看了%s的动态%s停留了%d秒", mUserName, dynamicValue, item.getStayTime()));
            }
        } else if (item.getType() == 3) {
            if (TextUtils.isEmpty(mUserName)) {
                holder.setText(R.id.item_acs_text, String.format("查看了我的商品《%s》，停留了%d秒", item.getContent(), item.getStayTime()));
            } else {
                holder.setText(R.id.item_acs_text, String.format("查看了%s的商品《%s》，停留了%d秒", mUserName, item.getContent(), item.getStayTime()));
            }
        } else {//表示热文
            holder.setText(R.id.item_acs_text, item.getTitle());
        }

        //处理月份是否显示
        int position = holder.getAdapterPosition();
        final TextView tv_mouth = holder.cdFindViewById(R.id.tv_mouth);
        final TextView tv_year = holder.cdFindViewById(R.id.tv_year);

        if (position <= 0) {
            tv_mouth.setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_mouth, String.format("%d月", DateUtil.getMonth(item.getCreateTime()) + 1));

            tv_year.setVisibility(View.VISIBLE);
            tv_year.setText(String.format("%d年", DateUtil.getYear(item.getCreateTime())));
        } else {
            int preMonth = DateUtil.getMonth(getPositionModel(position - 1).getCreateTime()) + 1;
            int mouth = DateUtil.getMonth(getPositionModel(position).getCreateTime()) + 1;
            if (preMonth == mouth) {
                tv_mouth.setVisibility(View.INVISIBLE);
            } else {
                holder.setText(R.id.tv_mouth, String.format("%d月", mouth));
                tv_mouth.setVisibility(View.VISIBLE);
            }

            int preYear = DateUtil.getYear(getPositionModel(position - 1).getCreateTime());
            int year = DateUtil.getYear(getPositionModel(position).getCreateTime());
            if (preYear == year) {
                tv_year.setVisibility(View.GONE);
            } else {
                tv_year.setVisibility(View.VISIBLE);
                tv_year.setText(String.format("%d年", year));
            }
        }

        //处理来自于是否显示
        if (!mAccessType.equals(AccessType.SHARE.name())) {
            holder.setVisible(R.id.tv_from_type, true);
            if (mDataType == DataType.ACRTICE.getValue()) {
                holder.setText(R.id.tv_from_type, "来自：微信");
            } else {
                holder.setText(R.id.tv_from_type, String.format("来自：%s", item.getSourceZh()));
            }
        } else {
            holder.setVisible(R.id.tv_from_type, false);
        }
    }

    public void setmAccessType(String mAccessType) {
        this.mAccessType = mAccessType;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public void setmDataType(int mDataType) {
        this.mDataType = mDataType;
    }
}
