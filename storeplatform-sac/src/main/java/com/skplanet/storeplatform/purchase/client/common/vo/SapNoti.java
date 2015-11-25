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

/**
 * 
 * SAP 결제완료Noti VO
 * 
 * Updated on : 2014. 11. 20. Updated by : 이승택, nTels.
 */
public class SapNoti extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String prchsId;
	private Integer prchsDtlId;
	private String statusCd;
	private String insdUsermbrNo;
	private String insdDeviceId;
	private String marketDeviceKey;
	private String deviceId;
	private String userEmail;
	private String prchsCaseCd;
	private String prchsDt;
	private String cancelDt;
	private Double totAmt;
	private String ppTid;
	private String paymentInfo;
	private String prodId;
	private String prodNm;
	private Double prodAmt;
	private String autoPrchsYn;
	private String autoPrchsPeriodUnitCd;
	private Integer autoPrchsPeriod;
	private String sellerComp;
	private String sellerNm;
	private String sellerEmail;
	private String bizRegNo;
	private String sellerAddr;
	private String sellerTelNo;
	private String addParamInfo;
	private String procStatusCd;
	private String procDesc;
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;

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
	 * @return the insdUsermbrNo
	 */
	public String getInsdUsermbrNo() {
		return this.insdUsermbrNo;
	}

	/**
	 * @param insdUsermbrNo
	 *            the insdUsermbrNo to set
	 */
	public void setInsdUsermbrNo(String insdUsermbrNo) {
		this.insdUsermbrNo = insdUsermbrNo;
	}

	/**
	 * @return the insdDeviceId
	 */
	public String getInsdDeviceId() {
		return this.insdDeviceId;
	}

	/**
	 * @param insdDeviceId
	 *            the insdDeviceId to set
	 */
	public void setInsdDeviceId(String insdDeviceId) {
		this.insdDeviceId = insdDeviceId;
	}

	/**
	 * @return the marketDeviceKey
	 */
	public String getMarketDeviceKey() {
		return this.marketDeviceKey;
	}

	/**
	 * @param marketDeviceKey
	 *            the marketDeviceKey to set
	 */
	public void setMarketDeviceKey(String marketDeviceKey) {
		this.marketDeviceKey = marketDeviceKey;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return this.userEmail;
	}

	/**
	 * @param userEmail
	 *            the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
	 * @return the ppTid
	 */
	public String getPpTid() {
		return this.ppTid;
	}

	/**
	 * @param ppTid
	 *            the ppTid to set
	 */
	public void setPpTid(String ppTid) {
		this.ppTid = ppTid;
	}

	/**
	 * @return the paymentInfo
	 */
	public String getPaymentInfo() {
		return this.paymentInfo;
	}

	/**
	 * @param paymentInfo
	 *            the paymentInfo to set
	 */
	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
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
	 * @return the prodNm
	 */
	public String getProdNm() {
		return this.prodNm;
	}

	/**
	 * @param prodNm
	 *            the prodNm to set
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
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
	 * @return the autoPrchsYn
	 */
	public String getAutoPrchsYn() {
		return this.autoPrchsYn;
	}

	/**
	 * @param autoPrchsYn
	 *            the autoPrchsYn to set
	 */
	public void setAutoPrchsYn(String autoPrchsYn) {
		this.autoPrchsYn = autoPrchsYn;
	}

	/**
	 * @return the autoPrchsPeriodUnitCd
	 */
	public String getAutoPrchsPeriodUnitCd() {
		return this.autoPrchsPeriodUnitCd;
	}

	/**
	 * @param autoPrchsPeriodUnitCd
	 *            the autoPrchsPeriodUnitCd to set
	 */
	public void setAutoPrchsPeriodUnitCd(String autoPrchsPeriodUnitCd) {
		this.autoPrchsPeriodUnitCd = autoPrchsPeriodUnitCd;
	}

	/**
	 * @return the autoPrchsPeriod
	 */
	public Integer getAutoPrchsPeriod() {
		return this.autoPrchsPeriod;
	}

	/**
	 * @param autoPrchsPeriod
	 *            the autoPrchsPeriod to set
	 */
	public void setAutoPrchsPeriod(Integer autoPrchsPeriod) {
		this.autoPrchsPeriod = autoPrchsPeriod;
	}

	/**
	 * @return the sellerComp
	 */
	public String getSellerComp() {
		return this.sellerComp;
	}

	/**
	 * @param sellerComp
	 *            the sellerComp to set
	 */
	public void setSellerComp(String sellerComp) {
		this.sellerComp = sellerComp;
	}

	/**
	 * @return the sellerNm
	 */
	public String getSellerNm() {
		return this.sellerNm;
	}

	/**
	 * @param sellerNm
	 *            the sellerNm to set
	 */
	public void setSellerNm(String sellerNm) {
		this.sellerNm = sellerNm;
	}

	/**
	 * @return the sellerEmail
	 */
	public String getSellerEmail() {
		return this.sellerEmail;
	}

	/**
	 * @param sellerEmail
	 *            the sellerEmail to set
	 */
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	/**
	 * @return the bizRegNo
	 */
	public String getBizRegNo() {
		return this.bizRegNo;
	}

	/**
	 * @param bizRegNo
	 *            the bizRegNo to set
	 */
	public void setBizRegNo(String bizRegNo) {
		this.bizRegNo = bizRegNo;
	}

	/**
	 * @return the sellerAddr
	 */
	public String getSellerAddr() {
		return this.sellerAddr;
	}

	/**
	 * @param sellerAddr
	 *            the sellerAddr to set
	 */
	public void setSellerAddr(String sellerAddr) {
		this.sellerAddr = sellerAddr;
	}

	/**
	 * @return the sellerTelNo
	 */
	public String getSellerTelNo() {
		return this.sellerTelNo;
	}

	/**
	 * @param sellerTelNo
	 *            the sellerTelNo to set
	 */
	public void setSellerTelNo(String sellerTelNo) {
		this.sellerTelNo = sellerTelNo;
	}

	/**
	 * @return the addParamInfo
	 */
	public String getAddParamInfo() {
		return this.addParamInfo;
	}

	/**
	 * @param addParamInfo
	 *            the addParamInfo to set
	 */
	public void setAddParamInfo(String addParamInfo) {
		this.addParamInfo = addParamInfo;
	}

	/**
	 * @return the procStatusCd
	 */
	public String getProcStatusCd() {
		return this.procStatusCd;
	}

	/**
	 * @param procStatusCd
	 *            the procStatusCd to set
	 */
	public void setProcStatusCd(String procStatusCd) {
		this.procStatusCd = procStatusCd;
	}

	/**
	 * @return the procDesc
	 */
	public String getProcDesc() {
		return this.procDesc;
	}

	/**
	 * @param procDesc
	 *            the procDesc to set
	 */
	public void setProcDesc(String procDesc) {
		this.procDesc = procDesc;
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

}
