package com.skplanet.storeplatform.sac.client.user.vo;

import java.io.Serializable;

/**
 * 판매자 회원 탈퇴사유 VO
 * 
 * Updated on : 2013-12-20 Updated by : 반범진, 지티소프트.
 */
public class SellerSecedeResonVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String secedeReasonCode;

	private String secedeReasonMessage;

	public String getSecedeReasonCode() {
		return this.secedeReasonCode;
	}

	public void setSecedeReasonCode(String secedeReasonCode) {
		this.secedeReasonCode = secedeReasonCode;
	}

	public String getSecedeReasonMessage() {
		return this.secedeReasonMessage;
	}

	public void setSecedeReasonMessage(String secedeReasonMessage) {
		this.secedeReasonMessage = secedeReasonMessage;
	}

}
