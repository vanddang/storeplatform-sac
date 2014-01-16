/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReq;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyMember;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;

/**
 * 
 * Dummy 구매요청 정보
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class PrePurchaseInfo extends CommonInfo {
	private static final long serialVersionUID = 201401101L;

	private final CreatePurchaseReq createPurchaseReq;

	private String resultType; // 결과 타입: payment-결제Page 요청진행, free-무료구매 완료
	private String paymentPageUrl; // 결제Page_URL
	private String paymentPageParam; // 결제Page_요청_파라미터

	private double realTotAmt; // 최종 결제 총 금액

	private DummyMember purchaseMember;
	private DummyMember recvMember;

	private String deviceModelCd;
	private List<DummyProduct> productList;

	private String tenantId;
	private String systemId;
	private String insdUsermbrNo;
	private String insdDeviceId;
	private String recvTenantId;
	private String recvInsdUsermbrNo;
	private String recvInsdDeviceId;

	private boolean bBlock; // 구매 차단 여부
	private boolean bTestMdn; // 스토어 테스트폰 여부
	private boolean bSktTest; // SKT 시험폰 여부
	private boolean bSktCorp; // SKT 법인폰 여부
	private boolean bSkpCorp; // SKP 법인폰 여부

	// ================================================================================================

	/**
	 * @param createPurchaseReq
	 *            createPurchaseReq
	 */
	public PrePurchaseInfo(CreatePurchaseReq createPurchaseReq) {
		this.createPurchaseReq = createPurchaseReq;
		this.realTotAmt = createPurchaseReq.getTotAmt();
	}

	/**
	 * @return the resultType
	 */
	public String getResultType() {
		return this.resultType;
	}

	/**
	 * @param resultType
	 *            the resultType to set
	 */
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	/**
	 * @return the paymentPageUrl
	 */
	public String getPaymentPageUrl() {
		return this.paymentPageUrl;
	}

	/**
	 * @param paymentPageUrl
	 *            the paymentPageUrl to set
	 */
	public void setPaymentPageUrl(String paymentPageUrl) {
		this.paymentPageUrl = paymentPageUrl;
	}

	/**
	 * @return the paymentPageParam
	 */
	public String getPaymentPageParam() {
		return this.paymentPageParam;
	}

	/**
	 * @param paymentPageParam
	 *            the paymentPageParam to set
	 */
	public void setPaymentPageParam(String paymentPageParam) {
		this.paymentPageParam = paymentPageParam;
	}

	/**
	 * @return the realTotAmt
	 */
	public double getRealTotAmt() {
		return this.realTotAmt;
	}

	/**
	 * @param realTotAmt
	 *            the realTotAmt to set
	 */
	public void setRealTotAmt(double realTotAmt) {
		this.realTotAmt = realTotAmt;
	}

	/**
	 * @return the purchaseMember
	 */
	public DummyMember getPurchaseMember() {
		return this.purchaseMember;
	}

	/**
	 * @param purchaseMember
	 *            the purchaseMember to set
	 */
	public void setPurchaseMember(DummyMember purchaseMember) {
		this.purchaseMember = purchaseMember;
	}

	/**
	 * @return the recvMember
	 */
	public DummyMember getRecvMember() {
		return this.recvMember;
	}

	/**
	 * @param recvMember
	 *            the recvMember to set
	 */
	public void setRecvMember(DummyMember recvMember) {
		this.recvMember = recvMember;
	}

	/**
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * @return the productList
	 */
	public List<DummyProduct> getProductList() {
		return this.productList;
	}

	/**
	 * @param productList
	 *            the productList to set
	 */
	public void setProductList(List<DummyProduct> productList) {
		this.productList = productList;
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
	 * @return the bBlock
	 */
	public boolean isbBlock() {
		return this.bBlock;
	}

	/**
	 * @param bBlock
	 *            the bBlock to set
	 */
	public void setbBlock(boolean bBlock) {
		this.bBlock = bBlock;
	}

	/**
	 * @return the bTestMdn
	 */
	public boolean isbTestMdn() {
		return this.bTestMdn;
	}

	/**
	 * @param bTestMdn
	 *            the bTestMdn to set
	 */
	public void setbTestMdn(boolean bTestMdn) {
		this.bTestMdn = bTestMdn;
	}

	/**
	 * @return the bSktTest
	 */
	public boolean isbSktTest() {
		return this.bSktTest;
	}

	/**
	 * @param bSktTest
	 *            the bSktTest to set
	 */
	public void setbSktTest(boolean bSktTest) {
		this.bSktTest = bSktTest;
	}

	/**
	 * @return the bSktCorp
	 */
	public boolean isbSktCorp() {
		return this.bSktCorp;
	}

	/**
	 * @param bSktCorp
	 *            the bSktCorp to set
	 */
	public void setbSktCorp(boolean bSktCorp) {
		this.bSktCorp = bSktCorp;
	}

	/**
	 * @return the bSkpCorp
	 */
	public boolean isbSkpCorp() {
		return this.bSkpCorp;
	}

	/**
	 * @param bSkpCorp
	 *            the bSkpCorp to set
	 */
	public void setbSkpCorp(boolean bSkpCorp) {
		this.bSkpCorp = bSkpCorp;
	}

	/**
	 * @return the createPurchaseReq
	 */
	public CreatePurchaseReq getCreatePurchaseReq() {
		return this.createPurchaseReq;
	}

	// ================================================================================================

}
