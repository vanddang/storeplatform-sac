package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 비밀번호 Value
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MbrPwd extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String ID;
	private String loginFailCount;
	private String memberKey;
	private String oldPW;
	private String PW;
	private String pwRegDate;
	private String tenantID;

	public String getID() {
		return this.ID;
	}

	public void setID(String iD) {
		this.ID = iD;
	}

	public String getLoginFailCount() {
		return this.loginFailCount;
	}

	public void setLoginFailCount(String loginFailCount) {
		this.loginFailCount = loginFailCount;
	}

	public String getMemberKey() {
		return this.memberKey;
	}

	public void setMemberKey(String memberKey) {
		this.memberKey = memberKey;
	}

	public String getOldPW() {
		return this.oldPW;
	}

	public void setOldPW(String oldPW) {
		this.oldPW = oldPW;
	}

	public String getPW() {
		return this.PW;
	}

	public void setPW(String pW) {
		this.PW = pW;
	}

	public String getPwRegDate() {
		return this.pwRegDate;
	}

	public void setPwRegDate(String pwRegDate) {
		this.pwRegDate = pwRegDate;
	}

	public String getTenantID() {
		return this.tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
