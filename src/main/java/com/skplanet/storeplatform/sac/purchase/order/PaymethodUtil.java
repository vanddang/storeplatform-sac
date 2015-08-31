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

import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Pay Planet 과 결제수단코드 매핑
 *
 * Updated on : 2014. 2. 28. Updated by : 이승택, nTels.
 */
public class PaymethodUtil extends PurchaseConstants
{

	private static Map<String, String> convert2StoreMap;
	private static Map<String, String> convert2PayPlanetMap;

	static {
		convert2StoreMap = new HashMap<String, String>();
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_SKT_CARRIER_11, PAYMENT_METHOD_SKT_CARRIER); // SKT후불
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_DANAL_12, PAYMENT_METHOD_DANAL); // 다날
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_CREDIT_CARD_13, PAYMENT_METHOD_CREDIT_CARD); // 신용카드
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_PAYPIN_14, PAYMENT_METHOD_PAYPIN); // PayPin
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_SYRUPPAY_17, PAYMENT_METHOD_SYRUP_PAY); // Syrup Pay
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_OCB_20, PAYMENT_METHOD_OCB); // OCB
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_TMEMBERSHIP_21, PAYMENT_METHOD_TMEMBERSHIP); // T membership
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_MOBILE_TMONEY_22, PAYMENT_METHOD_MOBILE_TMONEY); // T money
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_DOTORI_23, PAYMENT_METHOD_DOTORI); // 도토리
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_CULTURE_24, PAYMENT_METHOD_CULTURE); // 문화상품권
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_TSTORE_CASH_25, PAYMENT_METHOD_TSTORE_CASH); // T store Cash
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_COUPON_26, PAYMENT_METHOD_COUPON); // Coupon
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_GAMECASH_27, PAYMENT_METHOD_GAMECASH); // 게임캐쉬
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_GAMECASH_POINT_28, PAYMENT_METHOD_GAMECASH_POINT); // 게임캐쉬 보너스 포인트
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_TSTORE_POINT_29, PAYMENT_METHOD_TSTORE_POINT); // T store Point
		convert2StoreMap.put(PAYPLANET_PAYMENT_METHOD_TGAMEPASS_POINT_30, PAYMENT_METHOD_TGAMEPASS); // T game pass

		convert2PayPlanetMap = new HashMap<String, String>();
		convert2PayPlanetMap.put(PAYMENT_METHOD_SKT_CARRIER, PAYPLANET_PAYMENT_METHOD_SKT_CARRIER_11); // SKT후불
		convert2PayPlanetMap.put(PAYMENT_METHOD_DANAL, PAYPLANET_PAYMENT_METHOD_DANAL_12); // 다날
		convert2PayPlanetMap.put(PAYMENT_METHOD_CREDIT_CARD, PAYPLANET_PAYMENT_METHOD_CREDIT_CARD_13); // 신용카드
		convert2PayPlanetMap.put(PAYMENT_METHOD_PAYPIN, PAYPLANET_PAYMENT_METHOD_PAYPIN_14); // PayPin
		convert2PayPlanetMap.put(PAYMENT_METHOD_SYRUP_PAY, PAYPLANET_PAYMENT_METHOD_SYRUPPAY_17); // Syrup Pay
		convert2PayPlanetMap.put(PAYMENT_METHOD_OCB, PAYPLANET_PAYMENT_METHOD_OCB_20); // OCB
		convert2PayPlanetMap.put(PAYMENT_METHOD_TMEMBERSHIP, PAYPLANET_PAYMENT_METHOD_TMEMBERSHIP_21); // T membership
		convert2PayPlanetMap.put(PAYMENT_METHOD_MOBILE_TMONEY, PAYPLANET_PAYMENT_METHOD_MOBILE_TMONEY_22); // T money
		convert2PayPlanetMap.put(PAYMENT_METHOD_DOTORI, PAYPLANET_PAYMENT_METHOD_DOTORI_23); // 도토리
		convert2PayPlanetMap.put(PAYMENT_METHOD_CULTURE, PAYPLANET_PAYMENT_METHOD_CULTURE_24); // 문화상품권
		convert2PayPlanetMap.put(PAYMENT_METHOD_TSTORE_CASH, PAYPLANET_PAYMENT_METHOD_TSTORE_CASH_25); // T store Cash
		convert2PayPlanetMap.put(PAYMENT_METHOD_COUPON, PAYPLANET_PAYMENT_METHOD_COUPON_26); // Coupon
		convert2PayPlanetMap.put(PAYMENT_METHOD_GAMECASH, PAYPLANET_PAYMENT_METHOD_GAMECASH_27); // 게임캐쉬
		convert2PayPlanetMap.put(PAYMENT_METHOD_GAMECASH_POINT, PAYPLANET_PAYMENT_METHOD_GAMECASH_POINT_28); // 게임캐쉬 보너스 포인트
		convert2PayPlanetMap.put(PAYMENT_METHOD_TSTORE_POINT, PAYPLANET_PAYMENT_METHOD_TSTORE_POINT_29); // T store Point
		convert2PayPlanetMap.put(PAYMENT_METHOD_TGAMEPASS, PAYPLANET_PAYMENT_METHOD_TGAMEPASS_POINT_30); // T game pass
		convert2PayPlanetMap.put(PAYMENT_METHOD_SKT_TEST_DEVICE, PAYPLANET_PAYMENT_METHOD_SKT_CARRIER_11); // 시험폰: T멤버쉽 QA테스트 지원
	}

	/**
	 * 
	 * <pre>
	 * Pay Planet 결제수단코드를 Store 코드로 변경.
	 * </pre>
	 * 
	 * @param ppCode
	 *            Pay Planet 결제수단코드
	 * @return Store 결제수단코드
	 */
	public static String convert2StoreCode(String ppCode) {
		return convert2StoreMap.containsKey(ppCode) ? convert2StoreMap.get(ppCode) : ppCode;
	}

	/**
	 * 
	 * <pre>
	 * Store 결제수단코드를 Pay Planet 코드로 변경.
	 * </pre>
	 * 
	 * @param storeCode
	 *            Store 결제수단코드
	 * @return Pay Planet 결제수단코드
	 */
	public static String convert2PayPlanetCode(String storeCode) {
		return convert2PayPlanetMap.containsKey(storeCode) ? convert2PayPlanetMap.get(storeCode) : storeCode;
	}

	/**
	 * 
	 * <pre>
	 * Store 결제수단코드를 Pay Planet 코드로 변경 - 포인트 코드는 캐쉬 코드로.
	 * </pre>
	 * 
	 * @param storeCode
	 *            Store 결제수단코드
	 * @return Pay Planet 결제수단코드
	 */
	public static String convert2PayPlanetCodeWithoutPointCode(String storeCode) {
		String code = convert2PayPlanetMap.containsKey(storeCode) ? convert2PayPlanetMap.get(storeCode) : storeCode;

		// TAKTODO
		if (StringUtils.equals(code, PAYPLANET_PAYMENT_METHOD_TSTORE_POINT_29)) {
			code = PAYPLANET_PAYMENT_METHOD_TSTORE_CASH_25;
		} else if (StringUtils.equals(code, PAYPLANET_PAYMENT_METHOD_GAMECASH_POINT_28)) {
			code = PAYPLANET_PAYMENT_METHOD_GAMECASH_27;
		}

		return code;
	}
}
