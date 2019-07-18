package com.lyzb.jbx.adapter.me.publish;

import android.content.Context;
import android.text.Html;
import android.view.View;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.date.DateStyle;
import com.like.utilslib.date.DateUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.me.PubReplyCommentModel;

import java.util.List;

/****
 * 我的-我的发表-回复-list
 *
 */
public class PubReplyItemAdapter extends BaseRecyleAdapter<PubReplyCommentModel> {

    public PubReplyItemAdapter(Context context, List<PubReplyCommentModel> list) {
        super(context, R.layout.item_un_me_pub_reply_list, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, PubReplyCommentModel item) {
        holder.setText(R.id.tv_un_me_pub_reply_list_text, Html.fromHtml(String.format("<font color='#247cf0'>回复：</font><font color='#333333'>%s</font>  {iconclose}"
                , item.getContent())));
        holder.setText(R.id.tv_un_me_pub_reply_list_time, DateUtil.StringToString(item.getCreateDate(), DateStyle.YYYY_MM_DD));

        View item_line = holder.cdFindViewById(R.id.item_line);
        item_line.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        holder.addItemClickListener(R.id.tv_un_me_pub_reply_list_text);
    }

}
