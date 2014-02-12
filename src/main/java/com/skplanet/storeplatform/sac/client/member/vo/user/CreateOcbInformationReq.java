package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

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
	 * 카드 번호.
	 */
	@NotEmpty
	private String cardNumber = "";

	/**
	 * 인증방법 코드.
	 */
	private String authMethodCode = "";

	/**
	 * 사용종료 일시.
	 */
	@DateTimeFormat
	private String endDate = "";

	/**
	 * 사용시작 일시.
	 */
	private String startDate = "";

	/**
	 * 사용여부 (Y/N).
	 */
	private String isUsed = "";

	/**
	 * 등록일시.
	 */
	private String regDate = "";

	/**
	 * 등록 ID.
	 */
	private String regId = "";

	/**
	 * 수정일시.
	 */
	private String updateDate = "";

	/**
	 * 수정 ID.
	 */
	private String updateId = "";

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

	/**
	 * @return String : regId
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            String : the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return String : updateDate
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * @param updateDate
	 *            String : the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return String : updateId
	 */
	public String getUpdateId() {
		return this.updateId;
	}

	/**
	 * @param updateId
	 *            String : the updateId to set
	 */
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

}
