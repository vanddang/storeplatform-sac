package com.skplanet.storeplatform.sac.purchase.order;

import java.util.HashMap;
import java.util.Map;

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
		convert2StoreMap.put("11", "OR000605"); // SKT후불
		convert2StoreMap.put("12", "OR000602"); // 다날
		convert2StoreMap.put("13", "OR000601"); // 신용카드
		convert2StoreMap.put("14", "OR000605"); // PayPin
		convert2StoreMap.put("20", "OR000603"); // OCB
		convert2StoreMap.put("21", "OR000616"); // T membership
		convert2StoreMap.put("22", "OR000614"); // T money
		convert2StoreMap.put("23", "OR000604"); // 도토리
		convert2StoreMap.put("24", "OR000610"); // 문화상품권
		convert2StoreMap.put("25", "OR000607"); // T store Cash
		convert2StoreMap.put("26", "OR000606"); // Coupon

		convert2PayPlanetMap = new HashMap<String, String>();
		convert2PayPlanetMap.put("OR000605", "11"); // SKT후불
		convert2PayPlanetMap.put("OR000602", "12"); // 다날
		convert2PayPlanetMap.put("OR000601", "13"); // 신용카드
		convert2PayPlanetMap.put("OR000605", "14"); // PayPin
		convert2PayPlanetMap.put("OR000603", "20"); // OCB
		convert2PayPlanetMap.put("OR000616", "21"); // T membership
		convert2PayPlanetMap.put("OR000614", "22"); // T money
		convert2PayPlanetMap.put("OR000604", "23"); // 도토리
		convert2PayPlanetMap.put("OR000610", "24"); // 문화상품권
		convert2PayPlanetMap.put("OR000607", "25"); // T store Cash
		convert2PayPlanetMap.put("OR000606", "26"); // Coupon

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

}
