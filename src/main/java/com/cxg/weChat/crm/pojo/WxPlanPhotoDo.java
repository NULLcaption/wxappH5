package com.cxg.weChat.crm.pojo;

import java.io.Serializable;

/**
 * @Description 微信推广管理员长传的图片
 * @Author xg.chen
 * @Date 14:20 2018/12/4
**/

public class WxPlanPhotoDo implements Serializable {

    private String openId;
    private String activityId;
    private String photoUrl;
    private String creartDate;
    private String id;
    private String nickName;
    private String avatarUrl;
    private String planId;
    private String userId;
    private String orgId;
    private String planStartDate;
    private String planEndDate;
    private String planTitle;
    private String planAddress;
    private String planStates;
    private String userRole;
    private String loginId;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreartDate() {
        return creartDate;
    }

    public void setCreartDate(String creartDate) {
        this.creartDate = creartDate;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "WxPlanPhotoDo{" +
                "openId='" + openId + '\'' +
                ", activityId='" + activityId + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", creartDate='" + creartDate + '\'' +
                '}';
    }
}
