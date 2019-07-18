package com.like.longshaolib.net.interceptor;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class ConnectionInterceptor extends BaseInterceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Connection", "close").build();
        return chain.proceed(request);
    }
}
