package com.szy.yishopcustomer.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.common.Other.CommonRequest;
import com.szy.common.ResponseModel.Common.ResponseCommonModel;
import com.szy.common.Util.ImageLoader;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Activity.ShareActivity;
import com.szy.yishopcustomer.Activity.ViewOriginalImageActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Shop.ResponseShopPrepareModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.View.ListViewForScrollView;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Smart on 2017/5/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class ShopPrepareFragment extends YSCBaseFragment {

    public static final int HTTP_WHAT_SHOP_POST_MESSAGE = 10001;
    private static final int REQUEST_CODE_SHARE = 1;
    private static final int HTTP_WHAT_SHOP_INFO = 2;

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.activity_shop_imageView)
    ImageView activity_shop_imageView;
    @BindView(R.id.activity_shop_name)
    TextView activity_shop_name;

    @BindView(R.id.textView_support_heat)
    TextView textView_support_heat;
    @BindView(R.id.textView_contact_number)
    TextView textView_contact_number;
    @BindView(R.id.textView_merchant_address)
    TextView textView_merchant_address;

    @BindView(R.id.imageView_facade_img)
    ImageView imageView_facade_img;
    @BindView(R.id.imageView_card_img)
    ImageView imageView_card_img;

    @BindView(R.id.linearlayout_merchant_album)
    LinearLayout linearlayout_merchant_album;
    @BindView(R.id.linearlayout_contact_number)
    LinearLayout linearlayout_contact_number;

    //邀请开店
    @BindView(R.id.activity_shop_collection)
    LinearLayout activity_shop_collection;
    //分享商家
    @BindView(R.id.activity_shop_share_two)
    LinearLayout activity_shop_share_two;

    @BindView(R.id.listView)
    ListViewForScrollView listview;

    @BindView(R.id.linearlayout_review)
    View linearlayout_review;
    private ResponseShopPrepareModel.DataBean mData;

    Context mContext;
    private String mShopId;

    private ArrayList<String> shareData = new ArrayList();
    private ArrayList<String> imageDate = new ArrayList<>();

    private List<ResponseShopPrepareModel.DataBean.ListBean> listData;
    private MylistAdpter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_shop_prepare;

        mContext = getActivity();

        Intent intent = getActivity().getIntent();
        if (intent == null) {
            Toast.makeText(mContext, R.string.pleaseEnterShopId, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mShopId = intent.getStringExtra(Key.KEY_SHOP_ID.getValue());
        if (mShopId == null) {
            Toast.makeText(mContext, R.string.pleaseEnterShopId, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        listData = new ArrayList<>();
        mAdapter = new MylistAdpter(getActivity(), listData);
        listview.setAdapter(mAdapter);

        return view;
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_SHOP_SUPPORT, HTTP_WHAT_SHOP_INFO);
        request.add("shop_id", mShopId);
        addRequest(request);
    }

    //设置数据
    private void refreshSucceed(String response) {
//        mShopModel = JSON.parseObject(response, ResponseShopPrepareModel.class);
        HttpResultManager.resolve(response, ResponseShopPrepareModel.class,new HttpResultManager.HttpResultCallBack<ResponseShopPrepareModel>() {

            @Override
            public void onSuccess(ResponseShopPrepareModel back) {
                updateUI(back.data);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(mContext,message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onEmptyData(int state) {
                toast("没有找到该店铺!");
                setResult(getActivity().RESULT_OK);
                finish();
            }

        });
    }

    public void updateUI(ResponseShopPrepareModel.DataBean data) {
        scrollView.setVisibility(View.VISIBLE);

        mData = data;
        ResponseShopPrepareModel.DataBean dataBean = data;
        ImageLoader.displayImage(Utils.urlOfImage(dataBean.shop_info.shop_image),
                activity_shop_imageView);
        activity_shop_name.setText(dataBean.shop_info.shop_name);

        textView_support_heat.setText(dataBean.support_count);
        textView_contact_number.setText(dataBean.shop_info.service_tel_format);
        textView_merchant_address.setText(dataBean.shop_info.region_name);

        String facade_img = dataBean.recommend_shop_info.facade_img;
        String card_img = dataBean.recommend_shop_info.card_img;


        if (TextUtils.isEmpty(facade_img) && TextUtils.isEmpty(card_img)) {
            linearlayout_merchant_album.setVisibility(View.GONE);

        } else {
            int height = (Utils.getWindowWidth(getActivity()) - Utils.dpToPx(getActivity(), 30)) / 2;
            imageView_facade_img.getLayoutParams().height = height;
            imageView_card_img.getLayoutParams().height = height;

            if (!TextUtils.isEmpty(facade_img)) {
                imageDate.add(facade_img);
                ImageLoader.displayImage(Utils.urlOfImage(facade_img),
                        imageView_facade_img);
                imageView_facade_img.getLayoutParams().width = height;
            }

            if (!TextUtils.isEmpty(card_img)) {
                imageDate.add(card_img);
                ImageLoader.displayImage(Utils.urlOfImage(card_img),
                        imageView_card_img);

                imageView_card_img.getLayoutParams().width = height;

            }

        }

        linearlayout_contact_number.setOnClickListener(this);
        activity_shop_collection.setOnClickListener(this);
        activity_shop_share_two.setOnClickListener(this);
        imageView_facade_img.setOnClickListener(this);
        imageView_card_img.setOnClickListener(this);

        shareData.add(Utils.urlOfWapShopindex() + "support.html?shop_id=" + dataBean.shop_info.shop_id);
        shareData.add(dataBean.shop_info.shop_name);
        shareData.add(dataBean.shop_info.simply_introduce);
        shareData.add(Utils.urlOfImage(dataBean.shop_info.shop_image));
        shareData.add(dataBean.shop_info.shop_id);

        //不按照留言是否可展示显示留言
/*        boolean canShow = false;
        for(int i = 0 ; i < data.list.size() ; i ++) {
            if("1".equals(data.list.get(i).is_show)) {
                canShow = true;
                break;
            }
        }

        if(canShow) {
            linearlayout_review.setVisibility(View.VISIBLE);
            listData.clear();
            listData.addAll(data.list);
            mAdapter.notifyDataSetChanged();
        } else {
            linearlayout_review.setVisibility(View.GONE);
        }*/
        //按照接口返回值显示留言
        if(data.list.size()>0) {
            linearlayout_review.setVisibility(View.VISIBLE);
            listData.clear();
            listData.addAll(data.list);
            mAdapter.notifyDataSetChanged();
        } else {
            linearlayout_review.setVisibility(View.GONE);
        }
    }


    @Override
    public void onConfirmDialogCanceled(int viewType, int position, int extraInfo) {

    }

    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        if (mData != null) {
            try {
                Intent mIntent = new Intent(Intent.ACTION_CALL);
                mIntent.setData(Uri.parse("tel:"+mData.shop_info.service_tel));
                startActivity(mIntent);
            } catch (Exception e) {
            }
        } else{
        }
    }

    @Override
    protected void onRequestSucceed(int what, String response) {
        switch (what) {
            case HTTP_WHAT_SHOP_INFO:
                refreshSucceed(response);
                break;
            case HTTP_WHAT_SHOP_POST_MESSAGE:
                submitCallback(response);
            default:
                super.onRequestSucceed(what, response);
                break;
        }
    }

    private static final int REQUEST_CODE_LOGIN_FOR_COLLECT_SHOP = 2;

    private void submitCallback(String response) {
        HttpResultManager.resolve(response, ResponseCommonModel.class, new HttpResultManager.HttpResultCallBack<ResponseCommonModel>() {
            @Override
            public void onSuccess(ResponseCommonModel back) {
                Toast.makeText(getActivity(), back.message, Toast.LENGTH_SHORT).show();
                refresh();
            }

            @Override
            public void onLogin() {
                openLoginActivityForResult(REQUEST_CODE_LOGIN_FOR_COLLECT_SHOP);
            }
        }, true);
    }

    private void openLoginActivityForResult(Integer i) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, i);
    }

    //给商家发送邀请消息
    private void postMesage(String msg) {
        if (mData != null) {
            CommonRequest request = new CommonRequest(Api.API_SHOP_SUPPORT_MESSAGE, HTTP_WHAT_SHOP_POST_MESSAGE, RequestMethod.POST);
            request.add("shop_id", mData.shop_info.shop_id);
            request.add("message", msg);
            addRequest(request);
        }
    }


    private void openShareActivity() {
        Intent intent = new Intent(mContext, ShareActivity.class);
        intent.putExtra(ShareActivity.SHARE_TYPE, ShareActivity.TYPE_SUPPORT_SHOP);
        intent.putStringArrayListExtra(ShareActivity.SHARE_DATA, shareData);
        startActivityForResult(intent,
                REQUEST_CODE_SHARE);
    }

    private void openInvitationDialog() {

        if (!mData.shop_info.exists) {

            final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
            dialog.setView(LayoutInflater.from(mContext).inflate(R.layout.alert_dialog, null));
            dialog.show();
            dialog.getWindow().setContentView(R.layout.alert_dialog);
            Button btnPositive = (Button) dialog.findViewById(R.id.btn_add);
            Button btnNegative = (Button) dialog.findViewById(R.id.btn_cancel);
            final EditText etContent = (EditText) dialog.findViewById(R.id.et_content);
            btnPositive.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    String str = etContent.getText().toString();
                    if (TextUtils.isEmpty(str)) {
                        Toast.makeText(getActivity(), "留言内容不能为空", Toast.LENGTH_SHORT).show();

                    } else {
                        postMesage(str);
                        dialog.dismiss();
                    }
                }
            });
            btnNegative.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });
        } else {
            Toast.makeText(getActivity(), "您已邀请过，请勿重复邀请！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.linearlayout_contact_number:
                showConfirmDialog(R.string.confirmDial, ViewType.VIEW_TYPE_CALL_PHONE_CONFIRM
                        .ordinal(), 0);
                break;
            case R.id.activity_shop_collection:
                //邀请商家
                openInvitationDialog();
                break;
            case R.id.activity_shop_share_two:
                //分享
                openShareActivity();
                break;
            case R.id.imageView_facade_img:
                openImageActivity(0);
                break;
            case R.id.imageView_card_img:
                openImageActivity(1);
                break;
            default:
                super.onClick(v);
        }
    }

    void openImageActivity(int index) {
        if (mData != null) {
            String facade_img = mData.recommend_shop_info.facade_img;
            String card_img = mData.recommend_shop_info.card_img;

            Intent intent = new Intent();
            intent.setClass(mContext, ViewOriginalImageActivity.class);
            intent.putStringArrayListExtra(Key.KEY_GOODS_IMAGE_LIST.getValue(), imageDate);
            if (TextUtils.isEmpty(facade_img)) {
                intent.putExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.getValue(), 0);
            } else if (TextUtils.isEmpty(card_img)) {
                intent.putExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.getValue(), 0);
            } else {
                intent.putExtra(Key.KEY_GOODS_IMAGE_SELECTED_INDEX.getValue(), index);
            }

            startActivity(intent);
        }
    }


    //用于ListView的监听类
    class MylistAdpter extends BaseAdapter {
        private Context ctx;
        private List<ResponseShopPrepareModel.DataBean.ListBean> data;
        private LayoutInflater inflater;

        //自定义构造函数，以取得List
        public MylistAdpter(Context ctx1, List<ResponseShopPrepareModel.DataBean.ListBean> list) {
            super();
            // TODO Auto-generated constructor stub
            data = list;
            this.ctx = ctx1;
            inflater = LayoutInflater.from(ctx);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ResponseShopPrepareModel.DataBean.ListBean object = data.get(position);
            //加载布局为一个视图
            View view = inflater.inflate(R.layout.item_shop_prepare_review, null);

            ImageView circleImageView = (ImageView) view.findViewById(R.id.circleImageView);
            TextView fragment_comment_user_name = (TextView) view.findViewById(R.id.fragment_comment_user_name);
            TextView comment_content = (TextView) view.findViewById(R.id.comment_content);
            TextView comment_time = (TextView) view.findViewById(R.id.comment_time);
            View fragment_goods_comment_layout = view.findViewById(R.id.fragment_goods_comment_layout);

            ImageLoader.displayImage(Utils.urlOfImage(object.headimg),circleImageView);
            fragment_comment_user_name.setText(object.user_name);
            comment_time.setText(Utils.times(object.add_time));

            comment_content.setMaxLines(200);
            comment_content.setText(object.message);

/*            if("0".equals(object.is_show)){
                fragment_goods_comment_layout.setVisibility(View.GONE);
            } else{
                fragment_goods_comment_layout.setVisibility(View.VISIBLE);
            }*/
            fragment_goods_comment_layout.setVisibility(View.VISIBLE);
            return view;
        }
    }

}
