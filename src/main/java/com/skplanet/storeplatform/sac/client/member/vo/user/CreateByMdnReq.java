package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * [REQUEST] 모바일 전용 회원 가입
 * 
 * Updated on : 2014. 1. 2. Updated by : 심대진, 다모아 솔루션.
 */
public class CreateByMdnReq implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	// deviceId
	// deviceTelecom
	// joinId
	// deviceModelNo
	// agreementList [
	// extraAgreementID
	// extraAgreementVersion
	// isExtraAgreement
	// ]
	// ownBirth
	// parentType
	// realNameMethod
	// parentName
	// parentCI
	// parentEmail
	// parentTelecom
	// parentMdn
	// parentResident
	// parentBirth

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
