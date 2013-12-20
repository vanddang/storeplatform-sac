package com.skplanet.storeplatform.sac.client.user.vo;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;

/**
 * 판매자 회원 탈퇴사유 VO
 * 
 * Updated on : 2013-12-20 Updated by : 반범진, 지티소프트.
 */
@ProtobufMapping(SellerSecedeInfoProto.SellerSecedeResonInfo.class)
public class SellerSecedeResonVO {

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
