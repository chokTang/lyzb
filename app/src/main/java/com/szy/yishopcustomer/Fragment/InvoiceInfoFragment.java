package com.szy.yishopcustomer.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Adapter.TextWatcherAdapter;
import com.szy.yishopcustomer.Adapter.InvoiceInfoAdapter;
import com.szy.yishopcustomer.Adapter.InvoiceInfoAdapter.ITEM_VIEW_TYPE;
import com.szy.yishopcustomer.Constant.Key;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.BlankModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.ContentItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.InvoiceCompanyContentModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.InvoiceCompanyModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.InvoiceItemModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.InvoiceResultModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.InvoiceTitleModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.TitleModel;
import com.szy.yishopcustomer.ResponseModel.Checkout.VatLabelModel;
import com.szy.yishopcustomer.Util.JSON;
import com.szy.yishopcustomer.Util.LogUtils;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.EmailViewModel;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 宗仁 on 2016/6/8.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */
public class InvoiceInfoFragment extends YSCBaseFragment implements TextWatcherAdapter
        .TextWatcherListener {
    private static final String TAG = "InvoiceInfoFragment";
    private static final int ITEM_SPAN_COUNT = 3;//一行三列

    private static final int NO_INVOICE_POSITION = 0;
    public static final int NORMAL_INVOICE_POSITION = 1;
    public static final int VAT_INVOICE_POSITION = 2;
    public static final int EMAIL_INVOICE_POSITION = 3;

    private static final int INDIVIDUAL_TITLE_POSITION = 0;
    private static final int COMPANY_TITLE_POSITION = 1;

    @BindView(R.id.fragment_invoice_info_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.submit_button_two)
    TextView mOkButton;

    private GridLayoutManager mGridLayoutManager;
    private InvoiceInfoAdapter mAdapter;
    private ArrayList<InvoiceItemModel> mInvoiceInfo;
    private boolean is = false;

    public String getInvoiceCompany() {
        InvoiceItemModel normalInvoiceItemModel = null;

        if (!"disabled".equals(mInvoiceInfo.get(EMAIL_INVOICE_POSITION).disabled)) {//disable为空的时候 有电子发票
            normalInvoiceItemModel = mInvoiceInfo.get(EMAIL_INVOICE_POSITION);
        } else {
            normalInvoiceItemModel = mInvoiceInfo.get(NORMAL_INVOICE_POSITION);

        }
        if (normalInvoiceItemModel.selected.equals("selected")) {
            if (normalInvoiceItemModel.invoiceTitle.get(0).selected.equals("selected")){//选中个体
                return "";
            }else {
                return normalInvoiceItemModel.company.name;
            }
        }


        return "";
    }

    public String getInvoiceEmail() {
        InvoiceItemModel normalInvoiceItemModel = null;

        if (!"disabled".equals(mInvoiceInfo.get(EMAIL_INVOICE_POSITION).disabled)) {//disable为空的时候 有电子发票
            normalInvoiceItemModel = mInvoiceInfo.get(EMAIL_INVOICE_POSITION);
            if (normalInvoiceItemModel.selected.equals("selected")) {
                return normalInvoiceItemModel.email.emial;
            }
        } else {
            normalInvoiceItemModel = mInvoiceInfo.get(NORMAL_INVOICE_POSITION);
        }

        return "";
    }


    public String getInvoiceTaxpayer() {
        InvoiceItemModel normalInvoiceItemModel = null;
        if (!"disabled".equals(mInvoiceInfo.get(EMAIL_INVOICE_POSITION).disabled)) {//disable为空的时候 有电子发票
            normalInvoiceItemModel = mInvoiceInfo.get(EMAIL_INVOICE_POSITION);
        } else {
            normalInvoiceItemModel = mInvoiceInfo.get(NORMAL_INVOICE_POSITION);
        }
        if (normalInvoiceItemModel.selected.equals("selected")) {
            if (normalInvoiceItemModel.invoiceTitle.get(0).selected.equals("selected")){//选中个体
                return "";
            }else {
                return normalInvoiceItemModel.company.taxpayer;
            }
        }
        return "";
    }

    public int getInvoiceContent() {
        InvoiceItemModel normalInvoiceItemModel = null;
        if (!"disabled".equals(mInvoiceInfo.get(EMAIL_INVOICE_POSITION).disabled)) {//disable为空的时候 有电子发票
            normalInvoiceItemModel = mInvoiceInfo.get(EMAIL_INVOICE_POSITION);
        } else {
            normalInvoiceItemModel = mInvoiceInfo.get(NORMAL_INVOICE_POSITION);

        }
        if (normalInvoiceItemModel.selected.equals("selected")) {
            for (ContentItemModel contentItemModel : normalInvoiceItemModel.content_list) {
                if (contentItemModel.checked.equals("checked")) {
                    return contentItemModel.contentPosition;
                }
            }
        }

        return 0;
    }

    public String getInvoiceName() {
        if (mInvoiceInfo.get(NO_INVOICE_POSITION).selected.equals("selected")) {
            return mInvoiceInfo.get(NO_INVOICE_POSITION).name;
        } else if (mInvoiceInfo.get(NORMAL_INVOICE_POSITION).selected.equals("selected")) {
            return mInvoiceInfo.get(NORMAL_INVOICE_POSITION).name;
        } else if (mInvoiceInfo.get(VAT_INVOICE_POSITION).selected.equals("selected")) {
            return mInvoiceInfo.get(VAT_INVOICE_POSITION).name;
        } else if (mInvoiceInfo.get(EMAIL_INVOICE_POSITION).selected.equals("selected")) {
            return mInvoiceInfo.get(EMAIL_INVOICE_POSITION).name;
        }
        return "";
    }

    public String getInvoiceTitle() {
        InvoiceItemModel normalInvoiceItemModel = null;
        if (!"disabled".equals(mInvoiceInfo.get(EMAIL_INVOICE_POSITION).disabled)) {//disable为空的时候 有电子发票
            normalInvoiceItemModel = mInvoiceInfo.get(EMAIL_INVOICE_POSITION);
        } else {
            normalInvoiceItemModel = mInvoiceInfo.get(NORMAL_INVOICE_POSITION);
        }

        if (normalInvoiceItemModel.selected.equals("selected")) {
            InvoiceTitleModel individualInvoiceTitle = normalInvoiceItemModel.invoiceTitle.get
                    (INDIVIDUAL_TITLE_POSITION);
            if (individualInvoiceTitle.selected.equals("selected")) {
                return individualInvoiceTitle.name;
            } else {
                return normalInvoiceItemModel.company.name;
            }
        }

        return "";
    }

    public int getInvoiceType() {
        if (mInvoiceInfo.get(NO_INVOICE_POSITION).selected.equals("selected")) {
            return NO_INVOICE_POSITION;
        } else if (mInvoiceInfo.get(NORMAL_INVOICE_POSITION).selected.equals("selected")) {
            return NORMAL_INVOICE_POSITION;
        } else if (mInvoiceInfo.get(VAT_INVOICE_POSITION).selected.equals("selected")) {
            return VAT_INVOICE_POSITION;
        } else if (mInvoiceInfo.get(EMAIL_INVOICE_POSITION).selected.equals("selected")) {
            return EMAIL_INVOICE_POSITION;
        }
        return NO_INVOICE_POSITION;
    }

    @Override
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.submit_button_two:
                setResult();
                break;
            case R.id.fragment_invoice_info_type_textView:
                LogUtils.Companion.e("1111111111111");
                changeInvoiceType((int) view.getTag());
                break;
            case R.id.fragment_invoice_info_checkbox_wrapperRelativelayout:
                LogUtils.Companion.e("2222222222222222");
                int checkboxType = Utils.getExtraInfoOfTag(view);
                int position = Utils.getPositionOfTag(view);
                if (checkboxType == InvoiceInfoAdapter.CHECKBOX_TYPE.INVOICE_TITLE.ordinal()) {
                    changeInvoiceTitle(position);
                } else if (checkboxType == InvoiceInfoAdapter.CHECKBOX_TYPE.INVOICE_CONTENT
                        .ordinal()) {
                    changeInvoiceContent(position);
                }

            case R.id.et_inv_email:
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mGridLayoutManager = new GridLayoutManager(getContext(), ITEM_SPAN_COUNT);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (ITEM_VIEW_TYPE.valueOf(mAdapter.getItemViewType(position))) {
                    case INVOICE:
                    case INVOICE_TITLE:
                        return 1;
                    default:
                        return ITEM_SPAN_COUNT;
                }
            }
        });
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mOkButton.setOnClickListener(this);

        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration());

        return view;
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        public GridSpacingItemDecoration() {
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position

            switch (position) {
                case 1:
                    //在左边，加上左边边距
                    outRect.left = Utils.dpToPx(getContext(), 10);
                    outRect.right = Utils.dpToPx(getContext(), 0);
                    break;
                case 2:
                    //在中间，加上两边的边距
                    outRect.left = Utils.dpToPx(getContext(), 7);
                    outRect.right = Utils.dpToPx(getContext(), 7);
                    break;
                case 3:
                    //在右边，加上右边边距
                    outRect.left = Utils.dpToPx(getContext(), 0);
                    outRect.right = Utils.dpToPx(getContext(), 10);
                    break;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_invoice_info;
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        mInvoiceInfo = arguments.getParcelableArrayList(Key.KEY_INVOICE_INFO.getValue());
        if (mInvoiceInfo == null) {
            return;
        }

        InvoiceItemModel vatInvoiceItemModel = mInvoiceInfo.get(VAT_INVOICE_POSITION);
        vatInvoiceItemModel.vatLabelList = JSON.parseArray(vatInvoiceItemModel.contents,
                VatLabelModel.class);
        InvoiceItemModel normalInvoiceItemModel = mInvoiceInfo.get(NORMAL_INVOICE_POSITION);
        InvoiceItemModel emailInvoiceItemModel = mInvoiceInfo.get(EMAIL_INVOICE_POSITION);
        normalInvoiceItemModel.company = new InvoiceCompanyModel(
                JSON.parseObject
                        (normalInvoiceItemModel.contents, InvoiceCompanyContentModel.class).inv_company,
                JSON.parseObject
                        (normalInvoiceItemModel.contents, InvoiceCompanyContentModel.class).inv_taxpayers
        );
        emailInvoiceItemModel.company = new InvoiceCompanyModel(
                JSON.parseObject
                        (emailInvoiceItemModel.contents, InvoiceCompanyContentModel.class).inv_company,
                JSON.parseObject
                        (emailInvoiceItemModel.contents, InvoiceCompanyContentModel.class).inv_taxpayers
        );

        emailInvoiceItemModel.email = new EmailViewModel(
                JSON.parseObject
                        (emailInvoiceItemModel.contents, InvoiceCompanyContentModel.class).inv_email
        );

        //构造普通发票的个体与公司模块
        if (!Utils.isNull(normalInvoiceItemModel.company.name)) {
            normalInvoiceItemModel.invoiceTitle = new ArrayList<>();
            normalInvoiceItemModel.invoiceTitle.add(INDIVIDUAL_TITLE_POSITION, new
                    InvoiceTitleModel("", getString(R.string.invoiceIndividual)));
            normalInvoiceItemModel.invoiceTitle.add(COMPANY_TITLE_POSITION, new InvoiceTitleModel
                    ("selected", getString(R.string.invoiceCompany)));
        } else {
            normalInvoiceItemModel.invoiceTitle = new ArrayList<>();
            normalInvoiceItemModel.invoiceTitle.add(INDIVIDUAL_TITLE_POSITION, new
                    InvoiceTitleModel("selected", getString(R.string.invoiceIndividual)));
            normalInvoiceItemModel.invoiceTitle.add(COMPANY_TITLE_POSITION, new InvoiceTitleModel
                    ("", getString(R.string.invoiceCompany)));
        }
        //构造电子发票的个体与公司模块
        if (!Utils.isNull(emailInvoiceItemModel.company.name)) {
            emailInvoiceItemModel.invoiceTitle = new ArrayList<>();
            emailInvoiceItemModel.invoiceTitle.add(INDIVIDUAL_TITLE_POSITION, new
                    InvoiceTitleModel("", getString(R.string.invoiceIndividual)));
            emailInvoiceItemModel.invoiceTitle.add(COMPANY_TITLE_POSITION, new InvoiceTitleModel
                    ("selected", getString(R.string.invoiceCompany)));
        } else {
            emailInvoiceItemModel.invoiceTitle = new ArrayList<>();
            emailInvoiceItemModel.invoiceTitle.add(INDIVIDUAL_TITLE_POSITION, new
                    InvoiceTitleModel("selected", getString(R.string.invoiceIndividual)));
            emailInvoiceItemModel.invoiceTitle.add(COMPANY_TITLE_POSITION, new InvoiceTitleModel
                    ("", getString(R.string.invoiceCompany)));
        }

        //电子发票明细模块
        for (int i = 0; i < emailInvoiceItemModel.content_list.size(); i++) {
            ContentItemModel contentItemModel = emailInvoiceItemModel.content_list.get(i);
            contentItemModel.contentPosition = i;
        }
        //普通发票明细模块
        for (int i = 0; i < normalInvoiceItemModel.content_list.size(); i++) {
            ContentItemModel contentItemModel = normalInvoiceItemModel.content_list.get(i);
            contentItemModel.contentPosition = i;
        }


        mAdapter = new InvoiceInfoAdapter();
        mAdapter.onClickListener = this;
        mAdapter.textWatcherListener = this;
        setAdapterData();
    }

    @Override
    public void onTextChanged(EditText view, String text) {
        final int position = Utils.getPositionOfTag(view);
        final int extraInfo = Utils.getExtraInfoOfTag(view);
        if (extraInfo == InvoiceInfoAdapter.EDIT_TEXT_TYPE.COMPANY.ordinal()) {
            if (view.getId() == R.id.fragment_invoice_info_company_editText) {
                InvoiceItemModel invoiceItemModel = null;
                if (!"disabled".equals(mInvoiceInfo.get(EMAIL_INVOICE_POSITION).disabled)) {//disable为空的时候 有电子发票
                    invoiceItemModel = mInvoiceInfo.get(EMAIL_INVOICE_POSITION);
                } else {
                    invoiceItemModel = mInvoiceInfo.get(NORMAL_INVOICE_POSITION);
                }
                invoiceItemModel.company.name = view.getText().toString();
            } else {
                InvoiceItemModel invoiceItemModel = null;
                if (!"disabled".equals(mInvoiceInfo.get(EMAIL_INVOICE_POSITION).disabled)) {//disable为空的时候 有电子发票
                    invoiceItemModel = mInvoiceInfo.get(EMAIL_INVOICE_POSITION);
                } else {
                    invoiceItemModel = mInvoiceInfo.get(NORMAL_INVOICE_POSITION);
                }
                invoiceItemModel.company.taxpayer = view.getText().toString();
            }

        } else if (extraInfo == InvoiceInfoAdapter.EDIT_TEXT_TYPE.VAT_LABEL.ordinal()) {
            InvoiceItemModel invoiceItemModel = mInvoiceInfo.get(VAT_INVOICE_POSITION);
            invoiceItemModel.vatLabelList.get(position - 5).value = view.getText().toString();
        } else if (extraInfo == InvoiceInfoAdapter.EDIT_TEXT_TYPE.EMAIL.ordinal()) {
            InvoiceItemModel invoiceItemModel = mInvoiceInfo.get(EMAIL_INVOICE_POSITION);
            invoiceItemModel.email.emial = view.getText().toString();
        }
    }

    /**
     * 设置数据
     */
    public void setAdapterData() {
        ArrayList<Object> data = new ArrayList<>();
        //1
        data.add(new TitleModel(getString(R.string.invoiceType)));

        InvoiceItemModel noInvoiceItemModel = mInvoiceInfo.get(NO_INVOICE_POSITION);
        //2
        data.add(noInvoiceItemModel);
        InvoiceItemModel vatInvoiceItemModel = null, normalInvoiceItemModel = null, emailInvoiceItemModel = null;

        if (!"disabled".equals(mInvoiceInfo.get(EMAIL_INVOICE_POSITION).disabled)) {//disable为空的时候 有电子发票
            //添加电子发票
            emailInvoiceItemModel = mInvoiceInfo.get(EMAIL_INVOICE_POSITION);
            data.add(emailInvoiceItemModel);

            //添加电子发票下面的东西
            if (emailInvoiceItemModel.selected.equals("selected")) {
                data.add(new BlankModel());

                data.add(new TitleModel(getString(R.string.invoiceTitle)));

                //因为前面添加了5个数据源  所以 个体单位这栏的position-5
                data.addAll(emailInvoiceItemModel.invoiceTitle);

                if (emailInvoiceItemModel.invoiceTitle.get(COMPANY_TITLE_POSITION).selected.equals
                        ("selected")) {
                    data.add(emailInvoiceItemModel.company);
                }
                //发票内容
                data.add(new BlankModel());
                data.add(new TitleModel(getString(R.string.invoiceContent)));
                data.addAll(emailInvoiceItemModel.content_list);

                //收票邮箱
                data.add(new BlankModel());
                data.add(new TitleModel(getString(R.string.invoiceMail)));
                data.add(emailInvoiceItemModel.email);

            }
        } else {//小于或则等于三 相当于普通发票

            //添加普通发票
            normalInvoiceItemModel = mInvoiceInfo.get(NORMAL_INVOICE_POSITION);
            data.add(normalInvoiceItemModel);

            //添加增值随发票
            vatInvoiceItemModel = mInvoiceInfo.get(VAT_INVOICE_POSITION);
            data.add(vatInvoiceItemModel);

            //添加普通发票下面的东西
            if (normalInvoiceItemModel.selected.equals("selected")) {
                data.add(new BlankModel());

                data.add(new TitleModel(getString(R.string.invoiceTitle)));

                //因为前面添加了6个数据源  所以 个体单位这栏的position-6
                data.addAll(normalInvoiceItemModel.invoiceTitle);

                if (normalInvoiceItemModel.invoiceTitle.get(COMPANY_TITLE_POSITION).selected.equals
                        ("selected")) {
                    data.add(normalInvoiceItemModel.company);
                }
                //发票内容
                data.add(new BlankModel());
                data.add(new TitleModel(getString(R.string.invoiceContent)));
                data.addAll(normalInvoiceItemModel.content_list);

            }
            //添加增值税发票下面的东西
            if (vatInvoiceItemModel.selected.equals("selected")) {
                data.add(new BlankModel());
                data.addAll(vatInvoiceItemModel.vatLabelList);
            }
        }


        mAdapter.data.clear();
        mAdapter.data.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    public void setVatResult(InvoiceResultModel result) {
        InvoiceItemModel vatInvoiceItemModel = null;
        vatInvoiceItemModel = mInvoiceInfo.get(VAT_INVOICE_POSITION);
        if (vatInvoiceItemModel.selected.equals("selected")) {
            for (VatLabelModel vatLabelModel : vatInvoiceItemModel.vatLabelList) {
                Utils.copyField(result, vatLabelModel.name, vatLabelModel.value);
            }
        }
    }

    //内容切换
    private void changeInvoiceContent(int position) {
        InvoiceItemModel invoiceItemModel = null;
        if (!"disabled".equals(mInvoiceInfo.get(EMAIL_INVOICE_POSITION).disabled)) {//disable为空的时候 有电子发票
            invoiceItemModel = mInvoiceInfo.get(EMAIL_INVOICE_POSITION);
        } else {
            invoiceItemModel = mInvoiceInfo.get(NORMAL_INVOICE_POSITION);
        }
        for (int i = 0; i < invoiceItemModel.content_list.size(); i++) {
            ContentItemModel contentItemModel = invoiceItemModel.content_list.get(i);
            if (i == position) {
                contentItemModel.checked = "checked";
            } else {
                contentItemModel.checked = "";
            }
        }
        setAdapterData();
    }

    private void changeInvoiceTitle(int position) {
        InvoiceItemModel invoiceItemModel = null;
        int myposition = 0;
        if (!"disabled".equals(mInvoiceInfo.get(EMAIL_INVOICE_POSITION).disabled)) {//disable为空的时候 有电子发票
            invoiceItemModel = mInvoiceInfo.get(EMAIL_INVOICE_POSITION);
            myposition = position - 5;
        } else {
            invoiceItemModel = mInvoiceInfo.get(NORMAL_INVOICE_POSITION);
            myposition = position - 6;
        }
        for (int i = 0; i < invoiceItemModel.invoiceTitle.size(); i++) {
            InvoiceTitleModel invoiceTitleModel = invoiceItemModel.invoiceTitle.get(i);
            if (i == myposition) {
                invoiceTitleModel.selected = "selected";
            } else {
                invoiceTitleModel.selected = "";
            }
        }
        setAdapterData();
    }

    /**
     * 选择发票的种类
     *
     * @param position
     */
    private void changeInvoiceType(int position) {
        int realPosition = position - 1;
        if (!"disabled".equals(mInvoiceInfo.get(EMAIL_INVOICE_POSITION).disabled)) {//disable为空的时候 有电子发票
            switch (position) {
                case 1://不开发票
                    mInvoiceInfo.get(NO_INVOICE_POSITION).selected = "selected";
                    mInvoiceInfo.get(EMAIL_INVOICE_POSITION).selected = "";
                    break;
                case 2://电子发票
                    mInvoiceInfo.get(NO_INVOICE_POSITION).selected = "";
                    mInvoiceInfo.get(EMAIL_INVOICE_POSITION).selected = "selected";
                    break;
            }
        } else {//普通发票
            for (int i = 0; i < mInvoiceInfo.size(); i++) {
                InvoiceItemModel invoiceItemModel = mInvoiceInfo.get(i);
                if (realPosition == i) {
                    invoiceItemModel.selected = "selected";
                } else {
                    invoiceItemModel.selected = "";
                }
            }
        }

        setAdapterData();
    }

    private boolean checkResult() {
        InvoiceItemModel noInvoice = mInvoiceInfo.get(NO_INVOICE_POSITION);
        InvoiceItemModel normalInvoice = null;
        InvoiceItemModel vatInvoice = null;
        InvoiceItemModel emailInvoice = null;
        vatInvoice = mInvoiceInfo.get(VAT_INVOICE_POSITION);
        emailInvoice = mInvoiceInfo.get(EMAIL_INVOICE_POSITION);
        normalInvoice = mInvoiceInfo.get(NORMAL_INVOICE_POSITION);
        if (noInvoice.selected.equals("selected")) {
            return true;
        } else if (normalInvoice.selected.equals("selected")) {
            InvoiceTitleModel companyTitleModel = normalInvoice.invoiceTitle.get
                    (COMPANY_TITLE_POSITION);
            return !(companyTitleModel.selected.equals("selected") && (Utils.isNull(normalInvoice
                    .company.name) || Utils.isNull(normalInvoice.company.taxpayer)));
        } else if (vatInvoice.selected.equals("selected")) {
            for (VatLabelModel vatLabelModel : vatInvoice.vatLabelList) {
                if (Utils.isNull(vatLabelModel.value)) {
                    return false;
                }

            }
            return true;
        } else if (emailInvoice.selected.equals("selected")) {
            InvoiceTitleModel companyTitleModel, privateTitleModel;
            privateTitleModel = emailInvoice.invoiceTitle.get(INDIVIDUAL_TITLE_POSITION);
            companyTitleModel = emailInvoice.invoiceTitle.get(COMPANY_TITLE_POSITION);
            if (companyTitleModel.selected.equals("selected")) {
                return !(companyTitleModel.selected.equals("selected") && (Utils.isNull(emailInvoice
                        .company.name) || Utils.isNull(emailInvoice.company.taxpayer)
                        || Utils.isNull(emailInvoice.email.emial)));
            }
            if (privateTitleModel.selected.equals("selected")) {
                return !(privateTitleModel.selected.equals("selected") && Utils.isNull(emailInvoice.email.emial));
            }

        }

        return false;
    }

    private void setResult() {
        if (checkResult()) {
            InvoiceResultModel result = new InvoiceResultModel();
            result.inv_type = getInvoiceType();
            result.inv_name = getInvoiceName();
            result.inv_title = getInvoiceTitle();
            result.inv_content = getInvoiceContent();
            result.inv_email = getInvoiceEmail();
            result.inv_company = getInvoiceCompany();
            result.inv_taxpayers = getInvoiceTaxpayer();

            setVatResult(result);
            Intent intent = new Intent();
            intent.putExtra(Key.KEY_INVOICE_INFO.getValue(), result);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), getString(R.string.pleaseEnterRequiredInformation),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
