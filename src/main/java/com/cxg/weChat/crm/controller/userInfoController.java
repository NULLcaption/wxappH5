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
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description: H5后台基础类
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

    private static Integer CODE_NUMBER = 0;

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
//        String openId = "oFC_wwvzIsoo9iJxkFRUSK57g_Yg";
        //不是扫码进来的
        if(StringUtils.isEmpty(openId)) {
            return "webappError/error_1";
        }
        //获取活动数据
        PlanActivityDo planActivityDo = getPlanActivityData(id, planId);
        int count = userInfoService.getUserInfoCountByPlanId(id);
        //控制活动派样数量
        if (count >= Integer.parseInt(planActivityDo.getPlanNum())) {
            return "webappError/startedDone";
        }
        //控制活动扫码时间
        boolean scanCodeTime = getPlanScanCodeTime(planActivityDo);
        if (false == scanCodeTime) {
            return "webappError/unstarttime";
        }
        //控制活动起止时间
        String planDate = getPlanDate(planActivityDo);
        if ("1".equals(planDate)) {
            return "webappError/unstart";
        }
        if ("2".equals(planDate)) {
            return "webappError/started";
        }
        //活动正常执行
        WxUserInfoDo wxUserInfoDo = new WxUserInfoDo();
        wxUserInfoDo.setOpenId(openId);
        wxUserInfoDo.setActivityId(id);
        wxUserInfoDo.setPlanId(planId);
        wxUserInfoDo.setCreateTime(String.valueOf(LocalDate.now().getYear()));
        wxUserInfoDo.setBrand(planActivityDo.getBrand());
        wxUserInfoDo.setMatnr02(planActivityDo.getMatnr02());
        //查询用户是否参加过之前的活动
        List<WxUserInfoDo> wxUserInfo = userInfoService.findUserInfoStatus(wxUserInfoDo);
        if (wxUserInfo !=null && !wxUserInfo.isEmpty()) {
            //是否参加
            for (WxUserInfoDo wxUserInfoDo1 : wxUserInfo) {
                if(wxUserInfoDo1.getStatus().equals("Y")){
                    return "webappError/error";
                }
            }
            if (wxUserInfo.get(0).getStatus().equals("N")) {
                if (planActivityDo != null) {
                    String[] string = planActivityDo.getPlanPhotoUrl().split(",");
                    planActivityDo.setPlanPhotoUrl(string[0]);
                }
                //MECO展示页面
                if ("G".equals(planActivityDo.getBrand())){
                    //当前活动参与人数作为用户的参与码
                    //防止用户刷新时数据变化，实际展示的数据和参与码数据不符合
                    int codeNum = userInfoService.getUserInfoCount(id);
                    planActivityDo.setCodeNumber(String.valueOf(codeNum));
                    planActivityDo.setCreateTime(wxUserInfo.get(0).getCreateTime());
                    planActivityDo.setOpenId(openId);
                    model.addAttribute("planActivityDo", planActivityDo);
                    return "webappIndex";
                }
                //兰芳园展示页面
                if ("L".equals(planActivityDo.getBrand())){
                    planActivityDo.setOpenId(openId);
                    model.addAttribute("planActivityDo", planActivityDo);
                    return "webappIndex_l";
                }
                //香飘飘展示页面
                if ("X".equals(planActivityDo.getBrand())){
                    planActivityDo.setOpenId(openId);
                    model.addAttribute("planActivityDo", planActivityDo);
                    return "webappIndex_x";
                }
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
            valueOperations.set("planActivityDo_"+id, planActivityDo, Constant.KEY_TIME, TimeUnit.SECONDS);
            return planActivityDo;
        }
        return redisDo;
    }

    /**
     * 扫码活动时间控制
     * @param planActivityDo
     * @return
     * @throws ParseException
     */
    public boolean getPlanScanCodeTime(PlanActivityDo planActivityDo) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date nowTime = new SimpleDateFormat("HH:mm:ss").parse(df.format(new Date()));
        if ("0".equals(planActivityDo.getPlanStates()) ||
                "1".equals(planActivityDo.getPlanStates()) ||
                "2".equals(planActivityDo.getPlanStates())) {
            Date startTime = new SimpleDateFormat("HH:mm:ss").parse("09:00:00");
            Date endTime = new SimpleDateFormat("HH:mm:ss").parse("20:00:00");
            boolean isTime = isEffectiveDate(nowTime,startTime,endTime);
            if (isTime == false){
                return false;
            }
        }
        if ("3".equals(planActivityDo.getPlanStates())) {
            Date startTime = new SimpleDateFormat("HH:mm:ss").parse("09:00:00");
            Date endTime = new SimpleDateFormat("HH:mm:ss").parse("23:00:00");
            boolean isTime = isEffectiveDate(nowTime,startTime,endTime);
            if (isTime == false){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description 活动起止时间控制
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
                                 @RequestParam("openId") String openId) {
        WxUserInfoDo wxUserInfoDo = new WxUserInfoDo();
        wxUserInfoDo.setOpenId(openId);
        wxUserInfoDo.setActivityId(id);
        wxUserInfoDo.setCreateTime(String.valueOf(LocalDate.now().getYear()));
        List<WxUserInfoDo> wxUserInfo = userInfoService.findUserInfoStatusByOpenId(wxUserInfoDo);
        if (wxUserInfo != null) {
            //用户已经领取
            if ("Y".equals(wxUserInfo.get(0).getStatus())) {
                return R.ok();
            }
        }
        return R.error();
    }

    /**
     * 领取成功后跳转
     *
     * @return
     */
    @GetMapping("/success")
    public String successRed(String id,String planId, String openId, Model model) {
        //根据活动类型来处理不同的海报
        return "webappError/success";
    }

    /**
     * 动态二维码请求超时60s后跳转至失效页面
     * @return
     */
    @GetMapping("/timeout")
    public String timeout() {
        return "webappError/error_2";
    }

}
