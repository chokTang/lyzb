package com.lyzb.jbx.adapter.school;

import android.content.Context;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.school.SchoolModel;

import java.util.List;

public class ArticleAdapter extends BaseRecyleAdapter<SchoolModel> {

    public ArticleAdapter(Context context, List<SchoolModel> list) {
        super(context, R.layout.recycle_school_item, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, SchoolModel item) {
        holder.setImageUrl(R.id.img_article_header, item.getFileUrl());
        holder.setText(R.id.tv_school_title, item.getTitle());
        holder.setText(R.id.tv_read_number, String.format("%d阅读", item.getArticleReadnum()));
        holder.setText(R.id.tv_zan_number, item.getArticleThumb() == 0 ? "赞" : String.valueOf(item.getArticleThumb()));

        TextView tv_zan_number = holder.cdFindViewById(R.id.tv_zan_number);
        tv_zan_number.setSelected(item.isZan());

        holder.addItemClickListener(R.id.tv_zan_number);
    }
}
