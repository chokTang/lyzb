package com.lyzb.jbx.adapter.home.first;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.like.utilslib.screen.DensityUtil;
import com.like.utilslib.screen.ScreenUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.model.params.FileBody;

import java.util.List;

public class SendMessagePicAdapter extends BaseRecyleAdapter<FileBody> {
    public SendMessagePicAdapter(Context context, List<FileBody> list) {
        super(context, R.layout.item_send_message_pic, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, FileBody item) {

        holder.addItemClickListener(R.id.img_delete);
        Glide.with(_context).load(item.getFile())
                .thumbnail(0.2f).into((ImageView) holder.cdFindViewById(R.id.pic));
    }

    @Override
    public void onConvert(BaseViewHolder holder, FileBody item, int position) {
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DensityUtil.dpTopx(85), DensityUtil.dpTopx(85));
//        if (position + 1 < getItemCount()) {
//            params.setMargins(DensityUtil.dpTopx(15), 0, 0, 0);
//        } else {
//            params.setMargins(DensityUtil.dpTopx(15), 0, DensityUtil.dpTopx(15), 0);
//        }
//        holder.get_view().setLayoutParams(params);
        super.onConvert(holder, item, position);
    }
}
