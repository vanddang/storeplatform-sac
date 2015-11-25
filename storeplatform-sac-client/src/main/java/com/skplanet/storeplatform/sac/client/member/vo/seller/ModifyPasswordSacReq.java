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
	private String oldPw;
	/** 새 비밀번호. */
	@NotBlank
	private String newPw;

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
	 * @return the oldPw
	 */
	public String getOldPw() {
		return this.oldPw;
	}

	/**
	 * @param oldPw
	 *            the oldPw to set
	 */
	public void setOldPw(String oldPw) {
		this.oldPw = oldPw;
	}

	/**
	 * @return the newPw
	 */
	public String getNewPw() {
		return this.newPw;
	}

	/**
	 * @param newPw
	 *            the newPw to set
	 */
	public void setNewPw(String newPw) {
		this.newPw = newPw;
	}

}
