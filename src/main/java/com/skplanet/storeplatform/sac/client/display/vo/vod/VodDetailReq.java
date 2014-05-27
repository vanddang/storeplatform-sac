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
 * <p/>
 * Updated on : 2014. 01. 07. Updated by : 임근대, SK 플래닛.
 */
public class VodDetailReq extends CommonInfo {

    private static final long serialVersionUID = 3567228512653706925L;

    /**
     * 체널 ID
     */
    @NotEmpty
    private String channelId;

    /**
     * 상품 정렬 순서
     */
    private String orderedBy;

    /**
     * 시작점 ROW, default : 1
     */
    private Integer offset = 1;

    /**
     * 페이지당 노출될 ROW 개수, default :20
     */
    private Integer count = 20;

    /**
     * 언어 코드
     */
    private String langCd;

    /**
     * Tenant ID
     */
    private String tenantId;

    /**
     * 디바이스 모델
     */
    private String deviceModel;

    /** 기준 Chapter */
    private Integer baseChapter;

    private String userKey;
    private String deviceKey;
    
    /**
     * 판매 중지(다운로드 허용) 상품 포함 여부
     * <ul>
     * <li>- 기구매 체크 로직에서 판매 중지, 판매불가-다운로드 허용 상태의 상품도 조회 가능해야함</li>
     * <li>- 서비스에 영향이 없도록 기구매 체크 하는 부분에서만 파라미터를 추가하여 사용</li>
     * <ul>
     * <br/>
     * 
     * Parameter
     * <ul>
     * <li>N (Default) : 판매 상태</li>
     * <li>Y :(판매중지, 판매불가-다운허용)</li>
     * </ul>
     * 
     */
    private String includeProdStopStatus;
    
	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}
	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	/**
	 * @return the orderedBy
	 */
	public String getOrderedBy() {
		return orderedBy;
	}
	/**
	 * @param orderedBy the orderedBy to set
	 */
	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}
	/**
	 * @return the offset
	 */
	public Integer getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * @return the langCd
	 */
	public String getLangCd() {
		return langCd;
	}
	/**
	 * @param langCd the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}
	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * @return the deviceModel
	 */
	public String getDeviceModel() {
		return deviceModel;
	}
	/**
	 * @param deviceModel the deviceModel to set
	 */
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	/**
	 * @return the baseChapter
	 */
	public Integer getBaseChapter() {
		return baseChapter;
	}
	/**
	 * @param baseChapter the baseChapter to set
	 */
	public void setBaseChapter(Integer baseChapter) {
		this.baseChapter = baseChapter;
	}
	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return userKey;
	}
	/**
	 * @param userKey the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return deviceKey;
	}
	/**
	 * @param deviceKey the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}
	/**
	 * @return the includeProdStopStatus
	 */
	public String getIncludeProdStopStatus() {
		return includeProdStopStatus;
	}
	/**
	 * @param includeProdStopStatus the includeProdStopStatus to set
	 */
	public void setIncludeProdStopStatus(String includeProdStopStatus) {
		this.includeProdStopStatus = includeProdStopStatus;
	}

}
