package com.skplanet.storeplatform.sac.client.member.vo.seller;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.2. 판매자 ID / Email 중복 체크 [REQUEST]
 * 
 * Updated on : 2014. 1. 23. Updated by : 김경복, 부르칸.
 */
public class DuplicateByIdEmailReq extends CommonInfo {

	/**
	 * Default SerialVersion.
	 */
	private static final long serialVersionUID = 1L;
	/** 검색구분 (id / email). */
	@NotBlank
	@Pattern(regexp = "^id|^email")
	private String keyType;
	/** 검색 값. */
	@NotBlank
	private String keyString;

	/**
	 * @return the keyType
	 */
	public String getKeyType() {
		return this.keyType;
	}

	/**
	 * @param keyType
	 *            the keyType to set
	 */
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	/**
	 * @return the keyString
	 */
	public String getKeyString() {
		return this.keyString;
	}

	/**
	 * @param keyString
	 *            the keyString to set
	 */
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

}
