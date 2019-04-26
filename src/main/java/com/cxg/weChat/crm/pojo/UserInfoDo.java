package com.cxg.weChat.crm.pojo;

import java.io.Serializable;

/**
* @Description:    用户实体类
* @Author:         Cheney Master
* @CreateDate:     2018/11/5 9:29
* @Version:        1.0
*/

public class UserInfoDo implements Serializable{
    private String userId;

    private String passWd;

    private String userName;

    private String userShowName;

    private String userState;

    private String custType;

    private String loginId;

    private String rtx_LoginId;

    private String mobile;

    private String mobilephone;

    private String address;

    private String orgId;

    private String email;

    private String company;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWd() {
        return passWd;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserShowName() {
        return userShowName;
    }

    public void setUserShowName(String userShowName) {
        this.userShowName = userShowName;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getRtx_LoginId() {
        return rtx_LoginId;
    }

    public void setRtx_LoginId(String rtx_LoginId) {
        this.rtx_LoginId = rtx_LoginId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "userInfoDo{" +
                "userId='" + userId + '\'' +
                ", passWd='" + passWd + '\'' +
                ", userName='" + userName + '\'' +
                ", userShowName='" + userShowName + '\'' +
                ", userState='" + userState + '\'' +
                ", custType='" + custType + '\'' +
                ", loginId='" + loginId + '\'' +
                ", rtx_LoginId='" + rtx_LoginId + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
