/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci.service;

import com.skplanet.storeplatform.sac.client.internal.member.user.vo.CreateGiftChargeInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.CreateGiftChargeInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.RemoveSSOCredentialSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.RemoveSSOCredentialSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 회원 내부메소드 호출 Service.
 * 
 * Updated on : 2015. 9. 22. Updated by : 반범진.
 */
public interface UserSCIService {

	/**
	 * <pre>
	 * 2.1.15.	회원 SSOCredential 정보 삭제.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param request
	 *            RemoveSSOCredentialSacReq
	 * @return RemoveSSOCredentialSacRes
	 */
	public RemoveSSOCredentialSacRes removeSSOCredential(SacRequestHeader sacHeader, RemoveSSOCredentialSacReq request);

	/**
	 * <pre>
	 * 2.1.16.	회원 상품권 충전 정보 등록.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param request
	 *            CreateGiftChargeInfoSacReq
	 * @return CreateGiftChargeInfoSacRes
	 */
	public CreateGiftChargeInfoSacRes createGiftChargeInfo(SacRequestHeader sacHeader,
			CreateGiftChargeInfoSacReq request);
}
