package com.cxg.weChat.crm.pojo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
* @Description:    ��ʵ����
* @Author:         Cheney Master
* @CreateDate:     2018/11/13 15:45
* @Version:        1.0
*/

public class AllUsersDo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;

	private String passWd;

	/**
	 * emp_name
	 */
	private String userName;

	/**
	 * emp_showname ��ʾ��
	 */
	private String userShowName;

	/**
	 * EMP_STATE ��Ա״̬
	 */
	private String userState;

	private String custType;

	/**
	 * emp_code ��Ա��OA�ϵ�ID
	 */
	private String loginId;
	/**
	 * rtx_code ��Ա��rtx��id
	 */
	private String rtx_LoginId;

	/**
	 * emp_mobile_phone ��Ա�ֻ����� / ��ԭͨѶ¼��ʹ��bus_mobilephone��Ϊ�ֻ����� �˹���һ���ֶ�
	 */
	private String mobile;
	
	/**
	 * st_mobile ��Ա�ֻ��̺�
	 */
	private String mobileSimple;

	/**
	 * emp_shortmsgbook �ֻ����Ŷ���
	 */
	private String empShortmsgbook;

	private String smsUserTypeId;

	private Long chargeId;

	/**
	 * emp_phone ��Ա�绰����
	 */
	private String phone;
	/**
	 * bus_mobilephone �����ֻ�����
	 */
	private String busMobilephone;

	/**
	 * �Ƿ��ܲ���Ա 0:��;1:��
	 */
	private String hqSign;

	private String kunnrSign;

	private String isOffice;

	private Date paswdSignDate;

	private String orgId;

	private String orgName;

	/**
	 * emp_id_card ������Ա���֤����
	 */
	private String idCard;

	/**
	 * emp_workfax ����
	 */
	private String workFax;

	/**
	 * emp_homephone
	 */
	private String homePhone;

	/**
	 * emp_start_date ��ʼʱ��
	 */
	private String startDate;

	/**
	 * emp_address ��ַ
	 */
	private String address;

	/**
	 * emp_email �����ʼ�
	 */
	private String email;

	/**
	 * emp_sex ��Ա�Ա�
	 */
	private String sex;

	/**
	 * pos_name
	 */
	private String posName;
	/**
	 * ְλID
	 */
	private String posId;

	/**
	 * havemail �Ƿ�ͨ���䣨0��1�ǣ�
	 */
	private String haveMail;

	/**
	 * emp_remark ��ע
	 */
	private String remark;

	/**
	 * position_type_name
	 */
	private String positionTypeName;

	private String[] orgIds;

	private Long id;

	private String orgStr;
	
	private String orgCodes;

	/**
	 * �Ȳ��ڲ���Ӧ�̱��
	 */
	private String supplierNumber;

	private String questionLinkId;

	private String reason;

	private String roleIds;// ��λID

	private String stationState;// ��λ״̬

	private String ids;// station_user���ID ����

	private String[] userIds;

	private String empPostId;// ְ��ID

	private String empPostName;// ְ��ID

	private String expressly;// ��������

	private String stationNames;// ְλ���� ���

	private String ip;// ��¼��IP

	private String[] allOrg;// ��¼��������֯
	private String expiredpsw;// �жϵ�¼�Ƿ����
	private Long staffNubmer;// �û�������
	
	private String empUserId; //��Ա���
	
	//��˾��Ա�����ھ����̹�Ա������������˾��Ա
	private String xppEmpId;
	private String xppEmpName;
	
	private Long emailOrgId;//������֯id  ��ѶҪ�����ָ�ʽ
	
	public Long getStaffNubmer() {
		return staffNubmer;
	}

	public void setStaffNubmer(Long staffNubmer) {
		this.staffNubmer = staffNubmer;
	}

	public String[] getAllOrg() {
		return allOrg;
	}

	public void setAllOrg(String[] allOrg) {
		this.allOrg = allOrg;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStationNames() {
		return stationNames;
	}

	public void setStationNames(String stationNames) {
		this.stationNames = stationNames;
	}

	public String getExpressly() {
		return expressly;
	}

	public void setExpressly(String expressly) {
		this.expressly = expressly;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHqSign() {
		return hqSign;
	}

	public void setHqSign(String hqSign) {
		this.hqSign = hqSign;
	}

	public String getKunnrSign() {
		return kunnrSign;
	}

	public void setKunnrSign(String kunnrSign) {
		this.kunnrSign = kunnrSign;
	}

	public String getIsOffice() {
		return isOffice;
	}

	public void setIsOffice(String isOffice) {
		this.isOffice = isOffice;
	}

	public Date getPaswdSignDate() {
		return paswdSignDate;
	}

	public void setPaswdSignDate(Date paswdSignDate) {
		this.paswdSignDate = paswdSignDate;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getWorkFax() {
		return workFax;
	}

	public void setWorkFax(String workFax) {
		this.workFax = workFax;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getHaveMail() {
		return haveMail;
	}

	public void setHaveMail(String haveMail) {
		this.haveMail = haveMail;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPositionTypeName() {
		return positionTypeName;
	}

	public void setPositionTypeName(String positionTypeName) {
		this.positionTypeName = positionTypeName;
	}

	public String[] getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String[] orgIds) {
		this.orgIds = orgIds;
	}

	public String getBusMobilephone() {
		return busMobilephone;
	}

	public void setBusMobilephone(String busMobilephone) {
		this.busMobilephone = busMobilephone;
	}

	public String getEmpShortmsgbook() {
		return empShortmsgbook;
	}

	public void setEmpShortmsgbook(String empShortmsgbook) {
		this.empShortmsgbook = empShortmsgbook;
	}

	public String getSmsUserTypeId() {
		return smsUserTypeId;
	}

	public void setSmsUserTypeId(String smsUserTypeId) {
		this.smsUserTypeId = smsUserTypeId;
	}

	public Long getChargeId() {
		return chargeId;
	}

	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgStr() {
		return orgStr;
	}

	public void setOrgStr(String orgStr) {
		this.orgStr = orgStr;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getQuestionLinkId() {
		return questionLinkId;
	}

	public void setQuestionLinkId(String questionLinkId) {
		this.questionLinkId = questionLinkId;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getStationState() {
		return stationState;
	}

	public void setStationState(String stationState) {
		this.stationState = stationState;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String[] getUserIds() {
		return userIds;
	}

	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}

	public String getEmpPostId() {
		return empPostId;
	}

	public void setEmpPostId(String empPostId) {
		this.empPostId = empPostId;
	}

	public String getEmpPostName() {
		return empPostName;
	}

	public void setEmpPostName(String empPostName) {
		this.empPostName = empPostName;
	}

	public String getExpiredpsw() {
		return expiredpsw;
	}

	public void setExpiredpsw(String expiredpsw) {
		this.expiredpsw = expiredpsw;
	}

	public String getMobileSimple() {
		return mobileSimple;
	}

	public void setMobileSimple(String mobileSimple) {
		this.mobileSimple = mobileSimple;
	}

	public String getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(String orgCodes) {
		this.orgCodes = orgCodes;
	}

	public String getEmpUserId() {
		return empUserId;
	}

	public void setEmpUserId(String empUserId) {
		this.empUserId = empUserId;
	}

	public String getXppEmpId() {
		return xppEmpId;
	}

	public void setXppEmpId(String xppEmpId) {
		this.xppEmpId = xppEmpId;
	}

	public String getXppEmpName() {
		return xppEmpName;
	}

	public void setXppEmpName(String xppEmpName) {
		this.xppEmpName = xppEmpName;
	}

	public Long getEmailOrgId() {
		return emailOrgId;
	}

	public void setEmailOrgId(Long emailOrgId) {
		this.emailOrgId = emailOrgId;
	}

	@Override
	public String toString() {
		return "AllUsersDo{" +
				"userId='" + userId + '\'' +
				", passWd='" + passWd + '\'' +
				", userName='" + userName + '\'' +
				", userShowName='" + userShowName + '\'' +
				", userState='" + userState + '\'' +
				", custType='" + custType + '\'' +
				", loginId='" + loginId + '\'' +
				", rtx_LoginId='" + rtx_LoginId + '\'' +
				", mobile='" + mobile + '\'' +
				", mobileSimple='" + mobileSimple + '\'' +
				", empShortmsgbook='" + empShortmsgbook + '\'' +
				", smsUserTypeId='" + smsUserTypeId + '\'' +
				", chargeId=" + chargeId +
				", phone='" + phone + '\'' +
				", busMobilephone='" + busMobilephone + '\'' +
				", hqSign='" + hqSign + '\'' +
				", kunnrSign='" + kunnrSign + '\'' +
				", isOffice='" + isOffice + '\'' +
				", paswdSignDate=" + paswdSignDate +
				", orgId='" + orgId + '\'' +
				", orgName='" + orgName + '\'' +
				", idCard='" + idCard + '\'' +
				", workFax='" + workFax + '\'' +
				", homePhone='" + homePhone + '\'' +
				", startDate='" + startDate + '\'' +
				", address='" + address + '\'' +
				", email='" + email + '\'' +
				", sex='" + sex + '\'' +
				", posName='" + posName + '\'' +
				", posId='" + posId + '\'' +
				", haveMail='" + haveMail + '\'' +
				", remark='" + remark + '\'' +
				", positionTypeName='" + positionTypeName + '\'' +
				", orgIds=" + Arrays.toString(orgIds) +
				", id=" + id +
				", orgStr='" + orgStr + '\'' +
				", orgCodes='" + orgCodes + '\'' +
				", supplierNumber='" + supplierNumber + '\'' +
				", questionLinkId='" + questionLinkId + '\'' +
				", reason='" + reason + '\'' +
				", roleIds='" + roleIds + '\'' +
				", stationState='" + stationState + '\'' +
				", ids='" + ids + '\'' +
				", userIds=" + Arrays.toString(userIds) +
				", empPostId='" + empPostId + '\'' +
				", empPostName='" + empPostName + '\'' +
				", expressly='" + expressly + '\'' +
				", stationNames='" + stationNames + '\'' +
				", ip='" + ip + '\'' +
				", allOrg=" + Arrays.toString(allOrg) +
				", expiredpsw='" + expiredpsw + '\'' +
				", staffNubmer=" + staffNubmer +
				", empUserId='" + empUserId + '\'' +
				", xppEmpId='" + xppEmpId + '\'' +
				", xppEmpName='" + xppEmpName + '\'' +
				", emailOrgId=" + emailOrgId +
				'}';
	}
}
