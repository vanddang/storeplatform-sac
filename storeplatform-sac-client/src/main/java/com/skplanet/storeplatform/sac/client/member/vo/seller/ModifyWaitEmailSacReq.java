package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.33. 가가입 이메일 수정 [REQUEST]
 * 
 * Updated on : 2014. 3. 3. Updated by : Rejoice, Burkhan
 */
public class ModifyWaitEmailSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 회원 Id. */
	@NotBlank
	private String sellerId;
	/** 새 이메일 주소. */
	@NotBlank
	private String newEmailAddress;

	/**
	 * @return the sellerId
	 */
	public String getSellerId() {
		return this.sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the newEmailAddress
	 */
	public String getNewEmailAddress() {
		return this.newEmailAddress;
	}

	/**
	 * @param newEmailAddress
	 *            the newEmailAddress to set
	 */
	public void setNewEmailAddress(String newEmailAddress) {
		this.newEmailAddress = newEmailAddress;
	}

}
