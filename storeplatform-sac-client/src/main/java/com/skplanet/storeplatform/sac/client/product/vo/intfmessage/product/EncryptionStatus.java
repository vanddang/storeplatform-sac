/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * EncryptionStatus Value Object.
 *
 * Updated on : 2014. 08. 04. Updated by : 양해엽, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EncryptionStatus extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String purchaseHide; // 구매내역 노출/숨김 유무
	private String updateAlarm; // 업데이트 알람 수신 여부

	public String getPurchaseHide() {
		return this.purchaseHide;
	}

	public void setPurchaseHide(String purchaseHide) {
		this.purchaseHide = purchaseHide;
	}

	public String getUpdateAlarm() {
		return this.updateAlarm;
	}

	public void setUpdateAlarm(String updateAlarm) {
		this.updateAlarm = updateAlarm;
	}


}
