package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 회원 OCB 정보 삭제
 * 
 * Updated on : 2014. 1. 6. Updated by : 심대진, 다모아 솔루션.
 */
public class RemoveOcbInformationReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 Key.
	 */
	@NotEmpty
	private String userKey = "";

	/**
	 * 카드 번호.
	 */
	@NotEmpty
	private String cardNumber = "";

	/**
	 * @return String : userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String : the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return String : cardNumber
	 */
	public String getCardNumber() {
		return this.cardNumber;
	}

	/**
	 * @param cardNumber
	 *            String : the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

}
