/**
 * 
 */
package com.skplanet.storeplatform.sac.member.miscellaneous.sci.service;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 기타 기능 내부메소드 호출 Service.
 * 
 * Updated on : 2014. 3. 11. Updated by : 강신완, 부르칸.
 */
public interface MiscellaneousSCIService {
	/**
	 * <pre>
	 * 2.3.8. 사용자별 정책 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            GetIndividualPolicyReq
	 * @return GetIndividualPolicyRes
	 */
	public GetIndividualPolicyRes getIndividualPolicy(SacRequestHeader header, GetIndividualPolicyReq req);
}
