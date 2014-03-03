/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.shopping;

import javax.validation.constraints.NotNull;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 쇼핑 (브랜드 다른상품) Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */

public class ShoppingBrandAnotherReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	@NotNull
	private String brandId; // 브랜드아이디
	@NotNull
	private String exceptId; // 제외할 메뉴ID – 지정하면 응답에서 해당 메뉴 제외
	private String listId; // 리스트아이디
	private String imageCd; // 이미지코드
	private String prodCharge; // 유료/무료 구분
	private String[] arrayProdGradeCd; // 상품등급코드 Array
	private String prodGradeCd; // 상품등급코드
	private String stdDt; // 기준일자
	private String virtualDeviceModelNo; // android_standard2
	private String prodRshpCd; // 채널 에피소드 관계
	private String orderedBy; // 정렬순서
	private Integer offset; // offset
	private Integer count; // count

	/**
	 * @return the brandId
	 */
	public String getBrandId() {
		return this.brandId;
	}

	/**
	 * @param brandId
	 *            the brandId to set
	 */
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	/**
	 * @return the exceptId
	 */
	public String getExceptId() {
		return this.exceptId;
	}

	/**
	 * @param exceptId
	 *            the exceptId to set
	 */
	public void setExceptId(String exceptId) {
		this.exceptId = exceptId;
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
	 * @return the imageCd
	 */
	public String getImageCd() {
		return this.imageCd;
	}

	/**
	 * @param imageCd
	 *            the imageCd to set
	 */
	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}

	/**
	 * @return the prodCharge
	 */
	public String getProdCharge() {
		return this.prodCharge;
	}

	/**
	 * @param prodCharge
	 *            the prodCharge to set
	 */
	public void setProdCharge(String prodCharge) {
		this.prodCharge = prodCharge;
	}

	/**
	 * @return the arrayProdGradeCd
	 */
	public String[] getArrayProdGradeCd() {
		return this.arrayProdGradeCd;
	}

	/**
	 * @param arrayProdGradeCd
	 *            the arrayProdGradeCd to set
	 */
	public void setArrayProdGradeCd(String[] arrayProdGradeCd) {
		this.arrayProdGradeCd = arrayProdGradeCd;
	}

	/**
	 * @return the prodGradeCd
	 */
	public String getProdGradeCd() {
		return this.prodGradeCd;
	}

	/**
	 * @param prodGradeCd
	 *            the prodGradeCd to set
	 */
	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}

	/**
	 * @return the stdDt
	 */
	public String getStdDt() {
		return this.stdDt;
	}

	/**
	 * @param stdDt
	 *            the stdDt to set
	 */
	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}

	/**
	 * @return the virtualDeviceModelNo
	 */
	public String getVirtualDeviceModelNo() {
		return this.virtualDeviceModelNo;
	}

	/**
	 * @param virtualDeviceModelNo
	 *            the virtualDeviceModelNo to set
	 */
	public void setVirtualDeviceModelNo(String virtualDeviceModelNo) {
		this.virtualDeviceModelNo = virtualDeviceModelNo;
	}

	/**
	 * @return the prodRshpCd
	 */
	public String getProdRshpCd() {
		return this.prodRshpCd;
	}

	/**
	 * @param prodRshpCd
	 *            the prodRshpCd to set
	 */
	public void setProdRshpCd(String prodRshpCd) {
		this.prodRshpCd = prodRshpCd;
	}

	/**
	 * @return the orderedBy
	 */
	public String getOrderedBy() {
		return this.orderedBy;
	}

	/**
	 * @param orderedBy
	 *            the orderedBy to set
	 */
	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	/**
	 * @return the offset
	 */
	public Integer getOffset() {
		return this.offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

}
