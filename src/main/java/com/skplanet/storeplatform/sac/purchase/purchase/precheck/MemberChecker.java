package com.skplanet.storeplatform.sac.purchase.purchase.precheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.sac.purchase.purchase.dummy.service.DummyMemberServiceImpl;
import com.skplanet.storeplatform.sac.purchase.purchase.dummy.vo.DummyMember;
import com.skplanet.storeplatform.sac.purchase.purchase.vo.PrePurchaseInfo;

/**
 * 
 * 회원 정보 체크
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
public class MemberChecker implements PurchasePreChecker {
	private static final Logger logger = LoggerFactory.getLogger(MemberChecker.class);

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
		logger.debug("PRCHS,DUMMY,MEMBER,START," + purchaseInfo);

		// 회원 정보 조회 : 테넌트ID, 내부회원NO, 디바이스ID
		DummyMember member = this.dummyService.getMemberInfo(purchaseInfo.getTenantId(),
				purchaseInfo.getInsdUsermbrNo(), purchaseInfo.getInsdDeviceId());

		logger.debug("DUMMY,MEMBER," + member);
		purchaseInfo.setPurchaseMember(member);

		// if( "선물발신코드".equals(purchaseInfo.getCreatePurchaseReq().getPrchsCaseCd()) ) {
		if (purchaseInfo.getCreatePurchaseReq().getPrchsCaseCd() != null) {
			member = this.dummyService.getMemberInfo(purchaseInfo.getRecvTenantId(),
					purchaseInfo.getRecvInsdUsermbrNo(), purchaseInfo.getRecvInsdDeviceId());

			logger.debug("DUMMY,MEMBER,RECV," + member);
			purchaseInfo.setRecvMember(member);
		}

		logger.debug("PRCHS,DUMMY,MEMBER,END," + purchaseInfo);
		return true;
	}
}
