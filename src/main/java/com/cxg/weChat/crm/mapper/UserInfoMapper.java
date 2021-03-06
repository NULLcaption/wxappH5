package com.cxg.weChat.crm.mapper;

import com.cxg.weChat.crm.pojo.PlanActivityDo;
import com.cxg.weChat.crm.pojo.UserInfoDo;
import com.cxg.weChat.crm.pojo.WxAdminInfoDo;
import com.cxg.weChat.crm.pojo.WxUserInfoDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @Description:
* @Author:         Cheney Master
* @CreateDate:     2018/11/5 10:27
* @Version:        1.0
*/

@Mapper
public interface UserInfoMapper {
    UserInfoDo getUserInfoById(int id);

    PlanActivityDo getPlanActivityById(PlanActivityDo planActivityDo);

    int getWxUserInfoById(WxUserInfoDo wxUserInfoDo);

    void creatWxUserInfo(WxUserInfoDo wxUserInfoDo);

    List<PlanActivityDo> getPlanActivityList(String openId);

    int checkAdminRole(String code);

    void creatWxAdminSignInfo(WxAdminInfoDo wxAdminInfoDo);

    List<WxUserInfoDo> getUserInfoByPlanId(String planId);

    List<WxUserInfoDo> findUserInfoStatus(WxUserInfoDo wxUserInfoDo);

    void updateUserInfoStatus(WxUserInfoDo wxUserInfoDo);

    void updateUserInfoStatus1(WxUserInfoDo wxUserInfoDo);

    String findUserInfoStatus1(WxUserInfoDo wxUserInfoDo);

    List<WxUserInfoDo> getOpeIdAndactivityId();

    List<WxUserInfoDo> getWxUserInfoList();

    int getUserInfoCountByPlanId(String planId);

    List<WxUserInfoDo> findUserInfoStatusByOpenId(WxUserInfoDo wxUserInfoDo);

    int getUserInfoCount(String id);
}
