package com.skplanet.storeplatform.sac.purchase.purchase.precheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.sac.purchase.purchase.dummy.service.DummyMemberServiceImpl;
import com.skplanet.storeplatform.sac.purchase.purchase.vo.PrePurchaseInfo;

/**
 * 
 * 구매 차단 체크
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
public class BlockChecker implements PurchasePreChecker {
	private static final Logger logger = LoggerFactory.getLogger(BlockChecker.class);

	private final DummyMemberServiceImpl dummyService = new DummyMemberServiceImpl();

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
		// 유료결제
		return (purchaseInfo.getRealTotAmt() > 0);
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
		logger.debug("PRCHS,DUMMY,BLOCK,START," + purchaseInfo);

		// 구매 차단 여부 조회 : 테넌트ID, 내부회원NO, 디바이스ID
		if (this.dummyService.isBlock(purchaseInfo.getTenantId(), purchaseInfo.getInsdUsermbrNo(),
				purchaseInfo.getInsdDeviceId())) {

			purchaseInfo.setbBlock(true);
			return false;
		}

		logger.debug("PRCHS,DUMMY,BLOCK,END," + purchaseInfo);

		return true;
	}
}
