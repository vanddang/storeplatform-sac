/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매 파트에서 사용하는 테넌트 정책 VO
 * 
 * Updated on : 2014. 2. 5. Updated by : 이승택, nTels.
 */
public class PurchaseTenantPolicy extends CommonInfo {
	private static final long serialVersionUID = 201402051L;

	/**
	 * 테넌트 ID
	 */
	private String tenantId;
	/**
	 * 정책 ID
	 */
	private String policyId;
	/**
	 * 정책 일련번호
	 */
	private int policySeq;
	/**
	 * 정책 명
	 */
	private String policyNm;
	/**
	 * <pre>
	 * 처리 패턴 코드
	 *  - SKT후불 구매/결제 한도 제한
	 *  - SKT후불 선물수신 한도 제한
	 *  - 법인명의 제한
	 *  - 시험폰결제 허용
	 *  - 비과금결제 허용
	 *  - Device기반 구매내역 관리 처리
	 *  - 유료상품 Admin 구매취소 허용
	 *  - 유료상품 사용자 구매취소 허용
	 *  - 무료상품 Admin 구매취소 허용
	 *  - 무료상품 사용자 구매취소 허용
	 *  - 구매차단 정책 코드 (회원Part 정책 코드)
	 * </pre>
	 */
	private String procPatternCd;
	/**
	 * 테넌트 상품 그룹 코드: 정책 대상 카테고리 - LIKE (전체는 NULL)
	 */
	private String tenantProdGrpCd;
	/**
	 * <pre>
	 * 적용 단위 코드
	 *  - 금액
	 *  - 법인번호
	 *  - MDN
	 * </pre>
	 */
	private String applyUnitCd;
	/**
	 * 적용 값
	 */
	private String applyValue;
	/**
	 * <pre>
	 * 조건 단위 코드
	 *  - 무제한
	 *  - 분
	 *  - 시
	 *  - 일
	 *  - 월
	 *  - 년
	 *  - 당일
	 *  - 당월
	 *  - 당년
	 * </pre>
	 */
	private String condUnitCd;
	/**
	 * 조건 값
	 */
	private String condValue;
	/**
	 * <pre>
	 * 조건 기간 단위 코드
	 *  - 무제한
	 *  - 당일
	 *  - 당월
	 *  - 당년
	 *  - 전일
	 *  - 전월
	 *  - 전년
	 * </pre>
	 */
	private String condPeriodUnitCd;
	/**
	 * 조건 기간 값
	 */
	private String condPeriodValue;
	/**
	 * <pre>
	 * 조건 구분 단위 코드
	 *  - 건
	 *  - 금액
	 * </pre>
	 */
	private String condClsfUnitCd;
	/**
	 * 조건 구분 값
	 */
	private String condClsfValue;
	/**
	 * 정책 우선순위
	 */
	private long policyPrior;

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the policyId
	 */
	public String getPolicyId() {
		return this.policyId;
	}

	/**
	 * @param policyId
	 *            the policyId to set
	 */
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	/**
	 * @return the policySeq
	 */
	public int getPolicySeq() {
		return this.policySeq;
	}

	/**
	 * @param policySeq
	 *            the policySeq to set
	 */
	public void setPolicySeq(int policySeq) {
		this.policySeq = policySeq;
	}

	/**
	 * @return the policyNm
	 */
	public String getPolicyNm() {
		return this.policyNm;
	}

	/**
	 * @param policyNm
	 *            the policyNm to set
	 */
	public void setPolicyNm(String policyNm) {
		this.policyNm = policyNm;
	}

	/**
	 * @return the procPatternCd
	 */
	public String getProcPatternCd() {
		return this.procPatternCd;
	}

	/**
	 * @param procPatternCd
	 *            the procPatternCd to set
	 */
	public void setProcPatternCd(String procPatternCd) {
		this.procPatternCd = procPatternCd;
	}

	/**
	 * @return the tenantProdGrpCd
	 */
	public String getTenantProdGrpCd() {
		return this.tenantProdGrpCd;
	}

	/**
	 * @param tenantProdGrpCd
	 *            the tenantProdGrpCd to set
	 */
	public void setTenantProdGrpCd(String tenantProdGrpCd) {
		this.tenantProdGrpCd = tenantProdGrpCd;
	}

	/**
	 * @return the applyUnitCd
	 */
	public String getApplyUnitCd() {
		return this.applyUnitCd;
	}

	/**
	 * @param applyUnitCd
	 *            the applyUnitCd to set
	 */
	public void setApplyUnitCd(String applyUnitCd) {
		this.applyUnitCd = applyUnitCd;
	}

	/**
	 * @return the applyValue
	 */
	public String getApplyValue() {
		return this.applyValue;
	}

	/**
	 * @param applyValue
	 *            the applyValue to set
	 */
	public void setApplyValue(String applyValue) {
		this.applyValue = applyValue;
	}

	/**
	 * @return the condUnitCd
	 */
	public String getCondUnitCd() {
		return this.condUnitCd;
	}

	/**
	 * @param condUnitCd
	 *            the condUnitCd to set
	 */
	public void setCondUnitCd(String condUnitCd) {
		this.condUnitCd = condUnitCd;
	}

	/**
	 * @return the condValue
	 */
	public String getCondValue() {
		return this.condValue;
	}

	/**
	 * @param condValue
	 *            the condValue to set
	 */
	public void setCondValue(String condValue) {
		this.condValue = condValue;
	}

	/**
	 * @return the condPeriodUnitCd
	 */
	public String getCondPeriodUnitCd() {
		return this.condPeriodUnitCd;
	}

	/**
	 * @param condPeriodUnitCd
	 *            the condPeriodUnitCd to set
	 */
	public void setCondPeriodUnitCd(String condPeriodUnitCd) {
		this.condPeriodUnitCd = condPeriodUnitCd;
	}

	/**
	 * @return the condPeriodValue
	 */
	public String getCondPeriodValue() {
		return this.condPeriodValue;
	}

	/**
	 * @param condPeriodValue
	 *            the condPeriodValue to set
	 */
	public void setCondPeriodValue(String condPeriodValue) {
		this.condPeriodValue = condPeriodValue;
	}

	/**
	 * @return the condClsfUnitCd
	 */
	public String getCondClsfUnitCd() {
		return this.condClsfUnitCd;
	}

	/**
	 * @param condClsfUnitCd
	 *            the condClsfUnitCd to set
	 */
	public void setCondClsfUnitCd(String condClsfUnitCd) {
		this.condClsfUnitCd = condClsfUnitCd;
	}

	/**
	 * @return the condClsfValue
	 */
	public String getCondClsfValue() {
		return this.condClsfValue;
	}

	/**
	 * @param condClsfValue
	 *            the condClsfValue to set
	 */
	public void setCondClsfValue(String condClsfValue) {
		this.condClsfValue = condClsfValue;
	}

	/**
	 * @return the policyPrior
	 */
	public long getPolicyPrior() {
		return this.policyPrior;
	}

	/**
	 * @param policyPrior
	 *            the policyPrior to set
	 */
	public void setPolicyPrior(long policyPrior) {
		this.policyPrior = policyPrior;
	}

}
