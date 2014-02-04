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
 * ChangeFeedbackUserKeyRes Value Object
 * 
 * Updated on : 2014. 1. 28. Updated by : 김현일, 인크로스.
 */
public class ChangeFeedbackUserKeySacRes extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 결과 상태.
	 */
	private String resultStatus;

	/**
	 * @return String
	 */
	public String getResultStatus() {
		return this.resultStatus;
	}

	/**
	 * @param resultStatus
	 *            resultStatus
	 */
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

}
