/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매인증 요청 VO
 * 
 * Updated on : 2014. 2. 26. Updated by : 이승택, nTels.
 */
public class VerifyOrderSacReq extends CommonInfo {
	private static final long serialVersionUID = 201402261L;

	@NotBlank
	private String prchsId; // 구매ID
	private String mctSpareParam; // 가맹점 파라미터
	private String marketDeviceKey; // 타 스토어 디바이스 Key
	private String userAuthKey; // 인증 Key

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the mctSpareParam
	 */
	public String getMctSpareParam() {
		return this.mctSpareParam;
	}

	/**
	 * @param mctSpareParam
	 *            the mctSpareParam to set
	 */
	public void setMctSpareParam(String mctSpareParam) {
		this.mctSpareParam = mctSpareParam;
	}

	/**
	 * @return the marketDeviceKey
	 */
	public String getMarketDeviceKey() {
		return this.marketDeviceKey;
	}

	/**
	 * @param marketDeviceKey
	 *            the marketDeviceKey to set
	 */
	public void setMarketDeviceKey(String marketDeviceKey) {
		this.marketDeviceKey = marketDeviceKey;
	}

	/**
	 * @return the userAuthKey
	 */
	public String getUserAuthKey() {
		return this.userAuthKey;
	}

	/**
	 * @param userAuthKey
	 *            the userAuthKey to set
	 */
	public void setUserAuthKey(String userAuthKey) {
		this.userAuthKey = userAuthKey;
	}

}
