package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
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
	public WithdrawRes executeWithdraw(SacRequestHeader requestHeader, WithdrawReq req);

	/**
	 * 회원존재여부 체크
	 * 
	 * @param SacRequestHeader
	 * @param WithdrawReq
	 * @return
	 * @throws Exception
	 */
	public SearchUserResponse searchUser(SacRequestHeader requestHeader, WithdrawReq req);

	/**
	 * OneId 연동
	 * 
	 * @param SacRequestHeader
	 * @param WithdrawReq
	 * @return
	 * @throws Exception
	 */
	public void oneIdUser(SacRequestHeader requestHeader, SearchUserResponse schUserRes, WithdrawReq req);

	/**
	 * IDP 연동 모바일회원(무선)
	 * 
	 * @param SacRequestHeader
	 * @param SearchUserResponse
	 *            , WithdrawReq
	 * @return
	 * @throws Exception
	 */
	public void idpMobileUser(SacRequestHeader requestHeader, SearchUserResponse schUserRes, WithdrawReq req);

	/**
	 * IDP 연동 아이디회원
	 * 
	 * @param SacRequestHeader
	 * @param SearchUserResponse
	 *            , WithdrawReq
	 * @return
	 * @throws Exception
	 */
	public void idpIdUser(SacRequestHeader requestHeader, SearchUserResponse schUserRes, WithdrawReq req);

}
