package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 회원탈퇴 관련 인터페이스
 * 
 * Updated on : 2014. 1. 16. Updated by : 강신완, 부르칸.
 */
public interface UserWithdrawService {

	/**
	 * 회원탈퇴
	 * 
	 * @param SacRequestHeader
	 * @param WithdrawReq
	 * @return
	 * @throws Exception
	 */
	public WithdrawRes withdraw(SacRequestHeader requestHeader, WithdrawReq req);

	/**
	 * <pre>
	 * deviceId 삭제처리
	 * - 모바일 전용회원 인증 시에 SC는 회원이고, IDP는 비회원인 경우 deviceId를 삭제처리 하기 위해 호출한다.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param deviceId
	 *            String
	 * @param userAuthKey
	 *            String
	 */
	public void removeDevice(SacRequestHeader requestHeader, String deviceId);

	/**
	 * <pre>
	 * 아이디 삭제처리.
	 * - 아이디 기반 인증시에 IDP 비회원인 경우 아이디 탈퇴처리를 위해 호출한다.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param userId
	 *            String
	 */
	public void removeUser(SacRequestHeader requestHeader, String userId);
}
