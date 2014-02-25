/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import com.skplanet.storeplatform.sac.client.display.vo.personal.RecommandNewMemberProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.RecommandNewMemberProductRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 신규 가입 고객 추천 상품 조회.
 * 
 * Updated on : 2014. 2. 24. Updated by : 이석희, 아이에스 플러스.
 */
public interface RecommandNewMemberProductService {
	/**
	 * <pre>
	 * 자동 Update 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return PersonalAutoUpgradeRes
	 */
	public RecommandNewMemberProductRes recommandNewMemberProductList(SacRequestHeader header,
			RecommandNewMemberProductReq req);

}
