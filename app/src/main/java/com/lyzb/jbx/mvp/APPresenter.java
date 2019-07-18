package com.lyzb.jbx.mvp;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.like.longshaolib.base.BaseFragment;
import com.like.longshaolib.base.inter.IRequestListener;
import com.like.longshaolib.base.presenter.BaseStatusPresenter;
import com.like.longshaolib.dialog.fragment.AlertDialogFragment;
import com.like.longshaolib.net.helper.HttpResultStringFunc;
import com.like.longshaolib.net.helper.RequestSubscriber;
import com.like.longshaolib.net.inter.SubscriberOnNextListener;
import com.like.utilslib.other.NetUtil;
import com.lyzb.jbx.api.ApiFrotacy;
import com.lyzb.jbx.api.IAccountApi;
import com.lyzb.jbx.api.ICampaginApi;
import com.lyzb.jbx.api.ICardCilpApi;
import com.lyzb.jbx.api.ICircleApi;
import com.lyzb.jbx.api.ICommonApi;
import com.lyzb.jbx.api.IDynamicApi;
import com.lyzb.jbx.api.IFollowApi;
import com.lyzb.jbx.api.IMeApi;
import com.lyzb.jbx.api.IPhpCommonApi;
import com.lyzb.jbx.api.ISchoolApi;
import com.lyzb.jbx.api.ISendApi;
import com.lyzb.jbx.api.IStatisticsApi;
import com.lyzb.jbx.api.IWXApi;
import com.lyzb.jbx.inter.ISelectPictureListener;
import com.lyzb.jbx.util.JavaMD5Util;
import com.szy.yishopcustomer.Activity.LoginActivity;
import com.szy.yishopcustomer.Util.App;
import com.szy.yishopcustomer.Util.Oss;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/1.
 */

public class APPresenter<V> extends BaseStatusPresenter<V> {

    protected static final IAccountApi accountApi = ApiFrotacy.getAccountApiSingleton();
    protected static final ICommonApi commonApi = ApiFrotacy.getCommonApiSingleton();
    protected static final ICampaginApi campaginApi = ApiFrotacy.getCampaginApiSingleton();
    protected static final ICircleApi circleApi = ApiFrotacy.getCircleApiSingleton();
    protected static final ISchoolApi schoolApi = ApiFrotacy.getSchoolApiSingleton();
    protected static final ISendApi sendApi = ApiFrotacy.getSendApiSingleton();
    protected static final IMeApi meApi = ApiFrotacy.getMeApiSingleton();
    protected static final IFollowApi followApi = ApiFrotacy.getFollowApiSingleton();
    protected static final IDynamicApi dynamicApi = ApiFrotacy.getDynamicApiSingleton();
    protected static final ICardCilpApi cardCilpApi = ApiFrotacy.getCardCilpApiSingleton();
    protected static final IStatisticsApi statisticsApi = ApiFrotacy.getStatisticsApiSingleton();
    protected static final IPhpCommonApi phpCommonApi = ApiFrotacy.getPhpCommonApiSingleton();
    protected static final IWXApi wxapi = ApiFrotacy.getWXApi();
    protected static final String GET = "GET";
    protected static final String POST = "POST";
    protected static final String CONTENT_TYPE_FILE = "multipart/form-data";

    protected Map<String, Object> paramsDeal(Map<String, Object> params) {
        return params;
    }


    @Override
    protected <T> void onRequestData(IRequestListener<T> listener) {
        onRequestData(true, listener);
    }

    @Override
    protected <T> void onRequestData(boolean isShowDialog, final IRequestListener<T> listener) {
        if (!NetUtil.isConnected()) {
            ((BaseFragment) getView()).showView(BaseFragment.NONET_VIEW);
            return;
        }

        RequestSubscriber<T> requestSubscriber = new RequestSubscriber<>(context);
        requestSubscriber.isShowDilog(isShowDialog);
        requestSubscriber.bindCallbace(new SubscriberOnNextListener<T>() {
            @Override
            public void onNext(T s) {
                if (isViewAttached()) {
                    if (getView() instanceof BaseFragment) {
                        ((BaseFragment) getView()).showView(BaseFragment.MAIN_VIEW);
                    }
                    if (listener != null) {
                        listener.onSuccess(s);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    if (getView() instanceof BaseFragment) {
                        ((BaseFragment) getView()).showView(BaseFragment.ERROR_VIEW, e.getMessage());
                    }
                    if (!TextUtils.isEmpty(e.getMessage()) && e.getMessage().indexOf("登陆超时") != -1) {
                        AlertDialogFragment.newIntance()
                                .setContent("您的登录已超时，请重新登录!")
                                .setSureBtn("登录", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(((BaseFragment) getView()).getContext(), LoginActivity.class);
                                        ((BaseFragment) getView()).startActivity(intent);
                                    }
                                }).setCancleBtn(null)
                                .show(((BaseFragment) getView()).getFragmentManager(), "Login_Tag");
                    }
                    if (listener != null) {
                        if (e.getMessage() == null) {
                            listener.onFail("");
                        } else {
                            listener.onFail(e.getMessage());
                        }
                    }
                }
            }
        });

