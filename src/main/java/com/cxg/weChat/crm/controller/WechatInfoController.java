package com.cxg.weChat.crm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cxg.weChat.core.config.Constant;
import com.cxg.weChat.core.utils.HttpUtil;
import com.cxg.weChat.core.utils.RandomStr;
import com.cxg.weChat.core.utils.Sha1;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 微信JS认证获取微信信息
 * @Author xg.chen
 * @Date 14:15 2020/5/15
 **/
@Controller
@RequestMapping("/api/userInfo/wx")
public class WechatInfoController {
    private static Logger logger = LoggerFactory.getLogger(WechatInfoController.class);
    @Autowired
    HttpSession session;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    ValueOperations<String, Object> valueOperations;

    /**jsapi_ticket*/
    private static String jsapiTicket;
    /**jsapi_ticket过期时间*/
    private static long jsapiTicketExpires;

    /**
     * 香飘飘新品测试H5首页
     * @return
     */
    @GetMapping("/xppTest")
    public String xppNewProductTest(){
        return "mecoIndex";
    }

    /**
     * @Description 获取微信JS授权
     * @Author xg.chen
     * @Date 10:10 2019/8/29
     **/
    @PostMapping("/config")
    @ResponseBody
    public String configWxJs(@RequestParam("appId") String appId,@RequestParam("url") String url,HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> configValue = new HashMap<String, Object>();
        /**设置jsapiTicket*/
        int setTicketRes = setJsapiTicket(request,appId);
        if( setTicketRes == 0 || jsapiTicket == null){
            logger.debug("get jsapiTicket failed!");
            return null;
        }
        String jsapi_ticket = jsapiTicket; //获取jsapi_ticket
        long timestamp = System.currentTimeMillis() / 1000;
        String nonceStr = RandomStr.createRandomString(16);
        /**加密字符串*/
        String str = "jsapi_ticket="+ jsapi_ticket +"&noncestr="+ nonceStr +"&timestamp="+ timestamp +"&url="+url;
        String signature = Sha1.encode(str);
        logger.debug("signature:" + signature);

        configValue.put("signature", signature);
        configValue.put("nonceStr", nonceStr);
        configValue.put("timestamp", timestamp);

        String config = JSON.toJSONString(configValue);
        return config;
    }

    /**
     * @Description 设置jsapiTicket
     * @Author xg.chen
     * @Date 13:32 2019/8/29
     **/
    private int setJsapiTicket(HttpServletRequest request,String appid){
        long curTime = System.currentTimeMillis();
        if(curTime < jsapiTicketExpires && jsapiTicket != null){
            logger.debug("jsapiTicket not timeout,don't try again!");
            return 1;
        }
        //获取access_token
        Map<String, Object> accessTokenRes = getAccessToken();
        if(accessTokenRes == null){
            logger.debug("3 get accessToken failed!");
            return 0;
        }
        String accessToken = (String) accessTokenRes.get("access_token");
        try{
            String res = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi");
            JSONObject result = JSON.parseObject(res);
            logger.debug("json{}：",result);
            if(result.getString("errmsg").equals("ok")){
                jsapiTicket = result.getString("ticket");
                jsapiTicketExpires = curTime + 7200*1000;
            }
        }catch (Exception e){
            logger.debug("4 error on get JsapiTicket" + e);
            return 0;
        }
        return 1;
    }

    /**
     * @Description token就重新获取
     * @Author xg.chen
     * @Date 13:41 2019/8/29
     **/
    private Map<String, Object> getAccessToken () {
        String url = Constant.GET_URL+"&appid="+Constant.X_APP_ID+"&secret="+Constant.X_APP_SERCET;
        //http发送请求
        String data = HttpUtil.get(url);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> json = null;
        try{
            json = mapper.readValue(data, Map.class);
            logger.debug("json{}：",json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
