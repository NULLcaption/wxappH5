package com.cxg.weChat.crm.service;

import com.cxg.weChat.crm.pojo.PlanActivityDo;
import com.cxg.weChat.crm.pojo.UserInfoDo;
import com.cxg.weChat.crm.pojo.WxAdminInfoDo;
import com.cxg.weChat.crm.pojo.WxUserInfoDo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @Description:    后端服务接口
* @Author:         Cheney Master
* @CreateDate:     2018/11/5 10:20
* @Version:        1.0
*/
@Service
public interface UserInfoService {

    UserInfoDo getUserInfoById(int i);

    PlanActivityDo getPlanActivityById(PlanActivityDo planActivityDo);

    int getWxUserInfoById(WxUserInfoDo wxUserInfoDo);

    void creatWxUserInfo(WxUserInfoDo wxUserInfoDo);

    List<PlanActivityDo> getPlanActivityList(String openId);

    int checkAdminRole(String code);

    int creatWxAdminSignInfo(WxAdminInfoDo wxAdminInfoDo);

    List<WxUserInfoDo> getUserInfoByPlanId(String planId);

    List<WxUserInfoDo> findUserInfoStatus(WxUserInfoDo wxUserInfoDo);

    int updateUserInfoStatus(WxUserInfoDo wxUserInfoDo);

    int updateUserInfoStatus1(WxUserInfoDo wxUserInfoDo);

    String findUserInfoStatus1(WxUserInfoDo wxUserInfoDo);

    List<WxUserInfoDo> getOpeIdAndactivityId();

    List<WxUserInfoDo> getWxUserInfoList();

    int getUserInfoCountByPlanId(String planId);

    List<WxUserInfoDo> findUserInfoStatusByOpenId(WxUserInfoDo wxUserInfoDo);
}
