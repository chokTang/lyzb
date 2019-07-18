package com.lyzb.jbx.dialog;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.longshaolib.adapter.BaseRecyleAdapter;
import com.like.longshaolib.adapter.rvhelper.OnRecycleItemClickListener;
import com.like.longshaolib.app.LongshaoAPP;
import com.like.longshaolib.app.config.ConfigType;
import com.like.longshaolib.dialog.BaseDialogFragment;
import com.like.longshaolib.net.convert.BodyConverterFactory;
import com.like.longshaolib.net.helper.HttpResultStringFunc;
import com.like.longshaolib.net.helper.RequestSubscriber;
import com.like.longshaolib.net.inter.SubscriberOnNextListener;
import com.like.utilslib.json.GSONUtil;
import com.lyzb.jbx.R;
import com.lyzb.jbx.adapter.dialog.CitySelectAdapter;
import com.lyzb.jbx.api.ICommonApi;
import com.lyzb.jbx.inter.ISelectDialogListener;
import com.lyzb.jbx.model.common.CityDialogModel;
import com.lyzb.jbx.util.JavaMD5Util;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 选择city接口
 */
public class CityRengionDilaog extends BaseDialogFragment implements View.OnClickListener {

    private ImageView img_close;
    private TextView first_tv, second_tv, three_tv;
    private RecyclerView recycle_region;
    private CitySelectAdapter mAdapter;

    private Retrofit retrofit;

    private int leven = 1;
    private CityDialogModel first, second, three;
    private ISelectDialogListener selectDialogListener;

    public CityRengionDilaog setSelectDialogListener(ISelectDialogListener selectDialogListener) {
        this.selectDialogListener = selectDialogListener;
        return this;
    }

    @Override
    public Object getResId() {
        return R.layout.dialog_city_rengion_layout;
    }

