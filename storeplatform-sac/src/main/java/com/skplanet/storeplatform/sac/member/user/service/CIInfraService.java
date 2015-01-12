/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraDetailUserSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraDetailUserSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraListUserKeySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraListUserKeySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraSearchUserInfoSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraSearchUserInfoSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * CI Infra API 인터페이스
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
public interface CIInfraService {
	/**
	 * <pre>
	 * 회원키 조회.
	 * </pre>
	 * 
	 * @param request
	 *            CIInfraSearchUserInfoSacReq
	 * @return CIInfraSearchUserInfoSacRes
	 */
	public CIInfraSearchUserInfoSacRes searchUserInfo(SacRequestHeader requestHeader,
			CIInfraSearchUserInfoSacReq request);

	/**
	 * <pre>
	 * 회원키 리스트 조회.
	 * </pre>
	 * 
	 * @param request
	 *            CIInfraListUserKeySacReq
	 * @return CIInfraListUserKeySacRes
	 */
	public CIInfraListUserKeySacRes listUserKey(SacRequestHeader requestHeader, CIInfraListUserKeySacReq request);

	/**
	 * <pre>
	 * 회원 정보 상세 조회.
	 * </pre>
	 * 
	 * @param request
	 *            CIInfraDetailUserSacReq
	 * @return CIInfraDetailUserSacRes
	 */
	public CIInfraDetailUserSacRes detail(SacRequestHeader requestHeader, CIInfraDetailUserSacReq request);
}
