package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 회원 OCB 정보 등록/수정
 * 
 * Updated on : 2014. 1. 6. Updated by : 심대진, 다모아 솔루션.
 */
public class CreateOcbInformationReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 Key.
	 */
	@NotEmpty
	private String userKey = "";

	/**
	 * 인증방법 코드.
	 */
	@NotEmpty
	private String authMethodCode = "";

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
	 * @return String : authMethodCode
	 */
	public String getAuthMethodCode() {
		return this.authMethodCode;
	}

	/**
	 * @param authMethodCode
	 *            String : the authMethodCode to set
	 */
	public void setAuthMethodCode(String authMethodCode) {
		this.authMethodCode = authMethodCode;
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
