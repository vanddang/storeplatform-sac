/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.transaction.vo;

import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonScRes;

/**
 * 트랜잭션 응답.
 * 
 * Updated on : 2014. 1. 10. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseTransactionScRes extends PurchaseCommonScRes {

	private static final long serialVersionUID = 1L;

	private Integer procCount;

	private String tenantId;
	private String prchsId;
	private String interfaceId;
	private Integer procSeq;
	private String procStatusCd;

	private boolean isDuplicate = false;

	/**
	 * @return the procCount
	 */
	public Integer getProcCount() {
		return this.procCount;
	}

	/**
	 * @param procCount
	 *            the procCount to set
	 */
	public void setProcCount(Integer procCount) {
		this.procCount = procCount;
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
	 * @return the interfaceId
	 */
	public String getInterfaceId() {
		return this.interfaceId;
	}

	/**
	 * @param interfaceId
	 *            the interfaceId to set
	 */
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	/**
	 * @return the procSeq
	 */
	public Integer getProcSeq() {
		return this.procSeq;
	}

	/**
	 * @param procSeq
	 *            the procSeq to set
	 */
	public void setProcSeq(Integer procSeq) {
		this.procSeq = procSeq;
	}

	/**
	 * @return the procStatusCd
	 */
	public String getProcStatusCd() {
		return this.procStatusCd;
	}

	/**
	 * @param procStatusCd
	 *            the procStatusCd to set
	 */
	public void setProcStatusCd(String procStatusCd) {
		this.procStatusCd = procStatusCd;
	}

	/**
	 * @return the isDuplicate
	 */
	public boolean isDuplicate() {
		return this.isDuplicate;
	}

	/**
	 * @param isDuplicate
	 *            the isDuplicate to set
	 */
	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

}
