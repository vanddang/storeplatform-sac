package com.skplanet.storeplatform.sac.display.localsci.sci.service;

/**
 * SearchSellerKeyService Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 1. 21. Updated by : 이석희, 인크로스
 */

public interface SearchSellerKeyService {
	/**
	 * 
	 * <pre>
	 * App id 를 이용한 SellerKey 조회.
	 * </pre>
	 * 
	 * @param aid
	 *            aid
	 * @return String
	 */
	String searchSellerKeyForAid(String aid);
}
