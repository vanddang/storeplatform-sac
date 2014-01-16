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
 * [Prototype] 구매내역 조회 요청 VO
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class MyPagePurchaseHistoryReq extends CommonInfo {
	private static final long serialVersionUID = 201311131L;

	private String tenantId;
	private String mbrNo;
	private String deviceNo;
	private String prchsId;
	private String prchsDtlId;
	private String prodId;
	private String prodOwnType;
	private String startDt;
	private String endDt;
	private int startRow;
	private int endRow;
	private String prodGrpCd;
	private String target;
	private String prchsStatus;
	private String hiding;

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
	 * @return the mbrNo
	 */
	public String getMbrNo() {
		return this.mbrNo;
	}

	/**
	 * @param mbrNo
	 *            the mbrNo to set
	 */
	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}

	/**
	 * @return the deviceNo
	 */
	public String getDeviceNo() {
		return this.deviceNo;
	}

	/**
	 * @param deviceNo
	 *            the deviceNo to set
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
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
	 * @return the prodOwnType
	 */
	public String getProdOwnType() {
		return this.prodOwnType;
	}

	/**
	 * @param prodOwnType
	 *            the prodOwnType to set
	 */
	public void setProdOwnType(String prodOwnType) {
		this.prodOwnType = prodOwnType;
	}

	/**
	 * @return the startDt
	 */
	public String getStartDt() {
		return this.startDt;
	}

	/**
	 * @param startDt
	 *            the startDt to set
	 */
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	/**
	 * @return the endDt
	 */
	public String getEndDt() {
		return this.endDt;
	}

	/**
	 * @param endDt
	 *            the endDt to set
	 */
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	/**
	 * @return the startRow
	 */
	public int getStartRow() {
		return this.startRow;
	}

	/**
	 * @param startRow
	 *            the startRow to set
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	/**
	 * @return the endRow
	 */
	public int getEndRow() {
		return this.endRow;
	}

	/**
	 * @param endRow
	 *            the endRow to set
	 */
	public void setEndRow(int endRow) {
		this.endRow = endRow;
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
	 * @return the target
	 */
	public String getTarget() {
		return this.target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the prchsStatus
	 */
	public String getPrchsStatus() {
		return this.prchsStatus;
	}

	/**
	 * @param prchsStatus
	 *            the prchsStatus to set
	 */
	public void setPrchsStatus(String prchsStatus) {
		this.prchsStatus = prchsStatus;
	}

	/**
	 * @return the hiding
	 */
	public String getHiding() {
		return this.hiding;
	}

	/**
	 * @param hiding
	 *            the hiding to set
	 */
	public void setHiding(String hiding) {
		this.hiding = hiding;
	}

}
