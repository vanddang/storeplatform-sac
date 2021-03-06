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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ListFeedbackReq Value Object
 * 
 * Updated on : 2014. 10. 27. Updated by : 백승현, SP Tek.
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
	private String userKey;
	/**
	 * 정렬.
	 */
	@NotBlank
	@Pattern(regexp = "^recommend|^recent")
	private String orderedBy;

	/**
	 * 채널 ID.
	 */
	private String chnlId;
	/**
	 * 상품 타입.
	 */
	@Pattern(regexp = "^$|^shopping")
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
	 * 최신 버전 보기
	 */
	@Pattern(regexp = "^Y|^N")
	private String updateVer;

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

	/**
	 * @return String
	 */
	public String getUpdateVer() {
		return this.updateVer;
	}

	/**
	 * @param updateVer
	 *            updateVer
	 */
	public void setUpdateVer(String updateVer) {
		this.updateVer = updateVer;
	}

}
