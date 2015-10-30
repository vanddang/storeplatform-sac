/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * SC Tlog 요청 Value Object.
 * 
 * Updated on : 2015. 3. 18.
 */
public class TlogRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * @return commonRequest
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * @param commonRequest
	 *            CommonRequest
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

	/**
	 * tlog VO.
	 */
	private TlogInfo tlogInfo;

	/**
	 * @return tlogInfo
	 */
	public TlogInfo getTlogInfo() {
		return this.tlogInfo;
	}

	/**
	 * @param tlogInfo
	 *            TlogInfo
	 */
	public void setTlogInfo(TlogInfo tlogInfo) {
		this.tlogInfo = tlogInfo;
	}

}
