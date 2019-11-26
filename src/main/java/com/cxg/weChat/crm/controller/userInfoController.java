package com.cxg.weChat.crm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cxg.weChat.core.config.Constant;
import com.cxg.weChat.core.utils.*;
import com.cxg.weChat.crm.pojo.PlanActivityDo;
import com.cxg.weChat.crm.pojo.UserInfoDo;
import com.cxg.weChat.crm.pojo.WxUserInfoDo;
import com.cxg.weChat.crm.service.UserInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.open.api.WxOpenService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 后台基础类
 * @Author: Cheney Master
 * @CreateDate: 2018/11/5 9:53
 * @Version: 1.0
 */
@Controller
@RequestMapping("/api/userInfo/admin")
public class userInfoController {
    private static Logger logger = LoggerFactory.getLogger(userInfoController.class);

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    WxOpenService wxOpenService;

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
        String url = Constant.GET_URL+"&appid="+Constant.M_APP_ID+"&secret="+Constant.M_APP_SERCET;
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


    /**
     * @Description 检查这个人是不是领取过了
     * @Author xg.chen
     * @Date 14:04 2019/3/13
     */
    @GetMapping("/userInfo")
    @ResponseBody
    public String creatWxUserInfo(String openId, String avatarUrl, String nickName, String id) {
        //保存至数据数据中
        WxUserInfoDo wxUserInfoDo = new WxUserInfoDo();
        wxUserInfoDo.setOpenId(openId);
        wxUserInfoDo.setAvatarUrl(avatarUrl);
        wxUserInfoDo.setNickName(nickName);
        wxUserInfoDo.setActivityId(id);
        wxUserInfoDo.setStatus("N");
        //先根据id和openId检查是否存在该用户
        int num = userInfoService.getWxUserInfoById(wxUserInfoDo);
        if (num == 0) {//没有的话就插入
            userInfoService.creatWxUserInfo(wxUserInfoDo);
        }
        return "success";
    }

    /**
     * @Description 获取已经参加测活动的人员的明细
     * @Author xg.chen
     * @Date 14:04 2019/3/13
     */
    @GetMapping("/plans")
    @ResponseBody
    public String getPlanActivityList(String openId) {
        List<PlanActivityDo> planActivityDoList = userInfoService.getPlanActivityList(openId);
        String json = JSONUtils.beanToJson(planActivityDoList);
        return json;
    }

    /**
     * @Description H5推广小程序页面
     * @Author xg.chen
     * @Date 14:07 2019/3/13
     */
    @GetMapping("/webappIndex/{QRCode}")
    public String webapp(@PathVariable(value = "QRCode", required = false) String QRCode,
                         HttpServletRequest request,
                         Model model)
            throws UnsupportedEncodingException, ParseException {
        //传入的参数
        String[] strs = QRCode.split(",");
        String id = strs[0].toString().trim();
        String planId = strs[1].toString().trim();
        //获取缓存中的openId
        session = request.getSession();
        String openId = (String) session.getAttribute("openId");
//        测试数据
//        String openId = "oVgr8wUxM2JWhdcEIiNbBhY1ppEA";
        //不是扫码进来的
        if(StringUtils.isEmpty(openId)) {
            return "webappError/error_1";
        }
        //获取活动数据
        PlanActivityDo planActivityDo = getPlanActivityData(id, planId);
        //控制活动的时间
        String plamDate = getPlanDate(planActivityDo);
        if ("1".equals(plamDate)) {
            //活动未开始
            return "webappError/unstart";
        }
        if ("2".equals(plamDate)) {
            //活动已结束
            return "webappError/started";
        }
        //活动正常执行
        WxUserInfoDo wxUserInfoDo = new WxUserInfoDo();
        wxUserInfoDo.setOpenId(openId);
        wxUserInfoDo.setActivityId(id);
        WxUserInfoDo wxUserInfo = userInfoService.findUserInfoStatus(wxUserInfoDo);
        if (null != wxUserInfo) {
            //是否参加
            if (wxUserInfo.getStatus().equals("N")) {
                if (planActivityDo != null) {
                    String[] string = planActivityDo.getPlanPhotoUrl().split(",");
                    planActivityDo.setPlanPhotoUrl(string[0]);
                }
                planActivityDo.setOpenId(openId);
                model.addAttribute("planActivityDo", planActivityDo);
                return "webappIndex";
            }
        }
        return "webappError/error";
    }

