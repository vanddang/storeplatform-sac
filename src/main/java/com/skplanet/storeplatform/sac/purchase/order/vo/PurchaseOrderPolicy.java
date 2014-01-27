/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * (임시) 구매 제한 정책 VO
 * 
 * Updated on : 2014. 1. 24. Updated by : 이승택, nTels.
 */
public class PurchaseOrderPolicy extends CommonInfo {
	private static final long serialVersionUID = 201401241L;

	private Integer id; // 정책 ID
	private String groupId; // 정책 그룹 ID
	private String name; // 정책명
	private String description; // 정책 설명
	private Integer patternType; // 정책 패턴 타입
	private String targetCd; // 정책 대상 상품그룹코드
	private Integer order; // 정책 우선순위 (낮을수록 먼저)
	private String value; // 정책 값
	private Integer checkObject; // 체크할 src대상
	private Boolean bCharge; // 유료 구매만 대상 여부
	private Boolean bSktTelecom; // SKT 통신사 대상 여부

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the patternType
	 */
	public Integer getPatternType() {
		return this.patternType;
	}

	/**
	 * @param patternType
	 *            the patternType to set
	 */
	public void setPatternType(Integer patternType) {
		this.patternType = patternType;
	}

	/**
	 * @return the targetCd
	 */
	public String getTargetCd() {
		return this.targetCd;
	}

	/**
	 * @param targetCd
	 *            the targetCd to set
	 */
	public void setTargetCd(String targetCd) {
		this.targetCd = targetCd;
	}

	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return this.order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the checkObject
	 */
	public Integer getCheckObject() {
		return this.checkObject;
	}

	/**
	 * @param checkObject
	 *            the checkObject to set
	 */
	public void setCheckObject(Integer checkObject) {
		this.checkObject = checkObject;
	}

	/**
	 * @return the bCharge
	 */
	public Boolean getbCharge() {
		return this.bCharge;
	}

	/**
	 * @param bCharge
	 *            the bCharge to set
	 */
	public void setbCharge(Boolean bCharge) {
		this.bCharge = bCharge;
	}

	/**
	 * @return the bSktTelecom
	 */
	public Boolean getbSktTelecom() {
		return this.bSktTelecom;
	}

	/**
	 * @param bSktTelecom
	 *            the bSktTelecom to set
	 */
	public void setbSktTelecom(Boolean bSktTelecom) {
		this.bSktTelecom = bSktTelecom;
	}

}
