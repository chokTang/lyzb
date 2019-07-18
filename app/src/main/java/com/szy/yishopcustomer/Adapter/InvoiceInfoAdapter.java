package com.szy.yishopcustomer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szy.common.Adapter.TextWatcherAdapter;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.BlankModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.ContentItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.InvoiceCompanyModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.InvoiceItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.InvoiceTitleModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.TitleModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.VatLabelModel;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewHolder.Checkout.InvoiceCheckboxViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.InvoiceCompanyViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.InvoiceEmailViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.InvoiceTitleViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.InvoiceTypeViewHolder;
import com.szy.yishopcustomer.ViewHolder.Checkout.VatLabelViewHolder;
import com.szy.yishopcustomer.ViewHolder.DividerViewHolder;
import com.szy.yishopcustomer.ViewModel.EmailViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 宗仁 on 16/7/22.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 * 发票适配器
 */
public class InvoiceInfoAdapter extends RecyclerView.Adapter {
    public static final int TAG_POSITION = 0;
    public static final int TAG_EXTRA_INFO = 1;
    public final List<Object> data;
    public View.OnClickListener onClickListener;
    public TextView.OnEditorActionListener onEditorActionListener;
    public TextWatcherAdapter.TextWatcherListener textWatcherListener;

    public InvoiceInfoAdapter(List<Object> data) {
        this.data = data;
    }

