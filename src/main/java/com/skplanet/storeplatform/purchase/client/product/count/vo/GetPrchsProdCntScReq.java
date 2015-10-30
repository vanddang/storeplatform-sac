/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.product.count.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 27. Updated by : nTels_cswoo81, nTels.
 */
public class GetPrchsProdCntScReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String updId;
	private String currProcStatus;
	private String newProcStatus;
	private Integer disPurCnt;

	/**
	 * @return the currProcStatus
	 */
	public String getCurrProcStatus() {
		return this.currProcStatus;
	}

	/**
	 * @param currProcStatus
	 *            the currProcStatus to set
	 */
	public void setCurrProcStatus(String currProcStatus) {
		this.currProcStatus = currProcStatus;
	}

	/**
	 * @return the newProcStatus
	 */
	public String getNewProcStatus() {
		return this.newProcStatus;
	}

	/**
	 * @param newProcStatus
	 *            the newProcStatus to set
	 */
	public void setNewProcStatus(String newProcStatus) {
		this.newProcStatus = newProcStatus;
	}

	/**
	 * @return the updId
	 */
	public String getUpdId() {
		return this.updId;
	}

	/**
	 * @param updId
	 *            the updId to set
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}

	/**
	 * @return the disPurCnt
	 */
	public Integer getDisPurCnt() {
		return this.disPurCnt;
	}

	/**
	 * @param disPurCnt
	 *            the disPurCnt to set
	 */
	public void setDisPurCnt(Integer disPurCnt) {
		this.disPurCnt = disPurCnt;
	}

}