    @Override
    public void initView() {
        img_close = findViewById(R.id.img_close);
        first_tv = findViewById(R.id.first_tv);
        second_tv = findViewById(R.id.second_tv);
        three_tv = findViewById(R.id.three_tv);
        recycle_region = findViewById(R.id.recycle_region);

        first_tv.setOnClickListener(this);
        second_tv.setOnClickListener(this);
        three_tv.setOnClickListener(this);
        img_close.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mAdapter = new CitySelectAdapter(getContext(), null);
        mAdapter.setLayoutManager(recycle_region);
        recycle_region.setAdapter(mAdapter);

        recycle_region.addOnItemTouchListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(BaseRecyleAdapter adapter, View view, int position) {
                if (leven == 3) {//回调
                    three = mAdapter.getPositionModel(position);
                    if (selectDialogListener != null) {
                        selectDialogListener.onSelect(three);
                    }
                    onDailogDismiss();
                } else {
                    intHeaderTv(mAdapter.getPositionModel(position));
                    onRequest(mAdapter.getPositionModel(position).getId());
                    leven++;
                }
            }
        });

        OkHttpClient.Builder mHttpBuilder = new OkHttpClient.Builder();
        mHttpBuilder.connectTimeout(5, TimeUnit.SECONDS);//设置超时时间
        mHttpBuilder.retryOnConnectionFailure(false);//连接超时了是否重新连接

        retrofit = new Retrofit.Builder()
                .client(mHttpBuilder.build())
                .addConverterFactory(BodyConverterFactory.create())//设置返回来为String类型的数据
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(LongshaoAPP.getConfiguration(ConfigType.API_HOST).toString() + "lbsapi/")
                .build();

        //初始化
        onRequest("");
        first_tv.setSelected(true);
        first_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public int getViewWidth() {
        return -1;
    }

    @Override
    public int getViewHeight() {
        return -2;
    }

    @Override
    public int getViewGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public int getAnimationType() {
        return FORM_BOTTOM_TO_BOTTOM;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_tv:
                leven = 1;
                second = null;
                three = null;
                first = null;
                first_tv.setSelected(true);
                second_tv.setSelected(false);
                three_tv.setSelected(false);
                second_tv.setText("");
                three_tv.setText("");
                onRequest("");
                break;
            case R.id.second_tv:
                leven = 2;
                first_tv.setSelected(false);
                second_tv.setSelected(true);
                three_tv.setSelected(false);
                second = null;
                three = null;
                three_tv.setText("");
                onRequest(first.getId());
                break;
            case R.id.img_close:
                onDailogDismiss();
                break;
        }
    }

    /**
     * 请求网络
     *
     * @param regionId
     */
    private void onRequest(final String regionId) {
        RequestSubscriber<String> requestSubscriber = new RequestSubscriber<>(getContext());
        requestSubscriber.isShowDilog(false);
        requestSubscriber.bindCallbace(new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
                JSONObject resultObject = JSONUtil.toJsonObject(s);
                JSONArray dataArray = JSONUtil.getJsonArray(resultObject, "data");
                List<CityDialogModel> list = GSONUtil.getEntityList(dataArray.toString(), CityDialogModel.class);
                if (TextUtils.isEmpty(regionId)) {
                    first = list.get(0);
                    first_tv.setText(first.getName());
                    mAdapter.setCityName(first.getName());
                }
                mAdapter.update(list);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getContext(), "请求出错", Toast.LENGTH_SHORT).show();
            }
        });

        retrofit.create(ICommonApi.class).getRecomendDynamicList(getHeadersMap("GET", "/lbs/gs/distributor/getOptChildren")
                , regionId)
                .map(new HttpResultStringFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(requestSubscriber);
    }

    private void intHeaderTv(CityDialogModel model) {
        if (leven == 1) {
            first = model;
            first_tv.setVisibility(View.VISIBLE);
            first_tv.setText(model.getName());
            second_tv.setSelected(true);
            first_tv.setSelected(false);
            second_tv.setText("请选择");
            second_tv.setVisibility(View.VISIBLE);
            three_tv.setVisibility(View.GONE);
        } else if (leven == 2) {
            second = model;
            first_tv.setVisibility(View.VISIBLE);
            second_tv.setText(second.getName());
            second_tv.setSelected(false);
            three_tv.setSelected(true);
            three_tv.setText("请选择");
            second_tv.setVisibility(View.VISIBLE);
            three_tv.setVisibility(View.VISIBLE);
        } else {
            three = model;
            first_tv.setVisibility(View.VISIBLE);
            three_tv.setSelected(true);
            second_tv.setVisibility(View.VISIBLE);
            three_tv.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 封装的java 统一的请求头部信息
     *
     * @param method
     * @param mUrl
     * @return
     */
    protected Map<String, String> getHeadersMap(String method, String mUrl) {
        String mUuid = UUID.randomUUID().toString();
        String timeTemp = System.currentTimeMillis() + "";

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(method).append("\n");
        stringBuffer.append("*/*").append("\n");
        stringBuffer.append("application/json; charset=utf-8").append("\n");
        stringBuffer.append("x-ca-nonce:" + mUuid).append("\n");
        stringBuffer.append("x-ca-timestamp:" + timeTemp).append("\n");
        stringBuffer.append(mUrl);
        StringBuffer mBuffer = new StringBuffer();
        mBuffer.append(JavaMD5Util.toMD5(stringBuffer.toString().trim())).append("$lw1XRJhQ#ys2q");

        Map<String, String> params = new HashMap<>();
        params.put("token", App.getInstance().user_token == null ? "" : App.getInstance().user_token);
        params.put("x-ca-key", "appScript");
        params.put("x-ca-timestamp", timeTemp);
        params.put("x-ca-nonce", mUuid);
        params.put("Accept", "*/*");
        params.put("Content-Type", "application/json; charset=utf-8");
        params.put("x-ca-signature", JavaMD5Util.toMD5(mBuffer.toString().trim()));
        return params;
    }
}
