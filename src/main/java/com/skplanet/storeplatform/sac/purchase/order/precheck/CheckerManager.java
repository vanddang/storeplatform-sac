package com.skplanet.storeplatform.sac.purchase.order.precheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 구매 전처리 체크 관리
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
public class CheckerManager {
	private final Map<String, List<PurchasePreChecker>> checkerMap = new HashMap<String, List<PurchasePreChecker>>();

	public CheckerManager() {
		//
		// ##### 순서 중요 #####
		//
		List<PurchasePreChecker> checkerList = new ArrayList<PurchasePreChecker>();
		checkerList.add(new MemberChecker()); // 회원 적합성
		checkerList.add(new ProductChecker()); // 상품 적합성
		checkerList.add(new ValidationChecker()); // 구매 적합성
		checkerList.add(new BlockChecker()); // 구매 차단 여부
		checkerList.add(new TestMdnChecker()); // Test MDN
		checkerList.add(new SktTestChecker()); // SKT 시험폰
		checkerList.add(new SktCorporateChecker()); // SKT 법인폰
		checkerList.add(new SkpCorporateChecker()); // SKP 법인폰
		checkerList.add(new SktLimitChecker()); // SKT 후불 구매한도
		checkerList.add(new ShoppingLimitChecker()); // 쇼핑 상품 구매한도
		checkerList.add(new GiftSendLimitChecker()); // 선물 발신 한도
		checkerList.add(new GiftRecvLimitChecker()); // 선물 수신 한도

		this.checkerMap.put("BASIC", checkerList);
	}

	public List<PurchasePreChecker> getCheckerList(String type) {
		if (StringUtils.isEmpty(type)) {
			type = "BASIC";
		}

		return this.checkerMap.get(type);
	}
}
