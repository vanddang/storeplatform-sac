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
 * Pay Planet 프로퍼티
 * 
 * Updated on : 2015. 3. 20. Updated by : 이승택, nTels.
 */
public class PpProperty extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String apiTypeCd;
	private String reqPathCd;
	private String prodInfo;
	private String exprDt;
	private String startDt;
	private String mid;
	private String authKey;
	private String encKey;
	private String url;
	private String sysDiv;
	private String cnclSysDiv;
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;

	// 조회용 추가
	private String prodId;
	private String parentProdId;
	private String tenantProdGrpCd;

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
	 * @return the apiTypeCd
	 */
	public String getApiTypeCd() {
		return this.apiTypeCd;
	}

	/**
	 * @param apiTypeCd
	 *            the apiTypeCd to set
	 */
	public void setApiTypeCd(String apiTypeCd) {
		this.apiTypeCd = apiTypeCd;
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
	 * @return the prodInfo
	 */
	public String getProdInfo() {
		return this.prodInfo;
	}

	/**
	 * @param prodInfo
	 *            the prodInfo to set
	 */
	public void setProdInfo(String prodInfo) {
		this.prodInfo = prodInfo;
	}

	/**
	 * @return the exprDt
	 */
	public String getExprDt() {
		return this.exprDt;
	}

	/**
	 * @param exprDt
	 *            the exprDt to set
	 */
	public void setExprDt(String exprDt) {
		this.exprDt = exprDt;
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
	 * @return the mid
	 */
	public String getMid() {
		return this.mid;
	}

	/**
	 * @param mid
	 *            the mid to set
	 */
	public void setMid(String mid) {
		this.mid = mid;
	}

	/**
	 * @return the authKey
	 */
	public String getAuthKey() {
		return this.authKey;
	}

	/**
	 * @param authKey
	 *            the authKey to set
	 */
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	/**
	 * @return the encKey
	 */
	public String getEncKey() {
		return this.encKey;
	}

	/**
	 * @param encKey
	 *            the encKey to set
	 */
	public void setEncKey(String encKey) {
		this.encKey = encKey;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the sysDiv
	 */
	public String getSysDiv() {
		return this.sysDiv;
	}

	/**
	 * @param sysDiv
	 *            the sysDiv to set
	 */
	public void setSysDiv(String sysDiv) {
		this.sysDiv = sysDiv;
	}

	/**
	 * @return the cnclSysDiv
	 */
	public String getCnclSysDiv() {
		return this.cnclSysDiv;
	}

	/**
	 * @param cnclSysDiv
	 *            the cnclSysDiv to set
	 */
	public void setCnclSysDiv(String cnclSysDiv) {
		this.cnclSysDiv = cnclSysDiv;
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

}
