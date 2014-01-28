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
 * 전처리 상품 정보 Value Object
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class TbDpProdInfo extends CommonInfo {
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

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdChrgYn() {
		return this.prodChrgYn;
	}

	public void setProdChrgYn(String prodChrgYn) {
		this.prodChrgYn = prodChrgYn;
	}

	public String getSellerMbrNo() {
		return this.sellerMbrNo;
	}

	public void setSellerMbrNo(String sellerMbrNo) {
		this.sellerMbrNo = sellerMbrNo;
	}

	public String getMetaClsfCd() {
		return this.metaClsfCd;
	}

	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
	}

	public String getSvcGrpCd() {
		return this.svcGrpCd;
	}

	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}

	public String getSvcTypeCd() {
		return this.svcTypeCd;
	}

	public void setSvcTypeCd(String svcTypeCd) {
		this.svcTypeCd = svcTypeCd;
	}

	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	public String getExpoSellerNm() {
		return this.expoSellerNm;
	}

	public void setExpoSellerNm(String expoSellerNm) {
		this.expoSellerNm = expoSellerNm;
	}

	public String getExpoSellerTelno() {
		return this.expoSellerTelno;
	}

	public void setExpoSellerTelno(String expoSellerTelno) {
		this.expoSellerTelno = expoSellerTelno;
	}

	public String getExpoSellerEmail() {
		return this.expoSellerEmail;
	}

	public void setExpoSellerEmail(String expoSellerEmail) {
		this.expoSellerEmail = expoSellerEmail;
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getMallCd() {
		return this.mallCd;
	}

	public void setMallCd(String mallCd) {
		this.mallCd = mallCd;
	}

	public String getFeeCaseCd() {
		return this.feeCaseCd;
	}

	public void setFeeCaseCd(String feeCaseCd) {
		this.feeCaseCd = feeCaseCd;
	}

	public String getFeeUnitCd() {
		return this.feeUnitCd;
	}

	public void setFeeUnitCd(String feeUnitCd) {
		this.feeUnitCd = feeUnitCd;
	}

	public String getUsePeriodUnitCd() {
		return this.usePeriodUnitCd;
	}

	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}

	public long getUsePeriod() {
		return this.usePeriod;
	}

	public void setUsePeriod(long usePeriod) {
		this.usePeriod = usePeriod;
	}

	public String getDrmYn() {
		return this.drmYn;
	}

	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}

	public String getDrmSetCd() {
		return this.drmSetCd;
	}

	public void setDrmSetCd(String drmSetCd) {
		this.drmSetCd = drmSetCd;
	}

	public String getDrmSetValue() {
		return this.drmSetValue;
	}

	public void setDrmSetValue(String drmSetValue) {
		this.drmSetValue = drmSetValue;
	}

	public String getContentsTypeCd() {
		return this.contentsTypeCd;
	}

	public void setContentsTypeCd(String contentsTypeCd) {
		this.contentsTypeCd = contentsTypeCd;
	}

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
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

	public String getCudType() {
		return this.cudType;
	}

	public void setCudType(String cudType) {
		this.cudType = cudType;
	}

}
