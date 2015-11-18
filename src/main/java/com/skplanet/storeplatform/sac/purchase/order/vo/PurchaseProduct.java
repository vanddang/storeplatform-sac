/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.vo;

import org.apache.commons.lang3.StringUtils;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfo;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 
 * 구매 상품 (전시Part 에서 넘겨받는 상품Object 기반)
 * 
 * Updated on : 2014. 2. 28. Updated by : 이승택, nTels.
 */
public class PurchaseProduct extends PaymentInfo {
	private static final long serialVersionUID = 1L;

	private PossLendProductInfo possLendProductInfo; // 소장/대여 상품 정보
	private PurchaseProduct fullIapProductInfo; // IAP 정식판 전환 상품 정보

	// 게임 캐쉬
	private String bonusCashUsableDayCnt; // 보너스 캐쉬 유효기간(일)
	private String afterAutoPayDt; // 다음 자동 결제일

	private double specialCouponAmt;
	private int prodQty;
	private String resvCol01;
	private String resvCol02;
	private String resvCol03;
	private String resvCol04;
	private String resvCol05;
	private String useExprDt; // [비과금 구매요청 시 사용]
	private String useFixrateProdId; // 이용한 정액제 상품ID
	private String useFixratePrchsId; // 이용한 정액제 구매ID
	private String useFixrateProdClsfCd; // 이용한 정액제 상품 타입
	// IAP
	private String tid; // 부분유료화 개발사 구매Key
	private String txId; // 부분유료화 전자영수증 번호
	private String parentProdId; // 부모_상품_ID
	private String partChrgVer; // 부분_유료_버전
	private String partChrgProdNm; // 부분_유료_상품_명
	private boolean fullProd; // 정식판 전환 상품 여부
	// IAP P/P
	private String s2sAutoPrchsYn; // S2S 월자동결제 상품 여부
	private String iapPostbackUrl; // 모상품 결제결과 전송 URL
	private String iapProdKind; // 부분유료화 상품 유형
	private String iapProdCase; // 부분유료화 상품 종류
	private Integer iapUsePeriod; // 이용기간 값
	// IAP 자동결제
	private Integer autoPrchsLastPeriodValue; // 자동결제 지속일

	// Ring & Bell
	private String rnBillCd; // RN_과금_코드
	private String infoUseFee; // 정보_이용_요금 (ISU_AMT_ADD)
	private String cid; // 컨텐츠ID
	private String contentsClsf; // 컨텐츠_구분
	private String contentsType; // 컨텐츠_타입
	private String prchsType; // 구매_타입
	private String timbreClsf; // 음질_구분
	private String timbreSctn; // 음질_구간
	private String menuId; // 메뉴_ID
	private String genreClsfCd; // 장르_구분_코드

	// CLINK 처리용
	private String resultCd; // 구매처리 결과코드

	// 정액권으로 에피소드 상품 이용할 때
	private String dwldExprDt; // 다운로드 종료 일시

	/**
	 * 디폴트 생성자
	 */
	public PurchaseProduct() {
	}

