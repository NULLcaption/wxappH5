package com.cxg.weChat.crm.pojo;

import java.io.Serializable;
import java.util.Arrays;

/**
* @Description:    推广活动实体类
* @Author:         Cheney Master
* @CreateDate:     2018/11/7 15:42
* @Version:        1.0
*/

public class PlanActivityDo implements Serializable {
	private static final long serialVersionUID = -2050435892404221535L;
	
	private String id;
	private String planId;
	private String userId;
	private String orgId;
	private String planStartDate;
	private String planEndDate;
	private String planTitle;
	private String planAddress;
	private String planStates;
	private String planCode;
	private String planPhotoUrl;
	private String planExplain;
	private String planGetExplain;
	private String planQrcodeUrl;
	private String[] urls;
	private String status;
	private String avatarUrl;
	private String NickName;
	private String openId;
	private String isTransmit;
	private String createTime;
	private String planNum;
	private String brand;
	private String matnr02;
	private String matnr;
	private String codeNumber;

	public String getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	}

	public String getMatnr02() {
		return matnr02;
	}

	public void setMatnr02(String matnr02) {
		this.matnr02 = matnr02;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPlanNum() {
		return planNum;
	}

	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getIsTransmit() {
		return isTransmit;
	}

	public void setIsTransmit(String isTransmit) {
		this.isTransmit = isTransmit;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getUrls() {
		return urls;
	}

	public void setUrls(String[] urls) {
		this.urls = urls;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getPlanPhotoUrl() {
		return planPhotoUrl;
	}
	public void setPlanPhotoUrl(String planPhotoUrl) {
		this.planPhotoUrl = planPhotoUrl;
	}
	public String getPlanExplain() {
		return planExplain;
	}
	public void setPlanExplain(String planExplain) {
		this.planExplain = planExplain;
	}
	public String getPlanGetExplain() {
		return planGetExplain;
	}
	public void setPlanGetExplain(String planGetExplain) {
		this.planGetExplain = planGetExplain;
	}
	public String getPlanQrcodeUrl() {
		return planQrcodeUrl;
	}
	public void setPlanQrcodeUrl(String planQrcodeUrl) {
		this.planQrcodeUrl = planQrcodeUrl;
	}

	@Override
	public String toString() {
		return "PlanActivityDo{" +
				"id='" + id + '\'' +
				", planId='" + planId + '\'' +
				", userId='" + userId + '\'' +
				", orgId='" + orgId + '\'' +
				", planStartDate='" + planStartDate + '\'' +
				", planEndDate='" + planEndDate + '\'' +
				", planTitle='" + planTitle + '\'' +
				", planAddress='" + planAddress + '\'' +
				", planStates='" + planStates + '\'' +
				", planCode='" + planCode + '\'' +
				", planPhotoUrl='" + planPhotoUrl + '\'' +
				", planExplain='" + planExplain + '\'' +
				", planGetExplain='" + planGetExplain + '\'' +
				", planQrcodeUrl='" + planQrcodeUrl + '\'' +
				", urls=" + Arrays.toString(urls) +
				", status='" + status + '\'' +
				", avatarUrl='" + avatarUrl + '\'' +
				", NickName='" + NickName + '\'' +
				", openId='" + openId + '\'' +
				", isTransmit='" + isTransmit + '\'' +
				", createTime='" + createTime + '\'' +
				", planNum='" + planNum + '\'' +
				", brand='" + brand + '\'' +
				", matnr02='" + matnr02 + '\'' +
				", matnr='" + matnr + '\'' +
				", codeNumber='" + codeNumber + '\'' +
				'}';
	}
}
