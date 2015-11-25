/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.other;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * OtherTMembershipUseStatusRes Vo.
 * 
 * Updated on : 2014. 04. 23. Updated by : 김형식, SK 플래닛.
 */
public class OtherTMembershipUseStatusRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String useStatus;

	/**
	 * @return the useStatus
	 */
	public String getUseStatus() {
		return this.useStatus;
	}

	/**
	 * @param useStatus
	 *            the useStatus to set
	 */
	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

}
