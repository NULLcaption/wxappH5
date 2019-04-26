package com.cxg.weChat.crm.controller;

import com.cxg.weChat.core.utils.JSONUtils;
import com.cxg.weChat.core.utils.R;
import com.cxg.weChat.crm.pojo.PlanActivityDo;
import com.cxg.weChat.crm.pojo.UserInfoDo;
import com.cxg.weChat.crm.pojo.WxUserInfoDo;
import com.cxg.weChat.crm.service.UserInfoService;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public static String TEST_URL = "http://exptest.zjxpp.com:7186";
    public static String ONLINE_URL = "http://exp.zjxpp.com:8186";

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
    public String webapp(@PathVariable(value = "QRCode", required = false) String QRCode, Model model, HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        //传入的参数
        String[] strs = QRCode.split(",");
        String id = strs[0].toString();
        String planId = strs[1].toString();
//        String openId = strs[2].toString();
        HttpSession session = request.getSession();
        String openId = (String) session.getAttribute("openId");
        logger.debug("session openid---------------->" + openId);
        if (null == openId) {
            return "webappError/error_1";
        }
        //从后台获取数据
        PlanActivityDo planActivityDo = new PlanActivityDo();
        planActivityDo.setId(id);
        planActivityDo.setPlanId(planId);
        planActivityDo = userInfoService.getPlanActivityById(planActivityDo);
        String startDate = planActivityDo.getPlanStartDate();
        String endDate = planActivityDo.getPlanEndDate();
        String y=startDate.substring(4, 5);
        String m=startDate.substring(7,8);
        String d=startDate.substring(10,11);
        String ymd="yyyy"+y+"MM"+m+"dd"+d;
        SimpleDateFormat sdf = new SimpleDateFormat(ymd);
        Date utilDate = sdf.parse(startDate);
        SimpleDateFormat sdf1 = new SimpleDateFormat(ymd);
        Date utilDate1 = sdf1.parse(endDate);
        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
        Integer year = Integer.parseInt(strNow[0]);
        Integer month = Integer.parseInt(strNow[1]);
        Integer day = Integer.parseInt(strNow[2]);
        String dateNow = year+y+month+m+day+d;
        SimpleDateFormat sdf3 = new SimpleDateFormat(ymd);
        Date utilDate2 = sdf3.parse(dateNow);
        long a = utilDate.getTime();
        long b = utilDate1.getTime();
        long c = utilDate2.getTime();
        if (c < a) {
            return "webappError/unstart";
        } else if (c > b) {
            return "webappError/started";
        } else {
            //领取成功以后不能在进入活动主界面
            WxUserInfoDo wxUserInfoDo = new WxUserInfoDo();
            wxUserInfoDo.setOpenId(openId);
            wxUserInfoDo.setActivityId(id);
            String status = userInfoService.findUserInfoStatus(wxUserInfoDo);
            if (null != status) {
                if (status.equals("N")) {
                    if (planActivityDo != null) {
                        String[] string = planActivityDo.getPlanPhotoUrl().split(",");
                        planActivityDo.setPlanPhotoUrl(string[0]);
                    }
                    planActivityDo.setOpenId(openId);
                    model.addAttribute("planActivityDo", planActivityDo);
                    return "webappIndex";
                }
            }
        }
        return "webappError/error";
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

    /**
     * 转发后用户没有扫面二维码
     *
     * @return
     */
    @GetMapping("/notConcern/{QRCode}")
    public String notConcern(@PathVariable(value = "QRCode", required = false) String QRCode, Model model) {
        //传入的参数
        String[] strs = QRCode.split(",");
        String id = strs[0].toString();
        String planId = strs[1].toString();
        //从后台获取数据
        PlanActivityDo planActivityDo = new PlanActivityDo();
        planActivityDo.setId(id);
        planActivityDo.setPlanId(planId);
        planActivityDo = userInfoService.getPlanActivityById(planActivityDo);
        planActivityDo.setPlanQrcodeUrl(TEST_URL + planActivityDo.getPlanQrcodeUrl());
        model.addAttribute("planActivityDo", planActivityDo);
        return "webappError/notConcern";
    }

    /**
     * 确认领取
     *
     * @param id
     * @param planId
     * @param openId
     * @return
     */
    @PostMapping("/submit")
    @ResponseBody
    public R Activities2Confirm(@RequestParam("id") String id,
                                @RequestParam("planId") String planId,
                                @RequestParam("openId") String openId) {
        WxUserInfoDo wxUserInfoDo = new WxUserInfoDo();
        wxUserInfoDo.setOpenId(openId);
        wxUserInfoDo.setActivityId(id);
        String status = userInfoService.findUserInfoStatus(wxUserInfoDo);
        if (null != status) {
            //用户领取领取过了，提示不能再领取了
            if (status.equals("Y")) {
                return R.error();
            } else {//用户没有领取，提示领取成功
                int num = userInfoService.updateUserInfoStatus(wxUserInfoDo);
                if (num > 0) {
                    return R.ok();
                }
            }
        }
        return R.error();
    }

    /**
     * @Description 获取活动详细页面
     * @Author xg.chen
     * @Date 14:05 2019/3/13
     */
    @GetMapping("/plan")
    @ResponseBody
    public String getPlanActivityById(String QRCode) {
        //处理获取到的二维码
        String[] strs = QRCode.split("-");
        String id = strs[0].toString();
        String planId = strs[1].toString();
        //从后台获取数据
        PlanActivityDo planActivityDo = new PlanActivityDo();
        planActivityDo.setId(id);
        planActivityDo.setPlanId(planId);

        planActivityDo = userInfoService.getPlanActivityById(planActivityDo);
        if (planActivityDo != null) {
            if (planActivityDo.getPlanStates().equals("0")) {
                planActivityDo.setPlanStates("简单派送");
            }
            if (planActivityDo.getPlanStates().equals("1")) {
                planActivityDo.setPlanStates("集赞派送");
            }
            if (planActivityDo.getPlanStates().equals("2")) {
                planActivityDo.setPlanStates("分享派送");
            }
            String[] string = planActivityDo.getPlanPhotoUrl().split(",");
            planActivityDo.setUrls(string);
        }
        String json = JSONUtils.beanToJson(planActivityDo);

        return json;
    }

    /**
     * @Description PC端首页
     * @Author xg.chen
     * @Date 14:05 2019/3/13
     */
    @GetMapping("/index")
    public String index(Model model) {
        UserInfoDo userInfoDo = userInfoService.getUserInfoById(93890);
        model.addAttribute("userId", userInfoDo.getUserId());
        model.addAttribute("userName", userInfoDo.getUserName());
        model.addAttribute("phone", userInfoDo.getMobilephone());
        model.addAttribute("address", userInfoDo.getAddress());
        return "index";
    }

    /**
     * @Description 测试页面
     * @Author xg.chen
     * @Date 14:06 2019/3/13
     */
    @RequestMapping("/userInfoById")
    @ResponseBody
    public String userInfoById(Model model) {
        UserInfoDo userInfoDo = userInfoService.getUserInfoById(93890);
        String json = JSONUtils.beanToJson(userInfoDo);
        return json;
    }
}
