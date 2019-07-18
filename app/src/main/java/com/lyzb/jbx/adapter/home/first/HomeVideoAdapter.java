package com.lyzb.jbx.adapter.home.first;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.BaseViewHolder;
import com.lyzb.jbx.R;
import com.lyzb.jbx.activity.CricleDetailActivity;
import com.lyzb.jbx.model.video.HomeVideoModel;
import com.lyzb.jbx.widget.link.AutoLinkMode;
import com.lyzb.jbx.widget.link.AutoLinkOnClickListener;
import com.lyzb.jbx.widget.link.AutoLinkTextView;
import com.szy.yishopcustomer.Util.BrowserUrlManager;

import java.util.List;

public class HomeVideoAdapter extends BaseRecyleAdapter<HomeVideoModel.ListBean> {
    public HomeVideoAdapter(Context context, List<HomeVideoModel.ListBean> list) {
        super(context, R.layout.item_home_video, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeVideoModel.ListBean item) {

        detailContent((AutoLinkTextView) holder.cdFindViewById(R.id.tv_content_value), item.getContent(), item.getId());

        if (item.getFileList() != null && item.getFileList().size() > 0) {
            holder.setImageUrl(R.id.image, item.getFileList().get(0).getFile());
        }
        holder.setRadiusImageUrl(R.id.ic_image, item.getHeadimg(), 4);
        holder.setText(R.id.tv_name, item.getGsName());
        
        holder.setText(R.id.tv_zan_number, item.getUpCount() + "");

        holder.addItemClickListener(R.id.tv_content_value);
        holder.addItemClickListener(R.id.ic_image);
        holder.addItemClickListener(R.id.tv_name);
        holder.addItemClickListener(R.id.tv_zan_number);

    }

    private void detailContent(final AutoLinkTextView textView, String content, final String dynamicId) {
        if (TextUtils.isEmpty(content)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setCustomRegex("#(.*?)#");
            textView.addAutoLinkMode(AutoLinkMode.MODE_CUSTOM, AutoLinkMode.MODE_URL);
            textView.setCustomModeColor(ContextCompat.getColor(_context, R.color.app_blue));
            textView.setUrlModeColor(ContextCompat.getColor(_context, R.color.app_blue));
            textView.setText(content);
        }
        textView.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
            @Override
            public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String matchedText) {
                ((View) textView.getParent()).setTag("I have click");
                switch (autoLinkMode) {
                    //进入h5页面
                    case MODE_URL:
                        new BrowserUrlManager(matchedText).parseUrl(_context, matchedText);
                        break;
                    //进入动态详情页面
                    case MODE_CUSTOM:
                        CricleDetailActivity.start(_context, "#" + matchedText + "#", dynamicId);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
