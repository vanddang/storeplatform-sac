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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 전처리 쇼핑 상품 Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class TbDpShpgProdInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private String prodId; // 상품_ID
	private long epsdCnt; // 에피소드_수
	private String chnlCompNm; // 채널_회사_명
	private String samplUrl; // 샘플_URL
	private String saleYn; // 판매_여부
	private String contentsOrdrCd; // 컨텐츠_오더_코드(D)
	private String mgzinSubscripCd; // 매거진_구독_코드
	private long mmMaxSaleQty; // 월_최대_판매_수량
	private long dayMaxSaleQty; // 일_최대_판매_수량
	private long mmMbrMaxPrchsQty; // 월_회원_최대_구매_수량
	private long dayMbrMaxPrchsQty; // 일_회원_최대_구매_수량
	private long firstMaxPrchsQty; // 1차_최대_구매_수량
	private String usePlac; // 사용_장소
	private String useLimtDesc; // 사용_제한_설명
	private String noticeMatt; // 공지_사항
	private String prchsCancelDrbkReason; // 구매_취소_환불_사유
	private String prodCaseCd; // 상품_유형_코드(DP0063)
	private String b2bProdYn; // B2B_상품_여부
	private String dlvProdYn; // 배송_상품_여부
	private String mangBpId; // 관리_BP_ID
	private String srcContentId; // 소스_컨텐트_ID
	private String regId; // 등록_ID
	private String regDt; // 등록_일시
	private String updId; // 수정_ID
	private String updDt; // 수정_일시
	private String cudType; // CUD

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the epsdCnt
	 */
	public long getEpsdCnt() {
		return this.epsdCnt;
	}

	/**
	 * @param epsdCnt
	 *            the epsdCnt to set
	 */
	public void setEpsdCnt(long epsdCnt) {
		this.epsdCnt = epsdCnt;
	}

	/**
	 * @return the chnlCompNm
	 */
	public String getChnlCompNm() {
		return this.chnlCompNm;
	}

	/**
	 * @param chnlCompNm
	 *            the chnlCompNm to set
	 */
	public void setChnlCompNm(String chnlCompNm) {
		this.chnlCompNm = chnlCompNm;
	}

	/**
	 * @return the samplUrl
	 */
	public String getSamplUrl() {
		return this.samplUrl;
	}

	/**
	 * @param samplUrl
	 *            the samplUrl to set
	 */
	public void setSamplUrl(String samplUrl) {
		this.samplUrl = samplUrl;
	}

	/**
	 * @return the saleYn
	 */
	public String getSaleYn() {
		return this.saleYn;
	}

	/**
	 * @param saleYn
	 *            the saleYn to set
	 */
	public void setSaleYn(String saleYn) {
		this.saleYn = saleYn;
	}

	/**
	 * @return the contentsOrdrCd
	 */
	public String getContentsOrdrCd() {
		return this.contentsOrdrCd;
	}

	/**
	 * @param contentsOrdrCd
	 *            the contentsOrdrCd to set
	 */
	public void setContentsOrdrCd(String contentsOrdrCd) {
		this.contentsOrdrCd = contentsOrdrCd;
	}

	/**
	 * @return the mgzinSubscripCd
	 */
	public String getMgzinSubscripCd() {
		return this.mgzinSubscripCd;
	}

	/**
	 * @param mgzinSubscripCd
	 *            the mgzinSubscripCd to set
	 */
	public void setMgzinSubscripCd(String mgzinSubscripCd) {
		this.mgzinSubscripCd = mgzinSubscripCd;
	}

	/**
	 * @return the mmMaxSaleQty
	 */
	public long getMmMaxSaleQty() {
		return this.mmMaxSaleQty;
	}

	/**
	 * @param mmMaxSaleQty
	 *            the mmMaxSaleQty to set
	 */
	public void setMmMaxSaleQty(long mmMaxSaleQty) {
		this.mmMaxSaleQty = mmMaxSaleQty;
	}

	/**
	 * @return the dayMaxSaleQty
	 */
	public long getDayMaxSaleQty() {
		return this.dayMaxSaleQty;
	}

	/**
	 * @param dayMaxSaleQty
	 *            the dayMaxSaleQty to set
	 */
	public void setDayMaxSaleQty(long dayMaxSaleQty) {
		this.dayMaxSaleQty = dayMaxSaleQty;
	}

	/**
	 * @return the mmMbrMaxPrchsQty
	 */
	public long getMmMbrMaxPrchsQty() {
		return this.mmMbrMaxPrchsQty;
	}

	/**
	 * @param mmMbrMaxPrchsQty
	 *            the mmMbrMaxPrchsQty to set
	 */
	public void setMmMbrMaxPrchsQty(long mmMbrMaxPrchsQty) {
		this.mmMbrMaxPrchsQty = mmMbrMaxPrchsQty;
	}

	/**
	 * @return the dayMbrMaxPrchsQty
	 */
	public long getDayMbrMaxPrchsQty() {
		return this.dayMbrMaxPrchsQty;
	}

	/**
	 * @param dayMbrMaxPrchsQty
	 *            the dayMbrMaxPrchsQty to set
	 */
	public void setDayMbrMaxPrchsQty(long dayMbrMaxPrchsQty) {
		this.dayMbrMaxPrchsQty = dayMbrMaxPrchsQty;
	}

	/**
	 * @return the firstMaxPrchsQty
	 */
	public long getFirstMaxPrchsQty() {
		return this.firstMaxPrchsQty;
	}

	/**
	 * @param firstMaxPrchsQty
	 *            the firstMaxPrchsQty to set
	 */
	public void setFirstMaxPrchsQty(long firstMaxPrchsQty) {
		this.firstMaxPrchsQty = firstMaxPrchsQty;
	}

	/**
	 * @return the usePlac
	 */
	public String getUsePlac() {
		return this.usePlac;
	}

	/**
	 * @param usePlac
	 *            the usePlac to set
	 */
	public void setUsePlac(String usePlac) {
		this.usePlac = usePlac;
	}

	/**
	 * @return the useLimtDesc
	 */
	public String getUseLimtDesc() {
		return this.useLimtDesc;
	}

	/**
	 * @param useLimtDesc
	 *            the useLimtDesc to set
	 */
	public void setUseLimtDesc(String useLimtDesc) {
		this.useLimtDesc = useLimtDesc;
	}

	/**
	 * @return the noticeMatt
	 */
	public String getNoticeMatt() {
		return this.noticeMatt;
	}

	/**
	 * @param noticeMatt
	 *            the noticeMatt to set
	 */
	public void setNoticeMatt(String noticeMatt) {
		this.noticeMatt = noticeMatt;
	}

	/**
	 * @return the prchsCancelDrbkReason
	 */
	public String getPrchsCancelDrbkReason() {
		return this.prchsCancelDrbkReason;
	}

	/**
	 * @param prchsCancelDrbkReason
	 *            the prchsCancelDrbkReason to set
	 */
	public void setPrchsCancelDrbkReason(String prchsCancelDrbkReason) {
		this.prchsCancelDrbkReason = prchsCancelDrbkReason;
	}

	/**
	 * @return the prodCaseCd
	 */
	public String getProdCaseCd() {
		return this.prodCaseCd;
	}

	/**
	 * @param prodCaseCd
	 *            the prodCaseCd to set
	 */
	public void setProdCaseCd(String prodCaseCd) {
		this.prodCaseCd = prodCaseCd;
	}

	/**
	 * @return the b2bProdYn
	 */
	public String getB2bProdYn() {
		return this.b2bProdYn;
	}

	/**
	 * @param b2bProdYn
	 *            the b2bProdYn to set
	 */
	public void setB2bProdYn(String b2bProdYn) {
		this.b2bProdYn = b2bProdYn;
	}

	/**
	 * @return the dlvProdYn
	 */
	public String getDlvProdYn() {
		return this.dlvProdYn;
	}

	/**
	 * @param dlvProdYn
	 *            the dlvProdYn to set
	 */
	public void setDlvProdYn(String dlvProdYn) {
		this.dlvProdYn = dlvProdYn;
	}

	/**
	 * @return the mangBpId
	 */
	public String getMangBpId() {
		return this.mangBpId;
	}

	/**
	 * @param mangBpId
	 *            the mangBpId to set
	 */
	public void setMangBpId(String mangBpId) {
		this.mangBpId = mangBpId;
	}

	/**
	 * @return the srcContentId
	 */
	public String getSrcContentId() {
		return this.srcContentId;
	}

	/**
	 * @param srcContentId
	 *            the srcContentId to set
	 */
	public void setSrcContentId(String srcContentId) {
		this.srcContentId = srcContentId;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * @param regDt
	 *            the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	/**
	 * @return the updId
	 */
	public String getUpdId() {
		return this.updId;
	}

	/**
	 * @param updId
	 *            the updId to set
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}

	/**
	 * @return the updDt
	 */
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param updDt
	 *            the updDt to set
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

	/**
	 * @return the cudType
	 */
	public String getCudType() {
		return this.cudType;
	}

	/**
	 * @param cudType
	 *            the cudType to set
	 */
	public void setCudType(String cudType) {
		this.cudType = cudType;
	}

}
