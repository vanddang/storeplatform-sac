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

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPolicyService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderService;
import com.skplanet.storeplatform.sac.purchase.order.vo.CheckPaymentPolicyParam;
import com.skplanet.storeplatform.sac.purchase.order.vo.CheckPaymentPolicyResult;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;

/**
 * 
 * 테넌트 정책 체크 서비스 테스트
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PurchaseOrderPolicyServiceImplTest {
	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@Autowired
	private PurchaseOrderPolicyService purchasePolicyService;

	CreatePurchaseSacReq createPurchaseReq;
	PurchaseOrderInfo purchaseInfo;

	CheckPaymentPolicyParam checkPaymentPolicyParam;

	@Before
	public void init() {
		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setTenantId("S01");
		tenantHeader.setSystemId("S01-01002");
		SacRequestHeader sacRequestHeader = new SacRequestHeader();
		sacRequestHeader.setTenantHeader(tenantHeader);
		RequestContextHolder.getRequestAttributes().setAttribute(SacRequestHeader.class.getName(), sacRequestHeader,
				RequestAttributes.SCOPE_REQUEST);

		String tenantId = tenantHeader.getTenantId();
		String useTenantId = tenantId;
		String checkSystemId = tenantId + "XXXX";

		String deviceId = "01046353524";
		String useDeviceId = "01046353524";

		boolean bGift = false;

		// 구매
		String useUserKey = "IM120000055266720140617110925";
		String useDeviceKey = "DE2014061711551312115042112";

		// 선물
		String sendUserKey = "IM120000055266720140617110925";
		String sendDeviceKey = "DE2014061711551312115042112";

		// 상품
		double totAmt = 5000.0;
		String tenantProdGrpCd = "OR006221DP28OR006311";

		// 통신사
		String telecom = "US001201"; // US001201-SKT, US001202-KT, US001203-U+

		// 상품 ID 별 정책 조회
		String checkProdId = "";

		// IAP 모상품 AID 별 정책 조회
		String checkParentProdId = "";

		// 쇼핑 타입 별 정책 조회
		String prodCaseCd = "";

		// 정액상품 타입 별 정책 조회
		String cmpxProdClsfCd = "";

		// SAP
		String marketDeviceKey = "";
		String deviceKeyAuth = "";

		// 자동결제
		String autoPrchsYn = "N";

		// S2S
		String s2sAutoYn = "N";
		String s2sYn = "N";

		this.checkPaymentPolicyParam = new CheckPaymentPolicyParam();
		this.checkPaymentPolicyParam.setTenantId(tenantId);
		this.checkPaymentPolicyParam.setSystemId(checkSystemId); // 구매인증 요청한 시스템ID
		this.checkPaymentPolicyParam.setDeviceId(deviceId);
		this.checkPaymentPolicyParam.setPaymentTotAmt(totAmt);
		this.checkPaymentPolicyParam.setTenantProdGrpCd(tenantProdGrpCd);
		this.checkPaymentPolicyParam.setTelecom(telecom);
		this.checkPaymentPolicyParam.setProdId(checkProdId);
		this.checkPaymentPolicyParam.setParentProdId(checkParentProdId);
		this.checkPaymentPolicyParam.setProdCaseCd(prodCaseCd);
		this.checkPaymentPolicyParam.setCmpxProdClsfCd(cmpxProdClsfCd);
		this.checkPaymentPolicyParam.setMarketDeviceKey(marketDeviceKey); // SAP
		this.checkPaymentPolicyParam.setDeviceKeyAuth(deviceKeyAuth); // SAP
		if (bGift) {
			this.checkPaymentPolicyParam.setUserKey(sendUserKey);
			this.checkPaymentPolicyParam.setDeviceKey(sendDeviceKey);
			this.checkPaymentPolicyParam.setRecvTenantId(useTenantId);
			this.checkPaymentPolicyParam.setRecvUserKey(useUserKey);
			this.checkPaymentPolicyParam.setRecvDeviceKey(useDeviceKey);
			this.checkPaymentPolicyParam.setRecvDeviceId(useDeviceId);
		} else {
			this.checkPaymentPolicyParam.setUserKey(useUserKey);
			this.checkPaymentPolicyParam.setDeviceKey(useDeviceKey);
		}
		this.checkPaymentPolicyParam.setAutoPrchs(StringUtils.equals(autoPrchsYn, "Y"));
		this.checkPaymentPolicyParam.setS2sAutoPrchs(StringUtils.equals(s2sAutoYn, "Y"));
		this.checkPaymentPolicyParam.setS2s(StringUtils.equals(s2sYn, "Y"));
	}

	/**
	 * <pre>
	 * 결제 정책 체크.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void checkTenantPolicy() {
		boolean bTest = false;

		CheckPaymentPolicyResult checkPaymentPolicyResult = null;

		// S01 / SKT / 구매 / 쇼핑
		checkPaymentPolicyResult = this.purchasePolicyService.checkPaymentPolicy(this.checkPaymentPolicyParam);
		checkPaymentPolicyResult.getPaymentAdjInfo(); // dummy 4 warning
		// System.out.println("RESULT\n" + checkPaymentPolicyResult);

		// S01 / KT / 구매 / 쇼핑
		this.checkPaymentPolicyParam.setTelecom("US001202");
		checkPaymentPolicyResult = this.purchasePolicyService.checkPaymentPolicy(this.checkPaymentPolicyParam);
		// System.out.println("RESULT\n" + checkPaymentPolicyResult);

		// S02 / KT / 구매 / 쇼핑 / 상품ID:S900017839
		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setTenantId("S02");
		tenantHeader.setSystemId("S02-01002");
		SacRequestHeader sacRequestHeader = new SacRequestHeader();
		sacRequestHeader.setTenantHeader(tenantHeader);
		RequestContextHolder.getRequestAttributes().setAttribute(SacRequestHeader.class.getName(), sacRequestHeader,
				RequestAttributes.SCOPE_REQUEST);

		this.checkPaymentPolicyParam.setTenantId(tenantHeader.getTenantId());
		this.checkPaymentPolicyParam.setSystemId(tenantHeader.getTenantId() + "XXXX");
		this.checkPaymentPolicyParam.setProdId("S900017839");

		checkPaymentPolicyResult = this.purchasePolicyService.checkPaymentPolicy(this.checkPaymentPolicyParam);
		// System.out.println("RESULT\n" + checkPaymentPolicyResult);

		this.checkPaymentPolicyParam.setProdId(null);

		if (bTest == false) {
			return;
		}

		// S03 / U+ / 구매 / IAP
		tenantHeader = new TenantHeader();
		tenantHeader.setTenantId("S03");
		tenantHeader.setSystemId("S03-01002");
		sacRequestHeader.setTenantHeader(tenantHeader);
		RequestContextHolder.getRequestAttributes().setAttribute(SacRequestHeader.class.getName(), sacRequestHeader,
				RequestAttributes.SCOPE_REQUEST);

		String tenantId = tenantHeader.getTenantId();
		String checkSystemId = tenantId + "XXXX";
		this.checkPaymentPolicyParam.setTenantId(tenantId);
		this.checkPaymentPolicyParam.setSystemId(checkSystemId); // 구매인증 요청한 시스템ID
		this.checkPaymentPolicyParam.setDeviceId("01022336370");
		this.checkPaymentPolicyParam.setPaymentTotAmt(10000);
		this.checkPaymentPolicyParam.setTenantProdGrpCd("OR006321DP01OR006311");
		this.checkPaymentPolicyParam.setTelecom("US001202");
		this.checkPaymentPolicyParam.setMarketDeviceKey("500059571797"); // SAP
		// this.checkPaymentPolicyParam.setDeviceKeyAuth("281oDicAcvPGxkPJN0IIRflE+of/Fhm7y46To7AIG2k="); // SAP
		this.checkPaymentPolicyParam.setUserKey("US201501291605070830010457");
		this.checkPaymentPolicyParam.setDeviceKey("DE2015012916050711115047157");
		checkPaymentPolicyResult = this.purchasePolicyService.checkPaymentPolicy(this.checkPaymentPolicyParam);
		// System.out.println("RESULT\n" + checkPaymentPolicyResult);

		// S03 / KT / 구매 / IAP
		this.checkPaymentPolicyParam.setTelecom("US001202");
		checkPaymentPolicyResult = this.purchasePolicyService.checkPaymentPolicy(this.checkPaymentPolicyParam);
		// System.out.println("RESULT\n" + checkPaymentPolicyResult);
	}

	// @Test
	public void getAvailablePaymethodAdjustInfo() {
		String tenantId = "S01";
		String tenantProdGrpCd = "OR006212DP18OR006311";
		String prodKindCd = "DP006303";
		String prodId = "OR006211DP01OR006311";
		String parentProdId = null;
		this.purchasePolicyService.getAvailablePaymethodAdjustInfo(tenantId, tenantProdGrpCd, prodKindCd, prodId,
				parentProdId);

	}
}
