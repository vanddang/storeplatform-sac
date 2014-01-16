package com.skplanet.storeplatform.sac.purchase.order.precheck;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.sac.purchase.order.dummy.service.DummyAdminServiceImpl;
import com.skplanet.storeplatform.sac.purchase.order.vo.PrePurchaseInfo;

/**
 * 
 * 선물 발신 한도 체크
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
public class GiftSendLimitChecker implements PurchasePreChecker {
	private static final Logger logger = LoggerFactory.getLogger(GiftSendLimitChecker.class);
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
		// 유료결제 && 선물
		return (purchaseInfo.getRealTotAmt() > 0 && StringUtils.isNotEmpty(purchaseInfo.getCreatePurchaseReq()
				.getRecvTenantId()));
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
		logger.debug("PRCHS,DUMMY,SENDLIMIT,START," + purchaseInfo);

		this.dummyService.getGiftSendLimit();

		logger.debug("PRCHS,DUMMY,SENDLIMIT,END," + purchaseInfo);
		return true;
	}
}
