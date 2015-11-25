/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.feedback.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * MqSendData Value Object
 * 
 * Updated on : 2015. 6. 4. Updated by : 심대진, 다모아 솔루션.
 */
public class SendMqData extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * SEND_MQ_DATA : MQ 연동 전체 정보, ID : MQ연동시 사용할 ContentId만 추출.
	 */
	private String returnType;

	/**
	 * 테넌트 아이디.
	 */
	private String tenantId;

	/**
	 * 상품 아이디.
	 */
	private String prodId;

	/**
	 * 기존 평균 평점.
	 */
	private String oldScore;

	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return this.returnType;
	}

	/**
	 * @param returnType
	 *            the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

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
	 * @return the oldScore
	 */
	public String getOldScore() {
		return this.oldScore;
	}

	/**
	 * @param oldScore
	 *            the oldScore to set
	 */
	public void setOldScore(String oldScore) {
		this.oldScore = oldScore;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
