/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.springframework.util.StringUtils;

/**
 * 구매상세 CommonVo
 *
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class PrchsDtl extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String prchsId;
	private Integer prchsDtlId;
	private String useTenantId;
	private String useInsdUsermbrNo;
	private String useInsdDeviceId;
	private String prchsDt;
	private Double totAmt;
	private String currencyCd;
	private String prchsReqPathCd;
	private String clientIp;
	private String sendInsdUsermbrNo;
	private String sendInsdDeviceId;
	private String recvDt;
	private String recvConfPathCd;
	private String prodId;
	private Double prodAmt;
	private Integer prodQty;
	private String tenantProdGrpCd;
	private String statusCd;
	private String cancelReqPathCd;
	private String cancelDt;
	private String prchsCaseCd;
	private String drmYn;
	private String useStartDt;
	private String useExprDt;
	private String dwldStartDt;
	private String dwldExprDt;
	private String prchsProdType;
	private String useHidingYn;
	private String sendHidingYn;
	private String useFixrateProdId;
	private String useFixratePrchsId;
	private String cpnPublishCd;
	private String cpnDlvUrl;
	private String cpnAddInfo;
	private String cpnBizProdSeq;
	private String cpnBizOrderNo;
	private String tid; // 부분유료화 개발사 구매Key
	private String txId; // 부분유료화 전자영수증 번호
	private String parentProdId; // 부모_상품_ID
	private String partChrgVer; // 부분_유료_버전
	private String partChrgProdNm; // 부분_유료_상품_명
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
	private String prchsResvDesc;
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;
	private String resvCol01;
	private String resvCol02;
	private String resvCol03;
	private String resvCol04;
	private String resvCol05;
	private String alarmYn;
	private String giftMsg; // 선물 메시지
	private String cpnUseStatusCd;
	private String usePeriodSetCd;
	private String usePeriodRedateCd;
	private String usePeriodUnitCd;
	private String usePeriod;
	private String cpnProdCaseCd;

	private String couponCmsPrchsId;

	/**
	 * @return the couponCmsPrchsId
	 */
	public String getCouponCmsPrchsId() {
		return this.couponCmsPrchsId;
	}

	/**
	 * @param couponCmsPrchsId
	 *            the couponCmsPrchsId to set
	 */
	public void setCouponCmsPrchsId(String couponCmsPrchsId) {
		this.couponCmsPrchsId = couponCmsPrchsId;
	}

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
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the prchsDtlId
	 */
	public Integer getPrchsDtlId() {
		return this.prchsDtlId;
	}

	/**
	 * @param prchsDtlId
	 *            the prchsDtlId to set
	 */
	public void setPrchsDtlId(Integer prchsDtlId) {
		this.prchsDtlId = prchsDtlId;
	}

	/**
	 * @return the useTenantId
	 */
	public String getUseTenantId() {
		return this.useTenantId;
	}

	/**
	 * @param useTenantId
	 *            the useTenantId to set
	 */
	public void setUseTenantId(String useTenantId) {
		this.useTenantId = useTenantId;
	}

	/**
	 * @return the useInsdUsermbrNo
	 */
	public String getUseInsdUsermbrNo() {
		return this.useInsdUsermbrNo;
	}

	/**
	 * @param useInsdUsermbrNo
	 *            the useInsdUsermbrNo to set
	 */
	public void setUseInsdUsermbrNo(String useInsdUsermbrNo) {
		this.useInsdUsermbrNo = useInsdUsermbrNo;
	}

	/**
	 * @return the useInsdDeviceId
	 */
	public String getUseInsdDeviceId() {
		return this.useInsdDeviceId;
	}

	/**
	 * @param useInsdDeviceId
	 *            the useInsdDeviceId to set
	 */
	public void setUseInsdDeviceId(String useInsdDeviceId) {
		this.useInsdDeviceId = useInsdDeviceId;
	}

	/**
	 * @return the prchsDt
	 */
	public String getPrchsDt() {
		return this.prchsDt;
	}

	/**
	 * @param prchsDt
	 *            the prchsDt to set
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * @return the totAmt
	 */
	public Double getTotAmt() {
		return this.totAmt;
	}

	/**
	 * @param totAmt
	 *            the totAmt to set
	 */
	public void setTotAmt(Double totAmt) {
		this.totAmt = totAmt;
	}

	/**
	 * @return the currencyCd
	 */
	public String getCurrencyCd() {
		return this.currencyCd;
	}

	/**
	 * @param currencyCd
	 *            the currencyCd to set
	 */
	public void setCurrencyCd(String currencyCd) {
		this.currencyCd = currencyCd;
	}

	/**
	 * @return the prchsReqPathCd
	 */
	public String getPrchsReqPathCd() {
		return this.prchsReqPathCd;
	}

	/**
	 * @param prchsReqPathCd
	 *            the prchsReqPathCd to set
	 */
	public void setPrchsReqPathCd(String prchsReqPathCd) {
		this.prchsReqPathCd = prchsReqPathCd;
	}

	/**
	 * @return the clientIp
	 */
	public String getClientIp() {
		return this.clientIp;
	}

	/**
	 * @param clientIp
	 *            the clientIp to set
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * @return the sendInsdUsermbrNo
	 */
	public String getSendInsdUsermbrNo() {
		return this.sendInsdUsermbrNo;
	}

	/**
	 * @param sendInsdUsermbrNo
	 *            the sendInsdUsermbrNo to set
	 */
	public void setSendInsdUsermbrNo(String sendInsdUsermbrNo) {
		this.sendInsdUsermbrNo = sendInsdUsermbrNo;
	}

	/**
	 * @return the sendInsdDeviceId
	 */
	public String getSendInsdDeviceId() {
		return this.sendInsdDeviceId;
	}

	/**
	 * @param sendInsdDeviceId
	 *            the sendInsdDeviceId to set
	 */
	public void setSendInsdDeviceId(String sendInsdDeviceId) {
		this.sendInsdDeviceId = sendInsdDeviceId;
	}

	/**
	 * @return the recvDt
	 */
	public String getRecvDt() {
		return this.recvDt;
	}

	/**
	 * @param recvDt
	 *            the recvDt to set
	 */
	public void setRecvDt(String recvDt) {
		this.recvDt = recvDt;
	}

	/**
	 * @return the recvConfPathCd
	 */
	public String getRecvConfPathCd() {
		return this.recvConfPathCd;
	}

	/**
	 * @param recvConfPathCd
	 *            the recvConfPathCd to set
	 */
	public void setRecvConfPathCd(String recvConfPathCd) {
		this.recvConfPathCd = recvConfPathCd;
	}

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
	 * @return the prodAmt
	 */
	public Double getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(Double prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the prodQty
	 */
	public Integer getProdQty() {
		return this.prodQty;
	}

	/**
	 * @param prodQty
	 *            the prodQty to set
	 */
	public void setProdQty(Integer prodQty) {
		this.prodQty = prodQty;
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
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return this.statusCd;
	}

	/**
	 * @param statusCd
	 *            the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	/**
	 * @return the cancelReqPathCd
	 */
	public String getCancelReqPathCd() {
		return this.cancelReqPathCd;
	}

	/**
	 * @param cancelReqPathCd
	 *            the cancelReqPathCd to set
	 */
	public void setCancelReqPathCd(String cancelReqPathCd) {
		this.cancelReqPathCd = cancelReqPathCd;
	}

	/**
	 * @return the cancelDt
	 */
	public String getCancelDt() {
		return this.cancelDt;
	}

	/**
	 * @param cancelDt
	 *            the cancelDt to set
	 */
	public void setCancelDt(String cancelDt) {
		this.cancelDt = cancelDt;
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
	 * @return the dwldStartDt
	 */
	public String getDwldStartDt() {
		return this.dwldStartDt;
	}

	/**
	 * @param dwldStartDt
	 *            the dwldStartDt to set
	 */
	public void setDwldStartDt(String dwldStartDt) {
		this.dwldStartDt = dwldStartDt;
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

	/**
	 * @return the prchsProdType
	 */
	public String getPrchsProdType() {
		return this.prchsProdType;
	}

	/**
	 * @param prchsProdType
	 *            the prchsProdType to set
	 */
	public void setPrchsProdType(String prchsProdType) {
		this.prchsProdType = prchsProdType;
	}

	/**
	 * @return the useHidingYn
	 */
	public String getUseHidingYn() {
		return this.useHidingYn;
	}

	/**
	 * @param useHidingYn
	 *            the useHidingYn to set
	 */
	public void setUseHidingYn(String useHidingYn) {
		this.useHidingYn = useHidingYn;
	}

	/**
	 * @return the sendHidingYn
	 */
	public String getSendHidingYn() {
		return this.sendHidingYn;
	}

	/**
	 * @param sendHidingYn
	 *            the sendHidingYn to set
	 */
	public void setSendHidingYn(String sendHidingYn) {
		this.sendHidingYn = sendHidingYn;
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
	 * @return the cpnPublishCd
	 */
	public String getCpnPublishCd() {
		return this.cpnPublishCd;
	}

	/**
	 * @param cpnPublishCd
	 *            the cpnPublishCd to set
	 */
	public void setCpnPublishCd(String cpnPublishCd) {
		this.cpnPublishCd = cpnPublishCd;
	}

	/**
	 * @return the cpnDlvUrl
	 */
	public String getCpnDlvUrl() {
		return this.cpnDlvUrl;
	}

	/**
	 * @param cpnDlvUrl
	 *            the cpnDlvUrl to set
	 */
	public void setCpnDlvUrl(String cpnDlvUrl) {
		this.cpnDlvUrl = cpnDlvUrl;
	}

	/**
	 * @return the cpnAddInfo
	 */
	public String getCpnAddInfo() {
		return this.cpnAddInfo;
	}

	/**
	 * @param cpnAddInfo
	 *            the cpnAddInfo to set
	 */
	public void setCpnAddInfo(String cpnAddInfo) {
		this.cpnAddInfo = cpnAddInfo;
	}

	/**
	 * @return the cpnBizProdSeq
	 */
	public String getCpnBizProdSeq() {
		return this.cpnBizProdSeq;
	}

	/**
	 * @param cpnBizProdSeq
	 *            the cpnBizProdSeq to set
	 */
	public void setCpnBizProdSeq(String cpnBizProdSeq) {
		this.cpnBizProdSeq = cpnBizProdSeq;
	}

	/**
	 * @return the cpnBizOrderNo
	 */
	public String getCpnBizOrderNo() {
		return this.cpnBizOrderNo;
	}

	/**
	 * @param cpnBizOrderNo
	 *            the cpnBizOrderNo to set
	 */
	public void setCpnBizOrderNo(String cpnBizOrderNo) {
		this.cpnBizOrderNo = cpnBizOrderNo;
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
		this.partChrgProdNm = StringUtils.replace(partChrgProdNm, "\u0000","");
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
	 * @return the prchsResvDesc
	 */
	public String getPrchsResvDesc() {
		return this.prchsResvDesc;
	}

	/**
	 * @param prchsResvDesc
	 *            the prchsResvDesc to set
	 */
	public void setPrchsResvDesc(String prchsResvDesc) {
		this.prchsResvDesc = prchsResvDesc;
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
	 * @return the alarmYn
	 */
	public String getAlarmYn() {
		return this.alarmYn;
	}

	/**
	 * @param alarmYn
	 *            the alarmYn to set
	 */
	public void setAlarmYn(String alarmYn) {
		this.alarmYn = alarmYn;
	}

	/**
	 * @return the giftMsg
	 */
	public String getGiftMsg() {
		return this.giftMsg;
	}

	/**
	 * @param giftMsg
	 *            the giftMsg to set
	 */
	public void setGiftMsg(String giftMsg) {
		this.giftMsg = giftMsg;
	}

	/**
	 * @return the cpnUseStatusCd
	 */
	public String getCpnUseStatusCd() {
		return this.cpnUseStatusCd;
	}

	/**
	 * @param cpnUseStatusCd
	 *            the cpnUseStatusCd to set
	 */
	public void setCpnUseStatusCd(String cpnUseStatusCd) {
		this.cpnUseStatusCd = cpnUseStatusCd;
	}

	/**
	 * @return the usePeriodSetCd
	 */
	public String getUsePeriodSetCd() {
		return this.usePeriodSetCd;
	}

	/**
	 * @param usePeriodSetCd
	 *            the usePeriodSetCd to set
	 */
	public void setUsePeriodSetCd(String usePeriodSetCd) {
		this.usePeriodSetCd = usePeriodSetCd;
	}

	/**
	 * @return the usePeriodRedateCd
	 */
	public String getUsePeriodRedateCd() {
		return this.usePeriodRedateCd;
	}

	/**
	 * @param usePeriodRedateCd
	 *            the usePeriodRedateCd to set
	 */
	public void setUsePeriodRedateCd(String usePeriodRedateCd) {
		this.usePeriodRedateCd = usePeriodRedateCd;
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
	public String getUsePeriod() {
		return this.usePeriod;
	}

	/**
	 * @param usePeriod
	 *            the usePeriod to set
	 */
	public void setUsePeriod(String usePeriod) {
		this.usePeriod = usePeriod;
	}

	/**
	 * Gets cpn prod case cd.
	 *
	 * @return the cpn prod case cd
	 */
	public String getCpnProdCaseCd() {
		return cpnProdCaseCd;
	}

	/**
	 * Sets cpn prod case cd.
	 *
	 * @param cpnProdCaseCd
	 *            the cpn prod case cd
	 */
	public void setCpnProdCaseCd(String cpnProdCaseCd) {
		this.cpnProdCaseCd = cpnProdCaseCd;
	}
}
