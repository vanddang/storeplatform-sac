package com.skplanet.storeplatform.sac.client.user.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;

/**
 * 판매자 회원 탈퇴사유 리스트 request VO
 * 
 * Updated on : 2013-12-20 Updated by : 반범진, 지티소프트.
 */
@ProtobufMapping(SellerSecedeInfoProto.SellerSecedeResonListRequest.class)
public class SellerSecedeResonListRequestVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String langCd;

	public String getLangCd() {
		return this.langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

}
