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

import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonScReq;

/**
 * 트랜잭션 요청 VO.
 * 
 * Updated on : 2014. 1. 10. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseTransactionScReq extends PurchaseCommonScReq {

	private static final long serialVersionUID = 1L;

	private String prchsId;
	private String interfaceId;
	private Integer procSeq;
	private String procStatusCd;

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

}
