/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매인증 응답 VO
 * 
 * Updated on : 2014. 2. 26. Updated by : 이승택, nTels.
 */
public class VerifyOrderSacRes extends CommonInfo {
	private static final long serialVersionUID = 201420261L;

	private String mdn; // 결제 단말 번호
	private String oneId; // ONE ID 회원 ID
	private String flgMbrStatus; // 회원상태: 0-비정상, 1-정상
	private String flgProductStatus; // 상품상태: 0-구매불가상품, 1-구매가능상품
	private String flgBlockPayment; // 결제차단여부: 0-결제차단, 1-결제가능
	private String flgTeleBillingAgree; // 통신과금 동의여부: Y/N
	private String flgOcbUseAgree; // OCB 이용약관 동의여부: Y/N
	private String typeDanalContent; // (다날) 컨텐츠 타입: 0-디지털, 1-실물
	private String prchsCaseCd; // 구매/선물 구분 코드
	private String approvalSd; // SKT후불 승인용 SYSTEM_DIVISION
	private String cancelSd; // SKT후불 취소용 SYSTEM_DIVISION
	private String typeSktLimit; // SKT후불 결제수단 재정의 원인: L00~L09
	private String cdMaxAmtRate; // 결제수단 별 가능 거래금액/비율 조정 정보
	private String cdPriority; // 결제수단 정렬 재조정
	private String cdOcbSaveInfo; // OCB 적립코드
	private String ocbAuthMtdCd; // OCB 인증수단 코드
	private String noOcbCard; // OCB 카드번호
	private String noCouponList; // 쿠폰 List
	private String cashPointList; // 캐쉬/포인트 잔액 통합 정보
	private String typeTestMdn; // 법인 및 일반 시험폰 처리 타입: T01, T02, T03
	private String cdPaymentTemplate; // 결제Page 템플릿 코드: TC01-일반, TC02-정액제(게임캐쉬), TC03-대여/소장, TC04-자동결제, TC05-쇼핑, TC06-선물
	private String topMenuId; // 상품 TOP 메뉴 ID
	private String bonusCashPoint; // 보너스 캐쉬 지급 Point
	private String bonusCashUsableDayCnt; // 보너스 캐쉬 유효기간(일)
	private String afterAutoPayDt; // 다음 자동 결제일
	private String useStartDt; // 이용 시작 일시
	private String useExprDt; // 이용 종료 일시
	private String dwldAvailableDayCnt; // 다운로드 가능기간(일)
	private String usePeriodCnt; // 이용기간(일)
	private String basePid; // 기본 상품 ID (대여/소장 TAB 하이라이트 처리)
	private String loanPid; // 대여하기 상품 ID
	private Double loanAmt; // 대여하기 상품 금액
	private String ownPid; // 소장하기 상품 ID
	private Double ownAmt; // 소장하기 상품 금액
	private String prodKind; // 쇼핑상품 종류
	private String nmSeller; // 판매자명
	private String emailSeller; // 판매자 이메일 주소
	private String noTelSeller; // 판매자 전화번호
	private String nmDelivery; // 선물수신자 성명
	private String noMdnDelivery; // 선물수신자 MDN

	/**
	 * @return the mdn
	 */
	public String getMdn() {
		return this.mdn;
	}

	/**
	 * @param mdn
	 *            the mdn to set
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * @return the oneId
	 */
	public String getOneId() {
		return this.oneId;
	}

	/**
	 * @param oneId
	 *            the oneId to set
	 */
	public void setOneId(String oneId) {
		this.oneId = oneId;
	}

	/**
	 * @return the flgMbrStatus
	 */
	public String getFlgMbrStatus() {
		return this.flgMbrStatus;
	}

	/**
	 * @param flgMbrStatus
	 *            the flgMbrStatus to set
	 */
	public void setFlgMbrStatus(String flgMbrStatus) {
		this.flgMbrStatus = flgMbrStatus;
	}

	/**
	 * @return the flgProductStatus
	 */
	public String getFlgProductStatus() {
		return this.flgProductStatus;
	}

