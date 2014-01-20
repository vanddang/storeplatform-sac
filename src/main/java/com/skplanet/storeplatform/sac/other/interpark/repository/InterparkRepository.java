package com.skplanet.storeplatform.sac.other.interpark.repository;

import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyReq;
import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyRes;
import com.skplanet.storeplatform.external.client.interpark.vo.Purchase;

/**
 * 
 * Interpark Repository
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
public interface InterparkRepository {

	/**
	 * 
	 * <pre>
	 * Interpark 주문전송 EC연동.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 */
	void createOrderByEC(Purchase req);

	/**
	 * 
	 * <pre>
	 * Interpark 인증정보 EC연동.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @return AuthKeyRes
	 */
	AuthKeyRes getAuthKeyFromEC(AuthKeyReq req);

}
