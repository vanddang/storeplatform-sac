/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import java.util.List;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseHeaderSacReq;

/**
 * 
 * 구매내역 이관 응답 요청 VO
 * 
 * Updated on : 2015. 2. 5. Updated by : 양주원, nTels.
 */
public class PurchaseTransferSacReq extends PurchaseHeaderSacReq {
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String marketDeviceKey;
	private int prchsCount;
	private List<PurchaseTransferSac> prchsList;

	/**
	 * @return the tenantId
	 */
	@Override
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	@Override
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the marketDeviceKey
	 */
	public String getMarketDeviceKey() {
		return this.marketDeviceKey;
	}

	/**
	 * @param marketDeviceKey
	 *            the marketDeviceKey to set
	 */
	public void setMarketDeviceKey(String marketDeviceKey) {
		this.marketDeviceKey = marketDeviceKey;
	}

	/**
	 * @return the prchsCount
	 */
	public int getPrchsCount() {
		return this.prchsCount;
	}

	/**
	 * @param prchsCount
	 *            the prchsCount to set
	 */
	public void setPrchsCount(int prchsCount) {
		this.prchsCount = prchsCount;
	}

	/**
	 * @return the prchsList
	 */
	public List<PurchaseTransferSac> getPrchsList() {
		return this.prchsList;
	}

	/**
	 * @param prchsList
	 *            the prchsList to set
	 */
	public void setPrchsList(List<PurchaseTransferSac> prchsList) {
		this.prchsList = prchsList;
	}

}
