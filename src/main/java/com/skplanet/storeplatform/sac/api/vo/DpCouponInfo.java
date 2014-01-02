/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.vo;

public class DpCouponInfo {

	String couponCode = ""; // 상품코드
	String couponName = ""; // 쿠폰명
	String issueSDate = ""; // 발급시작일시
	String issueEDate = ""; // 발급종료일시
	String validSDate = ""; // 유효시작일시
	String validEDate = ""; // 유효종료일시
	String validUntil = ""; // 유효일수
	String tag = ""; // 태그정보
	String description = ""; // 쿠폰설명
	String direction = ""; // 사용장소
	String useCondition = ""; // 사용제한
	String addtionalInfo = ""; // 주의사항
	String refundCondition = ""; // 구매취소(환불) 조건
	String storeSaleType = ""; // 상품유형
	String storeb2bFlag = ""; // B2B상품여부
	String storeCatalogCode = ""; // 카탈로그 번호
	String accountingRate = ""; // 정산율
	String taxType = ""; // 세금구분유형
	String bpId = ""; // 업체아이디
	String coupnStatus = ""; // 쿠폰상태

	public String getCouponCode() {
		return this.couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getCouponName() {
		return this.couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getIssueSDate() {
		return this.issueSDate;
	}

	public void setIssueSDate(String issueSDate) {
		this.issueSDate = issueSDate;
	}

	public String getIssueEDate() {
		return this.issueEDate;
	}

	public void setIssueEDate(String issueEDate) {
		this.issueEDate = issueEDate;
	}

	public String getValidSDate() {
		return this.validSDate;
	}

	public void setValidSDate(String validSDate) {
		this.validSDate = validSDate;
	}

	public String getValidEDate() {
		return this.validEDate;
	}

	public void setValidEDate(String validEDate) {
		this.validEDate = validEDate;
	}

	public String getValidUntil() {
		return this.validUntil;
	}

	public void setValidUntil(String validUntil) {
		this.validUntil = validUntil;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getUseCondition() {
		return this.useCondition;
	}

	public void setUseCondition(String useCondition) {
		this.useCondition = useCondition;
	}

	public String getAddtionalInfo() {
		return this.addtionalInfo;
	}

	public void setAddtionalInfo(String addtionalInfo) {
		this.addtionalInfo = addtionalInfo;
	}

	public String getRefundCondition() {
		return this.refundCondition;
	}

	public void setRefundCondition(String refundCondition) {
		this.refundCondition = refundCondition;
	}

	public String getStoreSaleType() {
		return this.storeSaleType;
	}

	public void setStoreSaleType(String storeSaleType) {
		this.storeSaleType = storeSaleType;
	}

	public String getStoreb2bFlag() {
		return this.storeb2bFlag;
	}

	public void setStoreb2bFlag(String storeb2bFlag) {
		this.storeb2bFlag = storeb2bFlag;
	}

	public String getStoreCatalogCode() {
		return this.storeCatalogCode;
	}

	public void setStoreCatalogCode(String storeCatalogCode) {
		this.storeCatalogCode = storeCatalogCode;
	}

	public String getAccountingRate() {
		return this.accountingRate;
	}

	public void setAccountingRate(String accountingRate) {
		this.accountingRate = accountingRate;
	}

	public String getTaxType() {
		return this.taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getBpId() {
		return this.bpId;
	}

	public void setBpId(String bpId) {
		this.bpId = bpId;
	}

	public String getCoupnStatus() {
		return this.coupnStatus;
	}

	public void setCoupnStatus(String coupnStatus) {
		this.coupnStatus = coupnStatus;
	}

}