	/**
	 * @param flgProductStatus
	 *            the flgProductStatus to set
	 */
	public void setFlgProductStatus(String flgProductStatus) {
		this.flgProductStatus = flgProductStatus;
	}

	/**
	 * @return the flgBlockPayment
	 */
	public String getFlgBlockPayment() {
		return this.flgBlockPayment;
	}

	/**
	 * @param flgBlockPayment
	 *            the flgBlockPayment to set
	 */
	public void setFlgBlockPayment(String flgBlockPayment) {
		this.flgBlockPayment = flgBlockPayment;
	}

	/**
	 * @return the flgTeleBillingAgree
	 */
	public String getFlgTeleBillingAgree() {
		return this.flgTeleBillingAgree;
	}

	/**
	 * @param flgTeleBillingAgree
	 *            the flgTeleBillingAgree to set
	 */
	public void setFlgTeleBillingAgree(String flgTeleBillingAgree) {
		this.flgTeleBillingAgree = flgTeleBillingAgree;
	}

	/**
	 * @return the flgOcbUseAgree
	 */
	public String getFlgOcbUseAgree() {
		return this.flgOcbUseAgree;
	}

	/**
	 * @param flgOcbUseAgree
	 *            the flgOcbUseAgree to set
	 */
	public void setFlgOcbUseAgree(String flgOcbUseAgree) {
		this.flgOcbUseAgree = flgOcbUseAgree;
	}

	/**
	 * @return the typeDanalContent
	 */
	public String getTypeDanalContent() {
		return this.typeDanalContent;
	}

	/**
	 * @param typeDanalContent
	 *            the typeDanalContent to set
	 */
	public void setTypeDanalContent(String typeDanalContent) {
		this.typeDanalContent = typeDanalContent;
	}

	/**
	 * @return the prchsCaseCd
	 */
	public String getPrchsCaseCd() {
		return this.prchsCaseCd;
	}

	/**
	 * @param prchsCaseCd
	 *            the prchsCaseCd to set
	 */
	public void setPrchsCaseCd(String prchsCaseCd) {
		this.prchsCaseCd = prchsCaseCd;
	}

	/**
	 * @return the approvalSd
	 */
	public String getApprovalSd() {
		return this.approvalSd;
	}

	/**
	 * @param approvalSd
	 *            the approvalSd to set
	 */
	public void setApprovalSd(String approvalSd) {
		this.approvalSd = approvalSd;
	}

	/**
	 * @return the cancelSd
	 */
	public String getCancelSd() {
		return this.cancelSd;
	}

	/**
	 * @param cancelSd
	 *            the cancelSd to set
	 */
	public void setCancelSd(String cancelSd) {
		this.cancelSd = cancelSd;
	}

	/**
	 * @return the typeSktLimit
	 */
	public String getTypeSktLimit() {
		return this.typeSktLimit;
	}

	/**
	 * @param typeSktLimit
	 *            the typeSktLimit to set
	 */
	public void setTypeSktLimit(String typeSktLimit) {
		this.typeSktLimit = typeSktLimit;
	}

	/**
	 * @return the cdMaxAmtRate
	 */
	public String getCdMaxAmtRate() {
		return this.cdMaxAmtRate;
	}

	/**
	 * @param cdMaxAmtRate
	 *            the cdMaxAmtRate to set
	 */
	public void setCdMaxAmtRate(String cdMaxAmtRate) {
		this.cdMaxAmtRate = cdMaxAmtRate;
	}

	/**
	 * @return the cdPriority
	 */
	public String getCdPriority() {
		return this.cdPriority;
	}

	/**
	 * @param cdPriority
	 *            the cdPriority to set
	 */
	public void setCdPriority(String cdPriority) {
		this.cdPriority = cdPriority;
	}

	/**
	 * @return the cdOcbSaveInfo
	 */
	public String getCdOcbSaveInfo() {
		return this.cdOcbSaveInfo;
	}

	/**
	 * @param cdOcbSaveInfo
	 *            the cdOcbSaveInfo to set
	 */
	public void setCdOcbSaveInfo(String cdOcbSaveInfo) {
		this.cdOcbSaveInfo = cdOcbSaveInfo;
	}

