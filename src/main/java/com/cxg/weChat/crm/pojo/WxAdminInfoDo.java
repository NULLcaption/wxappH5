package com.cxg.weChat.crm.pojo;

import java.io.Serializable;

/**
* @Description:    活动执行人实体来
* @Author:         Cheney Master
* @CreateDate:     2018/11/8 14:43
* @Version:        1.0
*/
public class WxAdminInfoDo implements Serializable{
    private String id;
    private String openId;
    private String nickName;
    private String avatarUrl;
    private String signAddress;
    private String signDate;
    private String activityId;
    private String planId;
    private String userId;
    private String orgId;
    private String planStartDate;
    private String planEndDate;
    private String planTitle;
    private String planAddress;
    private String planStates;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public String getPlanAddress() {
        return planAddress;
    }

    public void setPlanAddress(String planAddress) {
        this.planAddress = planAddress;
    }

    public String getPlanStates() {
        return planStates;
    }

    public void setPlanStates(String planStates) {
        this.planStates = planStates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSignAddress() {
        return signAddress;
    }

    public void setSignAddress(String signAddress) {
        this.signAddress = signAddress;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    @Override
    public String toString() {
        return "WxAdminInfoDo{" +
                "id='" + id + '\'' +
                ", openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", signAddress='" + signAddress + '\'' +
                ", signDate='" + signDate + '\'' +
                '}';
    }
}
