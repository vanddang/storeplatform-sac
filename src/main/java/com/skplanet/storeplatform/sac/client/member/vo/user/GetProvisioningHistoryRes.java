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

	/* 사용자 신규 Key */
	private String mbrNo;
	/* 사용자 기존 Key */
	private String oldMbrNo;
	/* 등록일자 */
	private String regDate;

	public String getMbrNo() {
		return this.mbrNo;
	}

	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}

	public String getOldMbrNo() {
		return this.oldMbrNo;
	}

	public void setOldMbrNo(String oldMbrNo) {
		this.oldMbrNo = oldMbrNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
