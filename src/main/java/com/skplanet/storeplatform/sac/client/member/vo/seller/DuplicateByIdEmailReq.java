package com.skplanet.storeplatform.sac.client.member.vo.seller;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class DuplicateByIdEmailReq extends CommonInfo {

	/**
	 * Default SerialVersion
	 */
	private static final long serialVersionUID = 1L;
	/** 검색구분 (id / email) */
	@NotNull
	@NotBlank
	private String keyType;
	/** 검색 값 */
	@NotNull
	@NotBlank
	private String keyString;

	public String getKeyType() {
		return this.keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getKeyString() {
		return this.keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
}