        listener.onCreateObservable()
                .map(new HttpResultStringFunc<T>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(requestSubscriber);
    }

    /**
     * 获取用户token
     *
     * @return
     */
    protected String getToken() {
        return App.getInstance().user_token == null ? "" : App.getInstance().user_token;
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    protected String getUserId() {
        return App.getInstance().userId == null ? "" : App.getInstance().userId;
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
        params.put("token", getToken());
        params.put("x-ca-key", "appScript");
        params.put("x-ca-timestamp", timeTemp);
        params.put("x-ca-nonce", mUuid);
        params.put("Accept", "*/*");
        params.put("user-agent", "android");
        params.put("Content-Type", "application/json; charset=utf-8");
        params.put("x-ca-signature", JavaMD5Util.toMD5(mBuffer.toString().trim()));
        return params;
    }

    /**
     * 上传图片
     *
     * @param fileUrl
     * @param listener
     */
    public void upFileLoad(String fileUrl, final ISelectPictureListener listener) {
        File file = new File(fileUrl);
        Oss.getInstance().updaLoadImage(context, getToken(), file.getAbsolutePath(), new Oss.OssListener() {
            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onSuccess(String url) {
                if (listener != null) {
                    listener.onSuccess(url);
                }
            }

            @Override
            public void onFailure() {
                if (listener != null) {
                    listener.onFail();
                }
            }
        });
    }

    /**
     * 呱呱的接口签名
     * 【针对于 ICommonApi getGuaGuaHotShop】接口
     *
     * @param map
     * @return
     */
    protected Map<String, String> getKeyValueByMd5(HashMap<String, String> map) {
        Map<String, String> objectMap = new HashMap<>();
        /* 获取键值 */
        Set<String> keys = map.keySet();
        //将所有键值放入lists容器
        ArrayList<String> lists = new ArrayList<>();
        for (String key : keys) {
            lists.add(key);
        }
        Object[] strings = lists.toArray();
        /* 对lists容器排序 */
        Arrays.sort(strings);
        /* 1. 取出value值 拼接 */
        StringBuffer stringA = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            objectMap.put((String) strings[i], map.get(strings[i]));
            if (map.get(strings[i]) != null && map.get(strings[i]).toString().length() > 0) {
                if (TextUtils.isEmpty(stringA.toString())) {
                    stringA.append(strings[i] + "=" + map.get(strings[i]).toString());
                } else {
                    stringA.append("&" + strings[i] + "=" + map.get(strings[i]).toString());
                }
            }
        }
        /* 2.拼接API密匙 */
        String stringSignTemp;
        if (stringA.length() > 0) {
            stringSignTemp = stringA.toString() + "&key=Aa123456";
        } else {
            stringSignTemp = "&key=Aa123456";
        }
        /* 3.MD5加密 */
        Log.e("json", stringSignTemp);
        String sign = MD5(stringSignTemp);
        /* 4.转换成大写 */
        String signValue = sign.toUpperCase();
        objectMap.put("sign", signValue);
        return objectMap;
    }

    /**
     * MD5加密
     *
     * @param pwd
     * @return
     */
    private final String MD5(String pwd) {
        //用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = pwd.getBytes();
            // 获得指定摘要算法的 MessageDigest对象，此处为MD5
            //MessageDigest类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //System.out.println(mdInst);
            //MD5 Message Digest from SUN, <initialized>
            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);
            //System.out.println(mdInst);
            //MD5 Message Digest from SUN, <in progress>
            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();
            //System.out.println(md);
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            //System.out.println(j);
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {   //  i = 0
                byte byte0 = md[i];  //95
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }
            //返回经过加密后的字符串
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