	/**
	 * Super Class 멤버변수 세팅을 위한 생성자
	 */
	public PurchaseProduct(PaymentInfo displayInfo) {
		// 상품 군 조회 변수
		this.setTopMenuId(displayInfo.getTopMenuId());
		this.setSvcGrpCd(displayInfo.getSvcGrpCd());
		this.setInAppYn(displayInfo.getInAppYn());

		this.setParentProdNm(displayInfo.getParentProdNm());
		this.setProdId(displayInfo.getProdId());
		this.setProdNm(displayInfo.getProdNm());
		this.setProdAmt(displayInfo.getProdAmt());
		this.setProdStatusCd(displayInfo.getProdStatusCd());
		this.setProdGrdCd(displayInfo.getProdGrdCd());
		this.setAgeAllowedFrom(displayInfo.getAgeAllowedFrom()); // 허용 연령 (상품등급이 청소년이용불가 등급일 경우에 18/19 판별을 위해 사용)

		this.setProdSprtYn(displayInfo.getProdSprtYn());
		this.setDrmYn(StringUtils.defaultString(displayInfo.getDrmYn(), PurchaseConstants.USE_N));

		this.setUsePeriodUnitCd(displayInfo.getUsePeriodUnitCd());
		this.setUsePeriod(StringUtils.equals(displayInfo.getUsePeriodUnitCd(),
				PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED) ? "0" : displayInfo.getUsePeriod());
		this.setUsePeriodSetCd(displayInfo.getUsePeriodSetCd()); // DP013001-이용시점,DP013002-구매시점

		this.setAid(displayInfo.getAid());
		this.setTenantProdGrpCd(displayInfo.getTenantProdGrpCd());
		this.setMallCd(displayInfo.getMallCd());
		this.setOutsdContentsId(displayInfo.getOutsdContentsId());
		this.setSellerMbrNo(displayInfo.getSellerMbrNo());
		this.setSellerNm(displayInfo.getSellerNm());
		this.setSellerEmail(displayInfo.getSellerEmail());
		this.setSellerTelno(displayInfo.getSellerTelno());
		this.setPossLendClsfCd(displayInfo.getPossLendClsfCd());
		// 회차정보
		this.setBookClsfCd(displayInfo.getBookClsfCd());
		this.setChapter(StringUtils.isEmpty(displayInfo.getChapter()) ? displayInfo.getChapterText() : displayInfo.getChapter());
		this.setChapterText(StringUtils.isEmpty(displayInfo.getChapterText()) ? displayInfo.getChapter() : displayInfo.getChapterText());
		this.setChapterUnit(displayInfo.getChapterUnit());
		// 쇼핑
		this.setProdCaseCd(displayInfo.getProdCaseCd()); // DP006301-상품권, DP006302-교환권, DP006303-배송상품
		this.setCouponCode(displayInfo.getCouponCode());
		this.setItemCode(displayInfo.getItemCode());
		this.setSpecialTypeCd(displayInfo.getSpecialTypeCd());
		this.setSpecialSaleAmt(displayInfo.getSpecialSaleAmt());
		this.setSpecialSaleStartDt(displayInfo.getSpecialSaleStartDt());
		this.setSpecialSaleEndDt(displayInfo.getSpecialSaleEndDt());
		this.setSpecialSaleCouponId(displayInfo.getSpecialSaleCouponId());
		this.setSpecialSaleMonthLimit(displayInfo.getSpecialSaleMonthLimit());
		this.setSpecialSaleDayLimit(displayInfo.getSpecialSaleDayLimit());
		this.setSpecialSaleMonthLimitPerson(displayInfo.getSpecialSaleMonthLimitPerson());
		this.setSpecialSaleDayLimitPerson(displayInfo.getSpecialSaleDayLimitPerson());
		this.setSpecialSaleOncePrchsLimit(displayInfo.getSpecialSaleOncePrchsLimit());
		// 정액제
		this.setAvailableFixrateProdIdList(displayInfo.getAvailableFixrateProdIdList());
		this.setAvailableFixrateInfoList(displayInfo.getAvailableFixrateInfoList());
		this.setAutoPrchsYN(displayInfo.getAutoPrchsYN());
		if (StringUtils.equals(displayInfo.getAutoPrchsYN(), PurchaseConstants.USE_Y)) {
			this.setAutoPrchsPeriodUnitCd(displayInfo.getAutoPrchsPeriodUnitCd());
			this.setAutoPrchsPeriodValue(displayInfo.getAutoPrchsPeriodValue() == null ? 0 : displayInfo
					.getAutoPrchsPeriodValue());
		}
		this.setAutoPrchsLastDt(displayInfo.getAutoPrchsLastDt());
		this.setExclusiveFixrateProdExistYn(displayInfo.getExclusiveFixrateProdExistYn());
		this.setExclusiveFixrateProdIdList(displayInfo.getExclusiveFixrateProdIdList());
		this.setCmpxProdClsfCd(displayInfo.getCmpxProdClsfCd());
		this.setCmpxProdBookClsfCd(displayInfo.getCmpxProdBookClsfCd());
		// 게임캐쉬
		this.setBnsCashAmt(displayInfo.getBnsCashAmt());
		this.setBnsUsePeriodUnitCd(displayInfo.getBnsUsePeriodUnitCd());
		this.setBnsUsePeriod(displayInfo.getBnsUsePeriod() == null ? 0 : displayInfo.getBnsUsePeriod());
		// 멤버쉽
		this.setMileageRateMap(displayInfo.getMileageRateMap());
		this.setAcmlDt(displayInfo.getAcmlDt());
		this.setAcmlMethodCd(displayInfo.getAcmlMethodCd());
		this.setPromId(displayInfo.getPromId());
		// S2S
		this.setSearchPriceUrl(displayInfo.getSearchPriceUrl());

		this.setSeriesYn(displayInfo.getSeriesYn());
		this.setPackagePrchsYn(displayInfo.getPackagePrchsYn());
		this.setPrivateAcmlLimit(displayInfo.getPrivateAcmlLimit()); // 개인당 이벤트 적립 한도
		this.setPromForceCloseCd(displayInfo.getPromForceCloseCd()); // 프로모션 조기 종료 코드
		this.setPayMethodVdtyDt(displayInfo.getPayMethodVdtyDt()); // 적립금 유효일
		this.setMetaClsfCd(displayInfo.getMetaClsfCd()); // 메타 클래스 코드
		this.setBrandNm(displayInfo.getBrandNm()); // 브랜드명
		this.setBrandId(displayInfo.getBrandId()); // 브랜드ID
	}

