/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReqProduct;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseDisplayPartService;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseDisplayPartServiceImpl;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPolicyService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderService;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrder;

/**
 * 
 * 구매 처리 서비스 테스트
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PurchaseOrderServiceImplTest {

	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@Autowired
	private PurchaseOrderPolicyService purchasePolicyService;

	CreatePurchaseReq createPurchaseReq;
	PurchaseOrder purchaseInfo;

	/**
	 */
	@Before
	public void init() {
		this.createPurchaseReq = new CreatePurchaseReq();
		this.createPurchaseReq.setInsdUsermbrNo("TEST_MBR_NO_1"); // 내부 회원 번호
		this.createPurchaseReq.setInsdDeviceId("1"); // 내부 디바이스 ID
		this.createPurchaseReq.setPrchsReqPathCd("OR000401"); // 구매 요청 경로 코드
		this.createPurchaseReq.setPrchsCaseCd(PurchaseConstants.PRCHS_CASE_PURCHASE_CD); // 구매 유형 코드
		this.createPurchaseReq.setCurrencyCd("ko"); // 통화 코드
		this.createPurchaseReq.setTotAmt(0.0);
		this.createPurchaseReq.setClientIp("127.0.0.1"); // 클라이언트 IP
		this.createPurchaseReq.setNetworkTypeCd("DP004401"); // 네트워크 타입 코드
		this.createPurchaseReq.setMid("MID01");
		this.createPurchaseReq.setAuthKey("MID01_KEY01");
		this.createPurchaseReq.setResultUrl("http://localhost:8080/tenant/completePurchase");

		List<CreatePurchaseReqProduct> productList = new ArrayList<CreatePurchaseReqProduct>();
		productList.add(new CreatePurchaseReqProduct("0000044819", "DP000201", 0.0, 1));
		productList.add(new CreatePurchaseReqProduct("0000044820", "DP000201", 0.0, 1));
		this.createPurchaseReq.setProductList(productList);

		this.purchaseInfo = new PurchaseOrder(this.createPurchaseReq);
		this.purchaseInfo.setTenantId("S01"); // 구매(선물발신) 테넌트 ID
		this.purchaseInfo.setSystemId("S01-01002"); // 구매(선물발신) 시스템 ID
		this.purchaseInfo.setUserKey(this.createPurchaseReq.getInsdUsermbrNo()); // 구매(선물발신) 내부 회원 번호
		this.purchaseInfo.setDeviceKey(this.createPurchaseReq.getInsdDeviceId()); // 구매(선물발신) 내부 디바이스 ID
		this.purchaseInfo.setRecvTenantId("S01"); // 선물수신 테넌트 ID
		this.purchaseInfo.setRecvUserKey(this.createPurchaseReq.getRecvInsdUsermbrNo()); // 선물수신 내부 회원 번호
		this.purchaseInfo.setRecvDeviceKey(this.createPurchaseReq.getRecvInsdDeviceId()); // 선물수신 내부 디바이스 ID
		this.purchaseInfo.setPrchsReqPathCd(this.createPurchaseReq.getPrchsReqPathCd()); // 구매 요청 경로 코드
		this.purchaseInfo.setMid(this.createPurchaseReq.getMid()); // 가맹점 ID
		this.purchaseInfo.setAuthKey(this.createPurchaseReq.getAuthKey()); // 가맹점 인증키
		this.purchaseInfo.setResultUrl(this.createPurchaseReq.getResultUrl()); // 결과처리 URL
		this.purchaseInfo.setCurrencyCd(this.createPurchaseReq.getCurrencyCd()); // 통화 코드
		this.purchaseInfo.setTotAmt(this.createPurchaseReq.getTotAmt()); // 총 결제 금액
		this.purchaseInfo.setClientIp(this.createPurchaseReq.getClientIp()); // 클라이언트 IP
		this.purchaseInfo.setNetworkTypeCd(this.createPurchaseReq.getNetworkTypeCd()); // 네트워크 타입 코드
		this.purchaseInfo.setPrchsCaseCd(this.createPurchaseReq.getPrchsCaseCd()); // 구매 유형 코드

		String tenantId = this.purchaseInfo.getTenantId();
		String systemId = this.purchaseInfo.getSystemId();
		String deviceModelCd = "SHV-E210S";
		PurchaseDisplayPartService displayPartService = new PurchaseDisplayPartServiceImpl();

		List<DummyProduct> dummyProductList = this.purchaseInfo.getProductList();
		DummyProduct product = null;
		for (CreatePurchaseReqProduct reqProduct : productList) {
			product = displayPartService.searchDummyProductDetail(tenantId, systemId, reqProduct.getProdId(),
					deviceModelCd);

			dummyProductList.add(product);
		}
	}

	/**
	 * <pre>
	 * 구매 전처리 (제한정책 체크).
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void checkPurchase() throws Exception {
		this.purchasePolicyService.checkPolicy(this.purchaseInfo);

	}

	/**
	 * 
	 * <pre>
	 * (무료상품)구매요청에 따른 구매내역 생성 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void freePurchaseInsert() throws Exception {
		this.purchaseOrderService.freePurchase(this.purchaseInfo);
	}

	/**
	 * 
	 * <pre>
	 * 구매예약 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void reservePurchase() throws Exception {
		this.purchaseOrderService.reservePurchase(this.purchaseInfo);
	}
}
