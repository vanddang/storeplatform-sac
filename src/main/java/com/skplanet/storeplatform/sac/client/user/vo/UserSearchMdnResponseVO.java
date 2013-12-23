/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.user.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;

/**
 * MDN 회원 조회 응답
 * 
 * Updated on : 2013. 12. 20. Updated by : 한서구, 부르칸.
 */
@ProtobufMapping(UserSearchMdnResponseProto.UserSearchMdnResponse.class)
public class UserSearchMdnResponseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 결과 코드
	 */
	private String resultCode;

	/**
	 * 결과 메시지
	 */
	private String resultMessage;

	/**
	 * 사용자 Key 값
	 */
	private String userKey;

	public String getResultCode() {
		return this.resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return this.resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
