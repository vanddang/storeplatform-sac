/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.epub;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 이북/코믹 시리즈 조회 Request Value Object.
 *
 * Updated on : 2014. 01. 07. Updated by : 임근대, SK 플래닛.
 */
public class EpubSeriesReq extends CommonInfo {

	private static final long serialVersionUID = 1030336138926751912L;

	/** 체널 ID */
	private String channelld;
	/** 도서 구분 코드 (DP004301 : 단행본, DP004302 : 연재, DP004303 : 잡지 */
	private String bookTypeCd;
	/** 상품 정렬 순서 (recent : 최신순, regDate : 등록순, noPayment: 미구매) */
	private String orderedBy;
	/** 시작점 ROW (default 1) */
	private Integer offset = 1;
	/** 페이지당 노출될 ROW 개수 (default 20) */
	private Integer count = 20;

	private String langCd;
	private String tenantId;
	private String deviceModel;

	public String getChannelld() {
		return this.channelld;
	}

	public void setChannelld(String channelld) {
		this.channelld = channelld;
	}

	public String getBookTypeCd() {
		return this.bookTypeCd;
	}

	public void setBookTypeCd(String bookTypeCd) {
		this.bookTypeCd = bookTypeCd;
	}

	public String getOrderedBy() {
		return this.orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public Integer getOffset() {
		return this.offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getLangCd() {
		return this.langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getDeviceModel() {
		return this.deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

}
