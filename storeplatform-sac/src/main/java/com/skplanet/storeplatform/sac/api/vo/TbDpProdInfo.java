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
 * 전처리 상품 정보 Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class TbDpProdInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private String prodId; // 상품_ID
	private String prodChrgYn; // 상품_유료_여부
	private String sellerMbrNo; // 판매자_회원_번호
	private String svcGrpCd; // 서비스_그룹_코드
	private String svcTypeCd; // 서비스_타입_코드
	private String prodGrdCd; // 상품_등급_코드
	private String expoSellerNm; // 노출_판매자_명
	private String expoSellerTelno; // 노출_판매자_전화번호
	private String expoSellerEmail; // 노출_판매자_이메일
	private String cid; // CID
	private String mallCd; // 상점_코드
	private String feeCaseCd; // 요금_유형_코드
	private String feeUnitCd; // 요금_단위_코드
	private String usePeriodUnitCd; // 사용_기간_단위_코드
	private long usePeriod; // 사용_기간
	private String drmYn; // DRM_여부
	private String drmSetCd; // DRM_설정_코드
	private String drmSetValue; // DRM_설정_값
	private String contentsTypeCd; // 컨텐츠_타입_코드(PD0057, PD0073, PD0093, PD0104)
	private String metaClsfCd; // 메타_구분_코드(CT28)
	private String topMenuId; // 상위메뉴아이디
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
	 * @return the prodChrgYn
	 */
	public String getProdChrgYn() {
		return this.prodChrgYn;
	}

	/**
	 * @param prodChrgYn
	 *            the prodChrgYn to set
	 */
	public void setProdChrgYn(String prodChrgYn) {
		this.prodChrgYn = prodChrgYn;
	}

	/**
	 * @return the sellerMbrNo
	 */
	public String getSellerMbrNo() {
		return this.sellerMbrNo;
	}

	/**
	 * @param sellerMbrNo
	 *            the sellerMbrNo to set
	 */
	public void setSellerMbrNo(String sellerMbrNo) {
		this.sellerMbrNo = sellerMbrNo;
	}

	/**
	 * @return the svcGrpCd
	 */
	public String getSvcGrpCd() {
		return this.svcGrpCd;
	}

	/**
	 * @param svcGrpCd
	 *            the svcGrpCd to set
	 */
	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}

	/**
	 * @return the svcTypeCd
	 */
	public String getSvcTypeCd() {
		return this.svcTypeCd;
	}

	/**
	 * @param svcTypeCd
	 *            the svcTypeCd to set
	 */
	public void setSvcTypeCd(String svcTypeCd) {
		this.svcTypeCd = svcTypeCd;
	}

	/**
	 * @return the prodGrdCd
	 */
	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	/**
	 * @param prodGrdCd
	 *            the prodGrdCd to set
	 */
	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	/**
	 * @return the expoSellerNm
	 */
	public String getExpoSellerNm() {
		return this.expoSellerNm;
	}

	/**
	 * @param expoSellerNm
	 *            the expoSellerNm to set
	 */
	public void setExpoSellerNm(String expoSellerNm) {
		this.expoSellerNm = expoSellerNm;
	}

	/**
	 * @return the expoSellerTelno
	 */
	public String getExpoSellerTelno() {
		return this.expoSellerTelno;
	}

	/**
	 * @param expoSellerTelno
	 *            the expoSellerTelno to set
	 */
	public void setExpoSellerTelno(String expoSellerTelno) {
		this.expoSellerTelno = expoSellerTelno;
	}

	/**
	 * @return the expoSellerEmail
	 */
	public String getExpoSellerEmail() {
		return this.expoSellerEmail;
	}

	/**
	 * @param expoSellerEmail
	 *            the expoSellerEmail to set
	 */
	public void setExpoSellerEmail(String expoSellerEmail) {
		this.expoSellerEmail = expoSellerEmail;
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
	 * @return the mallCd
	 */
	public String getMallCd() {
		return this.mallCd;
	}

	/**
	 * @param mallCd
	 *            the mallCd to set
	 */
	public void setMallCd(String mallCd) {
		this.mallCd = mallCd;
	}

	/**
	 * @return the feeCaseCd
	 */
	public String getFeeCaseCd() {
		return this.feeCaseCd;
	}

	/**
	 * @param feeCaseCd
	 *            the feeCaseCd to set
	 */
	public void setFeeCaseCd(String feeCaseCd) {
		this.feeCaseCd = feeCaseCd;
	}

	/**
	 * @return the feeUnitCd
	 */
	public String getFeeUnitCd() {
		return this.feeUnitCd;
	}

	/**
	 * @param feeUnitCd
	 *            the feeUnitCd to set
	 */
	public void setFeeUnitCd(String feeUnitCd) {
		this.feeUnitCd = feeUnitCd;
	}

	/**
	 * @return the usePeriodUnitCd
	 */
	public String getUsePeriodUnitCd() {
		return this.usePeriodUnitCd;
	}

	/**
	 * @param usePeriodUnitCd
	 *            the usePeriodUnitCd to set
	 */
	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}

	/**
	 * @return the usePeriod
	 */
	public long getUsePeriod() {
		return this.usePeriod;
	}

	/**
	 * @param usePeriod
	 *            the usePeriod to set
	 */
	public void setUsePeriod(long usePeriod) {
		this.usePeriod = usePeriod;
	}

	/**
	 * @return the drmYn
	 */
	public String getDrmYn() {
		return this.drmYn;
	}

	/**
	 * @param drmYn
	 *            the drmYn to set
	 */
	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}

	/**
	 * @return the drmSetCd
	 */
	public String getDrmSetCd() {
		return this.drmSetCd;
	}

	/**
	 * @param drmSetCd
	 *            the drmSetCd to set
	 */
	public void setDrmSetCd(String drmSetCd) {
		this.drmSetCd = drmSetCd;
	}

	/**
	 * @return the drmSetValue
	 */
	public String getDrmSetValue() {
		return this.drmSetValue;
	}

	/**
	 * @param drmSetValue
	 *            the drmSetValue to set
	 */
	public void setDrmSetValue(String drmSetValue) {
		this.drmSetValue = drmSetValue;
	}

	/**
	 * @return the contentsTypeCd
	 */
	public String getContentsTypeCd() {
		return this.contentsTypeCd;
	}

	/**
	 * @param contentsTypeCd
	 *            the contentsTypeCd to set
	 */
	public void setContentsTypeCd(String contentsTypeCd) {
		this.contentsTypeCd = contentsTypeCd;
	}

	/**
	 * @return the metaClsfCd
	 */
	public String getMetaClsfCd() {
		return this.metaClsfCd;
	}

	/**
	 * @param metaClsfCd
	 *            the metaClsfCd to set
	 */
	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
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
