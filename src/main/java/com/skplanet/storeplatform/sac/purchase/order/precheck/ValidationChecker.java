package com.skplanet.storeplatform.sac.purchase.order.precheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.sac.purchase.order.dummy.service.DummyPurchaseServiceImpl;
import com.skplanet.storeplatform.sac.purchase.order.vo.PrePurchaseInfo;

/**
 * 
 * 구매 적합성 체크
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
public class ValidationChecker implements PurchasePreChecker {
	private static final Logger logger = LoggerFactory.getLogger(ValidationChecker.class);

	private final DummyPurchaseServiceImpl dummyService = new DummyPurchaseServiceImpl();

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
		logger.debug("PRCHS,DUMMY,VALIDATION,START," + purchaseInfo);

		logger.debug("PRCHS,DUMMY,VALIDATION,END," + purchaseInfo);
		return true;
	}
}
