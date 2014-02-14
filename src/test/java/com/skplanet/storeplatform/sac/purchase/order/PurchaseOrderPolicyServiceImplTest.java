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
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseDisplayPartService;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseDisplayPartServiceImpl;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyMember;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPolicyService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderService;
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

	/**
	 */
	@Before
	public void init() {
		this.createPurchaseReq = new CreatePurchaseSacReq();
		this.createPurchaseReq.setUserKey("MBR01"); // 내부 회원 번호
		this.createPurchaseReq.setDeviceKey("MBR01_1"); // 내부 디바이스 ID
		this.createPurchaseReq.setPrchsReqPathCd("OR000401"); // 구매 요청 경로 코드
		this.createPurchaseReq.setPrchsCaseCd(PurchaseConstants.PRCHS_CASE_PURCHASE_CD); // 구매 유형 코드
		this.createPurchaseReq.setCurrencyCd("ko"); // 통화 코드
		this.createPurchaseReq.setTotAmt(0.0);
		this.createPurchaseReq.setClientIp("127.0.0.1"); // 클라이언트 IP
		this.createPurchaseReq.setNetworkTypeCd("DP004401"); // 네트워크 타입 코드
		this.createPurchaseReq.setMid("MID01");
		this.createPurchaseReq.setAuthKey("MID01_KEY01");
		this.createPurchaseReq.setResultUrl("http://localhost:8080/tenant/completePurchase");

		List<CreatePurchaseSacReqProduct> productList = new ArrayList<CreatePurchaseSacReqProduct>();
		productList.add(new CreatePurchaseSacReqProduct("0000044819", "DP150101", 0.0, 1));
		productList.add(new CreatePurchaseSacReqProduct("0000044820", "DP150102", 0.0, 1));
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
		this.purchaseInfo.setResultUrl(this.createPurchaseReq.getResultUrl()); // 결과처리 URL
		this.purchaseInfo.setCurrencyCd(this.createPurchaseReq.getCurrencyCd()); // 통화 코드
		this.purchaseInfo.setTotAmt(this.createPurchaseReq.getTotAmt()); // 총 결제 금액
		this.purchaseInfo.setRealTotAmt(this.createPurchaseReq.getTotAmt()); // 총 결제 금액
		this.purchaseInfo.setClientIp(this.createPurchaseReq.getClientIp()); // 클라이언트 IP
		this.purchaseInfo.setNetworkTypeCd(this.createPurchaseReq.getNetworkTypeCd()); // 네트워크 타입 코드
		this.purchaseInfo.setPrchsCaseCd(this.createPurchaseReq.getPrchsCaseCd()); // 구매 유형 코드

		String tenantId = this.purchaseInfo.getTenantId();
		String systemId = this.purchaseInfo.getSystemId();
		String deviceModelCd = "SHV-E210S";
		PurchaseDisplayPartService displayPartService = new PurchaseDisplayPartServiceImpl();

		List<DummyProduct> dummyProductList = this.purchaseInfo.getProductList();
		DummyProduct product = null;
		for (CreatePurchaseSacReqProduct reqProduct : productList) {
			product = displayPartService.searchDummyProductDetail(tenantId, systemId, reqProduct.getProdId(),
					deviceModelCd);

			product.setProdQty(reqProduct.getProdQty());
			product.setTenantProdGrpCd(reqProduct.getTenantProdGrpCd());
			product.setResvCol01(reqProduct.getResvCol01());
			product.setResvCol02(reqProduct.getResvCol02());
			product.setResvCol03(reqProduct.getResvCol03());
			product.setResvCol04(reqProduct.getResvCol04());
			product.setResvCol05(reqProduct.getResvCol05());

			dummyProductList.add(product);
		}

		DummyMember user = new DummyMember();
		user.setTenantId(tenantId);
		user.setSystemId(systemId);
		user.setUserKey(this.purchaseInfo.getUserKey());
		user.setUserId("testid01");
		user.setDeviceKey(this.purchaseInfo.getDeviceKey());
		user.setDeviceId("01046353524");
		user.setDeviceModelCd("SHV-E210S");
		user.setUserTypeCd("US011501"); // 사용자 구분 코드 - US011501 : 기기 사용자 - US011502 : IDP 사용자 - US011503 : OneID 사용자 -
										// null : Tstore 회원 아님
		user.setUserStatusCd("US010701"); // 회원상태코드: US010701-정상, US010702-탈퇴, US010703-대기(가가입), US010704-가입,
										  // US010705-전환, US010706 : 탈퇴 - US010707-승인대기
		user.setAge(20);
		user.setbLogin(true);

		this.purchaseInfo.setPurchaseMember(user);

		if (StringUtils.equals(PurchaseConstants.PRCHS_CASE_GIFT_CD, this.purchaseInfo.getPrchsCaseCd())) {
			user = new DummyMember();
			user.setTenantId(tenantId);
			user.setSystemId(systemId);
			user.setUserKey(this.purchaseInfo.getRecvUserKey());
			user.setUserId("testid01");
			user.setDeviceKey(this.purchaseInfo.getRecvDeviceKey());
			user.setDeviceId("01046353524");
			user.setDeviceModelCd("SHV-E210S");
			user.setUserTypeCd("US011501"); // 사용자 구분 코드 - US011501 : 기기 사용자 - US011502 : IDP 사용자 - US011503 : OneID 사용자
											// -
											// null : Tstore 회원 아님
			user.setUserStatusCd("US010701"); // 회원상태코드: US010701-정상, US010702-탈퇴, US010703-대기(가가입), US010704-가입,
											  // US010705-전환, US010706 : 탈퇴 - US010707-승인대기
			user.setAge(20);
			user.setbLogin(true);

			this.purchaseInfo.setRecvMember(user);
		}

	}

	/**
	 * <pre>
	 * 테넌트 정책 체크.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void checkTenantPolicy() {
		this.purchasePolicyService.checkTenantPolicy(this.purchaseInfo);
	}
}
