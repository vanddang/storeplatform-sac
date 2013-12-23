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
 * MDN 회원 조회 요청
 * 
 * Updated on : 2013. 12. 20. Updated by : 한서구, 부르칸.
 */
@ProtobufMapping(UserSearchMdnRequestProto.UserSearchMdnRequest.class)
public class UserSearchMdnRequestVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userID;

	public String getUserID() {
		return this.userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
