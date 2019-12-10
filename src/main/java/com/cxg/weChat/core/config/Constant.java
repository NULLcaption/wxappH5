package com.cxg.weChat.core.config;

/**
 * @Description 微信基础信息配置常量
 * @Author xg.chen
 * @Date 11:08 2019/8/29
**/
public class Constant {
    //meco公众号的appId和appSercet
    public static final String M_APP_ID = "";
    public static final String M_APP_SERCET = "";
    //xpp公众号的appId和appSercet
    public static final String X_APP_ID = "";
    public static final String X_APP_SERCET = "";
    //LFY公众号的appId
    public static String L_APP_APPID = "";
    //请求地址
    public static String URL = "";
    public static String TEST_URL = "http://10.3.25.11";

    //获取access_token的url
    public  static final String GET_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
}
