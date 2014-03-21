/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.menu;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 일반 상품 카테고리 리스트 조회 Input Value Object.
 * 
 * Updated on : 2014. 02. 12. Updated by : 유시혁.
 */

public class MenuSacReq extends CommonInfo {
	/**
	 * 
	 * 테넌트 메뉴 조회.
	 * 
	 * Updated on : 2014. 2. 27. Updated by : 유시혁.
	 */
	public interface SearchMenuList {
	}

	/**
	 * 
	 * 테넌트 메뉴 상세 조회.
	 * 
	 * Updated on : 2014. 2. 27. Updated by : 유시혁.
	 */
	public interface SearchMenuDetail {
	}

	/**
	 * 
	 * 대분류 전시 카테고리 조회.
	 * 
	 * Updated on : 2014. 2. 27. Updated by : 유시혁.
	 */
	public interface SearchTopMenuList {
	}

	/**
	 * 
	 * 세부분류 전시 카테고리 조회.
	 * 
	 * Updated on : 2014. 2. 27. Updated by : 유시혁.
	 */
	public interface SearchSubMenuList {
	}

	private static final long serialVersionUID = 11123123129L;

	private String tenantId; // 테넌트ID

	@NotBlank(groups = SearchMenuDetail.class)
	private String menuId; // 메뉴ID

	@NotBlank(groups = SearchSubMenuList.class)
	private String topMenuId; // 탑 메뉴ID

	private String systemId; // 시스템ID

	private String deviceModelCd; // device model code

	private String langCd; // language code

	private String mmDeviceModelCd; // 멀티미디어 표준 단말

	@Pattern(regexp = "|Y|N", groups = SearchSubMenuList.class)
	private String featuredExposureYn; // Featured 여부

	/**
	 * 
	 * <pre>
	 * 테넌트ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * 
	 * <pre>
	 * 테넌트ID.
	 * </pre>
	 * 
	 * @param tenantId
	 *            String
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴ID.
	 * </pre>
	 * 
	 * @param menuId
	 *            String
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 
	 * <pre>
	 * 시스템ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * 
	 * <pre>
	 * 시스템ID.
	 * </pre>
	 * 
	 * @param systemId
	 *            String
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * 
	 * <pre>
	 * device model code.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * 
	 * <pre>
	 * device model code.
	 * </pre>
	 * 
	 * @param deviceModelCd
	 *            String
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * 
	 * <pre>
	 * language code.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * 
	 * <pre>
	 * language code.
	 * </pre>
	 * 
	 * @param langCd
	 *            String
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * 
	 * <pre>
	 * 탑 메뉴ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * 
	 * <pre>
	 * 탑 메뉴ID.
	 * </pre>
	 * 
	 * @param topMenuId
	 *            String
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * 
	 * <pre>
	 * 멀티미디어 표준 단말.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMmDeviceModelCd() {
		return this.mmDeviceModelCd;
	}

	/**
	 * 
	 * <pre>
	 * 멀티미디어 표준 단말.
	 * </pre>
	 * 
	 * @param mmDeviceModelCd
	 *            String
	 */
	public void setMmDeviceModelCd(String mmDeviceModelCd) {
		this.mmDeviceModelCd = mmDeviceModelCd;
	}

	/**
	 * 
	 * <pre>
	 * Featured 여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFeaturedExposureYn() {
		return this.featuredExposureYn;
	}

	/**
	 * 
	 * <pre>
	 * Featured 여부.
	 * </pre>
	 * 
	 * @param featuredExposureYn
	 *            String
	 */
	public void setFeaturedExposureYn(String featuredExposureYn) {
		this.featuredExposureYn = featuredExposureYn;
	}

}
