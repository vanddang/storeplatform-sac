/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.other.vo.feedback;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ListFeedbackReq Value Object
 * 
 * Updated on : 2014. 1. 23. Updated by : 김현일, 인크로스.
 */
public class ListFeedbackSacReq extends CommonInfo {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 상품 ID.
	 */
	@NotBlank
	private String prodId;
	/**
	 * 회원 Key.
	 */
	@NotBlank
	private String userKey;
	/**
	 * 정렬.
	 */
	@NotBlank
	private String orderedBy;
	/**
	 * 정렬 방식.
	 */
	@NotBlank
	private String orderedIn;

	/**
	 * 채널 ID.
	 */
	@NotBlank
	private String chnlId;
	/**
	 * 상품 타입.
	 */
	@NotBlank
	private String prodType;
	/**
	 * 페이징 시작위치.
	 */
	@NotNull
	private Integer offset;
	/**
	 * 페이징 갯수.
	 */
	@NotNull
	private Integer count;

	/**
	 * @return String
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            prodId
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return String
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            userKey
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return String
	 */
	public String getOrderedBy() {
		return this.orderedBy;
	}

	/**
	 * @param orderedBy
	 *            orderedBy
	 */
	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	/**
	 * @return String
	 */
	public String getOrderedIn() {
		return this.orderedIn;
	}

	/**
	 * @param orderedIn
	 *            orderedIn
	 */
	public void setOrderedIn(String orderedIn) {
		this.orderedIn = orderedIn;
	}

	/**
	 * @return String
	 */
	public String getChnlId() {
		return this.chnlId;
	}

	/**
	 * @param chnlId
	 *            chnlId
	 */
	public void setChnlId(String chnlId) {
		this.chnlId = chnlId;
	}

	/**
	 * @return String
	 */
	public String getProdType() {
		return this.prodType;
	}

	/**
	 * @param prodType
	 *            prodType
	 */
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	/**
	 * @return Integer
	 */
	public Integer getOffset() {
		return this.offset;
	}

	/**
	 * @param offset
	 *            offset
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * @return Integer
	 */
	public Integer getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            count
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

}
