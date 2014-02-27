/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.appguide;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * App guide 조회 Request Value Object.
 * 
 * Updated on : 2014. 02. 26. Updated by : 윤주영, SK 플래닛.
 */
public class AppguideSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2947876582860329839L;

	private String filteredBy; // 서비스 구분 (sample/dummy)

	private String b2bProd;

	private String listId;

	private String menuId;

	private String packageName;

	private String osVersion;

	private String themeId;

	private String userKey; // 사용자고유키

	private String deviceIdType; // 기기ID유형

	private String deviceId; // 기기ID

	private int offset = 1; // 시작점 ROW

	private int count = 100; // 페이지당 노출 ROW 수

	/**
	 * @return the b2bProd
	 */
	public String getB2bProd() {
		return this.b2bProd;
	}

	/**
	 * @param b2bProd
	 *            the b2bProd to set
	 */
	public void setB2bProd(String b2bProd) {
		this.b2bProd = b2bProd;
	}

	/**
	 * @return the listId
	 */
	public String getListId() {
		return this.listId;
	}

	/**
	 * @param listId
	 *            the listId to set
	 */
	public void setListId(String listId) {
		this.listId = listId;
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * @param menuId
	 *            the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return this.packageName;
	}

	/**
	 * @param packageName
	 *            the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the osVersion
	 */
	public String getOsVersion() {
		return this.osVersion;
	}

	/**
	 * @param osVersion
	 *            the osVersion to set
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	/**
	 * @return the themeId
	 */
	public String getThemeId() {
		return this.themeId;
	}

	/**
	 * @param themeId
	 *            the themeId to set
	 */
	public void setThemeId(String themeId) {
		this.themeId = themeId;
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
	 * @return the filteredBy
	 */
	public String getFilteredBy() {
		return this.filteredBy;
	}

	/**
	 * @param filteredBy
	 *            the filteredBy to set
	 */
	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return this.offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