	/**
	 * @return the possLendProductInfo
	 */
	public PossLendProductInfo getPossLendProductInfo() {
		return this.possLendProductInfo;
	}

	/**
	 * @param possLendProductInfo
	 *            the possLendProductInfo to set
	 */
	public void setPossLendProductInfo(PossLendProductInfo possLendProductInfo) {
		this.possLendProductInfo = possLendProductInfo;
	}

	/**
	 * @return the fullIapProductInfo
	 */
	public PurchaseProduct getFullIapProductInfo() {
		return this.fullIapProductInfo;
	}

	/**
	 * @param fullIapProductInfo
	 *            the fullIapProductInfo to set
	 */
	public void setFullIapProductInfo(PurchaseProduct fullIapProductInfo) {
		this.fullIapProductInfo = fullIapProductInfo;
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
	 * @return the specialCouponAmt
	 */
	public double getSpecialCouponAmt() {
		return this.specialCouponAmt;
	}

	/**
	 * @param specialCouponAmt
	 *            the specialCouponAmt to set
	 */
	public void setSpecialCouponAmt(double specialCouponAmt) {
		this.specialCouponAmt = specialCouponAmt;
	}

	/**
	 * @return the prodQty
	 */
	public int getProdQty() {
		return this.prodQty;
	}

	/**
	 * @param prodQty
	 *            the prodQty to set
	 */
	public void setProdQty(int prodQty) {
		this.prodQty = prodQty;
	}

	/**
	 * @return the resvCol01
	 */
	public String getResvCol01() {
		return this.resvCol01;
	}

	/**
	 * @param resvCol01
	 *            the resvCol01 to set
	 */
	public void setResvCol01(String resvCol01) {
		this.resvCol01 = resvCol01;
	}

	/**
	 * @return the resvCol02
	 */
	public String getResvCol02() {
		return this.resvCol02;
	}

	/**
	 * @param resvCol02
	 *            the resvCol02 to set
	 */
	public void setResvCol02(String resvCol02) {
		this.resvCol02 = resvCol02;
	}

	/**
	 * @return the resvCol03
	 */
	public String getResvCol03() {
		return this.resvCol03;
	}

	/**
	 * @param resvCol03
	 *            the resvCol03 to set
	 */
	public void setResvCol03(String resvCol03) {
		this.resvCol03 = resvCol03;
	}

	/**
	 * @return the resvCol04
	 */
	public String getResvCol04() {
		return this.resvCol04;
	}

	/**
	 * @param resvCol04
	 *            the resvCol04 to set
	 */
	public void setResvCol04(String resvCol04) {
		this.resvCol04 = resvCol04;
	}

	/**
	 * @return the resvCol05
	 */
	public String getResvCol05() {
		return this.resvCol05;
	}

	/**
	 * @param resvCol05
	 *            the resvCol05 to set
	 */
	public void setResvCol05(String resvCol05) {
		this.resvCol05 = resvCol05;
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
	 * @return the useFixrateProdId
	 */
	public String getUseFixrateProdId() {
		return this.useFixrateProdId;
	}

	/**
	 * @param useFixrateProdId
	 *            the useFixrateProdId to set
	 */
	public void setUseFixrateProdId(String useFixrateProdId) {
		this.useFixrateProdId = useFixrateProdId;
	}

	/**
	 * @return the useFixratePrchsId
	 */
	public String getUseFixratePrchsId() {
		return this.useFixratePrchsId;
	}

	/**
	 * @param useFixratePrchsId
	 *            the useFixratePrchsId to set
	 */
	public void setUseFixratePrchsId(String useFixratePrchsId) {
		this.useFixratePrchsId = useFixratePrchsId;
	}

	/**
	 * @return the useFixrateProdClsfCd
	 */
	public String getUseFixrateProdClsfCd() {
		return this.useFixrateProdClsfCd;
	}

	/**
	 * @param useFixrateProdClsfCd
	 *            the useFixrateProdClsfCd to set
	 */
	public void setUseFixrateProdClsfCd(String useFixrateProdClsfCd) {
		this.useFixrateProdClsfCd = useFixrateProdClsfCd;
	}

	/**
	 * @return the tid
	 */
	public String getTid() {
		return this.tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(String tid) {
		this.tid = tid;
	}

	/**
	 * @return the txId
	 */
	public String getTxId() {
		return this.txId;
	}

	/**
	 * @param txId
	 *            the txId to set
	 */
	public void setTxId(String txId) {
		this.txId = txId;
	}

	/**
	 * @return the parentProdId
	 */
	@Override
	public String getParentProdId() {
		return this.parentProdId;
	}

	/**
	 * @param parentProdId
	 *            the parentProdId to set
	 */
	@Override
	public void setParentProdId(String parentProdId) {
		this.parentProdId = parentProdId;
	}

	/**
	 * @return the partChrgVer
	 */
	public String getPartChrgVer() {
		return this.partChrgVer;
	}

	/**
	 * @param partChrgVer
	 *            the partChrgVer to set
	 */
	public void setPartChrgVer(String partChrgVer) {
		this.partChrgVer = partChrgVer;
	}

	/**
	 * @return the partChrgProdNm
	 */
	public String getPartChrgProdNm() {
		return this.partChrgProdNm;
	}

	/**
	 * @param partChrgProdNm
	 *            the partChrgProdNm to set
	 */
	public void setPartChrgProdNm(String partChrgProdNm) {
		this.partChrgProdNm = partChrgProdNm;
	}

	/**
	 * @return the fullProd
	 */
	public boolean isFullProd() {
		return this.fullProd;
	}

	/**
	 * @param fullProd
	 *            the fullProd to set
	 */
	public void setFullProd(boolean fullProd) {
		this.fullProd = fullProd;
	}

	/**
	 * @return the iapProdKind
	 */
	public String getIapProdKind() {
		return this.iapProdKind;
	}

	/**
	 * @param iapProdKind
	 *            the iapProdKind to set
	 */
	public void setIapProdKind(String iapProdKind) {
		this.iapProdKind = iapProdKind;
	}

	/**
	 * @return the iapProdCase
	 */
	public String getIapProdCase() {
		return this.iapProdCase;
	}

	/**
	 * @param iapProdCase
	 *            the iapProdCase to set
	 */
	public void setIapProdCase(String iapProdCase) {
		this.iapProdCase = iapProdCase;
	}

	/**
	 * @return the iapUsePeriod
	 */
	public Integer getIapUsePeriod() {
		return this.iapUsePeriod;
	}

	/**
	 * @param iapUsePeriod
	 *            the iapUsePeriod to set
	 */
	public void setIapUsePeriod(Integer iapUsePeriod) {
		this.iapUsePeriod = iapUsePeriod;
	}

	/**
	 * @return the s2sAutoPrchsYn
	 */
	public String getS2sAutoPrchsYn() {
		return this.s2sAutoPrchsYn;
	}

	/**
	 * @param s2sAutoPrchsYn
	 *            the s2sAutoPrchsYn to set
	 */
	public void setS2sAutoPrchsYn(String s2sAutoPrchsYn) {
		this.s2sAutoPrchsYn = s2sAutoPrchsYn;
	}

	/**
	 * @return the iapPostbackUrl
	 */
	public String getIapPostbackUrl() {
		return this.iapPostbackUrl;
	}

	/**
	 * @param iapPostbackUrl
	 *            the iapPostbackUrl to set
	 */
	public void setIapPostbackUrl(String iapPostbackUrl) {
		this.iapPostbackUrl = iapPostbackUrl;
	}

	/**
	 * @return the autoPrchsLastPeriodValue
	 */
	public Integer getAutoPrchsLastPeriodValue() {
		return this.autoPrchsLastPeriodValue;
	}

	/**
	 * @param autoPrchsLastPeriodValue
	 *            the autoPrchsLastPeriodValue to set
	 */
	public void setAutoPrchsLastPeriodValue(Integer autoPrchsLastPeriodValue) {
		this.autoPrchsLastPeriodValue = autoPrchsLastPeriodValue;
	}

	/**
	 * @return the rnBillCd
	 */
	public String getRnBillCd() {
		return this.rnBillCd;
	}

	/**
	 * @param rnBillCd
	 *            the rnBillCd to set
	 */
	public void setRnBillCd(String rnBillCd) {
		this.rnBillCd = rnBillCd;
	}

	/**
	 * @return the infoUseFee
	 */
	public String getInfoUseFee() {
		return this.infoUseFee;
	}

	/**
	 * @param infoUseFee
	 *            the infoUseFee to set
	 */
	public void setInfoUseFee(String infoUseFee) {
		this.infoUseFee = infoUseFee;
	}

	/**
	 * @return the cid
	 */
	public String getCid() {
		return this.cid;
	}

	/**
	 * @param cid
	 *            the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @return the contentsClsf
	 */
	public String getContentsClsf() {
		return this.contentsClsf;
	}

	/**
	 * @param contentsClsf
	 *            the contentsClsf to set
	 */
	public void setContentsClsf(String contentsClsf) {
		this.contentsClsf = contentsClsf;
	}

	/**
	 * @return the contentsType
	 */
	public String getContentsType() {
		return this.contentsType;
	}

	/**
	 * @param contentsType
	 *            the contentsType to set
	 */
	public void setContentsType(String contentsType) {
		this.contentsType = contentsType;
	}

	/**
	 * @return the prchsType
	 */
	public String getPrchsType() {
		return this.prchsType;
	}

	/**
	 * @param prchsType
	 *            the prchsType to set
	 */
	public void setPrchsType(String prchsType) {
		this.prchsType = prchsType;
	}

	/**
	 * @return the timbreClsf
	 */
	public String getTimbreClsf() {
		return this.timbreClsf;
	}

	/**
	 * @param timbreClsf
	 *            the timbreClsf to set
	 */
	public void setTimbreClsf(String timbreClsf) {
		this.timbreClsf = timbreClsf;
	}

	/**
	 * @return the timbreSctn
	 */
	public String getTimbreSctn() {
		return this.timbreSctn;
	}

	/**
	 * @param timbreSctn
	 *            the timbreSctn to set
	 */
	public void setTimbreSctn(String timbreSctn) {
		this.timbreSctn = timbreSctn;
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * @param menuId
	 *            the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the genreClsfCd
	 */
	public String getGenreClsfCd() {
		return this.genreClsfCd;
	}

	/**
	 * @param genreClsfCd
	 *            the genreClsfCd to set
	 */
	public void setGenreClsfCd(String genreClsfCd) {
		this.genreClsfCd = genreClsfCd;
	}

	/**
	 * @return the resultCd
	 */
	public String getResultCd() {
		return this.resultCd;
	}

	/**
	 * @param resultCd
	 *            the resultCd to set
	 */
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	/**
	 * @return the dwldExprDt
	 */
	public String getDwldExprDt() {
		return this.dwldExprDt;
	}

	/**
	 * @param dwldExprDt
	 *            the dwldExprDt to set
	 */
	public void setDwldExprDt(String dwldExprDt) {
		this.dwldExprDt = dwldExprDt;
	}

}
