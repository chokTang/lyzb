package com.szy.yishopcustomer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.SameCity.Address.AddressList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 外卖-收货地址列表 adapter
 * @time
 */

public class AdsListAdapter extends RecyclerView.Adapter {


    public Context mContext;
    public List<AddressList> mAddressLists;

    public AdsListAdapter(Context context) {
        this.mContext = context;
    }


    public RecyclerView.ViewHolder createAdsHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_list, parent, false);
        AdsViewHolder holder = new AdsViewHolder(view);
        return holder;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createAdsHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindAdsHolder((AdsViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return mAddressLists == null ? 0 : mAddressLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class AdsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.check_ads)
        CheckBox mCheckBox;

        @BindView(R.id.linea_ads_content)
        LinearLayout mLayout_Content;
        @BindView(R.id.tv_ads_out)
        TextView mTextView_Out;
        @BindView(R.id.tv_ads_content)
        TextView mTextView_Content;
        @BindView(R.id.tv_ads_phone)
        TextView mTextView_Phone;
        @BindView(R.id.tv_ads_edt)
        TextView mTextView_Edt;

        public AdsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void bindAdsHolder(AdsViewHolder holder, final int position) {

        holder.mTextView_Content.setText(mAddressLists.get(position).addressDetail);
        holder.mTextView_Phone.setText(mAddressLists.get(position).consignee + "\t" + mAddressLists.get(position).mobile);

        if (mAddressLists.get(position).distance == 0) {
            holder.mTextView_Out.setText("超出配送范围");
            holder.mTextView_Out.setVisibility(View.VISIBLE);
            holder.mTextView_Edt.setVisibility(View.GONE);

            holder.mTextView_Content.setTextColor(mContext.getResources().getColor(R.color.ads_out_bg));
            holder.mTextView_Phone.setTextColor(mContext.getResources().getColor(R.color.ads_out_bg));
        } else {
            holder.mTextView_Edt.setText("编辑");

            holder.mTextView_Edt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mOnItemClick != null) {
                        mOnItemClick.onAdsEdt(mAddressLists.get(position));
                    }
                }
            });
        }


        holder.mLayout_Content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnItemClick != null) {

                    if (mAddressLists.get(position).distance == 0) {
                        mOnItemClick.onAdsOutRange(mAddressLists.get(position));
                    } else {
                        mOnItemClick.onAdsNormal(mAddressLists.get(position));
                    }
                }
            }
        });

//        holder.mCheckBox.setChecked(mAddressLists.get(position).isSelected());

        //        //checkbox 单选
//        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                for (AddressList list : mAddressLists) {
//                    list.setSelected(false);
//                }
//
//                mAddressLists.get(position).setSelected(true);
//                notifyDataSetChanged();
//            }
//        });
    }

    private onItemClick mOnItemClick;

    public interface onItemClick {
        void onAdsNormal(AddressList addressList);

        void onAdsOutRange(AddressList addressList);

        void onAdsEdt(AddressList addressList);
    }

    public void setOnItemClick(onItemClick click) {
        this.mOnItemClick = click;
    }
}
