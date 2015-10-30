/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.purchase.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 기구매체크 요청.
 * 
 * Updated on : 2013. 12. 28. Updated by : 조용진, 엔텔스.
 */
public class ExistenceReq extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	private String tenantId; // Tenant ID
	@NotNull
	@NotEmpty
	private String userKey; // 내부사용자번호
	private String deviceKey; // 내부디바이스ID
	private String prchsId; // 구매ID
	private List<ExistenceItem> existenceItem; // 기구매 상품 리스트

	private String deviceHistoryYn; // 해당값이 Y일 경우 무조건 디바이스 기반구매내역 조회

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
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
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
	 * @return the existenceItem
	 */
	public List<ExistenceItem> getExistenceItem() {
		return this.existenceItem;
	}

	/**
	 * @param existenceItem
	 *            the existenceItem to set
	 */
	public void setExistenceItem(List<ExistenceItem> existenceItem) {
		this.existenceItem = existenceItem;
	}

	/**
	 * @return the deviceHistoryYn
	 */
	public String getDeviceHistoryYn() {
		return this.deviceHistoryYn;
	}

	/**
	 * @param deviceHistoryYn
	 *            the deviceHistoryYn to set
	 */
	public void setDeviceHistoryYn(String deviceHistoryYn) {
		this.deviceHistoryYn = deviceHistoryYn;
	}

}
