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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ListMyFeedbackReq Value Object
 * 
 * Updated on : 2014. 1. 23. Updated by : 김현일, 인크로스.
 */
public class ListMyFeedbackSacReq extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 상품ID 목록(구분자, ).
	 */
	private String prodIds;
	/**
	 * 회원 Key.
	 */
	private String userKey;
	/**
	 * 채널 ID.
	 */
	private String chnlId;
	/**
	 * 상품 타입.
	 */
	private String prodType;
	/**
	 * 페이징 시작 위치.
	 */
	private int offset;
	/**
	 * 페이징 개수.
	 */
	private int count;

	/**
	 * @return String
	 */
	public String getProdIds() {
		return this.prodIds;
	}

	/**
	 * @param prodIds
	 *            prodIds
	 */
	public void setProdIds(String prodIds) {
		this.prodIds = prodIds;
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
	 * @return int
	 */
	public int getOffset() {
		return this.offset;
	}

	/**
	 * @param offset
	 *            offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return int
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            count
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
