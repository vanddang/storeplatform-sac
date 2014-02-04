/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.vod;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Vod 상세 조회 Input Value Object.
 *
 * Updated on : 2014. 01. 07. Updated by : 임근대, SK 플래닛.
 */
public class VodDetailReq extends CommonInfo {

	private static final long serialVersionUID = 3567228512653706925L;

	/** 체널 ID */
	@NotEmpty
	private String channelId;

	/** 상품 정렬 순서 */
	@NotEmpty
	private String orderedBy;

	/** 시작점 ROW, default : 1 */
	private Integer offset = 1;

	/** 페이지당 노출될 ROW 개수, default :20 */
	private Integer count = 20;

	/** 언어 코드 */
	private String langCd;

	/** Tenant ID */
	private String tenantId;

	/** 디바이스 모델 */
	private String deviceModel;

	/** 대표이미지 코드 */
	private String imgCd;

	public String getChannelld() {
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
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

	public String getChannelId() {
		return this.channelId;
	}

	public String getImgCd() {
		return this.imgCd;
	}

	public void setImgCd(String imgCd) {
		this.imgCd = imgCd;
	}

}
