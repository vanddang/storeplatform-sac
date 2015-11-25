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

import java.util.List;

/**
 * 구매인증 응답 VO
 *
 * Updated on : 2014. 2. 26. Updated by : 이승택, nTels.
 */
public class VerifyOrderSacRes extends CommonInfo {
	private static final long serialVersionUID = 201420261L;

	private String tenantId; // 테넌트 ID
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
	private String purchaseSuspensionCd; // 시스템 점검중인 결제 수단
	private String purchaseSuspensionMsg; // 시스템 점검중인 결제 수단 메시지
	private String cdOcbSaveInfo; // OCB 적립코드
	private String ocbAuthMtdCd; // OCB 인증수단 코드
	private String noOcbCard; // OCB 카드번호
	// private String noCouponList; // (구)쿠폰 List
	private String couponList; // (신)쿠폰 List
	private String cashPointList; // 캐쉬/포인트 잔액 통합 정보
	private String userGrade; // 회원등급
	private String tMileageSaveRate; // 상품 T마일리지 적립률 (T마일리지 적립율 (등급:적립율;[반복])
	private String tMileageAvailMtd; // T마일리지 적립 가능 결제수단 (결제수단코드;결제수단코드;[반복])
	private Integer tMileageLimitAmt; // T마일리지 적립한도 금액
	private Integer tMileageReserveAmt; // T마일리지 적립예정 금액 (적립예정일:예정금액;[반복])
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
	private String prodKind; // 쇼핑상품 종류

	// 전시 정보
	private String svcGrpCd; // 서비스 그룹 코드
	private String prodCaseCd; // 쇼핑 상품 유형 코드
	private String specialTypeCd; // 특가상품 유형 코드,팅요금제 상품 유형
								  // 코드(특가상품-상품권/교환권:DP007501,특가상품-배송상품:DP007502,팅요금제-상품권/교환권:DP007503)
	private String metaClsfCd; // 메타 클래스 코드
	private String acmlMethodCd; // 적립 수단 코드
	private String promForceCloseCd; // 프로모션 강제 종료 코드

	private VerifyOrderIapInfoSac iapProdInfo; // IAP상품 정보
	private List<VerifyOrderPromotionInfoSac> promotionList; // 프로모션 정보
	private List<VerifyOrderBannerInfoSac> bannerList; // 배너 정보

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
	 * @return the userGrade
	 */
	public String getUserGrade() {
		return this.userGrade;
	}

