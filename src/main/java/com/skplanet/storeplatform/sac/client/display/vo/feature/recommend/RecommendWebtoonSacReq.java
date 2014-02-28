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

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 웹툰 request Input Value Object.
 * 
 * Updated on : 2013. 12. 23. Updated by : 김형식, SK 플래닛.
 */
public class RecommendWebtoonSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String tenantId; // 테넌트ID
	private String systemId; // 시스템ID
	private String imageCd; // 이미지코드
	private String upMenuId; // 상위 메뉴아이디
	private String menuId; // 메뉴아이디
	@NotBlank
	@Pattern(regexp = "^ADM000000016")
	private String listId; // 리스트아이디
	private String weekDayCd; // 요일별 구분 코드
	private String langCd; // 언어코드
	private String deviceModelCd; // 디바이스 모델 코드
	private String stdDt;
	private Integer offset = 1; // offset
	private Integer count = 20; // count

	/**
	 * 
	 * <pre>
	 * 테넌트 아이디.
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
	 * 테넌트 아이디.
	 * </pre>
	 * 
	 * @param tenantId
	 *            tenantId
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * 
	 * <pre>
	 * 시스템 아이디.
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
	 * 시스템 아이디.
	 * </pre>
	 * 
	 * @param systemId
	 *            systemId
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * 
	 * <pre>
	 * 이미지 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImageCd() {
		return this.imageCd;
	}

	/**
	 * 
	 * <pre>
	 * 이미지 코드.
	 * </pre>
	 * 
	 * @param imageCd
	 *            imageCd
	 */
	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴 아이디.
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
	 * 메뉴 아이디.
	 * </pre>
	 * 
	 * @param menuId
	 *            menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 
	 * <pre>
	 * 상위 메뉴 아이디.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUpMenuId() {
		return this.upMenuId;
	}

	/**
	 * 
	 * <pre>
	 * 상위 메뉴 아이디.
	 * </pre>
	 * 
	 * @param upMenuId
	 *            upMenuId
	 */
	public void setUpMenuId(String upMenuId) {
		this.upMenuId = upMenuId;
	}

	/**
	 * 
	 * <pre>
	 * 리스트 아이디.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getListId() {
		return this.listId;
	}

	/**
	 * 
	 * <pre>
	 * 리스트 아이디.
	 * </pre>
	 * 
	 * @param listId
	 *            listId
	 */
	public void setListId(String listId) {
		this.listId = listId;
	}

	/**
	 * 
	 * <pre>
	 * 요일 구분 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getWeekDayCd() {
		return this.weekDayCd;
	}

	/**
	 * 
	 * <pre>
	 * 요일 구분 코드.
	 * </pre>
	 * 
	 * @param weekDayCd
	 *            weekDayCd
	 */
	public void setWeekDayCd(String weekDayCd) {
		this.weekDayCd = weekDayCd;
	}

	/**
	 * 
	 * <pre>
	 * 배치완료일자.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getStdDt() {
		return this.stdDt;
	}

	/**
	 * 
	 * <pre>
	 * 배치완료일자.
	 * </pre>
	 * 
	 * @param stdDt
	 *            stdDt
	 */
	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}

	/**
	 * 
	 * <pre>
	 * 단말모델명.
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
	 * 단말모델명.
	 * </pre>
	 * 
	 * @param deviceModelCd
	 *            deviceModelCd
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * 
	 * <pre>
	 * 언어코드.
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
	 * 언어코드.
	 * </pre>
	 * 
	 * @param langCd
	 *            langCd
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * 
	 * <pre>
	 * offset.
	 * </pre>
	 * 
	 * @return int
	 */
	public Integer getOffset() {
		return this.offset;
	}

	/**
	 * 
	 * <pre>
	 * offset.
	 * </pre>
	 * 
	 * @param offset
	 *            offset
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * 
	 * <pre>
	 * count.
	 * </pre>
	 * 
	 * @return int
	 */
	public Integer getCount() {
		return this.count;
	}

	/**
	 * 
	 * <pre>
	 * count.
	 * </pre>
	 * 
	 * @param count
	 *            count
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

}
