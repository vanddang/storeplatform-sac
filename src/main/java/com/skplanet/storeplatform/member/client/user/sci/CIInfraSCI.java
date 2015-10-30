package com.skplanet.storeplatform.member.client.user.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraDetailUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraDetailUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraListUserKeyRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraListUserKeyResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraSearchUserInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraSearchUserInfoResponse;

/**
 * CI Infra에 제공할 회원정보를 조회하는 SCI
 * 
 * Updated on : 2014. 10. 8. Updated by : 반범진, SK 플래닛.
 */
@SCI
public interface CIInfraSCI {
	/**
	 * <pre>
	 * 회원키 조회.
	 * </pre>
	 * 
	 * @param request
	 *            CIInfraSearchUserInfoRequest
	 * @return CIInfraSearchUserInfoResponse
	 */
	public CIInfraSearchUserInfoResponse searchUserInfo(CIInfraSearchUserInfoRequest request);

	/**
	 * <pre>
	 * 회원키 리스트 조회.
	 * </pre>
	 * 
	 * @param request
	 *            CIInfraListUserKeyRequest
	 * @return CIInfraListUserKeyResponse
	 */
	public CIInfraListUserKeyResponse listUserKey(CIInfraListUserKeyRequest request);

	/**
	 * <pre>
	 * 회원 정보 상세 조회.
	 * </pre>
	 * 
	 * @param request
	 *            CIInfraDetailUserRequest
	 * @return CIInfraDetailUserResponse
	 */
	public CIInfraDetailUserResponse detail(CIInfraDetailUserRequest request);
}
