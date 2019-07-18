package com.szy.yishopcustomer.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lixk
 * @description url工具类
 * @date 2018/9/26 9:58
 */
public class UrlUtil {

    public static class UrlEntity {
        /**
         * 基础url
         */
        public String baseUrl;
        /**
         * url参数
         */
        public Map<String, String> params;
    }

    /**
     * 解析url
     *
     * @param url
     * @return
     */
    public static UrlEntity parse(String url) {
        UrlEntity entity = new UrlEntity();
        if (url == null) {
            return entity;
        }
        url = url.trim();
        if (url.equals("")) {
            return entity;
        }
//		String[] urlParts = url.split("\\?");
        String[] urlParts = new String[]{url.substring(0, url.indexOf("?")),
                url.substring(url.indexOf("?") + 1)};
        entity.baseUrl = urlParts[0];
        //没有参数
        if (urlParts.length == 1) {
            return entity;
        }
        //有参数
        String[] params = urlParts[1].split("&");
        entity.params = new HashMap<>();
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length>1){
                entity.params.put(keyValue[0], keyValue[1]);
            }else if(keyValue.length>0){
                entity.params.put(keyValue[0],"");
            }
        }

        return entity;
    }


}

