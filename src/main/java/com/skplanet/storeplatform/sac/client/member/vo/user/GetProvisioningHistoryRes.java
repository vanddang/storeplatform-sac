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

	/* 프로비저닝 구분 */
	private String workdCd;
	/* 사용자 신규 Key */
	private String afterUserKey;
	/* 사용자 기존 Key */
	private String preUserKey;
	/* 수정일시 */
	private String updateDate;

	public String getWorkdCd() {
		return this.workdCd;
	}

	public void setWorkdCd(String workdCd) {
		this.workdCd = workdCd;
	}

	public String getAfterUserKey() {
		return this.afterUserKey;
	}

	public void setAfterUserKey(String afterUserKey) {
		this.afterUserKey = afterUserKey;
	}

	public String getPreUserKey() {
		return this.preUserKey;
	}

	public void setPreUserKey(String preUserKey) {
		this.preUserKey = preUserKey;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
