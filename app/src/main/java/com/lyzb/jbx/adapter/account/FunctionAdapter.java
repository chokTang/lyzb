package com.lyzb.jbx.adapter.account;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.account.FunctionModel;

import java.util.List;

public class FunctionAdapter extends BaseRecyleAdapter<FunctionModel> {

    private ViewGroup.LayoutParams mParams;

    public FunctionAdapter(Context context, List<FunctionModel> list) {
        super(context, R.layout.recycle_recommend_function_item, list);
        int spacing = DensityUtil.dpTopx(20);
        int width = ScreenUtil.getScreenWidth() - spacing;
        mParams = new ViewGroup.LayoutParams(width / 5, DensityUtil.dpTopx(110));
    }

    @Override
    protected void convert(BaseViewHolder holder, FunctionModel item) {
        holder.setText(R.id.tv_name, item.getName());
        holder.setImageUrl(R.id.img_header, item.getImg());
        LinearLayout layout_function = holder.cdFindViewById(R.id.layout_function);
        layout_function.setLayoutParams(mParams);
    }
}
