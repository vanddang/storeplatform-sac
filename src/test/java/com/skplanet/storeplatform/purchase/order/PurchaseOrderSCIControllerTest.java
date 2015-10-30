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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.AutoPrchsMore;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.MakeFreePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.MakeFreePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.ShoppingCouponPublishInfo;
import com.skplanet.storeplatform.purchase.constant.PurchaseCDConstants;

/**
 * 
 * 구매SC - 구매 연동 컨트롤러 테스트
 * 
 * Updated on : 2014. 1. 20. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/purchase/spring-test/context-test.xml" })
public class PurchaseOrderSCIControllerTest {

	@Autowired
	private PurchaseOrderSCI orderSCI;

	private List<PrchsDtlMore> prchsDtlMoreList; // 구매상세, 구매, 추가정보
	private List<PrchsProdCnt> prchsProdCntList; // 구매건수
	private List<Payment> paymentList; // 결제
	private List<AutoPrchsMore> autoPrchsMoreList; // 자동구매
	private List<ShoppingCouponPublishInfo> shoppingCouponList; // 쇼핑 쿠폰

	/**
	 * 
	 * <pre>
	 * 테스트 기본 데이터 세팅.
	 * </pre>
	 */
	@Before
	public void init() {
		String tenantId = "S01";
		String systemId = "S01-01002";
		String userKey = "MBR01";
		String deviceKey = "MBR01_01";
		String prchsId = "TAK1234567890";
		String prchsDt = DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "yyyyMMddHHmmss");
		String tenantProdGrpCd = "OR006211DP03OR006311";
		String prodId = "P01";
		double prodAmt = 0.0;
		int prodQty = 1;

		// ------------------------------------------------------------------------
		// prchsDtlMoreList

		PrchsDtlMore prchsDtlMore = new PrchsDtlMore();
		prchsDtlMore.setTenantId(tenantId);
		prchsDtlMore.setSystemId(systemId);
		prchsDtlMore.setPrchsId(prchsId);
		prchsDtlMore.setPrchsDtlId(1);
		prchsDtlMore.setUseTenantId(tenantId);
		prchsDtlMore.setUseInsdUsermbrNo(userKey);
		prchsDtlMore.setUseInsdDeviceId(deviceKey);
		prchsDtlMore.setTenantProdGrpCd(tenantProdGrpCd);
		prchsDtlMore.setPrchsDt(prchsDt);
		prchsDtlMore.setTotAmt(0.0);
		prchsDtlMore.setPrchsReqPathCd(PurchaseCDConstants.PRCHS_REQ_PATH_MOBILE_CLIENT);
		prchsDtlMore.setClientIp("127.0.0.1");
		prchsDtlMore.setUseHidingYn("N");
		prchsDtlMore.setPrchsCaseCd(PurchaseCDConstants.PRCHS_CASE_PURCHASE_CD);
		prchsDtlMore.setRegId(systemId);
		prchsDtlMore.setUpdId(systemId);
		prchsDtlMore.setProdId(prodId);
		prchsDtlMore.setProdAmt(prodAmt);
		prchsDtlMore.setProdQty(prodQty);
		prchsDtlMore.setPrchsProdType(PurchaseCDConstants.PRCHS_PROD_TYPE_UNIT);
		prchsDtlMore.setUsePeriodUnitCd("PD00310");
		prchsDtlMore.setUsePeriod("0");
		prchsDtlMore.setDrmYn("N");
		prchsDtlMore.setAlarmYn("N");
		prchsDtlMore.setSendHidingYn("N");
		prchsDtlMore.setCurrencyCd("ko");
		prchsDtlMore.setNetworkTypeCd(PurchaseCDConstants.NETWORK_TYPE_ALL);

		this.prchsDtlMoreList = new ArrayList<PrchsDtlMore>();
		this.prchsDtlMoreList.add(prchsDtlMore);

		// ------------------------------------------------------------------------
		// prchsProdCntList

		PrchsProdCnt prchsProdCnt = new PrchsProdCnt();
		prchsProdCnt.setTenantId(tenantId);
		prchsProdCnt.setUseUserKey(userKey);
		prchsProdCnt.setUseDeviceKey(deviceKey);
		prchsProdCnt.setPrchsId(prchsId);
		prchsProdCnt.setPrchsDt(prchsDt);
		prchsProdCnt.setStatusCd("OR000301");
		prchsProdCnt.setProdGrpCd(tenantProdGrpCd);
		prchsProdCnt.setProdId(prodId);
		prchsProdCnt.setProdQty(prodQty);
		prchsProdCnt.setSprcProdYn("N");
		prchsProdCnt.setCntProcStatus("N");
		prchsProdCnt.setRegId(systemId);
		prchsProdCnt.setUpdId(systemId);

		this.prchsProdCntList = new ArrayList<PrchsProdCnt>();
		this.prchsProdCntList.add(prchsProdCnt);

		// ------------------------------------------------------------------------
		// paymentList

		Payment payment = new Payment();
		payment.setTenantId(tenantId);
		payment.setPrchsId(prchsId);
		payment.setPaymentDtlId(1);
		payment.setInsdUsermbrNo(userKey);
		payment.setInsdDeviceId(deviceKey);
		payment.setPrchsDt(prchsDt);
		payment.setTotAmt(1000.0);

		payment.setPaymentMtdCd("OR000605");
		payment.setPaymentAmt(1000.0);
		payment.setPaymentDt(prchsDt);
		payment.setStatusCd("OR000301");

		payment.setTid("TID001");

		payment.setRegId(systemId);
		payment.setUpdId(systemId);

		this.paymentList = new ArrayList<Payment>();
		this.paymentList.add(payment);

		// ------------------------------------------------------------------------
		// autoPrchsMoreList

		this.autoPrchsMoreList = new ArrayList<AutoPrchsMore>();

		// ------------------------------------------------------------------------
		// shoppingCouponList

		this.shoppingCouponList = new ArrayList<ShoppingCouponPublishInfo>();
	}

	/**
	 * 
	 * <pre>
	 * 구매예약 생성 테스트 - 유료구매(단건)
	 * </pre>
	 */
	@Test
	public void reservePurchase() {
		for (PrchsDtlMore prchsDtlMore : this.prchsDtlMoreList) {
			prchsDtlMore.setPrchsId(prchsDtlMore.getPrchsId() + (int) (Math.random() * 10000) + "R");
			prchsDtlMore.setTotAmt(1000.0);

			prchsDtlMore.setProdId(prchsDtlMore.getProdId() + (int) (Math.random() * 10000) + "R");
			prchsDtlMore.setProdAmt(1000.0);
		}

		ReservePurchaseScReq req = new ReservePurchaseScReq();
		req.setPrchsDtlMoreList(this.prchsDtlMoreList);

		ReservePurchaseScRes res = this.orderSCI.reservePurchase(req);

		assertEquals(res.getCount(), 1);
	}

	/**
	 * 
	 * <pre>
	 * 구매완료 생성 테스트 - 무료구매
	 * </pre>
	 */
	@Test
	public void makeFreePurchase() {
		for (PrchsDtlMore prchsDtlMore : this.prchsDtlMoreList) {
			prchsDtlMore.setPrchsId(prchsDtlMore.getPrchsId() + (int) (Math.random() * 10000) + "R");
		}
		for (PrchsProdCnt prchsProdCnt : this.prchsProdCntList) {
			prchsProdCnt.setProdId(prchsProdCnt.getProdId() + (int) (Math.random() * 10000) + "R");
		}

		MakeFreePurchaseScReq req = new MakeFreePurchaseScReq();
		req.setPrchsDtlMoreList(this.prchsDtlMoreList);
		req.setPrchsProdCntList(this.prchsProdCntList);

		MakeFreePurchaseScRes res = this.orderSCI.makeFreePurchase(req);

		assertEquals(res.getCount(), 1);
	}

	/**
	 * 
	 * <pre>
	 * 구매예약 ~ 구매확정&결제이력생성 테스트.
	 * </pre>
	 */
	@Test
	public void comfirmPurchase() {
		for (PrchsDtlMore prchsDtlMore : this.prchsDtlMoreList) {
			prchsDtlMore.setPrchsId(prchsDtlMore.getPrchsId() + (int) (Math.random() * 10000) + "C");
			prchsDtlMore.setTotAmt(1000.0);

			prchsDtlMore.setProdId(prchsDtlMore.getProdId() + (int) (Math.random() * 10000) + "C");
			prchsDtlMore.setProdAmt(1000.0);
		}

		PrchsDtlMore prchsDtlMore = this.prchsDtlMoreList.get(0);

		for (PrchsProdCnt prchsProdCnt : this.prchsProdCntList) {
			prchsProdCnt.setProdId(prchsDtlMore.getProdId());
		}
		for (Payment payment : this.paymentList) {
			payment.setPrchsId(prchsDtlMore.getPrchsId());
		}

		// 예약
		ReservePurchaseScReq req = new ReservePurchaseScReq();
		req.setPrchsDtlMoreList(this.prchsDtlMoreList);

		ReservePurchaseScRes res = this.orderSCI.reservePurchase(req);

		assertEquals(res.getCount(), 1);

		// 확정
		ConfirmPurchaseScReq reqConfirm = new ConfirmPurchaseScReq();
		reqConfirm.setTenantId(prchsDtlMore.getTenantId());
		reqConfirm.setSystemId(prchsDtlMore.getSystemId());
		reqConfirm.setPrchsId(prchsDtlMore.getPrchsId());
		// reqConfirm.setUseUserKey(prchsDtlMore.getUseInsdUsermbrNo());
		reqConfirm.setNetworkTypeCd(prchsDtlMore.getNetworkTypeCd());

		reqConfirm.setPrchsProdCntList(this.prchsProdCntList);
		reqConfirm.setPaymentList(this.paymentList);
		reqConfirm.setAutoPrchsMoreList(this.autoPrchsMoreList);
		reqConfirm.setShoppingCouponList(this.shoppingCouponList);

		ConfirmPurchaseScRes resConfirm = this.orderSCI.confirmPurchase(reqConfirm);

		assertEquals(resConfirm.getCount(), 1);

	}

}
