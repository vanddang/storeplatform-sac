package com.skplanet.storeplatform.sac.purchase.purchase.precheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.sac.purchase.purchase.dummy.service.DummyAdminServiceImpl;
import com.skplanet.storeplatform.sac.purchase.purchase.vo.PrePurchaseInfo;

/**
 * 
 * 쇼핑 상품 구매한도 체크
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
public class ShoppingLimitChecker implements PurchasePreChecker {
	private static final Logger logger = LoggerFactory.getLogger(ShoppingLimitChecker.class);

	private static final String SHOPPING_CD = "";

	private final DummyAdminServiceImpl dummyService = new DummyAdminServiceImpl();

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
		// 유료결제 && 쇼핑상품
		return (purchaseInfo.getRealTotAmt() > 0 && SHOPPING_CD.equals(""));
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
		logger.debug("PRCHS,DUMMY,SHOPLIMIT,START," + purchaseInfo);

		this.dummyService.getShoppingLimit();

		logger.debug("PRCHS,DUMMY,SHOPLIMIT,END," + purchaseInfo);
		return true;
	}
}
