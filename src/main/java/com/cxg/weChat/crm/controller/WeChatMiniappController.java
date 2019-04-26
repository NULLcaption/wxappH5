package com.cxg.weChat.crm.controller;

import com.cxg.weChat.core.utils.HttpUtil;
import com.cxg.weChat.core.utils.JSONUtils;
import com.cxg.weChat.crm.pojo.WxAdminInfoDo;
import com.cxg.weChat.crm.pojo.WxUserInfoDo;
import com.cxg.weChat.crm.service.UserInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * @Description: 微信小程序后台服务类
 * @Author: Cheney Master
 * @CreateDate: 2018/10/30 11:39
 * @Version: 1.0
 */

@Controller
@RequestMapping("/api/wx/admin")
public class WeChatMiniappController {
    private static Logger logger = LoggerFactory.getLogger(WeChatMiniappController.class);

    private String appid = "wxe581c3b3928cfece";
    private String appSercret = "8cf3f824d625e516181db2b506d6db60";
    private String grant_type = "authorization_code";
    private String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    UserInfoService userInfoService;

    @RequestMapping("/sigin")
    @ResponseBody
    public String userSigin(String address, String date, String openid, String avatarUrl, String nickName) {
        WxAdminInfoDo wxAdminInfoDo = new WxAdminInfoDo();
        wxAdminInfoDo.setOpenId(openid);
        wxAdminInfoDo.setAvatarUrl(avatarUrl);
        wxAdminInfoDo.setNickName(nickName);
        wxAdminInfoDo.setSignAddress(address);
        wxAdminInfoDo.setSignDate(date);

        int num = userInfoService.creatWxAdminSignInfo(wxAdminInfoDo);
        if (num == 0) {
            return "error";
        }
        return "success";
    }

    /**
    * @Description:    验证用户领取奖品的二维码
    * @Author:         Cheney Master
    * @CreateDate:     2018/11/1 15:15
    * @Version:        1.0
    */
    @RequestMapping("/userInfo")
    @ResponseBody
    public String checkUserQRCode(String scanCodeInfo, String planId) {
        //将传入的openid去后台数据中验证是否领取过了
        WxUserInfoDo wxUserInfoDo = new WxUserInfoDo();
        wxUserInfoDo.setOpenId(scanCodeInfo);
        wxUserInfoDo.setActivityId(planId);
        String status = userInfoService.findUserInfoStatus(wxUserInfoDo);
        if (status.equals("N")){
            int num = userInfoService.updateUserInfoStatus(wxUserInfoDo);
            if (num == 0) {
                return "error";
            }
            return "success";
        }
        return "error";

    }

    /**
    * @Description:    根据活动的id获取参加活动的用户的列表
    * @Author:         Cheney Master
    * @CreateDate:     2018/11/8 16:09
    * @Version:        1.0
    */
    @GetMapping("/userInfos")
    @ResponseBody
    public String getUserInfoByPlanId(String planId){
        List<WxUserInfoDo> wxUserInfoDoList = userInfoService.getUserInfoByPlanId(planId);
        String json = JSONUtils.beanToJson(wxUserInfoDoList);
        return json;
    }

    /**
    * @Description:    验证管理员权限
    * @Author:         Cheney Master
    * @CreateDate:     2018/11/1 11:29
    * @Version:        1.0
    */
    @RequestMapping("/adminCode")
    @ResponseBody
    public String checkAdminRole(String code, String openid) {
        logger.debug("验证码{}："+code);
        logger.debug("验证人的openid{}："+openid);
        //获取后台数据中提前随机生成的验证码
        int num  = userInfoService.checkAdminRole(code);
        if (String.valueOf(num) != null) {
            return String.valueOf(num);
        } else {
            return "error";
        }
    }

    /**
    * @Description:    通过java后台获取微信的相关的信息
    * @Author:         Cheney Master
    * @CreateDate:     2018/10/30 13:44
    * @Version:        1.0
    */
    @RequestMapping("/weChatInfo")
    @ResponseBody
    public Map<String, Object> getSession(String code) {
        return this.getSessionByCode(code);
    }

    /**
     * @Description: 通过java后台获取函数
     * @Author: Cheney Master
     * @CreateDate: 2018/10/30 13:30
     * @Version: 1.0
     */
    private Map<String, Object> getSessionByCode(String code) {
        String url = requestUrl + "?appid=" + appid + "&secret=" + appSercret + "&js_code=" + code + "&grant_type=" + grant_type;
        //http发送请求
        String data = HttpUtil.get(url);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> json = null;
        try{
            json = mapper.readValue(data, Map.class);
            logger.debug("返回的json数据{}：",json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        return "Hello wechat service";
    }
}
