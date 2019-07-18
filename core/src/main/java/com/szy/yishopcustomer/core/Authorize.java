package com.szy.yishopcustomer.core;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.szy.yishopcustomer.core.model.BaseModel;
import com.szy.yishopcustomer.core.model.JWTModel;
import com.szy.yishopcustomer.core.rsa.RSAEncrypt;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.StringRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Smart on 2017/12/12.
 */

public class Authorize {
    private static final String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0VO+8OvdWNXY9Vfl1gelEnjCua942YZUAQwZTKdUfucf7QRDUYByMkrLf2/5s3ggGdv/0aNKzNpNYSKpeAF8NvCw5HnLOxKnYoFdPhlUjXjfVtPnUf+wCtcCewoM+CsH/YTKZOiNQtbXvWG1c37r1fFMS7lKGedv4Lm71rtpf+wIDAQAB";
    private RequestQueue mRequestQueue;
    private Context mContext;

    Authorize(Context context){
        mContext = context;
        mRequestQueue = NoHttp.newRequestQueue();
    }

    //判断是否授权
    public void isAuthorize(final OnAuthorizeState listener){
        listener.onSuccess();

//        SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(mContext, "security_prefs", Context.MODE_PRIVATE);
//        final String token = securitySharedPreference.getString("token","");
//
//        if(TextUtils.isEmpty(token)) {
//            //如果本地没有token，就从网络获取
//
//            getSignKey(new OnResponseListener<String>() {
//                @Override
//                public void onStart(int what) {
//
//                }
//
//                @Override
//                public void onSucceed(int what, Response<String> response) {
//                    BaseModel baseModel = JSON.parseObject(response.get(),BaseModel.class);
//                    if(baseModel.code == 0) {
//                        validate(baseModel.data,listener);
//                    }
//                }
//
//                @Override
//                public void onFailed(int what, Response<String> response) {
//
//                }
//
//                @Override
//                public void onFinish(int what) {
//
//                }
//            });
//
//        } else {
//            validate(token,listener);
//        }
    }

    private void getSignKey(OnResponseListener responseListener){
        Request<String> req = NoHttp.createStringRequest("http://47.93.150.171:8080/index/Api/test?package=" + mContext.getPackageName());

        mRequestQueue.add(5, req, responseListener);
    }

    private void validate(String token,OnAuthorizeState listener) {
        SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(mContext, "security_prefs", Context.MODE_PRIVATE);
        SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();

        if(validate(token)) {
            securityEditor.putString("token", token);
            listener.onSuccess();
        } else {
            securityEditor.clear();
            listener.onError();
        }

        listener.onFinished();
        securityEditor.apply();
    }