	/**
	 * @return the ocbAuthMtdCd
	 */
	public String getOcbAuthMtdCd() {
		return this.ocbAuthMtdCd;
	}

	/**
	 * @param ocbAuthMtdCd
	 *            the ocbAuthMtdCd to set
	 */
	public void setOcbAuthMtdCd(String ocbAuthMtdCd) {
		this.ocbAuthMtdCd = ocbAuthMtdCd;
	}

	/**
	 * @return the noOcbCard
	 */
	public String getNoOcbCard() {
		return this.noOcbCard;
	}

	/**
	 * @param noOcbCard
	 *            the noOcbCard to set
	 */
	public void setNoOcbCard(String noOcbCard) {
		this.noOcbCard = noOcbCard;
	}

	/**
	 * @return the noCouponList
	 */
	public String getNoCouponList() {
		return this.noCouponList;
	}

	/**
	 * @param noCouponList
	 *            the noCouponList to set
	 */
	public void setNoCouponList(String noCouponList) {
		this.noCouponList = noCouponList;
	}

	/**
	 * @return the cashPointList
	 */
	public String getCashPointList() {
		return this.cashPointList;
	}

	/**
	 * @param cashPointList
	 *            the cashPointList to set
	 */
	public void setCashPointList(String cashPointList) {
		this.cashPointList = cashPointList;
	}

	/**
	 * @return the typeTestMdn
	 */
	public String getTypeTestMdn() {
		return this.typeTestMdn;
	}

	/**
	 * @param typeTestMdn
	 *            the typeTestMdn to set
	 */
	public void setTypeTestMdn(String typeTestMdn) {
		this.typeTestMdn = typeTestMdn;
	}

	/**
	 * @return the cdPaymentTemplate
	 */
	public String getCdPaymentTemplate() {
		return this.cdPaymentTemplate;
	}

	/**
	 * @param cdPaymentTemplate
	 *            the cdPaymentTemplate to set
	 */
	public void setCdPaymentTemplate(String cdPaymentTemplate) {
		this.cdPaymentTemplate = cdPaymentTemplate;
	}

	/**
	 * @return the topMenuId
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * @param topMenuId
	 *            the topMenuId to set
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * @return the bonusCashPoint
	 */
	public String getBonusCashPoint() {
		return this.bonusCashPoint;
	}

	/**
	 * @param bonusCashPoint
	 *            the bonusCashPoint to set
	 */
	public void setBonusCashPoint(String bonusCashPoint) {
		this.bonusCashPoint = bonusCashPoint;
	}

	/**
	 * @return the bonusCashUsableDayCnt
	 */
	public String getBonusCashUsableDayCnt() {
		return this.bonusCashUsableDayCnt;
	}

	/**
	 * @param bonusCashUsableDayCnt
	 *            the bonusCashUsableDayCnt to set
	 */
	public void setBonusCashUsableDayCnt(String bonusCashUsableDayCnt) {
		this.bonusCashUsableDayCnt = bonusCashUsableDayCnt;
	}

	/**
	 * @return the afterAutoPayDt
	 */
	public String getAfterAutoPayDt() {
		return this.afterAutoPayDt;
	}

	/**
	 * @param afterAutoPayDt
	 *            the afterAutoPayDt to set
	 */
	public void setAfterAutoPayDt(String afterAutoPayDt) {
		this.afterAutoPayDt = afterAutoPayDt;
	}

	/**
	 * @return the useStartDt
	 */
	public String getUseStartDt() {
		return this.useStartDt;
	}

	/**
	 * @param useStartDt
	 *            the useStartDt to set
	 */
	public void setUseStartDt(String useStartDt) {
		this.useStartDt = useStartDt;
	}

	/**
	 * @return the useExprDt
	 */
	public String getUseExprDt() {
		return this.useExprDt;
	}

	/**
	 * @param useExprDt
	 *            the useExprDt to set
	 */
	public void setUseExprDt(String useExprDt) {
		this.useExprDt = useExprDt;
	}

