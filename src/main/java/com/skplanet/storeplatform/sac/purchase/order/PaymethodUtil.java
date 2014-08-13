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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 
 * Pay Planet 과 결제수단코드 매핑
 * 
 * Updated on : 2014. 2. 28. Updated by : 이승택, nTels.
 */
public class PaymethodUtil {

	private static Map<String, String> convert2StoreMap;
	private static Map<String, String> convert2PayPlanetMap;

	static {
		convert2StoreMap = new HashMap<String, String>();
		convert2StoreMap.put(PurchaseConstants.PAYPLANET_PAYMENT_METHOD_SKT_CARRIER, "OR000605"); // SKT후불
		convert2StoreMap.put("12", "OR000602"); // 다날
		convert2StoreMap.put("13", "OR000601"); // 신용카드
		convert2StoreMap.put("14", "OR000622"); // PayPin
		convert2StoreMap.put("20", "OR000603"); // OCB
		convert2StoreMap.put("21", "OR000616"); // T membership
		convert2StoreMap.put("22", "OR000614"); // T money
		convert2StoreMap.put("23", "OR000604"); // 도토리
		convert2StoreMap.put("24", "OR000610"); // 문화상품권
		convert2StoreMap.put("25", "OR000607"); // T store Cash
		convert2StoreMap.put("26", "OR000606"); // Coupon
		convert2StoreMap.put("27", "OR000623"); // 게임캐쉬
		convert2StoreMap.put("28", "OR000624"); // 게임캐쉬 보너스 포인트
		convert2StoreMap.put("29", "OR000608"); // T store Point
		convert2StoreMap.put("30", "OR000625"); // T game pass

		convert2PayPlanetMap = new HashMap<String, String>();
		convert2PayPlanetMap.put("OR000605", PurchaseConstants.PAYPLANET_PAYMENT_METHOD_SKT_CARRIER); // SKT후불
		convert2PayPlanetMap.put("OR000602", "12"); // 다날
		convert2PayPlanetMap.put("OR000601", "13"); // 신용카드
		convert2PayPlanetMap.put("OR000622", "14"); // PayPin
		convert2PayPlanetMap.put("OR000603", "20"); // OCB
		convert2PayPlanetMap.put("OR000616", "21"); // T membership
		convert2PayPlanetMap.put("OR000614", "22"); // T money
		convert2PayPlanetMap.put("OR000604", "23"); // 도토리
		convert2PayPlanetMap.put("OR000610", "24"); // 문화상품권
		convert2PayPlanetMap.put("OR000607", "25"); // T store Cash
		convert2PayPlanetMap.put("OR000606", "26"); // Coupon
		convert2PayPlanetMap.put("OR000623", "27"); // 게임캐쉬
		convert2PayPlanetMap.put("OR000624", "28"); // 게임캐쉬 보너스 포인트
		convert2PayPlanetMap.put("OR000608", "29"); // T store Point
		convert2PayPlanetMap.put("OR000625", "30"); // T game pass
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
	 * @param ppCode
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
	 * @param ppCode
	 *            Store 결제수단코드
	 * @return Pay Planet 결제수단코드
	 */
	public static String convert2PayPlanetCodeWithoutPointCode(String storeCode) {
		String code = convert2PayPlanetMap.containsKey(storeCode) ? convert2PayPlanetMap.get(storeCode) : storeCode;

		// TAKTODO
		if (StringUtils.equals(code, "29")) {
			code = "25";
		} else if (StringUtils.equals(code, "28")) {
			code = "27";
		}

		return code;
	}
}