    public InvoiceInfoAdapter() {
        this.data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (ITEM_VIEW_TYPE.valueOf(viewType)) {
            case TITLE:
                viewHolder = createTitleViewHolder(parent);
                break;
            case BLANK:
                viewHolder = createBlankViewHolder(parent);
                break;
            case INVOICE:
                viewHolder = createTypeViewHolder(parent);
                break;
            case INVOICE_TITLE:
                viewHolder = createInvoiceTitleViewHolder(parent);
                break;
            case INVOICE_CONTENT:
                viewHolder = createContentViewHolder(parent);
                break;
            case COMPANY:
                viewHolder = createCompanyViewHolder(parent);
                break;
            case INVOICE_EMAIL:
                viewHolder = createEmailViewHolder(parent);
                break;
            /*case HTTP_GOODS:
                viewHolder = createGoodsViewHolder(parent);
                break;*/
            case VAT_LABEL:
                viewHolder = createVatViewHolder(parent);
                break;

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (ITEM_VIEW_TYPE.valueOf(getItemViewType(position))) {
            case TITLE:
                bindTitleViewHolder(holder, data.get(position));
                break;
            case BLANK:
                bindBlankViewHolder(holder, data.get(position));
                break;
            case INVOICE:
                bindTypeViewHolder(holder, data.get(position), position);
                break;
            case INVOICE_TITLE:
                bindInvoiceTitleViewHolder(holder, data.get(position), position);
                break;
            case INVOICE_CONTENT:
                bindContentViewHolder(holder, data.get(position), position);
                break;
            case COMPANY:
                bindCompanyViewHolder(holder, data.get(position), position);
                break;
            case INVOICE_EMAIL:
                bindEmailViewHolder(holder, data.get(position), position);
                break;
           /* case HTTP_GOODS:
                bindGoodsViewHolder(holder, data.get(position));
                break;*/
            case VAT_LABEL:
                bindVatViewHolder(holder, data.get(position), position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof TitleModel) {
            return ITEM_VIEW_TYPE.TITLE.ordinal();
        } else if (object instanceof BlankModel) {
            return ITEM_VIEW_TYPE.BLANK.ordinal();
        } else if (object instanceof InvoiceItemModel) {
            return ITEM_VIEW_TYPE.INVOICE.ordinal();
        } else if (object instanceof InvoiceTitleModel) {
            return ITEM_VIEW_TYPE.INVOICE_TITLE.ordinal();
        } else if (object instanceof InvoiceCompanyModel) {
            return ITEM_VIEW_TYPE.COMPANY.ordinal();
        } /*else if (object instanceof InvoiceGoodsModel) {
            return ITEM_VIEW_TYPE.HTTP_GOODS.ordinal();
        }*/ else if (object instanceof ContentItemModel) {
            return ITEM_VIEW_TYPE.INVOICE_CONTENT.ordinal();
        } else if (object instanceof VatLabelModel) {
            return ITEM_VIEW_TYPE.VAT_LABEL.ordinal();
        } else if (object instanceof EmailViewModel) {
            return ITEM_VIEW_TYPE.INVOICE_EMAIL.ordinal();
        } else {
            return -1;
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindBlankViewHolder(RecyclerView.ViewHolder holder, Object object) {
        DividerViewHolder viewHolder = (DividerViewHolder) holder;
        BlankModel item = (BlankModel) object;
        //viewHolder.textView.setText(item.title);
    }

    private void bindCompanyViewHolder(RecyclerView.ViewHolder holder, Object object, int
            position) {
        InvoiceCompanyViewHolder viewHolder = (InvoiceCompanyViewHolder) holder;
        InvoiceCompanyModel item = (InvoiceCompanyModel) object;
        if (!Utils.isNull(item.name)) {
            viewHolder.editText.setText(item.name);
        }
        if (!Utils.isNull(item.taxpayer)) {
            viewHolder.et_inv_taxpayers.setText(item.taxpayer);
        }
        Utils.setPositionForTag(viewHolder.editText, position);
        Utils.setExtraInfoForTag(viewHolder.editText, EDIT_TEXT_TYPE.COMPANY.ordinal());
        //        viewHolder.editText.setOnEditorActionListener(onEditorActionListener);
        viewHolder.editText.setTextWatcherListener(textWatcherListener);

        Utils.setPositionForTag(viewHolder.et_inv_taxpayers, position);
        Utils.setExtraInfoForTag(viewHolder.et_inv_taxpayers, EDIT_TEXT_TYPE.COMPANY.ordinal());
        //        viewHolder.editText.setOnEditorActionListener(onEditorActionListener);
        viewHolder.et_inv_taxpayers.setTextWatcherListener(textWatcherListener);
    }

    private void bindEmailViewHolder(RecyclerView.ViewHolder holder, Object object, int
            position) {
        InvoiceEmailViewHolder viewHolder = (InvoiceEmailViewHolder) holder;
        EmailViewModel item = (EmailViewModel) object;
        if (!Utils.isNull(item.emial)) {
            viewHolder.et_inv_email.setText(item.emial);
        }

        Utils.setPositionForTag(viewHolder.et_inv_email, position);
        Utils.setExtraInfoForTag(viewHolder.et_inv_email, EDIT_TEXT_TYPE.EMAIL.ordinal());
        viewHolder.et_inv_email.setTextWatcherListener(textWatcherListener);
    }

    private void bindContentViewHolder(RecyclerView.ViewHolder holder, Object object, int
            position) {
        InvoiceCheckboxViewHolder viewHolder = (InvoiceCheckboxViewHolder) holder;
        ContentItemModel item = (ContentItemModel) object;
        if (item.checked.equals("checked")) {
            viewHolder.imageView.setSelected(true);
        } else {
            viewHolder.imageView.setSelected(false);
        }
        viewHolder.textView.setText(item.name);
        Utils.setPositionForTag(viewHolder.wrapperRelativeLayout, item.contentPosition);
        Utils.setExtraInfoForTag(viewHolder.wrapperRelativeLayout, CHECKBOX_TYPE.INVOICE_CONTENT
                .ordinal());
        viewHolder.wrapperRelativeLayout.setOnClickListener(onClickListener);
    }

/*    private void bindGoodsViewHolder(RecyclerView.ViewHolder holder, Object object) {
        InvoiceGoodsViewHolder viewHolder = (InvoiceGoodsViewHolder) holder;
        InvoiceGoodsModel item = (InvoiceGoodsModel) object;
        if (!Utils.isNull(item.sku_image)) {
            ImageLoader.displayImage(Utils.urlOfImage(item.sku_image),
                    viewHolder.imageView);
        }
        viewHolder.nameTextView.setText(item.sku_name);
        viewHolder.shopNameTextView.setText(item.shop_name);
    }*/

    private void bindInvoiceTitleViewHolder(RecyclerView.ViewHolder holder, Object object, int
            position) {
        InvoiceCheckboxViewHolder viewHolder = (InvoiceCheckboxViewHolder) holder;
        InvoiceTitleModel item = (InvoiceTitleModel) object;
        if (item.selected.equals("selected")) {
            viewHolder.imageView.setSelected(true);
        } else {
            viewHolder.imageView.setSelected(false);
        }
        viewHolder.textView.setText(item.name);
        Utils.setPositionForTag(viewHolder.wrapperRelativeLayout, position);
        Utils.setExtraInfoForTag(viewHolder.wrapperRelativeLayout, CHECKBOX_TYPE.INVOICE_TITLE
                .ordinal());
        viewHolder.wrapperRelativeLayout.setOnClickListener(onClickListener);
    }

    private void bindTitleViewHolder(RecyclerView.ViewHolder holder, Object object) {
        InvoiceTitleViewHolder viewHolder = (InvoiceTitleViewHolder) holder;
        TitleModel item = (TitleModel) object;
        viewHolder.textView.setText(item.title);
    }

    private void bindTypeViewHolder(RecyclerView.ViewHolder holder, Object object, int position) {
        InvoiceTypeViewHolder viewHolder = (InvoiceTypeViewHolder) holder;
        InvoiceItemModel item = (InvoiceItemModel) object;
        viewHolder.textView.setText(item.name);
        if ("disabled".equals(item.disabled)) {
            viewHolder.textView.setBackgroundResource(R.drawable.disable_button);

            viewHolder.textView.setSelected(false);
            viewHolder.textView.setTextColor(viewHolder.textView.getResources().getColor(R.color
                    .colorSix));
            viewHolder.textView.setEnabled(false);
            viewHolder.textView.setOnClickListener(null);
        } else {
            viewHolder.textView.setBackgroundResource(R.drawable.item_background_selector);
            viewHolder.textView.setEnabled(true);
            if ("selected".equals(item.selected)) {
                viewHolder.textView.setSelected(true);
            } else {
                viewHolder.textView.setSelected(false);
            }
            viewHolder.textView.setTag(position);
            viewHolder.textView.setOnClickListener(onClickListener);
        }

    }

    private void bindVatViewHolder(RecyclerView.ViewHolder holder, Object object, int position) {
        VatLabelViewHolder viewHolder = (VatLabelViewHolder) holder;
        VatLabelModel item = (VatLabelModel) object;

        if (item.required) {
            viewHolder.starTextView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.starTextView.setVisibility(View.INVISIBLE);
        }

        viewHolder.textView.setText(item.label);
        viewHolder.editText.setHint("请输入" + item.label);
        Utils.setPositionForTag(viewHolder.editText, position);
        Utils.setExtraInfoForTag(viewHolder.editText, EDIT_TEXT_TYPE.VAT_LABEL.ordinal());
        viewHolder.editText.setText(item.value);

        viewHolder.editText.setTextWatcherListener(textWatcherListener);
    }

    private RecyclerView.ViewHolder createBlankViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_divider_one,
                parent, false);
        DividerViewHolder viewHolder = new DividerViewHolder(view);
        return viewHolder;
    }

    private RecyclerView.ViewHolder createCompanyViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_invoice_info_company, parent, false);
        InvoiceCompanyViewHolder viewHolder = new InvoiceCompanyViewHolder(view);
        return viewHolder;
    }

