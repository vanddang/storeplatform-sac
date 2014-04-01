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
 * 이북/코믹 시리즈 조회 Request Value Object.
 * <p/>
 * Updated on : 2014. 01. 07. Updated by : 임근대, SK 플래닛.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class EpubSeriesReq extends CommonInfo {

    private static final long serialVersionUID = 1030336138926751912L;

    /**
     * 체널 ID
     */
    @NotNull @NotBlank
    private String channelId;
    /**
     * 도서 구분 코드 (DP004301 : 단행본, DP004302 : 연재, DP004303 : 잡지
     */
    private String bookTypeCd;
    /**
     * 상품 정렬 순서 (recent : 최신순, regDate : 등록순, noPayment: 미구매)
     */
    private String orderedBy;
    /**
     * 시작점 ROW (default 1)
     */
    private Integer offset = 1;
    /**
     * 페이지당 노출될 ROW 개수 (default 20)
     */
    private Integer count = 20;

    private String userKey;
    private String deviceKey;

    private String langCd;
    private String tenantId;
    private String deviceModel;

    private String filteredBy;
    /** 기준 Chapter */
    private Integer baseChapter;
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
	 * @return the bookTypeCd
	 */
	public String getBookTypeCd() {
		return bookTypeCd;
	}
	/**
	 * @param bookTypeCd the bookTypeCd to set
	 */
	public void setBookTypeCd(String bookTypeCd) {
		this.bookTypeCd = bookTypeCd;
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
	 * @return the filteredBy
	 */
	public String getFilteredBy() {
		return filteredBy;
	}
	/**
	 * @param filteredBy the filteredBy to set
	 */
	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
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

}
