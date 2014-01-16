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

import com.skplanet.storeplatform.external.client.icas.sci.ICASSCI;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PurchaseProduct;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseService;
import com.skplanet.storeplatform.sac.purchase.order.vo.PrePurchaseInfo;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PurchaseServiceImplTest {

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private ICASSCI icas;

	CreatePurchaseReq createPurchaseReq;
	PrePurchaseInfo purchaseInfo;

	@Before
	public void init() {
		this.createPurchaseReq = new CreatePurchaseReq();
		this.createPurchaseReq.setTenantId("S01"); // 테넌트 ID
		this.createPurchaseReq.setInsdUsermbrNo("TEST_MBR_NO_1"); // 내부 회원 번호
		this.createPurchaseReq.setInsdDeviceId("1"); // 내부 디바이스 ID
		this.createPurchaseReq.setPrchsReqPathCd("OR000401"); // 구매 요청 경로 코드
		this.createPurchaseReq.setCurrencyCd("ko"); // 통화 코드
		this.createPurchaseReq.setTotAmt(0.0);
		this.createPurchaseReq.setClientIp("127.0.0.1"); // 클라이언트 IP
		this.createPurchaseReq.setNetworkTypeCd("DP004401"); // 네트워크 타입 코드

		List<PurchaseProduct> productList = new ArrayList<PurchaseProduct>();
		productList.add(new PurchaseProduct("0000044819", 0.0, 1, "GRP-1"));
		productList.add(new PurchaseProduct("0000044820", 0.0, 1, "GRP-1"));
		this.createPurchaseReq.setProductList(productList);

		this.purchaseInfo = new PrePurchaseInfo(this.createPurchaseReq);
		this.purchaseInfo.setTenantId(this.createPurchaseReq.getTenantId());
		this.purchaseInfo.setSystemId(this.createPurchaseReq.getSystemId());
		this.purchaseInfo.setInsdUsermbrNo(this.createPurchaseReq.getInsdUsermbrNo());
		this.purchaseInfo.setInsdDeviceId(this.createPurchaseReq.getInsdDeviceId());
		this.purchaseInfo.setRecvTenantId(this.createPurchaseReq.getRecvTenantId());
		this.purchaseInfo.setRecvInsdUsermbrNo(this.createPurchaseReq.getRecvInsdUsermbrNo());
		this.purchaseInfo.setRecvInsdDeviceId(this.createPurchaseReq.getRecvInsdDeviceId());
	}

	@Test
	public void checkPurchase() throws Exception {
		this.purchaseService.checkPurchase(this.purchaseInfo);

	}

	/**
	 * 
	 * <pre>
	 * (무료상품)구매요청에 따른 구매내역 생성 테스트.
	 * </pre>
	 * 
	 * @throws Exception
	 */
	// @Test
	public void freePurchaseInsert() throws Exception {
		this.purchaseService.freePurchase(this.purchaseInfo);
	}
}
