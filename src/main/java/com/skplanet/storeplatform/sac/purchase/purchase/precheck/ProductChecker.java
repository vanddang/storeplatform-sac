package com.skplanet.storeplatform.sac.purchase.purchase.precheck;

import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.sac.client.purchase.vo.purchase.PurchaseProduct;
import com.skplanet.storeplatform.sac.purchase.purchase.dummy.service.DummyProductServiceImpl;
import com.skplanet.storeplatform.sac.purchase.purchase.dummy.vo.DummyProduct;
import com.skplanet.storeplatform.sac.purchase.purchase.vo.PrePurchaseInfo;

/**
 * 
 * 상품 정보 체크
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
public class ProductChecker implements PurchasePreChecker {
	private final DummyProductServiceImpl dummyService = new DummyProductServiceImpl();

	/**
	 * <pre>
	 * 체크 대상 여부 확인.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크대상여부: true-체크대상, false-체크대상 아님
	 */
	@Override
	public boolean isTarget(PrePurchaseInfo purchaseInfo) {
		return true;
	}

	/**
	 * <pre>
	 * 구매 전처리 체크 및 필요한 정보 세팅.
	 * </pre>
	 * 
	 * @param purchaseInfo
	 *            구매요청 정보
	 * @return 체크진행 여부: true-체크진행 계속, false-체크진행 중지
	 */
	@Override
	public boolean checkAndSetInfo(PrePurchaseInfo purchaseInfo) {
		System.out.println("PRCHS,DUMMY,PRODUCT,START," + purchaseInfo);

		// 상품 정보 조회 : 테넌트ID, 상품ID, 디바이스모델코드
		// tenantId, String prodId, String deviceModelCd
		String tenantId = purchaseInfo.getTenantId();
		String deviceModelCd = purchaseInfo.getDeviceModelCd();

		List<DummyProduct> prodList = new ArrayList<DummyProduct>();
		for (PurchaseProduct product : purchaseInfo.getCreatePurchaseReq().getProductList()) {
			prodList.add(this.dummyService.getProductInfo(tenantId, product.getProdId(), deviceModelCd));
		}

		purchaseInfo.setProductList(prodList);

		System.out.println("PRCHS,DUMMY,PRODUCT,END," + purchaseInfo);
		return true;
	}
}