    /**
     * @Description 获取活动数据
     * 先去redis去获取，如果没有，就去数据库中去去获取
     * @Author xg.chen
     * @Date 14:40 2019/11/25
    **/
    public PlanActivityDo getPlanActivityData(String id, String planId) {
        //redis中获取
        //这里的redis的key值是Object_id的key，保证多数据的唯一性
        PlanActivityDo redisDo = (PlanActivityDo) valueOperations.get("planActivityDo_"+id);
        if (redisDo==null) {
            PlanActivityDo planActivityDo = new PlanActivityDo();
            //在数据中获取，并保存在redis中
            planActivityDo.setId(id);
            planActivityDo.setPlanId(planId);
            planActivityDo = userInfoService.getPlanActivityById(planActivityDo);
            valueOperations.set("planActivityDo_"+id, planActivityDo);
            return planActivityDo;
        }
        return redisDo;
    }

    /**
     * @Description 活动时间控制
     * @Author xg.chen
     * @Date 14:11 2019/11/25
    **/
    public String getPlanDate(PlanActivityDo planActivityDo) throws ParseException {
        //控制活动的时间
        String startDate = planActivityDo.getPlanStartDate();//活动开始时间
        String endDate = planActivityDo.getPlanEndDate();//活动结束时间
        //这里一长串是因为取出来的数据中的汉字是乱码
        String y=startDate.substring(4, 5);
        String m=startDate.substring(7,8);
        String d=startDate.substring(10,11);
        String ymd="yyyy"+y+"MM"+m+"dd"+d;
        //格式化开始时间
        SimpleDateFormat sdf = new SimpleDateFormat(ymd);
        Date utilDate = sdf.parse(startDate);
        //格式化结束时间
        SimpleDateFormat sdf1 = new SimpleDateFormat(ymd);
        Date utilDate1 = sdf1.parse(endDate);
        //取当前的服务器时间
        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
        Integer year = Integer.parseInt(strNow[0]);
        Integer month = Integer.parseInt(strNow[1]);
        Integer day = Integer.parseInt(strNow[2]);
        //格式化当前时间
        String dateNow = year+y+month+m+day+d;
        SimpleDateFormat sdf3 = new SimpleDateFormat(ymd);
        Date utilDate2 = sdf3.parse(dateNow);
        //开始时间
        long a = utilDate.getTime();
        //结束时间
        long b = utilDate1.getTime();
        //当前时间
        long c = utilDate2.getTime();
        if (c < a) {
            //活动未开始
            return "1";
        } else if (c > b) {
            //活动已结束
            return "2";
        }
        return "0";
    }

    /**
     * @Description 确认领取
     * @Author xg.chen
     * @Date 14:04 2019/8/3066
     **/
    @PostMapping("/submit1")
    @ResponseBody
    public R Activities2Confirm1(@RequestParam("id") String id,
                                 @RequestParam("planId") String planId,
                                 @RequestParam("openId") String openId,
                                 HttpServletRequest request) {
        WxUserInfoDo wxUserInfoDo = new WxUserInfoDo();
        wxUserInfoDo.setOpenId(openId);
        wxUserInfoDo.setActivityId(id);
        int num = userInfoService.updateUserInfoStatus(wxUserInfoDo);
        if (num > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 领取成功后跳转
     *
     * @return
     */
    @GetMapping("/success")
    public String successRed() {
        return "webappError/success";
    }

}
