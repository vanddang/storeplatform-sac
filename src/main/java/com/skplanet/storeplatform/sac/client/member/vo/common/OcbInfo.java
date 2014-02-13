package com.skplanet.storeplatform.sac.client.member.vo.common;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * OCB 정보
 * 
 * Updated on : 2014. 2. 12. Updated by : 심대진, 다모아 솔루션.
 */
public class OcbInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 Key.
	 */
	private String userKey = "";

	/**
	 * 인증방법 코드.
	 */
	private String authMethodCode = "";

	/**
	 * 카드 번호.
	 */
	private String cardNumber = "";

	/**
	 * 사용시작 일시.
	 */
	private String startDate = "";

	/**
	 * 사용종료 일시.
	 */
	private String endDate = "";

	/**
	 * 사용여부 (Y/N).
	 */
	private String isUsed = "";

	/**
	 * 등록 일시.
	 */
	private String regDate = "";

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

	/**
	 * @return String : startDate
	 */
	public String getStartDate() {
		return this.startDate;
	}

	/**
	 * @param startDate
	 *            String : the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return String : endDate
	 */
	public String getEndDate() {
		return this.endDate;
	}

	/**
	 * @param endDate
	 *            String : the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return String : isUsed
	 */
	public String getIsUsed() {
		return this.isUsed;
	}

	/**
	 * @param isUsed
	 *            String : the isUsed to set
	 */
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 * @return String : regDate
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * @param regDate
	 *            String : the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
