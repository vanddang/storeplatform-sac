/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 17. Updated by : nTels_cswoo81, nTels.
 */
public class PrchsDtlSacParam extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String prchsId;
	private Integer prchsDtlId;
	private String useTenantId;
	private String useInsdUsermbrNo;
	private String useInsdDeviceId;
	private String prchsDt;
	private Double totAmt;
	private String sendInsdUsermbrNo;
	private String sendInsdDeviceId;
	private String recvDt;
	private String recvConfPathCd;
	private String prodId;
	private Double prodAmt;
	private Integer prodQty;
	private String tenantProdGrpCd;
	private String statusCd;
	private String useStartDt;
	private String useExprDt;
	private String cancelReqPathCd;
	private String cancelDt;
	private String cpnPublishCd;
	private String cpnDlvUrl;
	private String prchsCaseCd;
	private String prchsProdType;
	private String useFixrateProdId;
	private String cpnAddInfo;
	private String cpnBizOrderNo;
	private String cpnBizProdSeq;
	private String prchsResvDesc;

	private String appId;
	private String specialSaleCouponId;

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return this.appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the specialSaleCouponId
	 */
	public String getSpecialSaleCouponId() {
		return this.specialSaleCouponId;
	}

	/**
	 * @param specialSaleCouponId
	 *            the specialSaleCouponId to set
	 */
	public void setSpecialSaleCouponId(String specialSaleCouponId) {
		this.specialSaleCouponId = specialSaleCouponId;
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

}
