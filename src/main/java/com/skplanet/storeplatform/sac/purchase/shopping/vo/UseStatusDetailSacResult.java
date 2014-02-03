/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 쇼핑쿠폰 사용여부 상세 결과 VO.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
public class UseStatusDetailSacResult extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String cpPublishCd;
	private String useStatusCd;

	/**
	 * @return the cpPublishCd
	 */
	public String getCpPublishCd() {
		return this.cpPublishCd;
	}

	/**
	 * @param cpPublishCd
	 *            the cpPublishCd to set
	 */
	public void setCpPublishCd(String cpPublishCd) {
		this.cpPublishCd = cpPublishCd;
	}

	/**
	 * @return the useStatusCd
	 */
	public String getUseStatusCd() {
		return this.useStatusCd;
	}

	/**
	 * @param useStatusCd
	 *            the useStatusCd to set
	 */
	public void setUseStatusCd(String useStatusCd) {
		this.useStatusCd = useStatusCd;
	}

}
