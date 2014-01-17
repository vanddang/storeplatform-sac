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
 * 기구매체크.
 * 
 * Updated on : 2013. 12. 28. Updated by : 조용진, 엔텔스.
 */
public class ExistenceListRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<ExistenceRes> existenceListRes;

	/**
	 * @return the existenceListRes
	 */
	public List<ExistenceRes> getExistenceListRes() {
		return this.existenceListRes;
	}

	/**
	 * @param existenceListRes
	 *            the existenceListRes to set
	 */
	public void setExistenceListRes(List<ExistenceRes> existenceListRes) {
		this.existenceListRes = existenceListRes;
	}

}
