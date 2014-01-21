/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.best;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * BEST 다운로드 상품 조회 List Value Object.
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */
public class BestDownloadReq extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private String tenantId; // tenantId
	private String systemId; // 시스템Id
	private String deviceModelCd; // 단말모델코드
	private String langCd; // 언어코드
	private String listId; // 리스트 Id
	private String menuId; // 메뉴 Id
	private String fiteredBy; // 카테고리 유형
	private String b2bprod; // B2B 상품구분
	private String hdv; // HDV 지원구분
	private String drm; // drm 지원구분
	private String prodGradeCd; // 상품등급코드
	private String offset; // 시작점 ROW
	private String count; // 페이지당 노출 ROW 수
	private String dummy;

	/**
	 * 
	 * <pre>
	 * tenantId.
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
	 * tenantId.
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
	 * 시스템Id.
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
	 * 시스템Id.
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
	 * 단말모델코드.
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
	 * 단말모델코드.
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
	 * 리스트 Id.
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
	 * 리스트 Id.
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
	 * 메뉴 Id.
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
	 * 메뉴 Id.
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
	 * 카테고리 유형.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFiteredBy() {
		return this.fiteredBy;
	}

	/**
	 * 
	 * <pre>
	 * 카테고리 유형.
	 * </pre>
	 * 
	 * @param fiteredBy
	 *            fiteredBy
	 */
	public void setFiteredBy(String fiteredBy) {
		this.fiteredBy = fiteredBy;
	}

	/**
	 * 
	 * <pre>
	 * B2B 상품구분.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getB2bprod() {
		return this.b2bprod;
	}

	/**
	 * 
	 * <pre>
	 * B2B 상품구분.
	 * </pre>
	 * 
	 * @param b2bprod
	 *            b2bprod
	 */
	public void setB2bprod(String b2bprod) {
		this.b2bprod = b2bprod;
	}

	/**
	 * 
	 * <pre>
	 * HDV 지원구분.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdv() {
		return this.hdv;
	}

	/**
	 * 
	 * <pre>
	 * HDV 지원구분.
	 * </pre>
	 * 
	 * @param hdv
	 *            String hdv
	 */
	public void setHdv(String hdv) {
		this.hdv = hdv;
	}

	/**
	 * 
	 * <pre>
	 * drm 지원구분.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDrm() {
		return this.drm;
	}

	/**
	 * 
	 * <pre>
	 * drm 지원구분.
	 * </pre>
	 * 
	 * @param drm
	 *            drm
	 */
	public void setDrm(String drm) {
		this.drm = drm;
	}

	/**
	 * 
	 * <pre>
	 * 상품 이용등급 코드.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdGradeCd() {
		return this.prodGradeCd;
	}

	/**
	 * 
	 * <pre>
	 * 상품 이용등급 코드.
	 * </pre>
	 * 
	 * @param prodGradeCd
	 *            prodGradeCd
	 */
	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}

	/**
	 * 
	 * <pre>
	 * 시작점 ROW.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getOffset() {
		return this.offset;
	}

	/**
	 * 
	 * <pre>
	 * 시작점 ROW.
	 * </pre>
	 * 
	 * @param offset
	 *            offset
	 */
	public void setOffset(String offset) {
		this.offset = offset;
	}

	/**
	 * 
	 * <pre>
	 * 페이지당 노출 ROW 수.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getCount() {
		return this.count;
	}

	/**
	 * 
	 * <pre>
	 * 페이지당 노출 ROW 수.
	 * </pre>
	 * 
	 * @param count
	 *            count
	 */
	public void setCount(String count) {
		this.count = count;
	}

	/**
	 * 
	 * <pre>
	 * dummy data check.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDummy() {
		return this.dummy;
	}

	/**
	 * 
	 * <pre>
	 * dummy data check.
	 * </pre>
	 * 
	 * @param dummy
	 *            dummy
	 */
	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

}
