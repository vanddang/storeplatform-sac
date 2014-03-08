package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Tenant 연동 Amqp Info(회원 탈퇴 정보)
 * 
 * Updated on : 2014. 3. 8. Updated by : 이현, 다모아솔루션.
 */
public class RemoveMemberAmqpSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 회원번호 */
	private String mbrNo;

	/* 회원ID */
	private String mbrId;

	/**
	 * @return the mbrNo
	 */
	public String getMbrNo() {
		return this.mbrNo;
	}

	/**
	 * @param mbrNo
	 *            the mbrNo to set
	 */
	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}

	/**
	 * @return the mbrId
	 */
	public String getMbrId() {
		return this.mbrId;
	}

	/**
	 * @param mbrId
	 *            the mbrId to set
	 */
	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
	}

}
