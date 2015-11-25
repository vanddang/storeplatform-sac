/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.history;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 기구매체크.
 * 
 * Updated on : 2013. 12. 28. Updated by : 조용진, 엔텔스.
 */
public class ExistenceSacResV2 extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private List<ExistenceListSacResV2> userList;

	/**
	 * @return the userList
	 */
	public List<ExistenceListSacResV2> getUserList() {
		return this.userList;
	}

	/**
	 * @param userList
	 *            the userList to set
	 */
	public void setUserList(List<ExistenceListSacResV2> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
