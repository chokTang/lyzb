package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyContentModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyFreightModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyFreightPriceModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyImageModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyNumberModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyReasonModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyRefundModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyServiceModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.BackApplyWayModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.TakePackModel;
import com.szy.yishopcustomer.ResponseModel.BackApply.TakeTypeModel;
import com.szy.yishopcustomer.ResponseModel.DividerModel;
import com.szy.yishopcustomer.Util.EditInputFilter;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Back.BackApplyContentViewHolder;
import com.szy.yishopcustomer.ViewHolder.Back.BackApplyFreightPriceViewHolder;
import com.szy.yishopcustomer.ViewHolder.Back.BackApplyFreightViewHolder;
import com.szy.yishopcustomer.ViewHolder.Back.BackApplyImageViewHolder;
import com.szy.yishopcustomer.ViewHolder.Back.BackApplyNumberViewHolder;
import com.szy.yishopcustomer.ViewHolder.Back.BackApplyReasonViewHolder;
import com.szy.yishopcustomer.ViewHolder.Back.BackApplyRefundViewHolder;
import com.szy.yishopcustomer.ViewHolder.Back.BackApplyServiceViewHolder;
import com.szy.yishopcustomer.ViewHolder.Back.BackApplyTypeViewHolder;
import com.szy.yishopcustomer.ViewHolder.Back.BackApplyWayViewHolder;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李威 on 17/3/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class BackApplyAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_DIVIDER = 0;
    private final int VIEW_TYPE_APPLY_SERVICE = 1;
    private final int VIEW_TYPE_APPLY_NUMBER = 2;
    private final int VIEW_TYPE_APPLY_REASON = 3;
    private final int VIEW_TYPE_APPLY_REFUND = 4;
    private final int VIEW_TYPE_APPLY_WAY = 5;
    private final int VIEW_TYPE_APPLY_CONTENT = 6;
    public final int VIEW_TYPE_APPLY_IMAGE = 7;
    private final int VIEW_TYPE_APPLY_FIEGHT = 8;//运费方式选择
    private final int VIEW_TYPE_APPLY_FIEGHT_PRICE = 9;//运费价格

    public final int VIEW_TYPE_APPLY_TAKE_TYPE = 10;//选择退件方式
    public final int VIEW_TYPE_APPLY_TAKE_PACK = 11;//选择包装类型

    public String addressDetail;
    public String addressRegion;

    public List<Object> data;
    public View.OnClickListener onClickListener;
    public TextView.OnEditorActionListener onEditorActionListener;
    public View.OnFocusChangeListener onFocusChangeListener;
    public TextWatcherAdapter.TextWatcherListener textWatcherListener;

    public BackApplyAdapter() {
        this.data = new ArrayList<>();
    }

    public BackApplyAdapter(List<Object> data) {
        this.data = data;
    }

    public RecyclerView.ViewHolder createDividerViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_divider_one,
                parent, false);
        return new DividerViewHolder(view);
    }

    public RecyclerView.ViewHolder createApplyServiceViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_back_apply_service, parent, false);
        return new BackApplyServiceViewHolder(view);
    }

    public RecyclerView.ViewHolder createApplyNumberViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_back_apply_number, parent, false);
        return new BackApplyNumberViewHolder(view);
    }

    public RecyclerView.ViewHolder createApplyReasonViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_back_apply_reason, parent, false);
        return new BackApplyReasonViewHolder(view);
    }

    public RecyclerView.ViewHolder createApplyRefundViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_back_apply_refund, parent, false);
        return new BackApplyRefundViewHolder(view);
    }

    public RecyclerView.ViewHolder createApplyWayViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_back_apply_way, parent, false);
        return new BackApplyWayViewHolder(view);
    }

    public RecyclerView.ViewHolder createApplyContentViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_back_apply_content, parent, false);
        return new BackApplyContentViewHolder(view);
    }

    //运费方式申请
    public RecyclerView.ViewHolder createApplyFreightViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_back_apply_freight, parent, false);
        return new BackApplyFreightViewHolder(view);
    }

    //运费价格
    public RecyclerView.ViewHolder createApplyFreightPriceViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_back_apply_yufen, parent, false);
        return new BackApplyFreightPriceViewHolder(view);
    }

    //选择退款方式与包装
    public RecyclerView.ViewHolder createApplyTypeViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_back_apply_type, parent, false);
        return new BackApplyTypeViewHolder(view);
    }

    private RecyclerView.ViewHolder createApplyImageViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_back_apply_image, parent, false);
        return new BackApplyImageViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_DIVIDER:
                return createDividerViewHolder(parent);
            case VIEW_TYPE_APPLY_SERVICE:
                return createApplyServiceViewHolder(parent);
            case VIEW_TYPE_APPLY_NUMBER:
                return createApplyNumberViewHolder(parent);
            case VIEW_TYPE_APPLY_REASON:
                return createApplyReasonViewHolder(parent);
            case VIEW_TYPE_APPLY_REFUND:
                return createApplyRefundViewHolder(parent);
            case VIEW_TYPE_APPLY_WAY:
                return createApplyWayViewHolder(parent);
            case VIEW_TYPE_APPLY_CONTENT:
                return createApplyContentViewHolder(parent);
            case VIEW_TYPE_APPLY_IMAGE:
                return createApplyImageViewHolder(parent);
            //运费方式
            case VIEW_TYPE_APPLY_FIEGHT:
                return createApplyFreightViewHolder(parent);
            case VIEW_TYPE_APPLY_FIEGHT_PRICE:
                return createApplyFreightPriceViewHolder(parent);
            case VIEW_TYPE_APPLY_TAKE_TYPE:
            case VIEW_TYPE_APPLY_TAKE_PACK:
                return createApplyTypeViewHolder(parent);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_DIVIDER:
                break;
            case VIEW_TYPE_APPLY_SERVICE:
                bindApplyServiceViewHolder((BackApplyServiceViewHolder) holder, position);
                break;
            case VIEW_TYPE_APPLY_NUMBER:
                bindApplyNumberViewHolder((BackApplyNumberViewHolder) holder, position);
                break;
            case VIEW_TYPE_APPLY_REASON:
                bindApplyReasonViewHolder((BackApplyReasonViewHolder) holder, position);
                break;
            case VIEW_TYPE_APPLY_REFUND:
                bindApplyRefundViewHolder((BackApplyRefundViewHolder) holder, position);
                break;
            case VIEW_TYPE_APPLY_WAY:
                bindApplyWayViewHolder((BackApplyWayViewHolder) holder, position);
                break;
            case VIEW_TYPE_APPLY_CONTENT:
                bindApplyContentViewHolder((BackApplyContentViewHolder) holder, position);
                break;
            case VIEW_TYPE_APPLY_IMAGE:
                bindApplyImageViewHolder((BackApplyImageViewHolder) holder, position);
                break;
            //绑定运费选择方式
            case VIEW_TYPE_APPLY_FIEGHT:
                BackApplyFreightViewHolder freightViewHolder = (BackApplyFreightViewHolder) holder;
                BackApplyFreightModel freightModel = (BackApplyFreightModel) data.get(position);
                freightViewHolder.back_apply_freight_content.setText(freightModel.content);

                Utils.setViewTypeForTag(freightViewHolder.freight_rl, ViewType.VIEW_TYPE_APPLY_FREIGHT);
                freightViewHolder.freight_rl.setOnClickListener(onClickListener);
                break;
            //运费价格
            case VIEW_TYPE_APPLY_FIEGHT_PRICE:
                BackApplyFreightPriceModel priceModel = (BackApplyFreightPriceModel) data.get(position);
                BackApplyFreightPriceViewHolder priceViewHolder = (BackApplyFreightPriceViewHolder) holder;

                priceViewHolder.back_apply_yunfeicontent.setFilters(new InputFilter[]{new EditInputFilter(priceModel.maxPrice)});
                priceViewHolder.back_apply_yunfeicontent.setHint(String.format("最大金额%.2f", priceModel.maxPrice));
                priceViewHolder.back_apply_yunfeicontent.setText(priceModel.content);

                priceViewHolder.back_apply_yunfeicontent.setOnEditorActionListener(onEditorActionListener);
                priceViewHolder.back_apply_yunfeicontent.setOnFocusChangeListener(onFocusChangeListener);
                priceViewHolder.back_apply_yunfeicontent.setTextWatcherListener(textWatcherListener);

                break;

            case VIEW_TYPE_APPLY_TAKE_TYPE:
                bindApplyTypeViewHolder((BackApplyTypeViewHolder) holder, position);
                break;
            case VIEW_TYPE_APPLY_TAKE_PACK:
                bindApplyPackViewHolder((BackApplyTypeViewHolder) holder, position);
                break;

        }
    }

    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof DividerModel) {
            return VIEW_TYPE_DIVIDER;
        } else if (object instanceof BackApplyServiceModel) {
            return VIEW_TYPE_APPLY_SERVICE;
        } else if (object instanceof BackApplyNumberModel) {
            return VIEW_TYPE_APPLY_NUMBER;
        } else if (object instanceof BackApplyReasonModel) {
            return VIEW_TYPE_APPLY_REASON;
        } else if (object instanceof BackApplyRefundModel) {
            return VIEW_TYPE_APPLY_REFUND;
        } else if (object instanceof BackApplyWayModel) {
            return VIEW_TYPE_APPLY_WAY;
        } else if (object instanceof BackApplyContentModel) {
            return VIEW_TYPE_APPLY_CONTENT;
        } else if (object instanceof BackApplyImageModel) {
            return VIEW_TYPE_APPLY_IMAGE;
        } else if (object instanceof BackApplyFreightModel) {
            return VIEW_TYPE_APPLY_FIEGHT;
        } else if (object instanceof BackApplyFreightPriceModel) {
            return VIEW_TYPE_APPLY_FIEGHT_PRICE;
        } else if (object instanceof TakeTypeModel) {
            return VIEW_TYPE_APPLY_TAKE_TYPE;
        } else if (object instanceof TakePackModel) {
            return VIEW_TYPE_APPLY_TAKE_PACK;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindApplyServiceViewHolder(BackApplyServiceViewHolder holder, int position) {
        BackApplyServiceModel item = (BackApplyServiceModel) data.get(position);
        holder.mContent.setText(item.content);

        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_BACK_SERVICE);
        Utils.setPositionForTag(holder.itemView, position);
        holder.itemView.setOnClickListener(onClickListener);
    }

    private void bindApplyNumberViewHolder(BackApplyNumberViewHolder holder, int position) {
        BackApplyNumberModel item = (BackApplyNumberModel) data.get(position);
        holder.mContent.setText(item.content);

        /*Utils.setViewTypeForTag(holder.mContent, item.viewType);
        holder.mContent.setOnEditorActionListener(onEditorActionListener);
        holder.mContent.setOnFocusChangeListener(onFocusChangeListener);
        holder.mContent.setTextWatcherListener(textWatcherListener);*/
    }

    private void bindApplyReasonViewHolder(BackApplyReasonViewHolder holder, int position) {
        BackApplyReasonModel item = (BackApplyReasonModel) data.get(position);
        holder.mContent.setText(item.content);

        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_BACK_REASON);
        Utils.setPositionForTag(holder.itemView, position);
        holder.itemView.setOnClickListener(onClickListener);
    }

    /**
     * 退款金额
     **/
    private void bindApplyRefundViewHolder(BackApplyRefundViewHolder holder, int position) {
        BackApplyRefundModel item = (BackApplyRefundModel) data.get(position);

        Utils.amountLimit(holder.mContent, item.order_amount);
        holder.mContent.setText(item.content);

        Utils.setViewTypeForTag(holder.mContent, item.viewType);
        holder.mContent.setOnEditorActionListener(onEditorActionListener);
        holder.mContent.setOnFocusChangeListener(onFocusChangeListener);
        holder.mContent.setTextWatcherListener(textWatcherListener);
    }

    private void bindApplyWayViewHolder(BackApplyWayViewHolder holder, int position) {
        BackApplyWayModel item = (BackApplyWayModel) data.get(position);

        holder.mContent.setText(item.content);

        Utils.setViewTypeForTag(holder.itemView, ViewType.VIEW_TYPE_BACK_WAY);
        Utils.setPositionForTag(holder.itemView, position);
        holder.itemView.setOnClickListener(onClickListener);
    }

    private void bindApplyContentViewHolder(BackApplyContentViewHolder holder, int position) {
        BackApplyContentModel item = (BackApplyContentModel) data.get(position);
        holder.mContent.setText(item.content);

        Utils.setViewTypeForTag(holder.mContent, item.viewType);
        holder.mContent.setOnEditorActionListener(onEditorActionListener);
        holder.mContent.setOnFocusChangeListener(onFocusChangeListener);
        holder.mContent.setTextWatcherListener(textWatcherListener);
    }


    public void refresh(String addressRegion) {
        this.addressRegion = addressRegion;
    }

    private void bindApplyTypeViewHolder(final BackApplyTypeViewHolder holder, int position) {
        TakeTypeModel item = (TakeTypeModel) data.get(position);
        holder.tv_title.setText("选择退款方式");
        if (!TextUtils.isEmpty(item.get_$4())) {
            holder.tv_type1.setVisibility(View.VISIBLE);
            holder.tv_type1.setText(item.get_$4());
        } else {
            holder.tv_type1.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.get_$40())) {
            holder.tv_type2.setVisibility(View.VISIBLE);
            holder.tv_type2.setText(item.get_$40());
        } else {
            holder.tv_type2.setVisibility(View.GONE);
        }
        holder.tv_type3.setVisibility(View.GONE);

        holder.fragment_address_region_valueTextView.setText(addressRegion);

        //点击上门取件
        holder.tv_type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tv_type1.setSelected(true);
                holder.tv_type2.setSelected(false);
                holder.address_regionLayout.setVisibility(View.VISIBLE);
                holder.address_detailLayout.setVisibility(View.VISIBLE);
                addressRegion = holder.fragment_address_region_valueTextView.getText().toString();
                holder.edt_address_detail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() > 0) {
                            addressDetail = charSequence.toString();
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
                onclickListener.onlcick(1, "type");
            }
        });

        //点击选择地址
        holder.address_regionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onlcick(3, "type");
            }
        });

        //点击客户发货
        holder.tv_type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tv_type1.setSelected(false);
                holder.tv_type2.setSelected(true);
                holder.address_regionLayout.setVisibility(View.GONE);
                holder.address_detailLayout.setVisibility(View.GONE);
                onclickListener.onlcick(2, "type");

            }
        });
    }

    private void bindApplyPackViewHolder(final BackApplyTypeViewHolder holder, int position) {
        TakePackModel item = (TakePackModel) data.get(position);
        holder.tv_title.setText("包装");
        if (!TextUtils.isEmpty(item.get_$0())) {
            holder.tv_type1.setVisibility(View.VISIBLE);
            holder.tv_type1.setText(item.get_$0());
        } else {
            holder.tv_type1.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.get_$10())) {
            holder.tv_type2.setVisibility(View.VISIBLE);
            holder.tv_type2.setText(item.get_$10());
        } else {
            holder.tv_type2.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.get_$20())) {
            holder.tv_type3.setVisibility(View.VISIBLE);
            holder.tv_type3.setText(item.get_$20());
        } else {
            holder.tv_type3.setVisibility(View.GONE);
        }

        holder.address_regionLayout.setVisibility(View.GONE);
        holder.address_detailLayout.setVisibility(View.GONE);

        holder.tv_type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tv_type1.setSelected(true);
                holder.tv_type2.setSelected(false);
                holder.tv_type3.setSelected(false);
                onclickListener.onlcick(1, "pack");
            }
        });
        holder.tv_type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tv_type1.setSelected(false);
                holder.tv_type2.setSelected(true);
                holder.tv_type3.setSelected(false);
                onclickListener.onlcick(2, "pack");

            }
        });
        holder.tv_type3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tv_type1.setSelected(false);
                holder.tv_type2.setSelected(false);
                holder.tv_type3.setSelected(true);
                onclickListener.onlcick(3, "pack");
            }
        });

    }

    private void bindApplyImageViewHolder(BackApplyImageViewHolder holder, int position) {

        BackApplyImageModel item = (BackApplyImageModel) data.get(position);

        if (item.imageList.size() == 0) {
            holder.mImageRecyclerView.setVisibility(View.GONE);
        } else {
            holder.mImageRecyclerView.setVisibility(View.VISIBLE);
        }

        if (item.imageList.size() < 3) {
            holder.mPicRelativeLayout.setVisibility(View.VISIBLE);
        } else {
            holder.mPicRelativeLayout.setVisibility(View.GONE);
        }

        ReviewShareOrderImgAdater reviewShareOrderImgAdater = new ReviewShareOrderImgAdater(item.imageList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(holder.mImageRecyclerView.getContext());
        mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        holder.mImageRecyclerView.setLayoutManager(mLayoutManager);
        holder.mImageRecyclerView.setAdapter(reviewShareOrderImgAdater);

        Utils.setViewTypeForTag(holder.mPicRelativeLayout, ViewType.VIEW_TYPE_UPLOAD);
        Utils.setPositionForTag(holder.mPicRelativeLayout, position);
        holder.mPicRelativeLayout.setOnClickListener(onClickListener);
    }


    public interface OnclickListener {
        void onlcick(int position, String type);
    }

    public OnclickListener onclickListener;


    public void setOnclickListener(OnclickListener onclickListener) {
        this.onclickListener = onclickListener;
    }

}