	/**
	 * @return the dwldAvailableDayCnt
	 */
	public String getDwldAvailableDayCnt() {
		return this.dwldAvailableDayCnt;
	}

	/**
	 * @param dwldAvailableDayCnt
	 *            the dwldAvailableDayCnt to set
	 */
	public void setDwldAvailableDayCnt(String dwldAvailableDayCnt) {
		this.dwldAvailableDayCnt = dwldAvailableDayCnt;
	}

	/**
	 * @return the usePeriodCnt
	 */
	public String getUsePeriodCnt() {
		return this.usePeriodCnt;
	}

	/**
	 * @param usePeriodCnt
	 *            the usePeriodCnt to set
	 */
	public void setUsePeriodCnt(String usePeriodCnt) {
		this.usePeriodCnt = usePeriodCnt;
	}

	/**
	 * @return the basePid
	 */
	public String getBasePid() {
		return this.basePid;
	}

	/**
	 * @param basePid
	 *            the basePid to set
	 */
	public void setBasePid(String basePid) {
		this.basePid = basePid;
	}

	/**
	 * @return the loanPid
	 */
	public String getLoanPid() {
		return this.loanPid;
	}

	/**
	 * @param loanPid
	 *            the loanPid to set
	 */
	public void setLoanPid(String loanPid) {
		this.loanPid = loanPid;
	}

	/**
	 * @return the loanAmt
	 */
	public Double getLoanAmt() {
		return this.loanAmt;
	}

	/**
	 * @param loanAmt
	 *            the loanAmt to set
	 */
	public void setLoanAmt(Double loanAmt) {
		this.loanAmt = loanAmt;
	}

	/**
	 * @return the ownPid
	 */
	public String getOwnPid() {
		return this.ownPid;
	}

	/**
	 * @param ownPid
	 *            the ownPid to set
	 */
	public void setOwnPid(String ownPid) {
		this.ownPid = ownPid;
	}

	/**
	 * @return the ownAmt
	 */
	public Double getOwnAmt() {
		return this.ownAmt;
	}

	/**
	 * @param ownAmt
	 *            the ownAmt to set
	 */
	public void setOwnAmt(Double ownAmt) {
		this.ownAmt = ownAmt;
	}

	/**
	 * @return the prodKind
	 */
	public String getProdKind() {
		return this.prodKind;
	}

	/**
	 * @param prodKind
	 *            the prodKind to set
	 */
	public void setProdKind(String prodKind) {
		this.prodKind = prodKind;
	}

	/**
	 * @return the nmSeller
	 */
	public String getNmSeller() {
		return this.nmSeller;
	}

	/**
	 * @param nmSeller
	 *            the nmSeller to set
	 */
	public void setNmSeller(String nmSeller) {
		this.nmSeller = nmSeller;
	}

	/**
	 * @return the emailSeller
	 */
	public String getEmailSeller() {
		return this.emailSeller;
	}

	/**
	 * @param emailSeller
	 *            the emailSeller to set
	 */
	public void setEmailSeller(String emailSeller) {
		this.emailSeller = emailSeller;
	}

	/**
	 * @return the noTelSeller
	 */
	public String getNoTelSeller() {
		return this.noTelSeller;
	}

	/**
	 * @param noTelSeller
	 *            the noTelSeller to set
	 */
	public void setNoTelSeller(String noTelSeller) {
		this.noTelSeller = noTelSeller;
	}

	/**
	 * @return the nmDelivery
	 */
	public String getNmDelivery() {
		return this.nmDelivery;
	}

	/**
	 * @param nmDelivery
	 *            the nmDelivery to set
	 */
	public void setNmDelivery(String nmDelivery) {
		this.nmDelivery = nmDelivery;
	}

	/**
	 * @return the noMdnDelivery
	 */
	public String getNoMdnDelivery() {
		return this.noMdnDelivery;
	}

	/**
	 * @param noMdnDelivery
	 *            the noMdnDelivery to set
	 */
	public void setNoMdnDelivery(String noMdnDelivery) {
		this.noMdnDelivery = noMdnDelivery;
	}

}
