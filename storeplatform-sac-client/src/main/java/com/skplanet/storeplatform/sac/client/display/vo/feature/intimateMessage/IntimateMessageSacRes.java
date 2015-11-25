/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
lose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.IntimateMessage;

/**
 * Intimate Message 조회 Response Value Object.
 * 
 * Updated on : 2014. 02. 03. Updated by : 이태희.
 */
public class IntimateMessageSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private CommonResponse commonResponse;
	private List<IntimateMessage> intimateMessageList;

	/**
	 * @return the commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * @param commonResponse
	 *            the commonResponse to set
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * @return the intimateMessageList
	 */
	public List<IntimateMessage> getIntimateMessageList() {
		return this.intimateMessageList;
	}

	/**
	 * @param intimateMessageList
	 *            the intimateMessageList to set
	 */
	public void setIntimateMessageList(List<IntimateMessage> intimateMessageList) {
		this.intimateMessageList = intimateMessageList;
	}
}
