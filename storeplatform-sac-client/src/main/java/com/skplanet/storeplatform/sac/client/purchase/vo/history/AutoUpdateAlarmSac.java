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

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 자동업데이트 거부/거부취소 요청.
 * 
 * Updated on : 2014. 2. 25. Updated by : 조용진, NTELS.
 */
public class AutoUpdateAlarmSac extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	private String prodId; // 상품 ID
	@NotNull
	@NotEmpty
	private String alarmYn; // 알람YN

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the alarmYn
	 */
	public String getAlarmYn() {
		return this.alarmYn;
	}

	/**
	 * @param alarmYn
	 *            the alarmYn to set
	 */
	public void setAlarmYn(String alarmYn) {
		this.alarmYn = alarmYn;
	}

}
