package com.cxg.weChat.crm.controller;

import com.cxg.weChat.core.utils.MD5Utils;
import com.cxg.weChat.core.utils.R;
import com.cxg.weChat.crm.pojo.UserInfoDo;
import com.cxg.weChat.crm.pojo.WxUserInfoDo;
import com.cxg.weChat.crm.service.UserInfoService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
* @Description:    用户登录以及获取用户信息控制类
* @Author:         Cheney Master
* @CreateDate:     2018/11/13 14:05
* @Version:        1.0
*/
@Controller
public class LoginController extends BaseController{
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    private WxOpenService wxOpenService;

    @Value("${server.domain}")
    private static String domain;

    private static String URL = "http://ssl.zjxpp.com";

    /**
     * @Description:    登录页面跳转链接
     * @Author:         Cheney Master
     * @CreateDate:     2018/11/13 14:17
     * @Version:        1.0
     */
    @GetMapping("/login/{activityId}")
    public String login(HttpSession session,
                        HttpServletResponse response,
                        @PathVariable("activityId") String activityId,
                        @RequestParam(value = "code", required = false) String code,
                        @RequestParam(value = "redirect", required = false) String redirect) throws IOException, WxErrorException {

        //appID获取授权认证
        String appid = activityId.substring(0, activityId.indexOf('_'));
        String[] QRCode = activityId.split("_");
        String s = QRCode[1].toString();
        String[] strs = s .split("-");
        //明细ID
        String id = strs[0].toString();
        //活动ID
        String planId = strs[1].toString();

        WxOpenComponentService wxOpenComponentService = wxOpenService.getWxOpenComponentService();
        WxMpService wxMpService = wxOpenComponentService.getWxMpServiceByAppid(appid);

        // 通过授权，获取用户基本信息
        if (null != code) {
            // 获取用户信息
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxOpenComponentService.oauth2getAccessToken(appid, code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
            if (null == wxMpUser) {
                return "授权失败，请重新打开";
            }
            wxMpUser = wxMpService.getUserService().userInfo(wxMpUser.getOpenId());
            String parm = id+","+planId;
            if (wxMpUser.getSubscribe()) {//用户已关注
                //将openId放在session中
                session.setAttribute("openId",wxMpUser.getOpenId());
                //保存至数据数据中
                WxUserInfoDo wxUserInfoDo = new WxUserInfoDo();
                wxUserInfoDo.setOpenId(wxMpUser.getOpenId());
                wxUserInfoDo.setAvatarUrl(wxMpUser.getHeadImgUrl());
                wxUserInfoDo.setNickName(wxMpUser.getNickname());
                wxUserInfoDo.setActivityId(id);
                wxUserInfoDo.setStatus("N");
                //先根据id和openId检查是否存在该用户
                int num = userInfoService.getWxUserInfoById(wxUserInfoDo);
                //没有的话就插入
                if (num == 0) {
                    userInfoService.creatWxUserInfo(wxUserInfoDo);
                }
                logger.error(URL + "/api/userInfo/admin/webappIndex/"+parm);
                // 跳转到活动页面
                response.sendRedirect(URL + "/api/userInfo/admin/webappIndex/"+parm);
            } else {//用户未关注
                logger.debug("------------>用户未关注");
                response.sendRedirect(URL + "/api/userInfo/admin/notConcern/"+parm);
            }
            return null;
        }else{
            // 重新进行授权
            String scope = "snsapi_userinfo";
            String state = "";
            String callbackUrl = URL + "/login/" + activityId;
            if (null != redirect) {
                callbackUrl += "?redirect=" + redirect;
            }
            String url = wxOpenComponentService.oauth2buildAuthorizationUrl(appid, callbackUrl, scope, state);
            response.sendRedirect(url);
        }
        return null;
    }

    /**
    * @Description:    登录页面跳转链接
    * @Author:         Cheney Master
    * @CreateDate:     2018/11/13 14:17
    * @Version:        1.0
    */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 用户登录登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public R ajaxLogin(@RequestParam("username")String username, @RequestParam("password")String password) {
        //密码MD5加密处理
        try {
            password = MD5Utils.md5Encry(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //验证用户名是存在
        //根据登录人账密生成token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //根据每个请求，创建一个Subject,
        //并保存到ThreadContext的resources(ThreadLocal<Map<Object, Object>>)变量中，
        //也就是一个http请求一个subject,并绑定到当前线程。
        Subject subject = SecurityUtils.getSubject();
        try {
            //设置登录以后的token，每次登录后检查登录用户
            //其本质就是依赖于浏览器的cookie来维护session的
            //扩展：如果不是web容器的app,如何实现实现无状态的会话怎么是现实呢？
            subject.login(token);
            return R.ok();
        } catch (AuthenticationException e) {
            return R.error("用户或密码错误");
        }
    }

    /**
    * @Description:    系统首页
    * @Author:         Cheney Master
    * @CreateDate:     2018/11/14 10:03
    * @Version:        1.0
    */
    @GetMapping("/index")
    public String index(Model model) {
        UserInfoDo userInfoDo = userInfoService.getUserInfoById(Integer.valueOf(getUserId()));
        model.addAttribute("userId",userInfoDo.getUserId());
        model.addAttribute("userName",userInfoDo.getUserName());
        model.addAttribute("phone",userInfoDo.getMobile());
        model.addAttribute("company",userInfoDo.getCompany());
        model.addAttribute("email",userInfoDo.getEmail());
        return "index";
    }
}