    private RecyclerView.ViewHolder createEmailViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_invoice_info_email, parent, false);
        InvoiceEmailViewHolder viewHolder = new InvoiceEmailViewHolder(view);
        return viewHolder;
    }

    private RecyclerView.ViewHolder createContentViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_invoice_info_checkbox, parent, false);
        InvoiceCheckboxViewHolder viewHolder = new InvoiceCheckboxViewHolder(view);
        return viewHolder;
    }

/*    private RecyclerView.ViewHolder createGoodsViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice_goods,
         parent, false);
        InvoiceGoodsViewHolder viewHolder = new InvoiceGoodsViewHolder(view);
        return viewHolder;
    }*/

    private RecyclerView.ViewHolder createInvoiceTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_invoice_info_title_checkbox, parent, false);
        InvoiceCheckboxViewHolder viewHolder = new InvoiceCheckboxViewHolder(view);
        return viewHolder;
    }

    private RecyclerView.ViewHolder createTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_invoice_info_title, parent, false);
        InvoiceTitleViewHolder viewHolder = new InvoiceTitleViewHolder(view);
        return viewHolder;
    }

    private RecyclerView.ViewHolder createTypeViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_invoice_info_type, parent, false);
        InvoiceTypeViewHolder viewHolder = new InvoiceTypeViewHolder(view);
        return viewHolder;
    }

    private RecyclerView.ViewHolder createVatViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .fragment_invoice_info_vat_label, parent, false);
        VatLabelViewHolder viewHolder = new VatLabelViewHolder(view);
        return viewHolder;
    }

    public enum ITEM_VIEW_TYPE {
        TITLE,
        BLANK,
        INVOICE,
        INVOICE_TITLE,
        INVOICE_CONTENT,
        COMPANY,
        INVOICE_EMAIL,
        //HTTP_GOODS,
        VAT_LABEL;

        private static Map<Integer, ITEM_VIEW_TYPE> mMap = new HashMap<>();

        static {
            for (ITEM_VIEW_TYPE itemViewType : ITEM_VIEW_TYPE.values()) {
                mMap.put(itemViewType.ordinal(), itemViewType);
            }
        }

        public static ITEM_VIEW_TYPE valueOf(int ordinal) {
            return mMap.get(ordinal);
        }

        public int getValue() {
            return this.ordinal();
        }
    }

    public enum CHECKBOX_TYPE {
        INVOICE_TITLE,
        INVOICE_CONTENT,
    }

    public enum EDIT_TEXT_TYPE {
        COMPANY,
        EMAIL,
        VAT_LABEL,
    }
}