    private boolean validate(String token){
        //验证token是否经过授权
        if(TextUtils.isEmpty(token)) return false;

        try{
            //解密json信息
            String decodeJWT = new String(RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(publickey), Base64.decode(token,Base64.URL_SAFE)));

            JWTModel jwtModel = JSON.parseObject(decodeJWT, JWTModel.class);
            if(!"www.68dsw.com".equals(jwtModel.iss)) return false;
            //这块可以用来判断是哪个app，暂时都判断包名了
//            if(!mContext.getPackageName().equals(jwtModel.sub)) return false;
            if(!mContext.getPackageName().equals(jwtModel.aud)) return false;

//            if(!"www.68dsw.com".equals(jwtModel.iss)) return false;
//            if(!"www.68dsw.com".equals(jwtModel.iss)) return false;
//            if(!"www.68dsw.com".equals(jwtModel.iss)) return false;

            return true;

        }catch (Exception e) {

        }
        return false;
    }


    public interface OnAuthorizeState{
        void onSuccess();
        void onError();
        void onFinished();
    }

    private class SecuritySharedPreference implements SharedPreferences {

        private SharedPreferences mSharedPreferences;
        private Context mContext;

        /**
         * constructor
         * @param context should be ApplicationContext not activity
         * @param name file name
         * @param mode context mode
         */
        public SecuritySharedPreference(Context context, String name, int mode){
            mContext = context;
            if (TextUtils.isEmpty(name)){
                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            } else {
                mSharedPreferences =  context.getSharedPreferences(name, mode);
            }

        }

        @Override
        public Map<String, String> getAll() {
            final Map<String, ?> encryptMap = mSharedPreferences.getAll();
            final Map<String, String> decryptMap = new HashMap<>();
            for (Map.Entry<String, ?> entry : encryptMap.entrySet()){
                Object cipherText = entry.getValue();
                if (cipherText != null){
                    decryptMap.put(entry.getKey(), entry.getValue().toString());
                }
            }
            return decryptMap;
        }

        /**
         * encrypt function
         * @return cipherText base64
         */
        private String encryptPreference(String plainText){
            return EncryptUtil.getInstance(mContext).encrypt(plainText);
        }

        /**
         * decrypt function
         * @return plainText
         */
        private String decryptPreference(String cipherText){
            return EncryptUtil.getInstance(mContext).decrypt(cipherText);
        }

        @Override
        public String getString(String key, String defValue) {
            final String encryptValue = mSharedPreferences.getString(encryptPreference(key), null);
            return encryptValue == null ? defValue : decryptPreference(encryptValue);
        }

        @Override
        public Set<String> getStringSet(String key, Set<String> defValues) {
            final Set<String> encryptSet = mSharedPreferences.getStringSet(encryptPreference(key), null);
            if (encryptSet == null){
                return defValues;
            }
            final Set<String> decryptSet = new HashSet<>();
            for (String encryptValue : encryptSet){
                decryptSet.add(decryptPreference(encryptValue));
            }
            return decryptSet;
        }

        @Override
        public int getInt(String key, int defValue) {
            final String encryptValue = mSharedPreferences.getString(encryptPreference(key), null);
            if (encryptValue == null) {
                return defValue;
            }
            return Integer.parseInt(decryptPreference(encryptValue));
        }

        @Override
        public long getLong(String key, long defValue) {
            final String encryptValue = mSharedPreferences.getString(encryptPreference(key), null);
            if (encryptValue == null) {
                return defValue;
            }
            return Long.parseLong(decryptPreference(encryptValue));
        }

        @Override
        public float getFloat(String key, float defValue) {
            final String encryptValue = mSharedPreferences.getString(encryptPreference(key), null);
            if (encryptValue == null) {
                return defValue;
            }
            return Float.parseFloat(decryptPreference(encryptValue));
        }

        @Override
        public boolean getBoolean(String key, boolean defValue) {
            final String encryptValue = mSharedPreferences.getString(encryptPreference(key), null);
            if (encryptValue == null) {
                return defValue;
            }
            return Boolean.parseBoolean(decryptPreference(encryptValue));
        }

        @Override
        public boolean contains(String key) {
            return mSharedPreferences.contains(encryptPreference(key));
        }

        @Override
        public SecuritySharedPreference.SecurityEditor edit() {
            return new SecuritySharedPreference.SecurityEditor();
        }

        @Override
        public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
            mSharedPreferences.registerOnSharedPreferenceChangeListener(listener);
        }

        @Override
        public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
            mSharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
        }

        /**
         * 处理加密过渡
         */
        public void handleTransition(){
            Map<String, ?> oldMap = mSharedPreferences.getAll();
            Map<String, String> newMap = new HashMap<>();
            for (Map.Entry<String, ?> entry : oldMap.entrySet()){
                newMap.put(encryptPreference(entry.getKey()), encryptPreference(entry.getValue().toString()));
            }
            Editor editor = mSharedPreferences.edit();
            editor.clear().commit();
            for (Map.Entry<String, String> entry : newMap.entrySet()){
                editor.putString(entry.getKey(), entry.getValue());
            }
            editor.commit();
        }

        /**
         * 自动加密Editor
         */
        final class SecurityEditor implements Editor {

            private Editor mEditor;

            /**
             * constructor
             */
            private SecurityEditor(){
                mEditor = mSharedPreferences.edit();
            }

            @Override
            public Editor putString(String key, String value) {
                mEditor.putString(encryptPreference(key), encryptPreference(value));
                return this;
            }

            @Override
            public Editor putStringSet(String key, Set<String> values) {
                final Set<String> encryptSet = new HashSet<>();
                for (String value : values){
                    encryptSet.add(encryptPreference(value));
                }
                mEditor.putStringSet(encryptPreference(key), encryptSet);
                return this;
            }

            @Override
            public Editor putInt(String key, int value) {
                mEditor.putString(encryptPreference(key), encryptPreference(Integer.toString(value)));
                return this;
            }

            @Override
            public Editor putLong(String key, long value) {
                mEditor.putString(encryptPreference(key), encryptPreference(Long.toString(value)));
                return this;
            }

            @Override
            public Editor putFloat(String key, float value) {
                mEditor.putString(encryptPreference(key), encryptPreference(Float.toString(value)));
                return this;
            }

            @Override
            public Editor putBoolean(String key, boolean value) {
                mEditor.putString(encryptPreference(key), encryptPreference(Boolean.toString(value)));
                return this;
            }

            @Override
            public Editor remove(String key) {
                mEditor.remove(encryptPreference(key));
                return this;
            }

            /**
             * Mark in the editor to remove all values from the preferences.
             * @return this
             */
            @Override
            public Editor clear() {
                mEditor.clear();
                return this;
            }

            /**
             * 提交数据到本地
             * @return Boolean 判断是否提交成功
             */
            @Override
            public boolean commit() {

                return mEditor.commit();
            }

            /**
             * Unlike commit(), which writes its preferences out to persistent storage synchronously,
             * apply() commits its changes to the in-memory SharedPreferences immediately but starts
             * an asynchronous commit to disk and you won't be notified of any failures.
             */
            @Override
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            public void apply() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    mEditor.apply();
                } else {
                    commit();
                }
            }
        }


    }

    private static class EncryptUtil {

        private String key;
        private static EncryptUtil instance;

        private EncryptUtil(Context context){
            String serialNo = getDeviceSerialNumber(context);
            //加密随机字符串生成AES key
            key = SHA(serialNo + "#$ERDTS$D%F^Gojikbh").substring(0, 16);
        }

        /**
         * 单例模式
         * @param context context
         * @return
         */
        public static EncryptUtil getInstance(Context context){
            if (instance == null){
                synchronized (EncryptUtil.class){
                    if (instance == null){
                        instance = new EncryptUtil(context);
                    }
                }
            }

            return instance;
        }

        /**
         * Gets the hardware serial number of this device.
         *
         * @return serial number or Settings.Secure.ANDROID_ID if not available.
         */
        @SuppressLint("HardwareIds")
        private String getDeviceSerialNumber(Context context) {
            // We're using the Reflection API because Build.SERIAL is only available
            // since API Level 9 (Gingerbread, Android 2.3).
            try {
                String deviceSerial = (String) Build.class.getField("SERIAL").get(null);
                if (TextUtils.isEmpty(deviceSerial)) {
                    return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                } else {
                    return deviceSerial;
                }
            } catch (Exception ignored) {
                // Fall back  to Android_ID
                return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }


        /**
         * SHA加密
         * @param strText 明文
         * @return
         */
        private String SHA(final String strText){
            // 返回值
            String strResult = null;
            // 是否是有效字符串
            if (strText != null && strText.length() > 0){
                try{
                    // SHA 加密开始
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    // 传入要加密的字符串
                    messageDigest.update(strText.getBytes());
                    byte byteBuffer[] = messageDigest.digest();
                    StringBuffer strHexString = new StringBuffer();
                    for (int i = 0; i < byteBuffer.length; i++){
                        String hex = Integer.toHexString(0xff & byteBuffer[i]);
                        if (hex.length() == 1){
                            strHexString.append('0');
                        }
                        strHexString.append(hex);
                    }
                    strResult = strHexString.toString();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }

            return strResult;
        }


        /**
         * AES128加密
         * @param plainText 明文
         * @return
         */
        public String encrypt(String plainText) {
            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
                cipher.init(Cipher.ENCRYPT_MODE, keyspec);
                byte[] encrypted = cipher.doFinal(plainText.getBytes());
                return Base64.encodeToString(encrypted, Base64.NO_WRAP);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * AES128解密
         * @param cipherText 密文
         * @return
         */
        public String decrypt(String cipherText) {
            try {
                byte[] encrypted1 = Base64.decode(cipherText, Base64.NO_WRAP);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
                cipher.init(Cipher.DECRYPT_MODE, keyspec);
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
