package com.szy.yishopcustomer.core.model;

/**
 * Created by Smart on 2017/12/13.
 */

public class JWTModel {
    //jwt签发者
    public String iss;
    //jwt所面向的用户
    public String sub;
    //接收jwt的一方
    public String aud;
    // jwt的过期时间，这个过期时间必须要大于签发时间
    public String exp;
    // 定义在什么时间之前，该jwt都是不可用的.
    public String nbf;
    //jwt的签发时间
    public String iat;
    //jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
    public String jti;

}