	/**
	 * @param userGrade
	 *            the userGrade to set
	 */
	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}

	/**
	 * @return the tMileageSaveRate
	 */
	public String gettMileageSaveRate() {
		return this.tMileageSaveRate;
	}

	/**
	 * @param tMileageSaveRate
	 *            the tMileageSaveRate to set
	 */
	public void settMileageSaveRate(String tMileageSaveRate) {
		this.tMileageSaveRate = tMileageSaveRate;
	}

	/**
	 * @return the tMileageAvailMtd
	 */
	public String gettMileageAvailMtd() {
		return this.tMileageAvailMtd;
	}

	/**
	 * @param tMileageAvailMtd
	 *            the tMileageAvailMtd to set
	 */
	public void settMileageAvailMtd(String tMileageAvailMtd) {
		this.tMileageAvailMtd = tMileageAvailMtd;
	}

	/**
	 * @return the tMileageLimitAmt
	 */
	public Integer gettMileageLimitAmt() {
		return this.tMileageLimitAmt;
	}

	/**
	 * @param tMileageLimitAmt
	 *            the tMileageLimitAmt to set
	 */
	public void settMileageLimitAmt(Integer tMileageLimitAmt) {
		this.tMileageLimitAmt = tMileageLimitAmt;
	}

	/**
	 * @return the tMileageReserveAmt
	 */
	public Integer gettMileageReserveAmt() {
		return this.tMileageReserveAmt;
	}

	/**
	 * @param tMileageReserveAmt
	 *            the tMileageReserveAmt to set
	 */
	public void settMileageReserveAmt(Integer tMileageReserveAmt) {
		this.tMileageReserveAmt = tMileageReserveAmt;
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
	 * @return the iapProdInfo
	 */
	public VerifyOrderIapInfoSac getIapProdInfo() {
		return this.iapProdInfo;
	}

	/**
	 * @param iapProdInfo
	 *            the iapProdInfo to set
	 */
	public void setIapProdInfo(VerifyOrderIapInfoSac iapProdInfo) {
		this.iapProdInfo = iapProdInfo;
	}

	/**
	 * @return the promotionList
	 */
	public List<VerifyOrderPromotionInfoSac> getPromotionList() {
		return this.promotionList;
	}

	/**
	 * @param promotionList
	 *            the promotionList to set
	 */
	public void setPromotionList(List<VerifyOrderPromotionInfoSac> promotionList) {
		this.promotionList = promotionList;
	}

	/**
	 * @return the bannerList
	 */
	public List<VerifyOrderBannerInfoSac> getBannerList() {
		return this.bannerList;
	}

	/**
	 * @param bannerList
	 *            the bannerList to set
	 */
	public void setBannerList(List<VerifyOrderBannerInfoSac> bannerList) {
		this.bannerList = bannerList;
	}

	/**
	 * Sets 쿠폰 리스트(신)
	 *
	 * @param couponList
	 *            the coupon list
	 */
	public void setCouponList(String couponList) {
		this.couponList = couponList;
	}

	/**
	 * Gets 쿠폰 리스트(신)
	 *
	 * @return the coupon list
	 */
	public String getCouponList() {
		return couponList;
	}

	/**
	 * Gets svc grp cd.
	 *
	 * @return the svc grp cd
	 */
	public String getSvcGrpCd() {
		return svcGrpCd;
	}

	/**
	 * Sets svc grp cd.
	 *
	 * @param svcGrpCd
	 *            the svc grp cd
	 */
	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}

	/**
	 * Gets prod case cd.
	 *
	 * @return the prod case cd
	 */
	public String getProdCaseCd() {
		return prodCaseCd;
	}

	/**
	 * Sets prod case cd.
	 *
	 * @param prodCaseCd
	 *            the prod case cd
	 */
	public void setProdCaseCd(String prodCaseCd) {
		this.prodCaseCd = prodCaseCd;
	}

	/**
	 * Gets special type cd. 특가상품 유형 코드,팅요금제 상품 유형 코드 (특가상품-상품권/교환권:DP007501,특가상품-배송상품:DP007502,팅요금제-상품권/교환권:DP007503)
	 *
	 * @return the special type cd
	 */
	public String getSpecialTypeCd() {
		return specialTypeCd;
	}

	/**
	 * Sets special type cd.
	 *
	 * @param specialTypeCd
	 *            the special type cd
	 */
	public void setSpecialTypeCd(String specialTypeCd) {
		this.specialTypeCd = specialTypeCd;
	}

	/**
	 * Gets purchase suspension cd.
	 *
	 * @return the purchase suspension cd
	 */
	public String getPurchaseSuspensionCd() {
		return purchaseSuspensionCd;
	}

	/**
	 * Sets purchase suspension cd.
	 *
	 * @param purchaseSuspensionCd
	 *            the purchase suspension cd
	 */
	public void setPurchaseSuspensionCd(String purchaseSuspensionCd) {
		this.purchaseSuspensionCd = purchaseSuspensionCd;
	}

	/**
	 * Gets purchase suspension msg.
	 *
	 * @return the purchase suspension msg
	 */
	public String getPurchaseSuspensionMsg() {
		return purchaseSuspensionMsg;
	}

	/**
	 * Sets purchase suspension msg.
	 *
	 * @param purchaseSuspensionMsg
	 *            the purchase suspension msg
	 */
	public void setPurchaseSuspensionMsg(String purchaseSuspensionMsg) {
		this.purchaseSuspensionMsg = purchaseSuspensionMsg;
	}

	/**
	 * Gets prom force close cd.
	 *
	 * @return the prom force close cd
	 */
	public String getPromForceCloseCd() {
		return promForceCloseCd;
	}

	/**
	 * Sets prom force close cd.
	 *
	 * @param promForceCloseCd
	 *            the prom force close cd
	 */
	public void setPromForceCloseCd(String promForceCloseCd) {
		this.promForceCloseCd = promForceCloseCd;
	}

	/**
	 * Gets acml method cd.
	 *
	 * @return the acml method cd
	 */
	public String getAcmlMethodCd() {
		return acmlMethodCd;
	}

	/**
	 * Sets acml method cd.
	 *
	 * @param acmlMethodCd
	 *            the acml method cd
	 */
	public void setAcmlMethodCd(String acmlMethodCd) {
		this.acmlMethodCd = acmlMethodCd;
	}

	/**
	 * Gets meta clsf cd.
	 *
	 * @return the meta clsf cd
	 */
	public String getMetaClsfCd() {
		return metaClsfCd;
	}

	/**
	 * Sets meta clsf cd.
	 *
	 * @param metaClsfCd
	 *            the meta clsf cd
	 */
	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
	}
}
