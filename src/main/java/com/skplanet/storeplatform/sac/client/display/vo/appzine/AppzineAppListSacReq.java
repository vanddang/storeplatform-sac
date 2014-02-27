/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.appzine;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Appzine 앱 목록 조회 Input Value Object.
 * 
 * Updated on : 2014. 02. 10. Updated by : 유시혁.
 */
public class AppzineAppListSacReq extends CommonInfo {

	private static final long serialVersionUID = 11123123129L;

	private String tenantId; // 테넌트ID

	private String menuId; // 메뉴ID

	private String systemId; // 시스템ID

	private String deviceModelCd; // device model code

	private String langCd; // language code

	private Integer offset; // 시작점 ROW

	private Integer count; // 페이지당 노출될 ROW 개수

	@NotBlank
	private String appznNo; // 앱진 번호
	@NotBlank
	@Pattern(regexp = "^001$|^002$|^003$")
	private String appType; // 앱 TYPE(인기:001, 신규:002, 테마:003)

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
	 * 시작점 ROW.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getOffset() {
		return this.offset;
	}

	/**
	 * 
	 * <pre>
	 * 시작점 ROW.
	 * </pre>
	 * 
	 * @param offset
	 *            Integer
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * 
	 * <pre>
	 * 페이지당 노출될 ROW 개수.
	 * </pre>
	 * 
	 * @return Integer
	 */
	public Integer getCount() {
		return this.count;
	}

	/**
	 * 
	 * <pre>
	 * 페이지당 노출될 ROW 개수.
	 * </pre>
	 * 
	 * @param count
	 *            Integer
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 
	 * <pre>
	 * 앱진 번호.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppznNo() {
		return this.appznNo;
	}

	/**
	 * 
	 * <pre>
	 * 앱진 번호.
	 * </pre>
	 * 
	 * @param appznNo
	 *            String
	 */
	public void setAppznNo(String appznNo) {
		this.appznNo = appznNo;
	}

	/**
	 * 
	 * <pre>
	 * 앱 TYPE(인기:001, 신규:002, 테마:003).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppType() {
		return this.appType;
	}

	/**
	 * 
	 * <pre>
	 * 앱 TYPE(인기:001, 신규:002, 테마:003).
	 * </pre>
	 * 
	 * @param appType
	 *            String
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}
}
