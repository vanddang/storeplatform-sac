package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.13. 판매자회원 password 수정 [REQUEST]
 * 
 * Updated on : 2014. 2. 6. Updated by : 김경복, 부르칸
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ModifyPasswordSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 인증 키. */
	@NotBlank
	private String sessionKey;
	/** 판매자 key. */
	@NotBlank
	private String sellerKey;
	/** 이전 비밀번호. */
	@NotBlank
	private String oldPW;
	/** 새 비밀번호. */
	@NotBlank
	private String newPW;

	/**
	 * @return the sessionKey
	 */
	public String getSessionKey() {
		return this.sessionKey;
	}

	/**
	 * @param sessionKey
	 *            the sessionKey to set
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	/**
	 * @return the sellerKey
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * @param sellerKey
	 *            the sellerKey to set
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * @return the oldPW
	 */
	public String getOldPW() {
		return this.oldPW;
	}

	/**
	 * @param oldPW
	 *            the oldPW to set
	 */
	public void setOldPW(String oldPW) {
		this.oldPW = oldPW;
	}

	/**
	 * @return the newPW
	 */
	public String getNewPW() {
		return this.newPW;
	}

	/**
	 * @param newPW
	 *            the newPW to set
	 */
	public void setNewPW(String newPW) {
		this.newPW = newPW;
	}

}
