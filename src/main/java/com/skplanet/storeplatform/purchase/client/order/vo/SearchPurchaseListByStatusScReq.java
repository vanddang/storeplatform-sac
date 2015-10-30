/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 상태코드 기준의 구매정보 조회 요청 VO
 * 
 * Updated on : 2014. 1. 23. Updated by : 이승택, nTels.
 */
public class SearchPurchaseListByStatusScReq extends CommonInfo {
	private static final long serialVersionUID = 201401231L;

	private String tenantId;
	private String prchsId;
	private String statusCd;
	// 구매DB 파티션
	private String useTenantId;
	private String useInsdUsermbrNo;

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
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return this.statusCd;
	}

	/**
	 * @param statusCd
	 *            the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	/**
	 * @return the useTenantId
	 */
	public String getUseTenantId() {
		return this.useTenantId;
	}

	/**
	 * @param useTenantId
	 *            the useTenantId to set
	 */
	public void setUseTenantId(String useTenantId) {
		this.useTenantId = useTenantId;
	}

	/**
	 * @return the useInsdUsermbrNo
	 */
	public String getUseInsdUsermbrNo() {
		return this.useInsdUsermbrNo;
	}

	/**
	 * @param useInsdUsermbrNo
	 *            the useInsdUsermbrNo to set
	 */
	public void setUseInsdUsermbrNo(String useInsdUsermbrNo) {
		this.useInsdUsermbrNo = useInsdUsermbrNo;
	}

}
