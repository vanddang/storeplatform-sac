/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.feature.recommend;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 테마추천 Request Value Object.
 * 
 * Updated on : 2014. 02. 05. Updated by : 윤주영, SK 플래닛.
 */
public class ThemeRecommendSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4984188877183740905L;

	@NotNull
	@NotBlank
	@Pattern(regexp = "short|long|dummy")
	private String filteredBy; // 서비스 구분 (short|long)

	@NotNull
	@NotBlank
	private String userKey; // 사용자고유키

	@NotNull
	@NotBlank
	@Pattern(regexp = "msisdn")
	private String deviceIdType; // 기기ID유형

	@NotNull
	@NotBlank
	private String deviceId; // 기기ID

	private String ver; // New UI 지원

	private int offset = 1; // 시작점 ROW

	private int count = 20; // 페이지당 노출 ROW 수

	public String getFilteredBy() {
		return this.filteredBy;
	}

	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
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
	 * @return the deviceIdType
	 */
	public String getDeviceIdType() {
		return this.deviceIdType;
	}

	/**
	 * @param deviceIdType
	 *            the deviceIdType to set
	 */
	public void setDeviceIdType(String deviceIdType) {
		this.deviceIdType = deviceIdType;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the ver
	 */
	public String getVer() {
		return this.ver;
	}

	/**
	 * @param ver
	 *            the ver to set
	 */
	public void setVer(String ver) {
		this.ver = ver;
	}

	public int getOffset() {
		return this.offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
