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
 * 전처리 쇼핑 상품 Value Object
 * </pre>
 *
 * Created on : 2014-01-02
 * Created by : 김형식, SK플래닛
 * Last Updated on : 2014-01-02
 * Last Updated by : 김형식, SK플래닛
 */
public class TbDpShpgProdInfo extends CommonInfo{
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

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public long getEpsdCnt() {
		return this.epsdCnt;
	}

	public void setEpsdCnt(long epsdCnt) {
		this.epsdCnt = epsdCnt;
	}

	public String getChnlCompNm() {
		return this.chnlCompNm;
	}

	public void setChnlCompNm(String chnlCompNm) {
		this.chnlCompNm = chnlCompNm;
	}

	public String getSamplUrl() {
		return this.samplUrl;
	}

	public void setSamplUrl(String samplUrl) {
		this.samplUrl = samplUrl;
	}

	public String getSaleYn() {
		return this.saleYn;
	}

	public void setSaleYn(String saleYn) {
		this.saleYn = saleYn;
	}

	public String getContentsOrdrCd() {
		return this.contentsOrdrCd;
	}

	public void setContentsOrdrCd(String contentsOrdrCd) {
		this.contentsOrdrCd = contentsOrdrCd;
	}

	public String getMgzinSubscripCd() {
		return this.mgzinSubscripCd;
	}

	public void setMgzinSubscripCd(String mgzinSubscripCd) {
		this.mgzinSubscripCd = mgzinSubscripCd;
	}

	public long getMmMaxSaleQty() {
		return this.mmMaxSaleQty;
	}

	public void setMmMaxSaleQty(long mmMaxSaleQty) {
		this.mmMaxSaleQty = mmMaxSaleQty;
	}

	public long getDayMaxSaleQty() {
		return this.dayMaxSaleQty;
	}

	public void setDayMaxSaleQty(long dayMaxSaleQty) {
		this.dayMaxSaleQty = dayMaxSaleQty;
	}

	public long getMmMbrMaxPrchsQty() {
		return this.mmMbrMaxPrchsQty;
	}

	public void setMmMbrMaxPrchsQty(long mmMbrMaxPrchsQty) {
		this.mmMbrMaxPrchsQty = mmMbrMaxPrchsQty;
	}

	public long getDayMbrMaxPrchsQty() {
		return this.dayMbrMaxPrchsQty;
	}

	public void setDayMbrMaxPrchsQty(long dayMbrMaxPrchsQty) {
		this.dayMbrMaxPrchsQty = dayMbrMaxPrchsQty;
	}

	public long getFirstMaxPrchsQty() {
		return this.firstMaxPrchsQty;
	}

	public void setFirstMaxPrchsQty(long firstMaxPrchsQty) {
		this.firstMaxPrchsQty = firstMaxPrchsQty;
	}

	public String getUsePlac() {
		return this.usePlac;
	}

	public void setUsePlac(String usePlac) {
		this.usePlac = usePlac;
	}

	public String getUseLimtDesc() {
		return this.useLimtDesc;
	}

	public void setUseLimtDesc(String useLimtDesc) {
		this.useLimtDesc = useLimtDesc;
	}

	public String getNoticeMatt() {
		return this.noticeMatt;
	}

	public void setNoticeMatt(String noticeMatt) {
		this.noticeMatt = noticeMatt;
	}

	public String getPrchsCancelDrbkReason() {
		return this.prchsCancelDrbkReason;
	}

	public void setPrchsCancelDrbkReason(String prchsCancelDrbkReason) {
		this.prchsCancelDrbkReason = prchsCancelDrbkReason;
	}

	public String getProdCaseCd() {
		return this.prodCaseCd;
	}

	public void setProdCaseCd(String prodCaseCd) {
		this.prodCaseCd = prodCaseCd;
	}

	public String getB2bProdYn() {
		return this.b2bProdYn;
	}

	public void setB2bProdYn(String b2bProdYn) {
		this.b2bProdYn = b2bProdYn;
	}

	public String getDlvProdYn() {
		return this.dlvProdYn;
	}

	public void setDlvProdYn(String dlvProdYn) {
		this.dlvProdYn = dlvProdYn;
	}

	public String getMangBpId() {
		return this.mangBpId;
	}

	public void setMangBpId(String mangBpId) {
		this.mangBpId = mangBpId;
	}

	public String getSrcContentId() {
		return this.srcContentId;
	}

	public void setSrcContentId(String srcContentId) {
		this.srcContentId = srcContentId;
	}

	public String getRegId() {
		return this.regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDt() {
		return this.regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getUpdId() {
		return this.updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public String getUpdDt() {
		return this.updDt;
	}

	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

}
