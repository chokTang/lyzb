package com.lyzb.jbx.adapter.me.publish;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.lyzb.jbx.R;
import com.lyzb.jbx.inter.IRecycleAnyClickListener;
import com.lyzb.jbx.model.me.PubReplyModel;
import com.szy.yishopcustomer.Util.DateUtil;

import java.util.List;

/****
 * 我的-我的发表-回复 适配器
 *
 */
public class PubReplyAdapter extends BaseRecyleAdapter<PubReplyModel> {

    public PubReplyAdapter(Context context, List<PubReplyModel> list) {
        super(context, R.layout.item_un_me_pub_reply, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, PubReplyModel item) {
        ListHodler listHodler = (ListHodler) holder;
        listHodler.setRadiusImageUrl(R.id.img_un_pub_reply_head, item.getHeadimg(), 4);
        listHodler.tv_un_pub_reply_name.setText(item.getGsName());
        if (item.getUserVipAction().size() == 0) {
            listHodler.tv_un_pub_reply_name.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            listHodler.tv_un_pub_reply_name.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    ContextCompat.getDrawable(_context, R.drawable.union_vip), null);
        }
        listHodler.setText(R.id.tv_un_pub_reply_commpany, item.getShopName());
        listHodler.setText(R.id.tv_un_pub_reply_time, DateUtil.format(DateUtil.StringToDate(item.getCreateDate()).getTime()));

        listHodler.setText(R.id.tv_un_pub_reply_text, Html.fromHtml(String.format("<font color='#247cf0'>原动态：</font>%s", item.getContent())));
        listHodler.mItemAdapter.update(item.getGsTopicCommentList());

        listHodler.addItemClickListener(R.id.tv_un_pub_reply_text);
        listHodler.addItemClickListener(R.id.img_un_pub_reply_head);
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        return new ListHodler(_context, getItemView(R.layout.item_un_me_pub_reply, parent));
    }

    class ListHodler extends BaseViewHolder {
        RecyclerView recy_un_me_pub_reply;
        TextView tv_un_pub_reply_name;
        PubReplyItemAdapter mItemAdapter;

        public ListHodler(Context context, View itemView) {
            super(context, itemView);
            tv_un_pub_reply_name = itemView.findViewById(R.id.tv_un_pub_reply_name);

            recy_un_me_pub_reply = itemView.findViewById(R.id.recy_un_me_pub_reply);
            recy_un_me_pub_reply.setNestedScrollingEnabled(false);
            mItemAdapter = new PubReplyItemAdapter(_context, null);
            mItemAdapter.setLayoutManager(recy_un_me_pub_reply);
            recy_un_me_pub_reply.setAdapter(mItemAdapter);

            recy_un_me_pub_reply.addOnItemTouchListener(new OnRecycleItemClickListener() {
                @Override
                public void onItemChildClick(BaseRecyleAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        //删除某个数据
                        case R.id.tv_un_me_pub_reply_list_text:
                            if (listener != null) {
                                listener.onItemClick(view, position, mItemAdapter.getPositionModel(position));
                            }
                            break;
                    }
                }
            });
        }
    }

    private IRecycleAnyClickListener listener;

    public void setListener(IRecycleAnyClickListener listener) {
        this.listener = listener;
    }
}
