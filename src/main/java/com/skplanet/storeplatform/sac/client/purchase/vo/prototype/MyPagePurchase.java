/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.prototype;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [Prototype] 구매내역 조회 - 구매정보 VO
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class MyPagePurchase extends CommonInfo {
	private static final long serialVersionUID = 201311131L;

	private String prchsId;
	private String prchsDtlId;
	private String useTenantId;
	private String useMbrNo;
	private String useDeviceNo;
	private String sendTenantId;
	private String sendMbrNo;
	private String sendDeviceNo;
	private String prchsDt;
	private int totAmt;
	private int prodAmt;
	private String prodGrpCd;
	private String statusCd;
	private String useStartDt;
	private String useExprDt;
	private String dwldStartDt;
	private String dwldExprDt;
	private String cancelDt;
	private String recvDt;
	private String rePrchsPmtYn;
	private String hidingYn;
	private String expiredYn;

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
	public String getPrchsDtlId() {
		return this.prchsDtlId;
	}

	/**
	 * @param prchsDtlId
	 *            the prchsDtlId to set
	 */
	public void setPrchsDtlId(String prchsDtlId) {
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
	 * @return the useMbrNo
	 */
	public String getUseMbrNo() {
		return this.useMbrNo;
	}

	/**
	 * @param useMbrNo
	 *            the useMbrNo to set
	 */
	public void setUseMbrNo(String useMbrNo) {
		this.useMbrNo = useMbrNo;
	}

	/**
	 * @return the useDeviceNo
	 */
	public String getUseDeviceNo() {
		return this.useDeviceNo;
	}

	/**
	 * @param useDeviceNo
	 *            the useDeviceNo to set
	 */
	public void setUseDeviceNo(String useDeviceNo) {
		this.useDeviceNo = useDeviceNo;
	}

	/**
	 * @return the sendTenantId
	 */
	public String getSendTenantId() {
		return this.sendTenantId;
	}

	/**
	 * @param sendTenantId
	 *            the sendTenantId to set
	 */
	public void setSendTenantId(String sendTenantId) {
		this.sendTenantId = sendTenantId;
	}

	/**
	 * @return the sendMbrNo
	 */
	public String getSendMbrNo() {
		return this.sendMbrNo;
	}

	/**
	 * @param sendMbrNo
	 *            the sendMbrNo to set
	 */
	public void setSendMbrNo(String sendMbrNo) {
		this.sendMbrNo = sendMbrNo;
	}

	/**
	 * @return the sendDeviceNo
	 */
	public String getSendDeviceNo() {
		return this.sendDeviceNo;
	}

	/**
	 * @param sendDeviceNo
	 *            the sendDeviceNo to set
	 */
	public void setSendDeviceNo(String sendDeviceNo) {
		this.sendDeviceNo = sendDeviceNo;
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
	public int getTotAmt() {
		return this.totAmt;
	}

	/**
	 * @param totAmt
	 *            the totAmt to set
	 */
	public void setTotAmt(int totAmt) {
		this.totAmt = totAmt;
	}

	/**
	 * @return the prodAmt
	 */
	public int getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(int prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the prodGrpCd
	 */
	public String getProdGrpCd() {
		return this.prodGrpCd;
	}

	/**
	 * @param prodGrpCd
	 *            the prodGrpCd to set
	 */
	public void setProdGrpCd(String prodGrpCd) {
		this.prodGrpCd = prodGrpCd;
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
	 * @return the rePrchsPmtYn
	 */
	public String getRePrchsPmtYn() {
		return this.rePrchsPmtYn;
	}

	/**
	 * @param rePrchsPmtYn
	 *            the rePrchsPmtYn to set
	 */
	public void setRePrchsPmtYn(String rePrchsPmtYn) {
		this.rePrchsPmtYn = rePrchsPmtYn;
	}

	/**
	 * @return the hidingYn
	 */
	public String getHidingYn() {
		return this.hidingYn;
	}

	/**
	 * @param hidingYn
	 *            the hidingYn to set
	 */
	public void setHidingYn(String hidingYn) {
		this.hidingYn = hidingYn;
	}

	/**
	 * @return the expiredYn
	 */
	public String getExpiredYn() {
		return this.expiredYn;
	}

	/**
	 * @param expiredYn
	 *            the expiredYn to set
	 */
	public void setExpiredYn(String expiredYn) {
		this.expiredYn = expiredYn;
	}

}
