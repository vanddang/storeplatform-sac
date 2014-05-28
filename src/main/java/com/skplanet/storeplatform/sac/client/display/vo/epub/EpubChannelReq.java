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

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 이북/코믹 채널 상세 조회 Request Value Object.
 * <p/>
 * Updated on : 2014. 01. 07. Updated by : 임근대, SK 플래닛.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class EpubChannelReq extends CommonInfo {

    private static final long serialVersionUID = 1L;
    @NotNull @NotBlank
    private String channelId; // 체널 ID
    private String userKey;
    private String deviceKey;

    private String langCd;
    private String tenantId;
    private String deviceModel;

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
    

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
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
