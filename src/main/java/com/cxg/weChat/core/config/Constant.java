package com.cxg.weChat.core.config;

/**
 * @Description 微信基础信息配置常量
 * @Author xg.chen
 * @Date 11:08 2019/8/29
**/
public class Constant {
    //meco公众号的appId和appSercet
    public static final String M_APP_ID = "wxe9bec4afeb0d2985";
    public static final String M_APP_SERCET = "be42b75c0ab7256af0cdef6192c16def";
    //xpp公众号的appId和appSercet
    public static final String X_APP_ID = "wx98e9f0f06ca7d370";
    public static final String X_APP_SERCET = "e904388a7894fcc7c77bd042f62ca201";
    //LFY公众号的appId
    public static String L_APP_APPID = "wx8d30f85a90b200f3";
    //请求地址
    public static String URL = "http://ssl.zjxpp.com";
    public static String TEST_URL = "http://10.3.25.11";

    //获取access_token的url
    public  static final String GET_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
}
