package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * [REQUEST] 회원 Market PIN 등록
 * 
 * Updated on : 2015. 1. 11. Updated by : 임근대. SKP.
 */
public class CreateMarketPinReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 Key */
	@NotEmpty
	private String userKey;

	/** PIN 번호 */
	@NotEmpty
	@Size(min = 4, max = 4)
	private String pinNo;

	@NotEmpty
	private String realNameAuthDate;

	@NotEmpty
	@Pattern(regexp = "[0-9]{10,11}")
	private String realNameAuthMdn;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getPinNo() {
		return pinNo;
	}

	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}

	public String getRealNameAuthDate() {
		return realNameAuthDate;
	}

	public void setRealNameAuthDate(String realNameAuthDate) {
		this.realNameAuthDate = realNameAuthDate;
	}

	public String getRealNameAuthMdn() {
		return realNameAuthMdn;
	}

	public void setRealNameAuthMdn(String realNameAuthMdn) {
		this.realNameAuthMdn = realNameAuthMdn;
	}
}
