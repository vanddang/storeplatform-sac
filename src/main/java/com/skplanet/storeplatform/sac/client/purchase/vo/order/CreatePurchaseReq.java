/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매요청 요청 VO
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class CreatePurchaseReq extends CommonInfo {
	private static final long serialVersionUID = 201401031L;

	private String tenantId; // 테넌트 ID
	private String systemId; // 시스템 ID
	private String insdUsermbrNo; // 내부 회원 번호
	private String insdDeviceId; // 내부 디바이스 ID
	private String prchsReqPathCd; // 구매 요청 경로 코드
	private String mid; // 가맹점 ID
	private String authKey; // 가맹점 인증키
	private String resultUrl; // 결과처리 URL
	private String currencyCd; // 통화 코드
	private Double totAmt; // 총 결제 금액
	private String clientIp; // 클라이언트 IP
	private String networkTypeCd; // 네트워크 타입 코드
	private String prchsCaseCd; // 구매 유형 코드
	private String recvTenantId; // 수신자 테넌트 ID
	private String recvInsdUsermbrNo; // 수신자 내부 회원 번호
	private String recvInsdDeviceId; // 수신자 내부 디바이스 ID
	private List<PurchaseProduct> productList; // 구매할 상품 리스트

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
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
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
	 * @return the resultUrl
	 */
	public String getResultUrl() {
		return this.resultUrl;
	}

	/**
	 * @param resultUrl
	 *            the resultUrl to set
	 */
	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
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
	 * @return the networkTypeCd
	 */
	public String getNetworkTypeCd() {
		return this.networkTypeCd;
	}

	/**
	 * @param networkTypeCd
	 *            the networkTypeCd to set
	 */
	public void setNetworkTypeCd(String networkTypeCd) {
		this.networkTypeCd = networkTypeCd;
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
	 * @return the recvTenantId
	 */
	public String getRecvTenantId() {
		return this.recvTenantId;
	}

	/**
	 * @param recvTenantId
	 *            the recvTenantId to set
	 */
	public void setRecvTenantId(String recvTenantId) {
		this.recvTenantId = recvTenantId;
	}

	/**
	 * @return the recvInsdUsermbrNo
	 */
	public String getRecvInsdUsermbrNo() {
		return this.recvInsdUsermbrNo;
	}

	/**
	 * @param recvInsdUsermbrNo
	 *            the recvInsdUsermbrNo to set
	 */
	public void setRecvInsdUsermbrNo(String recvInsdUsermbrNo) {
		this.recvInsdUsermbrNo = recvInsdUsermbrNo;
	}

	/**
	 * @return the recvInsdDeviceId
	 */
	public String getRecvInsdDeviceId() {
		return this.recvInsdDeviceId;
	}

	/**
	 * @param recvInsdDeviceId
	 *            the recvInsdDeviceId to set
	 */
	public void setRecvInsdDeviceId(String recvInsdDeviceId) {
		this.recvInsdDeviceId = recvInsdDeviceId;
	}

	/**
	 * @return the productList
	 */
	public List<PurchaseProduct> getProductList() {
		return this.productList;
	}

	/**
	 * @param productList
	 *            the productList to set
	 */
	public void setProductList(List<PurchaseProduct> productList) {
		this.productList = productList;
	}

}
