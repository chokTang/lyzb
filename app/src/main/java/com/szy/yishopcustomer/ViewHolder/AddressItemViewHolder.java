package com.szy.yishopcustomer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 宗仁 on 16/7/23.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class AddressItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_address_list_bottomWrapperRelativeLayout)
    public LinearLayout bottomWrapperRelativeLayout;
    @BindView(R.id.fragment_address_list_default)
    public TextView defaultTip;
    @BindView(R.id.fragment_address_list_nameTextView)
    public TextView nameTextView;
    @BindView(R.id.fragment_address_list_phoneTextView)
    public TextView phoneTextView;
    @BindView(R.id.fragment_address_list_addressTextView)
    public TextView addressTextView;
    @BindView(R.id.image_checkbox)
    public ImageView checkboxImageView;
    @BindView(R.id.fragment_address_list_checkboxTextView)
    public TextView checkboxTextView;
    @BindView(R.id.fragment_address_list_editTextView)
    public TextView editTextView;
    @BindView(R.id.fragment_address_list_deleteTextView)
    public TextView deleteTextView;

    public AddressItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
