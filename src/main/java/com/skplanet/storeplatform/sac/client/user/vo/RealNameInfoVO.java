package com.skplanet.storeplatform.sac.client.user.vo;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;

@ProtobufMapping(RealNameInfoProto.RealNameInfo.class)
public class RealNameInfoVO {

	private String isRealName;
	private String isOwn;
	private String parentType;
	private String perantEmail;
	private String realNameDate;
	private String userCI;
	private String userDI;
	private String realNameMethod;
	private String realNameSystemId;
	private String userTelecom;
	private String userPhone;
	private String userName;
	private String userSex;
	private String userBirthDay;
	private String resident;

	public String getIsRealName() {
		return this.isRealName;
	}

	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	public String getIsOwn() {
		return this.isOwn;
	}

	public void setIsOwn(String isOwn) {
		this.isOwn = isOwn;
	}

	public String getParentType() {
		return this.parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getPerantEmail() {
		return this.perantEmail;
	}

	public void setPerantEmail(String perantEmail) {
		this.perantEmail = perantEmail;
	}

	public String getRealNameDate() {
		return this.realNameDate;
	}

	public void setRealNameDate(String realNameDate) {
		this.realNameDate = realNameDate;
	}

	public String getUserCI() {
		return this.userCI;
	}

	public void setUserCI(String userCI) {
		this.userCI = userCI;
	}

	public String getUserDI() {
		return this.userDI;
	}

	public void setUserDI(String userDI) {
		this.userDI = userDI;
	}

	public String getRealNameMethod() {
		return this.realNameMethod;
	}

	public void setRealNameMethod(String realNameMethod) {
		this.realNameMethod = realNameMethod;
	}

	public String getRealNameSystemId() {
		return this.realNameSystemId;
	}

	public void setRealNameSystemId(String realNameSystemId) {
		this.realNameSystemId = realNameSystemId;
	}

	public String getUserTelecom() {
		return this.userTelecom;
	}

	public void setUserTelecom(String userTelecom) {
		this.userTelecom = userTelecom;
	}

	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSex() {
		return this.userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserBirthDay() {
		return this.userBirthDay;
	}

	public void setUserBirthDay(String userBirthDay) {
		this.userBirthDay = userBirthDay;
	}

	public String getResident() {
		return this.resident;
	}

	public void setResident(String resident) {
		this.resident = resident;
	}

}
