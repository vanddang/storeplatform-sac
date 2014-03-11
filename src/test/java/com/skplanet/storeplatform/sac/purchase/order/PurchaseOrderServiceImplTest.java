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

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReqProduct;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPolicyService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderService;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;

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

	CreatePurchaseSacReq createPurchaseReq;
	PurchaseOrderInfo purchaseInfo;

	/**
	 */
	@Before
	public void init() {
		this.createPurchaseReq = new CreatePurchaseSacReq();
		this.createPurchaseReq.setUserKey("IM142100005724280201303121051"); // 내부 회원 번호
		this.createPurchaseReq.setDeviceKey("01046353524"); // 내부 디바이스 ID
		this.createPurchaseReq.setPrchsReqPathCd("OR000401"); // 구매 요청 경로 코드
		this.createPurchaseReq.setPrchsCaseCd(PurchaseConstants.PRCHS_CASE_PURCHASE_CD); // 구매 유형 코드
		this.createPurchaseReq.setCurrencyCd("ko"); // 통화 코드
		this.createPurchaseReq.setTotAmt(0.0);
		this.createPurchaseReq.setClientIp("127.0.0.1"); // 클라이언트 IP
		this.createPurchaseReq.setNetworkTypeCd("DP004401"); // 네트워크 타입 코드
		this.createPurchaseReq.setMid("SKTstore01");
		this.createPurchaseReq.setAuthKey("6b6fa0a99e621f5b0fc9a77622c42b67e7a3317c");
		this.createPurchaseReq.setReturnUrl("http://localhost:8080/tenant/completePurchase");
		this.createPurchaseReq.setTenantProdGrpCd("OR006211DP01");

		List<CreatePurchaseSacReqProduct> productList = new ArrayList<CreatePurchaseSacReqProduct>();
		productList.add(new CreatePurchaseSacReqProduct("0000024129", 0.0, 1));
		// productList.add(new CreatePurchaseSacReqProduct("0000044820", 0.0, 1));
		this.createPurchaseReq.setProductList(productList);

		this.purchaseInfo = new PurchaseOrderInfo(this.createPurchaseReq);
		this.purchaseInfo.setTenantId("S01"); // 구매(선물발신) 테넌트 ID
		this.purchaseInfo.setSystemId("S01-01002"); // 구매(선물발신) 시스템 ID
		this.purchaseInfo.setUserKey(this.createPurchaseReq.getUserKey()); // 구매(선물발신) 내부 회원 번호
		this.purchaseInfo.setDeviceKey(this.createPurchaseReq.getDeviceKey()); // 구매(선물발신) 내부 디바이스 ID
		this.purchaseInfo.setRecvTenantId("S01"); // 선물수신 테넌트 ID
		this.purchaseInfo.setRecvUserKey(this.createPurchaseReq.getRecvUserKey()); // 선물수신 내부 회원 번호
		this.purchaseInfo.setRecvDeviceKey(this.createPurchaseReq.getRecvDeviceKey()); // 선물수신 내부 디바이스 ID
		this.purchaseInfo.setPrchsReqPathCd(this.createPurchaseReq.getPrchsReqPathCd()); // 구매 요청 경로 코드
		this.purchaseInfo.setMid(this.createPurchaseReq.getMid()); // 가맹점 ID
		this.purchaseInfo.setAuthKey(this.createPurchaseReq.getAuthKey()); // 가맹점 인증키
		this.purchaseInfo.setReturnUrl(this.createPurchaseReq.getReturnUrl()); // 결과처리 URL
		this.purchaseInfo.setCurrencyCd(this.createPurchaseReq.getCurrencyCd()); // 통화 코드
		this.purchaseInfo.setTotAmt(this.createPurchaseReq.getTotAmt()); // 총 결제 금액
		this.purchaseInfo.setClientIp(this.createPurchaseReq.getClientIp()); // 클라이언트 IP
		this.purchaseInfo.setNetworkTypeCd(this.createPurchaseReq.getNetworkTypeCd()); // 네트워크 타입 코드
		this.purchaseInfo.setPrchsCaseCd(this.createPurchaseReq.getPrchsCaseCd()); // 구매 유형 코드
		this.purchaseInfo.setTenantProdGrpCd(this.createPurchaseReq.getTenantProdGrpCd()); // 테넌트 상품 분류 코드

		String tenantId = this.purchaseInfo.getTenantId();

		List<PurchaseProduct> purchaseProductList = this.purchaseInfo.getPurchaseProductList();
		PurchaseProduct product = null;
		for (CreatePurchaseSacReqProduct reqProduct : productList) {
			product = new PurchaseProduct();

			product.setProdId("0000024129");
			product.setProdNm("G마켓");
			product.setProdAmt(0.0);
			product.setProdStatusCd("PD000403");
			product.setProdGrdCd("PD004401");
			product.setProdSprtYn("Y");
			product.setDrmYn("N");
			product.setUsePeriodUnitCd("PD00310");
			product.setUsePeriod(0);
			product.setAid("OA00024129");
			product.setSellerMbrNo("IF102158942020090723111912");
			product.setSellerNm("Seed");
			product.setSellerEmail("signtest@yopmail.com");
			product.setSellerTelno("0211112222");

			product.setProdQty(reqProduct.getProdQty());
			product.setResvCol01(reqProduct.getResvCol01());
			product.setResvCol02(reqProduct.getResvCol02());
			product.setResvCol03(reqProduct.getResvCol03());
			product.setResvCol04(reqProduct.getResvCol04());
			product.setResvCol05(reqProduct.getResvCol05());

			purchaseProductList.add(product);
		}

		PurchaseUserDevice user = new PurchaseUserDevice();
		user.setTenantId(tenantId);
		user.setUserKey(this.purchaseInfo.getUserKey());
		user.setUserId("testid01");
		user.setDeviceKey(this.purchaseInfo.getDeviceKey());
		user.setDeviceId("01046353524");
		user.setDeviceModelCd("SHV-E210S");
		user.setUserMainStatus("US010201"); // 사용자 메인 상태 코드. - US010201 정상 - US010202 자의탈퇴/직권탈퇴 - US010203 가가입 -
											// US010204 계정잠금/7일이용정지/30일이용정지/영구이용정지
		user.setAge(20);

		this.purchaseInfo.setPurchaseUser(user);

		if (StringUtils.equals(this.purchaseInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			user = new PurchaseUserDevice();
			user.setTenantId(tenantId);
			user.setUserKey(this.purchaseInfo.getRecvUserKey());
			user.setUserId("testid01");
			user.setDeviceKey(this.purchaseInfo.getRecvDeviceKey());
			user.setDeviceId("01046353524");
			user.setDeviceModelCd("SHV-E210S");
			user.setUserMainStatus("US010201");
			user.setAge(20);

			this.purchaseInfo.setReceiveUser(user);
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
		this.purchasePolicyService.checkUserPolicy(this.purchaseInfo);

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
		this.purchaseOrderService.createFreePurchase(this.purchaseInfo);
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
		this.purchaseOrderService.createReservedPurchase(this.purchaseInfo);
	}
}
