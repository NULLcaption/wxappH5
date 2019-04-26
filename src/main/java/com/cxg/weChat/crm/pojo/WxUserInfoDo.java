package com.cxg.weChat.crm.pojo;

import java.io.Serializable;

/**
* @Description:    参加活动的微信用户
* @Author:         Cheney Master
* @CreateDate:     2018/11/8 9:10
* @Version:        1.0
*/

public class WxUserInfoDo implements Serializable{

    private String id;
    private String openId;
    private String nickName;
    private String avatarUrl;
    private String activityId;
    private String status;

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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WxUserInfoDo{" +
                "id='" + id + '\'' +
                ", openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", activityId='" + activityId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
