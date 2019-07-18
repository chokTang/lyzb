package com.szy.yishopcustomer.Util;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.baidu.speech.utils.MD5Util;
import com.szy.yishopcustomer.Activity.im.ImCommonActivity;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Oss {
    private static Oss oss;
    private static RequestQueue mRequestQueue;
    private Context mContext;


    public interface OssListener {
        public void onProgress(int progress);

        public void onSuccess(String url);

        public void onFailure();
    }

    private Oss() {
    }

    public static Oss getInstance() {
        if (oss == null) {
            oss = new Oss();
            mRequestQueue = NoHttp.newRequestQueue(4);
        }
        return oss;
    }

    /**
     * 上传文件
     *
     * @param context
     * @param path
     * @param ossListener
     */
    public void updaLoadFile(Context context, String user_token, final String path, final OssListener ossListener) {
        mContext = context;
        Request<String> request = NoHttp.createStringRequest(Api.API_CITYLIFE_UPIMG, RequestMethod.GET);
        request.addHeader("token", user_token);
        mRequestQueue.add(0x2533, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int i) {

            }

            @Override
            public void onSucceed(int i, Response<String> response) {
                authenFileSuccess(response.get(), path, ossListener);
            }

            @Override
            public void onFailed(int i, Response<String> response) {

            }

            @Override
            public void onFinish(int i) {

            }
        });
    }

    public void upLoadSearImg(Context context, final String path, final OssListener ossListener) {
        mContext = context;
        Request<String> request = NoHttp.createStringRequest(Api.API_CITYLIFE_UPIMG, RequestMethod.GET);
        String user_token = (String) SharedPreferencesUtils.getParam(context, Key.USER_INFO_TOKEN.getValue(), "");
        request.addHeader("token", user_token);
        mRequestQueue.add(0x2533, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int i) {

            }

            @Override
            public void onSucceed(int i, Response<String> response) {
                authenSearchFileSuccess(response.get(), path, ossListener);
            }

            @Override
            public void onFailed(int i, Response<String> response) {

            }

            @Override
            public void onFinish(int i) {

            }
        });
    }

    private void authenSearchFileSuccess(final String response, String fileUrl, final OssListener ossListener) {
        try {
            final OssAuthenResponse authenResponse = JSON.parseObject(response, OssAuthenResponse.class);
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(authenResponse.getAccessKeyId(), authenResponse.getAccessKeySecret(), authenResponse.getSecurityToken());
            OSS oss = new OSSClient(mContext, authenResponse.getEndpoint(), credentialProvider);
            PutObjectRequest put = new PutObjectRequest(authenResponse.getBucketName(), authenResponse.getRootPath() + "/" + getSearchFileUrl(fileUrl), fileUrl);
            // 异步上传时可以设置进度回调
            put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                @Override
                public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                    int progress = (int) (100 * currentSize / totalSize);
                    if (ossListener != null)
                        ossListener.onProgress(progress);
                }
            });
            OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    if (ossListener != null)
                        ossListener.onSuccess(authenResponse.getImagePath() + "/" + request.getObjectKey());
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                    if (ossListener != null)
                        ossListener.onFailure();
                }
            });
        } catch (Exception e) {

        }
    }


    private void authenFileSuccess(final String response, String fileUrl, final OssListener ossListener) {
        try {
            final OssAuthenResponse authenResponse = JSON.parseObject(response, OssAuthenResponse.class);
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(authenResponse.getAccessKeyId(), authenResponse.getAccessKeySecret(), authenResponse.getSecurityToken());
            OSS oss = new OSSClient(mContext, authenResponse.getEndpoint(), credentialProvider);
            PutObjectRequest put = new PutObjectRequest(authenResponse.getBucketName(), authenResponse.getRootPath() + "/" + getFileUrl(fileUrl), fileUrl);
            // 异步上传时可以设置进度回调
            put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                @Override
                public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                    int progress = (int) (100 * currentSize / totalSize);
                    if (ossListener != null)
                        ossListener.onProgress(progress);
                }
            });
            OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    if (ossListener != null)
                        ossListener.onSuccess(authenResponse.getImagePath() + "/" + request.getObjectKey());
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                    if (ossListener != null)
                        ossListener.onFailure();
                }
            });
        } catch (Exception e) {

        }
    }

    /**
     * 获取文件名地址
     *
     * @param url
     * @return
     */
    private String getSearchFileUrl(String url) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("search");
        if (App.getInstance().isLogin()) {
            stringBuffer.append("/");
            stringBuffer.append(App.getInstance().userId);
        }
        stringBuffer.append("/");
        stringBuffer.append(getStringRandom(18)).append(".").append(getExtension(url));
        return stringBuffer.toString();
    }

    /**
     * 获取文件名地址
     *
     * @param url
     * @return
     */
    private String getFileUrl(String url) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        return "voice/" + simpleDateFormat.format(new Date()) + "/" + getStringRandom(10) + "." + getExtension(url);
    }

    //获取文件的后缀名
    public static String getExtension(String filePath) {
        String suffix = "";
        final File file = new File(filePath);
        final String name = file.getName();
        final int idx = name.lastIndexOf('.');
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }

    public void updaLoadImage(Context context, final String token, final String imgPath, final OssListener ossListener) {
        mContext = context;
        Request<String> request = NoHttp.createStringRequest(Api.API_CITYLIFE_UPIMG, RequestMethod.GET);
        request.addHeader("token", token);
        mRequestQueue.add(HttpWhat.HTPP_CL_UPIMG.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int i) {

            }

            @Override
            public void onSucceed(int i, Response<String> response) {
                authenSuccess(response.get(), imgPath, ossListener);
            }

            @Override
            public void onFailed(int i, Response<String> response) {
                if (ossListener!=null){
                    ossListener.onFailure();
                }
            }

            @Override
            public void onFinish(int i) {

            }
        });
    }

    private void authenSuccess(final String response, String imgPath, final OssListener ossListener) {
        final OssAuthenResponse authenResponse = JSON.parseObject(response, OssAuthenResponse.class);
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(authenResponse.getAccessKeyId(), authenResponse.getAccessKeySecret(), authenResponse.getSecurityToken());
        OSS oss = new OSSClient(mContext, authenResponse.getEndpoint(), credentialProvider);
        PutObjectRequest put = null;
        if (imgPath.endsWith(".mp4")) {//mp4文件
            put = new PutObjectRequest(authenResponse.getBucketName(), authenResponse.getRootPath() + "/" + getVideoFile(), imgPath);
        } else if (imgPath.endsWith(".mp3")) {
            put = new PutObjectRequest(authenResponse.getBucketName(), authenResponse.getRootPath() + "/" + getVoiceFile(), imgPath);
        } else {
            put = new PutObjectRequest(authenResponse.getBucketName(), authenResponse.getRootPath() + "/" + getImageFile(), imgPath);
        }
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                int progress = (int) (100 * currentSize / totalSize);
                if (ossListener != null)
                    ossListener.onProgress(progress);
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if (ossListener != null)
                    ossListener.onSuccess(authenResponse.getImagePath() + "/" + request.getObjectKey());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                if (ossListener != null)
                    ossListener.onFailure();
            }
        });
    }

    private String getImageFile() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        return "client/" + simpleDateFormat.format(new Date()) + "/" + getStringRandom(10) + ".jpg";
    }

    private String getVideoFile() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        return "client/" + simpleDateFormat.format(new Date()) + "/" + getStringRandom(10) + ".mp4";
    }

    private String getVoiceFile() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        return "client/" + simpleDateFormat.format(new Date()) + "/" + getStringRandom(10) + ".mp3";
    }

    //生成随机数字和字母,
    public String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}
