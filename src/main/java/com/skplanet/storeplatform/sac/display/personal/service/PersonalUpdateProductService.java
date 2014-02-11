/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateProductRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 업데이트 대상 목록 조회 Service
 * 
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스.
 */
public interface PersonalUpdateProductService {
	/**
	 * <pre>
	 * 업데이트 대상 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return PersonalUpdateProductRes
	 */
	public PersonalUpdateProductRes searchUpdateProductList(PersonalUpdateProductReq req, SacRequestHeader header);
}
