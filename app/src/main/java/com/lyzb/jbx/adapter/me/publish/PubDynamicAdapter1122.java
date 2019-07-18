package com.lyzb.jbx.adapter.me.publish;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.longshaolib.adapter.rvhelper.DividerItemDecoration;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.common.SingleImageAdapter;
import com.lyzb.jbx.model.me.PubDynamicModel;

import java.util.List;

/****
 * 我的-我的发表-动态 适配器
 *
 */
public class PubDynamicAdapter1122 extends BaseRecyleAdapter<PubDynamicModel> {

    private Context mContext;

    public PubDynamicAdapter1122(Context context, List<PubDynamicModel> list) {
        super(context, -1, list);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, PubDynamicModel item) {

        ImageHolder imageHolder = (ImageHolder) holder;
//        imageHolder.mImgAdapter.update(item.getImgLists());

        holder.setText(R.id.tv_item_dynamic_year, item.getYear() + "年");
        holder.setText(R.id.tv_item_dynamic_month, item.getMonth() + "月");
        holder.setText(R.id.tv_item_dynamic_day, item.getDay());

        holder.setText(R.id.tv_item_dynamic_text, item.getText());

        holder.setText(R.id.tv_item_dynamic_browse, item.getBrowse());
        holder.setText(R.id.tv_item_dynamic_like, item.getLike());
        holder.setText(R.id.tv_item_dynamic_msg, item.getMsg());
        holder.setText(R.id.tv_item_dynamic_share, item.getShare());

        final TextView text = holder.cdFindViewById(R.id.tv_item_dynamic_text);
        final TextView allText = holder.cdFindViewById(R.id.tv_item_dynamic_all);
        text.post(new Runnable() {
            @Override
            public void run() {
                if (text.getLineCount() > 3) {
                    text.setMaxLines(3);
                    text.setEllipsize(TextUtils.TruncateAt.valueOf("END"));

                    allText.setVisibility(View.VISIBLE);
                } else {
                    allText.setVisibility(View.GONE);
                }
            }
        });

        holder.addItemClickListener(R.id.img_item_dynamic_menu);
    }

    @Override
    protected BaseViewHolder onChildCreateViewHolder(int viewType, ViewGroup parent) {
        return new ImageHolder(_context, getItemView(R.layout.item_un_me_pub_dynamic, parent));
    }

    class ImageHolder extends BaseViewHolder {


        RecyclerView recy_item_dynamic;
        SingleImageAdapter mImgAdapter;

        public ImageHolder(Context context, View itemView) {
            super(context, itemView);
            recy_item_dynamic = itemView.findViewById(R.id.recy_item_dynamic_img);
            int width= (ScreenUtil.getScreenWidth()-DensityUtil.dpTopx(2*4+62))/3;
            mImgAdapter = new SingleImageAdapter(mContext, width, null);
            mImgAdapter.setGridLayoutManager(recy_item_dynamic, 3);
            recy_item_dynamic.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, R.drawable.listdivider_white_4));
            recy_item_dynamic.setAdapter(mImgAdapter);
        }
    }
}
