package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 회원 프로비저닝 이력 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetProvisioningHistoryRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String workCd;
	private String userKey;
	private String oldUserKey;
	private String regDate;

	public String getWorkCd() {
		return this.workCd;
	}

	public void setWorkCd(String workCd) {
		this.workCd = workCd;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getOldUserKey() {
		return this.oldUserKey;
	}

	public void setOldUserKey(String oldUserKey) {
		this.oldUserKey = oldUserKey;
	}

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
