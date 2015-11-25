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

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매내역숨김처리 컴포넌트 요청.
 * 
 * Updated on : 2014. 12. 20. Updated by : 조용진, 엔텔스.
 */
public class HidingListSacRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<HidingSacRes> responseList; // 숨김처리 응답리스트

	/**
	 * @return the responseList
	 */
	public List<HidingSacRes> getResponseList() {
		return this.responseList;
	}

	/**
	 * @param responseList
	 *            the responseList to set
	 */
	public void setResponseList(List<HidingSacRes> responseList) {
		this.responseList = responseList;
	}

}
