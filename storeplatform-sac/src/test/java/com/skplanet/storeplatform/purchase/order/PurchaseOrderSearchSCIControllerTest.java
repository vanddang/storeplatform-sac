/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSearchSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchSktPaymentScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchSktPaymentScRes;
import com.skplanet.storeplatform.purchase.constant.PurchaseCDConstants;

/**
 * 
 * 구매SC - 구매 (조회) 연동 컨트롤러 테스트
 * 
 * Updated on : 2014. 1. 20. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/purchase/spring-test/context-test.xml" })
public class PurchaseOrderSearchSCIControllerTest {
	@Autowired
	private PurchaseOrderSearchSCI orderSearchSCI;

	/**
	 * 
	 * <pre>
	 * SKT 후불 결제 관련 조회 테스트.
	 * </pre>
	 */
	// @Test
	public void searchSktPayment() {
		SearchSktPaymentScReq req = new SearchSktPaymentScReq();
		req.setTenantId("S01");
		req.setUserKey("IM142100005724280201303121051");
		req.setDeviceKey("01046353524");

		/*
		 * CM011301 금액 CM011302 법인번호 CM011303 MDN
		 */
		req.setApplyUnitCd("CM011301");
		/*
		 * CM011407 당일 CM011408 당월 CM011409 당년 CM011410 전일 CM011411 전월 CM011412 전년
		 */
		req.setCondUnitCd("CM011408");
		/*
		 * CM011407 당일 CM011408 당월 CM011409 당년 CM011410 전일 CM011411 전월 CM011412 전년
		 */
		req.setCondPeriodUnitCd("CM011411");
		req.setCondPeriodValue("3");
		/*
		 * CM011501 건 CM011502 금액
		 */
		req.setCondClsfUnitCd("CM011501");

		// -----

		SearchSktPaymentScRes res = null;

		// SKT 후불 결제 제한 조건구분값 조회 (구매상세 테넌트상품그룹코드 조건)
		req.setTenantProdGrpCd("DP000201");
		res = this.orderSearchSCI.searchSktLimitCondDetail(req);
		assertNotNull(res);

		// SKT 후불 결제 제한 조건구분값 조회 (결제내역 기준)
		req.setTenantProdGrpCd(null);
		res = this.orderSearchSCI.searchSktLimitCondDetail(req);
		assertNotNull(res);

		// SKT 후불 결제 금액 조회 (구매상세 테넌트상품그룹코드 조건)
		req.setTenantProdGrpCd("DP000201");
		res = this.orderSearchSCI.searchSktAmountDetail(req);
		assertNotNull(res);

		// SKT 후불 결제 금액 조회 (결제내역 기준)
		req.setTenantProdGrpCd(null);
		res = this.orderSearchSCI.searchSktAmountDetail(req);
		assertNotNull(res);

		// SKT 후불 결제 선물수신 금액 조회
		req.setTenantProdGrpCd("DP000201");
		res = this.orderSearchSCI.searchSktRecvAmountDetail(req);
		assertNotNull(res);
	}

	/**
	 * 
	 * <pre>
	 * IAP SKT 후불 결제 관련 조회 테스트.
	 * </pre>
	 */
	// @Test
	public void searchSktPaymentForIap() {
		SearchSktPaymentScReq sciReq = new SearchSktPaymentScReq();

		sciReq.setTenantId("S01");
		sciReq.setUserKey("IW1314389220140617201229");
		sciReq.setDeviceKey("DE2014061720122981315042137");
		sciReq.setTenantProdGrpCd(null);
		sciReq.setApplyUnitCd("CM011301");
		sciReq.setCondUnitCd("CM011408");
		sciReq.setCondValue("1");
		sciReq.setSvcMangNo("7024073233");
		// String exceptTenantProdGrpCd = PurchaseCDConstants.TENANT_PRODUCT_GROUP_IAP;
		String exceptTenantProdGrpCd = PurchaseCDConstants.TENANT_PRODUCT_GROUP_SHOPPING;
		sciReq.setExceptTenantProdGrpCd(exceptTenantProdGrpCd);

		// (정책 체크 값) 정책 요소기준 조회
		// SearchSktPaymentScRes sciRes = this.orderSearchSCI.searchSktAmountDetail(sciReq);
		this.orderSearchSCI.searchSktAmountDetail(sciReq);
	}

	/**
	 * 
	 * <pre>
	 * 쇼핑 특가상품 구매된 건수 조회 테스트.
	 * </pre>
	 */
	// @Test
	public void searchShoppingSpecialPurchaseCount() {
		String tenantId = "S01";
		String userKey = "MBR01";
		String deviceKey = "MBR01_1";
		String specialCouponId = "SPC01";
		double specialCouponAmt = 100.0;

		SearchShoppingSpecialCountScReq req = new SearchShoppingSpecialCountScReq();
		req.setTenantId(tenantId);
		req.setUserKey(userKey);
		req.setDeviceKey(deviceKey);
		req.setSpecialCouponId(specialCouponId);
		req.setSpecialCouponAmt(specialCouponAmt);

		SearchShoppingSpecialCountScRes res = this.orderSearchSCI.searchShoppingSpecialCount(req);
		assertEquals(res.getDayCount(), 0);
		assertEquals(res.getDayUserCount(), 0);
		assertEquals(res.getMonthCount(), 0);
		assertEquals(res.getMonthUserCount(), 0);
	}
}
