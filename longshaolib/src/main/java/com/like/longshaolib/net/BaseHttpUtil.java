package com.like.longshaolib.net;

import com.like.longshaolib.app.LongshaoAPP;
import com.like.longshaolib.app.config.ConfigType;
import com.like.longshaolib.net.convert.BodyConverterFactory;
import com.like.longshaolib.net.convert.GsonNullConverterFactory;
import com.like.longshaolib.net.interceptor.ConnectionInterceptor;
import com.like.longshaolib.net.interceptor.RetrofitLogInterceptor;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by longshao on 2017/3/12.
 */

public class BaseHttpUtil {

    private static final int DEFAULT_TIMEOUT = 60;//默认超时时间
    private static final int CACHE_SIZE = 10 * 1024 * 1024;//10M
    private Retrofit retrofit1, retrofit, retrofit2, retrofit3;
    private OkHttpClient.Builder mHttpBuilder;

    private static class BaseHolder {
        private static final BaseHttpUtil INTANCE = new BaseHttpUtil();
    }

    /**
     * 默认超时时间
     */
    private BaseHttpUtil() {
        mHttpBuilder = new OkHttpClient.Builder();
        mHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);//设置超时时间
        mHttpBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);//设置读取超时时间
        mHttpBuilder.retryOnConnectionFailure(false);//连接超时了是否重新连接
        final ArrayList<Interceptor> interceptors = LongshaoAPP.getConfiguration(ConfigType.INTERCEPTOR);
        if (interceptors != null && !interceptors.isEmpty()) {
            for (Interceptor interceptor : interceptors) {
                mHttpBuilder.addInterceptor(interceptor);
            }
        }
        mHttpBuilder.addInterceptor(new ConnectionInterceptor());
        mHttpBuilder.addInterceptor(new RetrofitLogInterceptor());

//        /*设置缓存大小*/
//        File httpCacheDirectory = new File(UtilApp.getIntance().getApplicationContext().getCacheDir(), "cacheresponse");
//        Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);
//        mHttpBuilder.cache(cache);
//
//        /*处理缓存策略*/
//        mHttpBuilder.addInterceptor(new CacheInterceptor());
//        mHttpBuilder.addNetworkInterceptor(new NetworkInterceptor());

        retrofit = new Retrofit.Builder()
                .client(mHttpBuilder.build())
                .addConverterFactory(BodyConverterFactory.create())//设置返回来为String类型的数据
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(LongshaoAPP.getConfiguration(ConfigType.API_HOST).toString() + "lbsapi/")
                .build();
    }

    public static final BaseHttpUtil getIntance() {
        return BaseHolder.INTANCE;
    }

    /**
     * 主要是针对于携带lbsapi/路径的java接口
     * 备注：Reponse<String>数据类型的封装
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> T createReqonseHaveLbsApi(Class<T> t) {
        return retrofit.create(t);
    }

    /**
     * 主要是针对于没有携带lbsapi/路径的java接口
     * 备注：Reponse<String>数据类型的封装
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> T createReqonseNoLbsapiApi(Class<T> t) {
        if (retrofit1 == null) {
            retrofit1 = new Retrofit.Builder()
                    .client(mHttpBuilder.build())
                    .addConverterFactory(BodyConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(LongshaoAPP.getConfiguration(ConfigType.API_HOST).toString())
                    .build();
        }
        return retrofit1.create(t);
    }

    /**
     * 主要是针对于没有携带lbsapi/路径的php接口
     * 备注：返回来为HttpResut<T>数据类型的封装
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> T createHttpResutApi(Class<T> t) {
        if (retrofit2 == null) {
            retrofit2 = new Retrofit.Builder()
                    .client(mHttpBuilder.build())
                    .addConverterFactory(GsonNullConverterFactory.create())//设置返回来为Gson类型的数据
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(LongshaoAPP.getConfiguration(ConfigType.API_HOST).toString())
                    .build();
        }
        return retrofit2.create(t);
    }

    /**
     * 请求微信openid用
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> T createWXHttpResutApi(Class<T> t) {
        if (retrofit3 == null) {
            retrofit3 = new Retrofit.Builder()
                    .client(mHttpBuilder.build())
                    .addConverterFactory(GsonNullConverterFactory.create())//设置返回来为Gson类型的数据
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://api.weixin.qq.com/")
                    .build();
        }
        return retrofit3.create(t);
    }
}
