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
 * MDN 회원 가입 요청
 * 
 * Updated on : 2013. 12. 19. Updated by : 심대진, 다모아 솔루션.
 */
@ProtobufMapping(UserJoinMdnRequestProto.UserJoinMdnRequest.class)
public class UserJoinMdnRequestVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 본인의 생년월일 (14세 미만 가입일 경우) 14세 미만이면 필수값
	 */
	private String birth;

	public String getBirth() {
		return this.birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
