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
 * 자동결제 CommonVo
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class AutoPrchs extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String fstPrchsId;
	private Integer fstPrchsDtlId;
	private String insdUsermbrNo;
	private String insdDeviceId;
	private String prodId;
	private String statusCd;
	private String paymentStartDt;
	private String paymentEndDt;
	private String afterPaymentDt;
	private String reqPathCd;
	private String clientIp;
	private Integer prchsTme;
	private String closedDt;
	private String closedReasonCd;
	private String closedReqPathCd;
	private String lastPrchsId;
	private Integer lastPrchsDtlId;
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;
	private String procStatusCd;
	private String resvCol01;
	private String resvCol02;
	private String resvCol03;
	private String resvCol04;
	private String resvCol05;
	private String autoPaymentStatusCd;

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
	 * @return the fstPrchsId
	 */
	public String getFstPrchsId() {
		return this.fstPrchsId;
	}

	/**
	 * @param fstPrchsId
	 *            the fstPrchsId to set
	 */
	public void setFstPrchsId(String fstPrchsId) {
		this.fstPrchsId = fstPrchsId;
	}

	/**
	 * @return the fstPrchsDtlId
	 */
	public Integer getFstPrchsDtlId() {
		return this.fstPrchsDtlId;
	}

	/**
	 * @param fstPrchsDtlId
	 *            the fstPrchsDtlId to set
	 */
	public void setFstPrchsDtlId(Integer fstPrchsDtlId) {
		this.fstPrchsDtlId = fstPrchsDtlId;
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
	 * @return the paymentStartDt
	 */
	public String getPaymentStartDt() {
		return this.paymentStartDt;
	}

	/**
	 * @param paymentStartDt
	 *            the paymentStartDt to set
	 */
	public void setPaymentStartDt(String paymentStartDt) {
		this.paymentStartDt = paymentStartDt;
	}

	/**
	 * @return the paymentEndDt
	 */
	public String getPaymentEndDt() {
		return this.paymentEndDt;
	}

	/**
	 * @param paymentEndDt
	 *            the paymentEndDt to set
	 */
	public void setPaymentEndDt(String paymentEndDt) {
		this.paymentEndDt = paymentEndDt;
	}

	/**
	 * @return the afterPaymentDt
	 */
	public String getAfterPaymentDt() {
		return this.afterPaymentDt;
	}

	/**
	 * @param afterPaymentDt
	 *            the afterPaymentDt to set
	 */
	public void setAfterPaymentDt(String afterPaymentDt) {
		this.afterPaymentDt = afterPaymentDt;
	}

	/**
	 * @return the reqPathCd
	 */
	public String getReqPathCd() {
		return this.reqPathCd;
	}

	/**
	 * @param reqPathCd
	 *            the reqPathCd to set
	 */
	public void setReqPathCd(String reqPathCd) {
		this.reqPathCd = reqPathCd;
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
	 * @return the prchsTme
	 */
	public Integer getPrchsTme() {
		return this.prchsTme;
	}

	/**
	 * @param prchsTme
	 *            the prchsTme to set
	 */
	public void setPrchsTme(Integer prchsTme) {
		this.prchsTme = prchsTme;
	}

	/**
	 * @return the closedDt
	 */
	public String getClosedDt() {
		return this.closedDt;
	}

	/**
	 * @param closedDt
	 *            the closedDt to set
	 */
	public void setClosedDt(String closedDt) {
		this.closedDt = closedDt;
	}

	/**
	 * @return the closedReasonCd
	 */
	public String getClosedReasonCd() {
		return this.closedReasonCd;
	}

	/**
	 * @param closedReasonCd
	 *            the closedReasonCd to set
	 */
	public void setClosedReasonCd(String closedReasonCd) {
		this.closedReasonCd = closedReasonCd;
	}

	/**
	 * @return the closedReqPathCd
	 */
	public String getClosedReqPathCd() {
		return this.closedReqPathCd;
	}

	/**
	 * @param closedReqPathCd
	 *            the closedReqPathCd to set
	 */
	public void setClosedReqPathCd(String closedReqPathCd) {
		this.closedReqPathCd = closedReqPathCd;
	}

	/**
	 * @return the lastPrchsId
	 */
	public String getLastPrchsId() {
		return this.lastPrchsId;
	}

	/**
	 * @param lastPrchsId
	 *            the lastPrchsId to set
	 */
	public void setLastPrchsId(String lastPrchsId) {
		this.lastPrchsId = lastPrchsId;
	}

	/**
	 * @return the lastPrchsDtlId
	 */
	public Integer getLastPrchsDtlId() {
		return this.lastPrchsDtlId;
	}

	/**
	 * @param lastPrchsDtlId
	 *            the lastPrchsDtlId to set
	 */
	public void setLastPrchsDtlId(Integer lastPrchsDtlId) {
		this.lastPrchsDtlId = lastPrchsDtlId;
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
	 * @return the autoPaymentStatusCd
	 */
	public String getAutoPaymentStatusCd() {
		return this.autoPaymentStatusCd;
	}

	/**
	 * @param autoPaymentStatusCd
	 *            the autoPaymentStatusCd to set
	 */
	public void setAutoPaymentStatusCd(String autoPaymentStatusCd) {
		this.autoPaymentStatusCd = autoPaymentStatusCd;
	}

}
