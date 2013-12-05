/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;

/**
 * 일반/특정 상품 카테고리 리스트 조회 Input Value Object.
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(ProductCategoryProto.reqProductCategory.class)
public class ProductCategoryReqVO {
	private String reqApiType; // API타입

	private String reqPurchaseYn; // 기구매체크여부

	private String reqTopCategoryNo; // 탑카테고리번호

	private String reqCategoryNo; // 카테고리번호

	private String reqStartDate; // 시작일

	private String reqEndDate; // 종료일

	private String reqMbrNo; // 사용자번호

	private String reqIdMdnFlag; // ID/MDN Flag

	private String reqDeviceModelCd; // 단말코드번호

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getReqApiType() {
		return this.reqApiType;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param reqApiType
	 *            reqApiType
	 */
	public void setReqApiType(String reqApiType) {
		this.reqApiType = reqApiType;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getReqPurchaseYn() {
		return this.reqPurchaseYn;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param reqPurchaseYn
	 *            reqPurchaseYn
	 */
	public void setReqPurchaseYn(String reqPurchaseYn) {
		this.reqPurchaseYn = reqPurchaseYn;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getReqTopCategoryNo() {
		return this.reqTopCategoryNo;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param reqTopCategoryNo
	 *            reqTopCategoryNo
	 */
	public void setReqTopCategoryNo(String reqTopCategoryNo) {
		this.reqTopCategoryNo = reqTopCategoryNo;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getReqCategoryNo() {
		return this.reqCategoryNo;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param reqCategoryNo
	 *            reqCategoryNo
	 */
	public void setReqCategoryNo(String reqCategoryNo) {
		this.reqCategoryNo = reqCategoryNo;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getReqStartDate() {
		return this.reqStartDate;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param reqStartDate
	 *            reqStartDate
	 */
	public void setReqStartDate(String reqStartDate) {
		this.reqStartDate = reqStartDate;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getReqEndDate() {
		return this.reqEndDate;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param reqEndDate
	 *            reqEndDate
	 */
	public void setReqEndDate(String reqEndDate) {
		this.reqEndDate = reqEndDate;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getReqMbrNo() {
		return this.reqMbrNo;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param reqMbrNo
	 *            reqMbrNo
	 */
	public void setReqMbrNo(String reqMbrNo) {
		this.reqMbrNo = reqMbrNo;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getReqIdMdnFlag() {
		return this.reqIdMdnFlag;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param reqIdMdnFlag
	 *            reqIdMdnFlag
	 */
	public void setReqIdMdnFlag(String reqIdMdnFlag) {
		this.reqIdMdnFlag = reqIdMdnFlag;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getReqDeviceModelCd() {
		return this.reqDeviceModelCd;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param reqDeviceModelCd
	 *            reqDeviceModelCd
	 */
	public void setReqDeviceModelCd(String reqDeviceModelCd) {
		this.reqDeviceModelCd = reqDeviceModelCd;
	}
}
