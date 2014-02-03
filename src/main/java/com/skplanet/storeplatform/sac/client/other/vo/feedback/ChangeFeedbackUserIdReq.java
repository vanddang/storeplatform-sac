/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.other.vo.feedback;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ChangeFeedbackUserIdReq Value Object
 * 
 * Updated on : 2014. 1. 27. Updated by : 김현일, 인크로스.
 */
public class ChangeFeedbackUserIdReq extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 사용자 ID.
	 */
	private String userId;

	/**
	 * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
