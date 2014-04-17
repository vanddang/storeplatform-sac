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

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfo;

/**
 * 
 * 구매 상품 (전시Part 에서 넘겨받는 상품Object 기반)
 * 
 * Updated on : 2014. 2. 28. Updated by : 이승택, nTels.
 */
public class PurchaseProduct extends PaymentInfo {
	private static final long serialVersionUID = 1L;

	private PossLendProductInfo possLendProductInfo; // 소장/대여 상품 정보

	private String bonusCashPoint; // 보너스 캐쉬 지급 Point
	private String bonusCashUsableDayCnt; // 보너스 캐쉬 유효기간(일)
	private String afterAutoPayDt; // 다음 자동 결제일
	private String dwldAvailableDayCnt; // 다운로드 가능기간(일)
	private String usePeriodCnt; // 이용기간(일)
	private String loanPid; // 대여하기 상품 ID
	private String loanAmt; // 대여하기 상품 금액
	private String ownPid; // 소장하기 상품 ID
	private String ownAmt; // 소장하기 상품 금액

	private double specialCouponAmt;
	private int prodQty;
	private String resvCol01;
	private String resvCol02;
	private String resvCol03;
	private String resvCol04;
	private String resvCol05;
	private String useExprDt; // [비과금 구매요청 시 사용]
	private String useFixrateProdId; // 이용한 정액제 상품ID
	/* IAP */
	private String tid; // 부분유료화 개발사 구매Key
	private String txId; // 부분유료화 전자영수증 번호
	private String parentProdId; // 부모_상품_ID
	private String partChrgVer; // 부분_유료_버전
	private String partChrgProdNm; // 부분_유료_상품_명
	/* Ring & Bell */
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
	public String getLoanAmt() {
		return this.loanAmt;
	}

	/**
	 * @param loanAmt
	 *            the loanAmt to set
	 */
	public void setLoanAmt(String loanAmt) {
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
	public String getOwnAmt() {
		return this.ownAmt;
	}

	/**
	 * @param ownAmt
	 *            the ownAmt to set
	 */
	public void setOwnAmt(String ownAmt) {
		this.ownAmt = ownAmt;
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
	public String getParentProdId() {
		return this.parentProdId;
	}

	/**
	 * @param parentProdId
	 *            the parentProdId to set
	 */
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

}
