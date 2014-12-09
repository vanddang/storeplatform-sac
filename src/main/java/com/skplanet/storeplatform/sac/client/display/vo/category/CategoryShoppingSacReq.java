package com.skplanet.storeplatform.sac.client.display.vo.category;
/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 특정 상품 shopping Value Object.
 * </pre>
 * 
 * Created on : 2014-12-07 Created by : 김형식, SK플래닛 
 * Last Updated on : 2014-12-07 Last Updated by : 김형식, SK플래닛
 */

public class CategoryShoppingSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	@NotNull
	@NotBlank
	private String productId; // 상품 ID (카탈로그)
	private String specialProdId; // 특가상품 ID
	private String saleDtUseYn; // 판매 기간이 넘어갈 경우 조회시
	private String includeProdStopStatus;// 판매 상태 여부
	private String imageCd; // 이미지코드
	private String virtualDeviceModelNo; // android_standard2
	
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the specialProdId
	 */
	public String getSpecialProdId() {
		return specialProdId;
	}
	/**
	 * @param specialProdId the specialProdId to set
	 */
	public void setSpecialProdId(String specialProdId) {
		this.specialProdId = specialProdId;
	}
	/**
	 * @return the saleDtUseYn
	 */
	public String getSaleDtUseYn() {
		return saleDtUseYn;
	}
	/**
	 * @param saleDtUseYn the saleDtUseYn to set
	 */
	public void setSaleDtUseYn(String saleDtUseYn) {
		this.saleDtUseYn = saleDtUseYn;
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
	/**
	 * @return the imageCd
	 */
	public String getImageCd() {
		return imageCd;
	}
	/**
	 * @param imageCd the imageCd to set
	 */
	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}
	/**
	 * @return the virtualDeviceModelNo
	 */
	public String getVirtualDeviceModelNo() {
		return virtualDeviceModelNo;
	}
	/**
	 * @param virtualDeviceModelNo the virtualDeviceModelNo to set
	 */
	public void setVirtualDeviceModelNo(String virtualDeviceModelNo) {
		this.virtualDeviceModelNo = virtualDeviceModelNo;
	}



}
